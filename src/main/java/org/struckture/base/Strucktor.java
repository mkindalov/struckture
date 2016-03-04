package org.struckture.base;

import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.struckture.base.annotations.StrucktureAnnotation;
import org.struckture.base.exceptions.FieldConfigurationException;
import org.struckture.base.exceptions.StrucktureReadException;
import org.struckture.base.exceptions.StructureConfigurationException;
import org.struckture.handlers.BooleanArrayHandler;
import org.struckture.handlers.BooleanHandler;
import org.struckture.handlers.ByteArrayHandler;
import org.struckture.handlers.ByteHandler;
import org.struckture.handlers.DoubleHandler;
import org.struckture.handlers.FloatHandler;
import org.struckture.handlers.IntHandler;
import org.struckture.handlers.LongHandler;
import org.struckture.handlers.ShortHandler;
import org.struckture.handlers.StringHandler;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of Struck.<br/>
 *
 * Usage:
 * <br/>
 * <pre>
 * {@code
 * Strucktor<SomeStruckture> struck =  Strucktor.forClass(SomeStruckture.class)
 * SomeStruckture structure = struck.read(stream);
 * }
 * </pre>
 *
 * @param <T> Structure type to read.
 */
public class Strucktor<T> implements Struck<T> {
    private final Class<T> type;
    private final Map<Field, Handler<?>> handlers = new HashMap<>();
    private final int dataLength;

    private static final Map<Type, Class<? extends Handler>> HANDLER_TYPES;

    static {
        HANDLER_TYPES = new HashMap<>();

        HANDLER_TYPES.put(Byte.TYPE, ByteHandler.class);
        HANDLER_TYPES.put(Byte.class, ByteHandler.class);

        HANDLER_TYPES.put(Short.TYPE, ShortHandler.class);
        HANDLER_TYPES.put(Short.class, ShortHandler.class);

        HANDLER_TYPES.put(Integer.TYPE, IntHandler.class);
        HANDLER_TYPES.put(Integer.class, IntHandler.class);

        HANDLER_TYPES.put(Long.TYPE, LongHandler.class);
        HANDLER_TYPES.put(Long.class, LongHandler.class);

        HANDLER_TYPES.put(Float.TYPE, FloatHandler.class);
        HANDLER_TYPES.put(Float.class, FloatHandler.class);

        HANDLER_TYPES.put(Double.TYPE, DoubleHandler.class);
        HANDLER_TYPES.put(Double.class, DoubleHandler.class);

        HANDLER_TYPES.put(Boolean.TYPE, BooleanHandler.class);
        HANDLER_TYPES.put(Boolean.class, BooleanHandler.class);

        HANDLER_TYPES.put(byte[].class, ByteArrayHandler.class);
        HANDLER_TYPES.put(boolean[].class, BooleanArrayHandler.class);
        HANDLER_TYPES.put(String.class, StringHandler.class);

    }

    /**
     * Gets a {@link Struck} instance.
     * @param type the type
     * @param <Q> the type
     * @return Struck instance which knows to read Struckture for type type(Q)
     */
    public static <Q> Struck<Q> forClass(Class<Q> type) {
        return new Strucktor<>(type);
    }

    private String format(String message, Object... args) {
        return MessageFormat.format(message, args);
    }

