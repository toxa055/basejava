package com.urise.webapp.util;

import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SimpleTextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractStorageTest.RESUME_5;

public class JsonParserTest {
    @Test
    public void testResume() {
        String json = JsonParser.write(RESUME_5);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_5, resume);
    }

    @Test
    public void write() {
        AbstractSection expectedSection = new SimpleTextSection("some description");
        String json = JsonParser.write(expectedSection, AbstractSection.class);
        System.out.println(json);
        AbstractSection actualSection = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(expectedSection, actualSection);
    }
}