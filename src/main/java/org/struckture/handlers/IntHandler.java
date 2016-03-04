package org.struckture.handlers;

import java.nio.ByteBuffer;

/**
 * Test.
 */
public class IntHandler extends ReversibleHandler<Integer> {

    private static final int BYTES_IN_INT = 4;


    @Override
    public int getSize() {
        return BYTES_IN_INT;
    }

    @Override
    public Integer getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getInt();
    }
}
