package org.struckture.handlers;

import java.nio.ByteBuffer;

/**
 * Test.
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
