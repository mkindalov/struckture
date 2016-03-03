package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class DoubleHandler extends ReversibleHandler<Double> {


    @Override
    public Double getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getDouble();
    }

    @Override
    public int getSize() {
        return 8;
    }
}
