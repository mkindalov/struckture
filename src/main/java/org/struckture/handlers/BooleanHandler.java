package org.struckture.handlers;

import org.struckture.base.annotations.BitPosition;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.util.Set;

/**
 * Boolean Handler.
 */
public class BooleanHandler extends ReversibleHandler<Boolean> {

    private boolean isBitOriented = false;
    private int position = 0;

    @Override
    public void init(Set<Annotation> annotations) {
        super.init(annotations);
        BitPosition bitPositionAnnotation = getAnnotation(BitPosition.class);
        if (bitPositionAnnotation != null) {
            isBitOriented = true;
            position = bitPositionAnnotation.value();
        }
    }

    private boolean isSet(byte b, int bit) {
        return (b >> bit & 1) == 1;
    }

    @Override
    public Boolean getValue(ByteBuffer byteBuffer) {
        if (isBitOriented) {
            return isSet(byteBuffer.get(), position);
        } else {
            return byteBuffer.get() > 0;
        }

    }

    @Override
    public int getSize() {
        return 1;
    }
}
