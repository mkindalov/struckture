package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

/**
 * TODO comment.
 */
public class ByteArrayHandler implements Handler<byte[]> {

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
    public byte[] getValue(byte[] data) {
        return new byte[] {1,2,3,4};
    }
}
