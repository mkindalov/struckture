package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class DoubleHandler implements Handler<Double> {

    private int offset;

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Double getValue(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, offset, getSize());
        return byteBuffer.getDouble();
    }
}
