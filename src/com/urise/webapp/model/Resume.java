package com.urise.webapp.model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private Map<ContactType, String> contactsMap = new HashMap<>();
    private Map<SectionType, AbstractSection> sectionMap = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContactsMap() {
        return contactsMap;
    }

    public Map<SectionType, AbstractSection> getSectionMap() {
        return sectionMap;
    }

    public void addContact(ContactType contactType, String contact) {
        contactsMap.put(contactType, contact);
    }

    public void addTextSection(SectionType sectionType, String text) {
        if ((sectionType == SectionType.OBJECTIVE) || (sectionType == SectionType.PERSONAL)) {
            sectionMap.put(sectionType, new TextSection(text));
        } else {
            System.out.println("Cannot create TextSection for " + sectionType);
        }
    }

    public void createTextListSection(SectionType sectionType) {
        if ((sectionType == SectionType.ACHIEVEMENT) || (sectionType == SectionType.QUALIFICATIONS)) {
            sectionMap.put(sectionType, new TextListSection());
        } else {
            System.out.println("Cannot create TextListSection for " + sectionType);
        }
    }

    public void addTextListSection(SectionType sectionType, String line) {
        if ((sectionType == SectionType.ACHIEVEMENT) || (sectionType == SectionType.QUALIFICATIONS)) {
            ((TextListSection) sectionMap.get(sectionType)).getList().add(line);
        } else {
            System.out.println("Cannot add TextListSection for " + sectionType);
        }
    }

    public void createTextBlockSection(SectionType sectionType) {
        if ((sectionType == SectionType.EDUCATION) || (sectionType == SectionType.EXPERIENCE)) {
            sectionMap.put(sectionType, new TextBlockSection());
        } else {
            System.out.println("Cannot create TextBlockSection for " + sectionType);
        }
    }

    public void addTextBlockSection(SectionType sectionType, int yearSince, int monthSince, int yearTo, int monthTo,
                                    String place, String position, String description) {
        if ((sectionType == SectionType.EDUCATION) || (sectionType == SectionType.EXPERIENCE)) {
            ((TextBlockSection) sectionMap.get(sectionType)).getList().add(new TextBlock(yearSince, monthSince,
                    yearTo, monthTo, place, position, description));
        } else {
            System.out.println("Cannot add TextBlockSection for " + sectionType);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + " (" + fullName + ")";
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.getFullName());
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
