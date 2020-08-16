package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStrategy implements Strategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            writeSimpleTextSection(dos, resume, SectionType.PERSONAL);
            writeSimpleTextSection(dos, resume, SectionType.OBJECTIVE);

            writeListSection(dos, resume, SectionType.QUALIFICATIONS);
            writeListSection(dos, resume, SectionType.ACHIEVEMENT);

            writeOrganizationSection(dos, resume, SectionType.EDUCATION);
            writeOrganizationSection(dos, resume, SectionType.EXPERIENCE);
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

            resume.addSection(readSectionType(dis), readSimpleTextSection(dis));
            resume.addSection(readSectionType(dis), readSimpleTextSection(dis));

            resume.addSection(readSectionType(dis), readListSection(dis));
            resume.addSection(readSectionType(dis), readListSection(dis));

            resume.addSection(readSectionType(dis), readOrganizationSection(dis));
            resume.addSection(readSectionType(dis), readOrganizationSection(dis));

            return resume;
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
        for (String str : list) {
            dos.writeUTF(str);
        }
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
        for (Organization org : organizations) {
            writeLink(dos, org.getHomepage());
            writePositions(dos, org.getPositions());
        }
    }

    private OrganizationSection readOrganizationSection(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            organizations.add(new Organization(readLink(dis), readPositions(dis)));
        }
        return new OrganizationSection(organizations);
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
        for (Organization.Position pos : positions) {
            String description = pos.getDescription();
            dos.writeInt(pos.getStartDate().getYear());
            dos.writeUTF(pos.getStartDate().getMonth().name());
            dos.writeInt(pos.getEndDate().getYear());
            dos.writeUTF(pos.getEndDate().getMonth().name());
            dos.writeUTF(pos.getTitle());
            dos.writeUTF(description == null ? "null" : description);
        }
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
