package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;
import com.kindalov.struck.helpers.ByteGetter;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class BooleanHandler implements Handler<Boolean> {

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
    public Boolean getValue(byte[] data) {
        byte[] bytes = ByteGetter.getBytes(data, offset, getSize(), true);
        return bytes[0] > 0;
    }
}