    private boolean hasNoArgConstructor(Class<?> klass) {
        for (Constructor c : klass.getDeclaredConstructors()) {
            if (c.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }

    private Strucktor(Class<T> type) {
        this.type = type;

        if (!hasNoArgConstructor(type)) {
            throw new StructureConfigurationException(
                    format("Struck ''{0}'' doesn't have no args constructor.", type));
        }

        Struckture strucktureAnnotation = type.getAnnotation(Struckture.class);
        if (strucktureAnnotation == null) {
            throw new StructureConfigurationException(
                    format("Struck ''{0}'' is not annotated with ''{1}''.", type, Struckture.class));
        }

        dataLength = strucktureAnnotation.length();
        if (dataLength < 1) {
            throw new StructureConfigurationException(
                    format("Length of structure ''{0}'' is not positive. length = ''{1}''.", type, dataLength));
        }

        for (Field field : type.getDeclaredFields()) {
            StruckField struckFieldAnnotation = field.getDeclaredAnnotation(StruckField.class);
            if (struckFieldAnnotation != null) {
                Handler<?> handler = getValidHandler(field, dataLength);
                field.setAccessible(true);
                handlers.put(field, handler);
            }
        }

        boolean overlapAllowed = strucktureAnnotation.allowOverlapping();
        if (!overlapAllowed) {
            checkOverlapping(handlers);
        }
    }

    private void checkOverlapping(Map<Field, Handler<?>> handlers) {
        Map<Interval, Field> checked = new HashMap<>();
        for (Map.Entry<Field, Handler<?>> entry : handlers.entrySet()) {
            Handler<?> handler = entry.getValue();
            Interval newInterval = new Interval(handler.getOffset(), handler.getOffset() + handler.getSize());
            for (Map.Entry<Interval, Field> c : checked.entrySet()) {
                Interval checkedInterval = c.getKey();
                if (checkedInterval.overlaps(newInterval)) {
                    String message = format("Fields ''{0}'' and ''{1}'' are overlapping. "
                                    + "You can allow overlapping on the structure on the ''{3}'' annotation.",
                            c.getValue(), entry.getKey(), Struckture.class);
                    throw new StructureConfigurationException(message);
                }
            }
            checked.put(newInterval, entry.getKey());
        }
    }

    private static class Interval {
        private final int from;
        private final int to;

        Interval(int from, int to) {
            this.from = from;
            this.to = to;
        }

        private boolean overlaps(Interval other) {
            boolean isOtherLeft = other.from <= from && other.to <= from;
            boolean isOtherRight = other.from >= to && other.to >= to;
            return !isOtherLeft && !isOtherRight;
        }
    }

    private Handler<?> getValidHandler(Field field, int dataLength) {
        try {
            Class<? extends Handler> handlerType = HANDLER_TYPES.get(field.getType());
            if (handlerType == null) {
                throw new FieldConfigurationException(
                        format("Handler for type ''{0}'' is not supported.", field.getType()));
            }
            Set<Annotation> annotations = getStruckAnnotations(field);
            Handler<?> handler = handlerType.newInstance();
            handler.init(annotations);

            if (handler.getOffset() < 0) {
                throw new FieldConfigurationException(
                        format("Offset for ''{0}'' negative. Should be non-negative.", field));
            }

            if (handler.getOffset() + handler.getSize() > dataLength) {
                throw new FieldConfigurationException(
                        format("Upper bound for ''{0}'' outside the structure size ''{1}''.", field, dataLength));
            }

            return handler;

        } catch (IllegalAccessException | InstantiationException  e) {
            throw new FieldConfigurationException(format("Error while configuring field ''{0}''.", field), e);
        }
    }

    private Set<Annotation> getStruckAnnotations(Field field) {
        Set<Annotation> annotations = new HashSet<>();
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(StrucktureAnnotation.class)) {
                annotations.add(annotation);
            }
        }
        return annotations;
    }

    @Override
    public T read(InputStream stream) throws StrucktureReadException {
        try {
            byte[] bytes = new byte[dataLength];
            int read = stream.read(bytes);
            if (read == -1) {
                return null;
            }
            if (read < dataLength) {
                throw new StrucktureReadException(
                        format("Couldn't read structure. Only ''{0}'' bytes are available", read));
            }
            Constructor<T> declaredConstructor = type.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            T result = declaredConstructor.newInstance();

            for (Field field : handlers.keySet()) {
                Handler handler = handlers.get(field);
                if (handler != null) {
                    Object value = handler.getValue(bytes);
                    field.set(result, value);
                }
            }
            return result;
        } catch (Exception e) {
           throw new StrucktureReadException("Could not read structure.", e);
        }
    }
}
