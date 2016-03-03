package com.kindalov.struck.handlers;

import com.kindalov.struck.annotations.StruckField;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.util.Set;

/**
 * TODO comment.
 */
public class ByteArrayHandler extends ReversibleHandler<byte[]> {

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

    public static void reverse(byte[] data) {
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            // swap the values at the left and right indices
            byte temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            // move the left and right index pointers in toward the center
            left++;
            right--;
        }
    }

    @Override
    public byte[] getValue(ByteBuffer byteBuffer) {
        byte[] result = new byte[size];
        byteBuffer.get(result);
        if (isReversed()) {
            reverse(result);
        }
        return result;
    }
}
