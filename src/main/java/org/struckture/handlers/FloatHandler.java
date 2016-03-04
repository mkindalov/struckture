package org.struckture.handlers;

import java.nio.ByteBuffer;

/**
 * Float Handler.
 */
public class FloatHandler extends ReversibleHandler<Float> {

    private static final int BYTES_IN_FLOAT = 4;

    @Override
    public int getSize() {
        return BYTES_IN_FLOAT;
    }

    @Override
    public Float getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getFloat();
    }
}
