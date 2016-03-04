package org.struckture.handlers;

import org.struckture.base.annotations.StruckField;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.util.Set;

/**
 * Boolean Array Handler.
 */
public class BooleanArrayHandler extends ReversibleHandler<boolean[]> {
    private static final int BITS_IN_BYTE = 8;
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
        int posByte = pos / BITS_IN_BYTE;
        int posBit = pos % BITS_IN_BYTE;
        byte valByte = data[posByte];
        return (valByte >> (BITS_IN_BYTE - (posBit + 1)) & 0x0001) == 1;
    }

    @Override
    public boolean[] getValue(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[size];
        byteBuffer.get(bytes);

        int bitSize = size * BITS_IN_BYTE;
        boolean[] result = new boolean[bitSize];
        for (int i = 0; i < bitSize; i++) {
            result[i] = getBit(bytes, i);
        }

        return result;
    }
}
