package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class TextBlockSection extends AbstractSection {
    private final List<TextBlock> list;

    public TextBlockSection() {
        list = new ArrayList<>();
    }

    public List<TextBlock> getList() {
        return list;
    }
}
