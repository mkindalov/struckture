package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class ShortHandler implements Handler<Short> {

    private int offset;

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Short getValue(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, offset, getSize());
        return byteBuffer.getShort();
    }
}
