package com.kindalov.struck.handlers;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class LongHandler extends ReversibleHandler<Long> {

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public Long getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getLong();
    }
}
