package com.kindalov.struck.handlers;

import com.kindalov.struck.annotations.Reverse;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Set;

/**
 * TODO comment.
 */
public abstract class ReversibleHandler<T> extends AbstractHandler<T> {

    private boolean reversed = false;

    public abstract T getValue(ByteBuffer byteBuffer);

    protected boolean isReversed() {
        return reversed;
    }

    @Override
    public void init(Set<Annotation> annotations) {
        super.init(annotations);
        reversed = getAnnotation(Reverse.class) != null;
    }

    @Override
    public T getValue(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, getOffset(), getSize());
        byteBuffer.order(isReversed() ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
        return getValue(byteBuffer);
    }
}
