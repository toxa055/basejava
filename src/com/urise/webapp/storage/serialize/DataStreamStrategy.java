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
            dos.writeInt(contacts.size());
            forEach(contacts.entrySet(),
                    x -> dos.writeUTF(x.getKey().name()),
                    x -> dos.writeUTF(x.getValue()));

            dos.writeInt(sections.size());
            forEach(sections.entrySet(), x ->
            {
                SectionType sectionType = x.getKey();
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

    private static <T> void forEach(Collection<T> collection, Performer<T>... performer) throws IOException {
        for (T t : collection) {
            for (Performer<T> p : performer)
                p.perform(t);
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
        dos.writeInt(list.size());
        forEach(list, dos::writeUTF);
    }

    private ListSection readListSection(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(dis.readUTF());
        }
        return new ListSection(list);
    }

    private void writeOrganizationSection(DataOutputStream dos, Resume resume, SectionType st) throws IOException {
        List<Organization> organizations = ((OrganizationSection) resume.getSection(st)).getOrganizations();
        dos.writeUTF(st.name());
        dos.writeInt(organizations.size());
        forEach(organizations, x -> writeOrganization(dos, x));
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            organizations.add(readOrganization(dis));
        }
        return new OrganizationSection(organizations);
    }

    private void writeOrganization(DataOutputStream dos, Organization org) throws IOException {
        writeLink(dos, org.getHomepage());
        writePositions(dos, org.getPositions());
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        return new Organization(readLink(dis), readPositions(dis));
    }

    private void writeLink(DataOutputStream dos, Link link) throws IOException {
        String url = link.getUrl();
        dos.writeUTF(link.getName());
        dos.writeUTF(url == null ? "null" : url);
    }

    private Link readLink(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String url = dis.readUTF();
        if (url.equals("null")) {
            url = null;
        }
        return new Link(name, url);
    }

    private void writePositions(DataOutputStream dos, List<Organization.Position> positions) throws IOException {
        dos.writeInt(positions.size());
        forEach(positions,
                x -> dos.writeInt(x.getStartDate().getYear()),
                x -> dos.writeUTF(x.getStartDate().getMonth().name()),
                x -> dos.writeInt(x.getEndDate().getYear()),
                x -> dos.writeUTF(x.getEndDate().getMonth().name()),
                x -> dos.writeUTF(x.getTitle()),
                x -> dos.writeUTF(x.getDescription() == null ? "null" : x.getDescription()));
    }

    private List<Organization.Position> readPositions(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
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
        return positions;
    }
}
