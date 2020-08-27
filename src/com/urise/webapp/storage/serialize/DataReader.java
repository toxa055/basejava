package com.urise.webapp.storage.serialize;

import java.io.IOException;

public interface DataReader<T> {
    T perform() throws IOException;
}