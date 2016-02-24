package com.kindalov.struck.handlers;

import com.kindalov.struck.Handler;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * TODO comment.
 */
public class StringHandler implements Handler<String> {

    private int offset;

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String getValue(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, offset, getSize());
        try {
            return new String(byteBuffer.array(), "ASCII");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
