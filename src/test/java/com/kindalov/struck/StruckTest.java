package com.kindalov.struck;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * TODO comment.
 */
public class StruckTest {

    protected InputStream stream(byte[]... bytes) {
        Byte[] combined = Arrays.stream(bytes).flatMap(a -> Stream.of(toObjects(a))).toArray(Byte[]::new);
        ByteArrayInputStream result = new ByteArrayInputStream(toPrimitives(combined));
        return result;
    }

    private Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];

        int i = 0;
        for (byte b : bytesPrim) bytes[i++] = b; // Autoboxing

        return bytes;
    }

    private byte[] toPrimitives(Byte[] oBytes)
    {
        byte[] bytes = new byte[oBytes.length];
        for(int i = 0; i < oBytes.length; i++){
            bytes[i] = oBytes[i];
        }
        return bytes;
    }

    protected byte[] x(int p1, int p2, int p3, int p4, int p5, int p6, int p7, int p8) {
        return new byte[] {(byte) p1, (byte) p2, (byte) p3, (byte) p4, (byte) p5, (byte) p6, (byte) p7, (byte) p8};
    }
}