package com.kindalov.struck;

import com.kindalov.struck.annotations.StruckField;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * TODO comment.
 */
public interface Handler<T> {
    void init(Set<Annotation> annotations);
    int getSize();
    int getOffset();
    T getValue(byte[] data);
}
