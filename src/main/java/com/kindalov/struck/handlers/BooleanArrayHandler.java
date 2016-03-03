package com.kindalov.struck.handlers;

import com.kindalov.struck.annotations.StruckField;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.util.Set;

/**
 * TODO comment.
 */
public class BooleanArrayHandler extends ReversibleHandler<boolean[]> {
    private int size = 0;

    @Override
    public void init(Set<Annotation> annotations) {
        super.init(annotations);
        StruckField struckField = getAnnotation(StruckField.class);
        size = struckField.size();
    }

    @Override
    public int getSize() {
        return size;
    }

    private static boolean getBit(byte[] data, int pos) {
        int posByte = pos/8;
        int posBit = pos%8;
        byte valByte = data[posByte];
        return (valByte>>(8-(posBit+1)) & 0x0001) == 1;
    }

    @Override
    public boolean[] getValue(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[size];
        byteBuffer.get(bytes);

        int bitSize = size * 8;
        boolean[] result = new boolean[bitSize];
        for (int i = 0; i < bitSize; i++) {
            result[i] = getBit(bytes, i);
        }

        return result;
    }
}
