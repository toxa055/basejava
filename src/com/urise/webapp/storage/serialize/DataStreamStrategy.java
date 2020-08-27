package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

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
                                LocalDate startDate = pos.getStartDate();
                                LocalDate endDate = pos.getEndDate();
                                dos.writeInt(startDate.getYear());
                                dos.writeUTF(startDate.getMonth().name());
                                dos.writeInt(endDate.getYear());
                                dos.writeUTF(endDate.getMonth().name());
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
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, new SimpleTextSection(dis.readUTF()));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        resume.addSection(sectionType, new ListSection(readEachElement(dis, dis::readUTF)));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        resume.addSection(sectionType, new OrganizationSection(readEachElement(dis, () -> {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            if (url.equals("null")) {
                                url = null;
                            }
                            return new Organization(new Link(name, url), readEachElement(dis, () -> {
                                int startYear = dis.readInt();
                                Month startMonth = Month.valueOf(dis.readUTF());
                                int endYear = dis.readInt();
                                Month endMonth = Month.valueOf(dis.readUTF());
                                String title = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("null")) {
                                    description = null;
                                }
                                return new Organization.Position(startYear, startMonth, endYear, endMonth, title, description);
                            }));
                        })));
                        break;
                    default:
                        break;
                }
            }
            return resume;
        }
    }

    private <T> void writeEachElement(DataOutputStream dos, Collection<T> collection, DataWriter<T> dw) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            dw.perform(t);
        }
    }

    private <T> List<T> readEachElement(DataInputStream dis, DataReader<T> dr) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(dr.perform());
        }
        return list;
    }
}