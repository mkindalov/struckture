package org.struckture.handlers;

import java.nio.ByteBuffer;

/**
 * Double Handler.
 */
public class DoubleHandler extends ReversibleHandler<Double> {
    private static final int BYTES_IN_DOUBLE = 8;

    @Override
    public Double getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getDouble();
    }

    @Override
    public int getSize() {
        return BYTES_IN_DOUBLE;
    }
}
