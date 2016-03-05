package org.struckture.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Abstract class adding helpers for testing.
 */
public abstract class StrucktureTest {

    /**
     * Creates a byte stream out of array of byte array.
     * @param bytesList array of byte array
     * @return input stream.
     */
    protected InputStream stream(byte[]... bytesList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        for (byte[] bytes : bytesList) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * Enables more easier byte stream definition. Note that integers are converted to byte.
     * @param b1 byte 1
     * @param b2 byte 2
     * @param b3 byte 3
     * @param b4 byte 4
     * @param b5 byte 5
     * @param b6 byte 6
     * @param b7 byte 7
     * @param b8 byte 8
     * @return byte array of the provided bytes.
     */
    protected byte[] o(int b1, int b2, int b3, int b4, int b5, int b6, int b7, int b8) {
        return new byte[] {(byte) b1, (byte) b2, (byte) b3, (byte) b4, (byte) b5, (byte) b6, (byte) b7, (byte) b8};
    }
}