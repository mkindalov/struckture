package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class FloatHandler implements Handler<Float> {

    private int offset;

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Float getValue(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, offset, getSize());
        return byteBuffer.getFloat();
    }
}
