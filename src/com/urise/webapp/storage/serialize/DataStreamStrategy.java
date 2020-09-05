package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class DataStreamStrategy implements Strategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeEachElement(dos, resume.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeEachElement(dos, resume.getSections().entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((SimpleTextSection) section).getDescription());
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        writeEachElement(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeEachElement(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                            dos.writeUTF(org.getHomepage().getName());
                            dos.writeUTF(org.getHomepage().getUrl());
                            writeEachElement(dos, org.getPositions(), pos -> {
                                writeDate(dos, pos.getStartDate());
                                writeDate(dos, pos.getEndDate());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            });
                        });
                        break;
                    default:
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            readEachElement(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readEachElement(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private <T> void writeEachElement(DataOutputStream dos, Collection<T> collection, DataWriter<T> dw) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            dw.write(t);
        }
    }

    private void readEachElement(DataInputStream dis, DataPerformer dp) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            dp.perform();
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new SimpleTextSection(dis.readUTF());
            case QUALIFICATIONS:
            case ACHIEVEMENT:
                return new ListSection(readList(dis, dis::readUTF));
            case EDUCATION:
            case EXPERIENCE:
                return new OrganizationSection(readList(dis, () ->
                        new Organization(new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () ->
                                        new Organization.Position(readDate(dis), readDate(dis),
                                                dis.readUTF(), dis.readUTF())
                                ))
                ));
            default:
                throw new IllegalStateException();
        }
    }

    private <T> List<T> readList(DataInputStream dis, DataReader<T> dr) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(dr.read());
        }
        return list;
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeUTF(date.getMonth().name());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.valueOf(dis.readUTF()));
    }

    private interface DataReader<T> {
        T read() throws IOException;
    }

    private interface DataWriter<T> {
        void write(T t) throws IOException;
    }

    private interface DataPerformer {
        void perform() throws IOException;
    }
}