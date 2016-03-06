package org.struckture.handlers;

import java.nio.ByteBuffer;

/**
 * Long Handler.
 */
public class LongHandler extends ReversibleHandler<Long> {

    private static final int BYTES_IN_LONG = 8;

    @Override
    public int getSize() {
        return BYTES_IN_LONG;
    }

    @Override
    public Long getValue(ByteBuffer byteBuffer) {
        return byteBuffer.getLong();
    }
}
