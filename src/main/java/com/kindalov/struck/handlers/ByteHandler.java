package com.kindalov.struck.handlers;

/**
 * TODO comment.
 */
public class ByteHandler extends AbstractHandler<Byte> {

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public Byte getValue(byte[] data) {
        return data[getOffset()];
    }
}
