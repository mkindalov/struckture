package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class FloatHandler extends ReversibleHandler<Float> {

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public Float getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getFloat();
    }
}
