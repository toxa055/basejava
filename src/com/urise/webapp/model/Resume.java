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

    public void setContactsMap(Map<ContactType, String> contactsMap) {
        this.contactsMap = contactsMap;
    }

    public Map<SectionType, AbstractSection> getSectionMap() {
        return sectionMap;
    }

    public void setSectionMap(Map<SectionType, AbstractSection> sectionMap) {
        this.sectionMap = sectionMap;
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
