package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class TextListSection extends AbstractSection {
    private final List<String> list;

    public TextListSection() {
        list = new ArrayList<>();
    }

    public List<String> getList() {
        return list;
    }
}