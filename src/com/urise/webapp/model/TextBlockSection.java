package com.urise.webapp.model;

import java.util.List;

public class TextBlockSection extends AbstractSection {
    private List<TextBlock> list;

    public TextBlockSection(List<TextBlock> list) {
        this.list = list;
    }

    public List<TextBlock> getList() {
        return list;
    }

    public void setList(List<TextBlock> list) {
        this.list = list;
    }
}
