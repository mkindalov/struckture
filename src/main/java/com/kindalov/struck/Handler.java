package com.kindalov.struck;

/**
 * TODO comment.
 */
public interface Handler<T> {
    int getSize();
    void setOffset(int offset);
    T getValue(byte[] data);
}
