package com.urise.webapp.storage.serialize;

import java.io.IOException;

public interface DataWriter<T> {
    void perform(T t) throws IOException;
}