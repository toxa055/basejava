package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamStrategy implements Strategy {
    private final XmlParser xmlParser;

    public XmlStreamStrategy() {
        xmlParser = new XmlParser(
                Resume.class, Organization.class, Link.class,
                OrganizationSection.class, SimpleTextSection.class, ListSection.class, Organization.Position.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
