package com.urise.webapp.storage.serialize;

import java.io.IOException;

public interface Performer<T> {
    void perform(T t) throws IOException;
}