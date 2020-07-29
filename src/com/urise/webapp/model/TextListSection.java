package com.urise.webapp.model;

import java.util.List;

public class TextListSection extends AbstractSection {
    private List<String> list;

    public TextListSection(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}