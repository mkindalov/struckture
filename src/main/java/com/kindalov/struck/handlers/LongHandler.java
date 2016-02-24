package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;
import com.kindalov.struck.helpers.ByteGetter;

/**
 * TODO comment.
 */
public class LongHandler implements Handler<Long> {

    private int offset;
    
    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Long getValue(byte[] data) {
        byte[] bytes = ByteGetter.getBytes(data, offset, getSize(), true);
        long result = 0;
        for (int i = 0; i < getSize(); i++) {
            result = result << 8;
            result += bytes[i];
        }
        return result;
    }
}
