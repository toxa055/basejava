package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        writeSimpleTextSection(dos, resume, sectionType);
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        writeListSection(dos, resume, sectionType);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeOrganizationSection(dos, resume, sectionType);
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
                SectionType sectionType = readSectionType(dis);
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(sectionType, readSimpleTextSection(dis));
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        resume.addSection(sectionType, readListSection(dis));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        resume.addSection(sectionType, readOrganizationSection(dis));
                        break;
                    default:
                        break;
                }
            }
            return resume;
        }
    }

    private <T> void writeEachElement(DataOutputStream dos, Collection<T> collection, Performer<T> performer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            performer.perform(t);
        }
    }

    private SectionType readSectionType(DataInputStream dis) throws IOException {
        return SectionType.valueOf(dis.readUTF());
    }

    private void writeSimpleTextSection(DataOutputStream dos, Resume resume, SectionType st) throws IOException {
        dos.writeUTF(st.name());
        dos.writeUTF(((SimpleTextSection) resume.getSection(st)).getDescription());
    }

    private SimpleTextSection readSimpleTextSection(DataInputStream dis) throws IOException {
        return new SimpleTextSection(dis.readUTF());
    }

    private void writeListSection(DataOutputStream dos, Resume resume, SectionType st) throws IOException {
        List<String> list = ((ListSection) resume.getSection(st)).getItems();
        dos.writeUTF(st.name());
        writeEachElement(dos, list, dos::writeUTF);
    }

    private ListSection readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        return new ListSection(list);
    }

    private void writeOrganizationSection(DataOutputStream dos, Resume resume, SectionType st) throws IOException {
        List<Organization> organizations = ((OrganizationSection) resume.getSection(st)).getOrganizations();
        dos.writeUTF(st.name());
        writeEachElement(dos, organizations, org -> {
            String url = org.getHomepage().getUrl();
            dos.writeUTF(org.getHomepage().getName());
            dos.writeUTF(url == null ? "null" : url);
            writeEachElement(dos, org.getPositions(), pos -> {
                String description = pos.getDescription();
                dos.writeInt(pos.getStartDate().getYear());
                dos.writeUTF(pos.getStartDate().getMonth().name());
                dos.writeInt(pos.getEndDate().getYear());
                dos.writeUTF(pos.getEndDate().getMonth().name());
                dos.writeUTF(pos.getTitle());
                dos.writeUTF(description == null ? "null" : description);
            });
        });
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<Organization> organizations = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String name = dis.readUTF();
            String url = dis.readUTF();
            int count = dis.readInt();
            List<Organization.Position> positions = new ArrayList<>(count);
            if (url.equals("null")) {
                url = null;
            }
            for (int j = 0; j < count; j++) {
                int startYear = dis.readInt();
                Month startMonth = Month.valueOf(dis.readUTF());
                int endYear = dis.readInt();
                Month endMonth = Month.valueOf(dis.readUTF());
                String title = dis.readUTF();
                String description = dis.readUTF();
                if (description.equals("null")) {
                    description = null;
                }
                positions.add(new Organization.Position(startYear, startMonth, endYear, endMonth, title, description));
            }
            organizations.add(new Organization(new Link(name, url), positions));
        }
        return new OrganizationSection(organizations);
    }
}