package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;
import com.kindalov.struck.helpers.ByteGetter;

/**
 * TODO comment.
 */
public class IntHandler implements Handler<Integer> {

    private int offset;

    @Override
    public int getSize() {
        return 4;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Integer getValue(byte[] data) {
        byte[] bytes = ByteGetter.getBytes(data, offset, getSize(), true);
        int result = 0;
        for (int i = 0; i < getSize(); i++) {
            result = result << 8;
            result += bytes[i];
        }
        return result;
    }
}
