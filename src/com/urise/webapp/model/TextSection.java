package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    private String description;

    public TextSection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
