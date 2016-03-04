package org.struckture.handlers;

/**
 * Test.
 */
public class ByteHandler extends AbstractHandler<Byte> {

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public Byte getValue(byte[] data) {
        return data[getOffset()];
    }
}
