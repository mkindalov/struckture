package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;
import com.kindalov.struck.helpers.ByteGetter;

/**
 * TODO comment.
 */
public class ByteHandler implements Handler<Byte> {

    private int offset;

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Byte getValue(byte[] data) {
        byte[] bytes = ByteGetter.getBytes(data, offset, getSize(), true);
        return bytes[0];
    }
}
