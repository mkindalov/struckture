package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class ShortHandler extends ReversibleHandler<Short> {

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public Short getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getShort();
    }
}
