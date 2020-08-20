package com.urise.webapp.storage.serialize;

public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}