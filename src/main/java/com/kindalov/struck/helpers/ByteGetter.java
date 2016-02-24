package com.kindalov.struck.helpers;

import com.kindalov.struck.annotations.Reverse;

import java.lang.reflect.Field;

/**
 * TODO comment.
 */
public class ByteGetter {
    public static byte[] getBytes(byte[] data, int offset, int size, boolean bigEndian) {
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            int j = bigEndian ? i : size - i;
            result[j] = data[offset + j];
        }
        return result;
    }

    public static byte[] getBytes(byte[] data, int offset, int size, Field field) {

        boolean bigEndian =  field.getDeclaredAnnotationsByType(Reverse.class) == null;
        return getBytes(data, offset, size, bigEndian);
    }
}
