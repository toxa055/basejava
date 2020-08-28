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
            Map<ContactType, String> contacts = resume.getContacts();
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeEachElement(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeEachElement(dos, sections.entrySet(), entry -> {
                SectionType sectionType = entry.getKey();
                String name = sectionType.name();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(name);
                        dos.writeUTF(((SimpleTextSection) resume.getSection(sectionType)).getDescription());
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        List<String> list = ((ListSection) resume.getSection(sectionType)).getItems();
                        dos.writeUTF(name);
                        writeEachElement(dos, list, dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = ((OrganizationSection) resume.getSection(sectionType)).getOrganizations();
                        dos.writeUTF(name);
                        writeEachElement(dos, organizations, org -> {
                            String url = org.getHomepage().getUrl();
                            dos.writeUTF(org.getHomepage().getName());
                            dos.writeUTF(url == null ? "null" : url);
                            writeEachElement(dos, org.getPositions(), pos -> {
                                String description = pos.getDescription();
                                writeDate(dos, pos.getStartDate());
                                writeDate(dos, pos.getEndDate());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(description == null ? "null" : description);
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
            readEachElement(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });

            readEachElement(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new SimpleTextSection(dis.readUTF()));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        List<String> list = new ArrayList<>();
                        readEachElement(dis, () -> list.add(dis.readUTF()));
                        resume.addSection(sectionType, new ListSection(list));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> organizations = new ArrayList<>();
                        readEachElement(dis, () -> {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            if (url.equals("null")) {
                                url = null;
                            }
                            List<Organization.Position> positions = new ArrayList<>();
                            readEachElement(dis, () -> {
                                LocalDate startDate = readDate(dis);
                                LocalDate endDate = readDate(dis);
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("null")) {
                                    description = null;
                                }
                                positions.add(new Organization.Position(startDate, endDate, title, description));
                            });
                            organizations.add(new Organization(new Link(name, url), positions));
                        });
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                        break;
                    default:
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void writeEachElement(DataOutputStream dos, Collection<T> collection, DataWriter<T> dw) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            dw.perform(t);
        }
    }

    private void readEachElement(DataInputStream dis, DataReader dr) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            dr.perform();
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeUTF(date.getMonth().name());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return DateUtil.of(dis.readInt(), Month.valueOf(dis.readUTF()));
    }
}