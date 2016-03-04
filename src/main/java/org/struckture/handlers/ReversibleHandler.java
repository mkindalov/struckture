package org.struckture.handlers;

import org.struckture.base.annotations.Reverse;

import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Set;

/**
 * Adds a possibility for reversing the ByteOrder.
 * Default is BigEndian.
 * @param <T> Type handled by the handler.
 */
public abstract class ReversibleHandler<T> extends AbstractHandler<T> {

    private boolean reversed = false;

    /**
     * Gets the value for the given byteBuffer.
     *
     * @param byteBuffer is already prepared in the right endian, and with bytes defined by the size and offset.
     * @return the value of type T
     */
    protected abstract T getValue(ByteBuffer byteBuffer);

    /**
     * Returns if the Handler is set as reversed. The endian is BigEndian if not reversed.
     * Handler is reversed if {@link Reverse} annotation is set.
     * @return true if reversed.
     */
    protected boolean isReversed() {
        return reversed;
    }

    @Override
    public void init(Set<Annotation> annotations) {
        super.init(annotations);
        reversed = getAnnotation(Reverse.class) != null;
    }

    @Override
    public T getValue(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, getOffset(), getSize());
        ByteOrder endian = ByteOrder.BIG_ENDIAN;
        if (isReversed()) {
            endian = ByteOrder.LITTLE_ENDIAN;
        }
        byteBuffer.order(endian);

        return getValue(byteBuffer);
    }
}
