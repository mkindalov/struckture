package com.kindalov.struck.handlers;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class IntHandler extends ReversibleHandler<Integer> {

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public Integer getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getInt();
    }
}
