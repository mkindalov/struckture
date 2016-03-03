package com.kindalov.struck;

import com.kindalov.struck.annotations.StruckAnnotation;
import com.kindalov.struck.annotations.StruckField;
import com.kindalov.struck.annotations.Structure;
import com.kindalov.struck.exceptions.FieldConfigurationException;
import com.kindalov.struck.exceptions.StruckReadException;
import com.kindalov.struck.exceptions.StructureConfigurationException;
import com.kindalov.struck.handlers.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

/**
 * Main
 */
public class Struck<T> implements IStruck<T> {
    private final Class<T> type;
    private final Map<Field, Handler<?>> handlers = new HashMap<>();
    private final int dataLength;

    private static Map<Type, Class<? extends Handler>> handlerTypes;

    {
        handlerTypes = new HashMap<>();

        handlerTypes.put(Byte.TYPE, ByteHandler.class);
        handlerTypes.put(Byte.class, ByteHandler.class);

        handlerTypes.put(Short.TYPE, ShortHandler.class);
        handlerTypes.put(Short.class, ShortHandler.class);

        handlerTypes.put(Integer.TYPE, IntHandler.class);
        handlerTypes.put(Integer.class, IntHandler.class);

        handlerTypes.put(Long.TYPE, LongHandler.class);
        handlerTypes.put(Long.class, LongHandler.class);

        handlerTypes.put(Float.TYPE, FloatHandler.class);
        handlerTypes.put(Float.class, FloatHandler.class);

        handlerTypes.put(Double.TYPE, DoubleHandler.class);
        handlerTypes.put(Double.class, DoubleHandler.class);

        handlerTypes.put(Boolean.TYPE, BooleanHandler.class);
        handlerTypes.put(Boolean.class, BooleanHandler.class);

        handlerTypes.put(byte[].class, ByteArrayHandler.class);
        handlerTypes.put(boolean[].class, BooleanArrayHandler.class);
        handlerTypes.put(String.class, StringHandler.class);

    }

    public static <Q> Struck<Q> forClass(Class<Q> type) {
        return new Struck<>(type);
    }

    private String format(String message, Object... args) {
        return MessageFormat.format(message, args);
    }

    boolean hasNoArgConstructor(Class<?> klass) {
        for(Constructor c : klass.getDeclaredConstructors()) {
            if(c.getParameterTypes().length == 0)  return true;
        }
        return false;
    }

    private Struck(Class<T> type) {
        this.type = type;

        if (!hasNoArgConstructor(type)) {
            throw new StructureConfigurationException(
                    format("Structure ''{0}'' doesn't have no args constructor.", type));
        }

        Structure structureAnnotation = type.getAnnotation(Structure.class);
        if (structureAnnotation == null) {
            throw new StructureConfigurationException(
                    format("Structure ''{0}'' is not annotated with ''{1}''.", type, Structure.class));
        }

        dataLength = structureAnnotation.len();
        if (dataLength < 1) {
            throw new StructureConfigurationException(
                    format("Length of structure ''{0}'' is not positive. len = ''{1}''.", type, dataLength));
        }

        for (Field field : type.getDeclaredFields()) {
            StruckField fieldAnnotation = field.getDeclaredAnnotation(StruckField.class);
            if (fieldAnnotation != null) {
                Handler<?> handler = getValidHandler(field, dataLength);
                field.setAccessible(true);
                handlers.put(field, handler);
            }
        }

        boolean overlapAllowed = structureAnnotation.allowOverlap();
        if (!overlapAllowed) {
            Map<Interval, Field> checked = new HashMap<>();
            for (Map.Entry<Field, Handler<?>> entry : handlers.entrySet()) {
                Handler<?> handler = entry.getValue();
                Interval newInterval = new Interval(handler.getOffset(), handler.getOffset() + handler.getSize());
                for (Map.Entry<Interval, Field> c : checked.entrySet()) {
                    Interval checkedInterval = c.getKey();
                    if (checkedInterval.contains(newInterval.getFrom()) &&
                            checkedInterval.contains(newInterval.getTo()) &&
                            newInterval.contains(checkedInterval.getFrom()) &&
                            newInterval.contains(checkedInterval.getTo())) {
                        throw new FieldConfigurationException(format("Fields ''{0}'' and ''{1}'' are overlapping.", c.getValue(), entry.getKey()));
                    }
                }
                checked.put(newInterval, entry.getKey());
            }
        }
    }

    private static class Interval {
        private int from;
        private int to;

        public Interval(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public boolean contains(int i) {
            return from < i && i < to;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }

    private Handler<?> getValidHandler(Field field, int dataLength) {
        try {
            Class<? extends Handler> handlerType = handlerTypes.get(field.getType());
            if (handlerType == null) {
                throw new FieldConfigurationException(
                        format("Handler for type ''{0}'' is not supported.", field.getType()));
            }
            Set<Annotation> annotations = getStruckAnnotations(field);
            Handler<?> handler = handlerType.newInstance();
            handler.init(annotations);

            if (handler.getOffset() < 0) {
                throw new FieldConfigurationException(format("Offset for ''{0}'' negative. Should be non-negative.", field));
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
        for(Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(StruckAnnotation.class)) {
                annotations.add(annotation);
            }
        }
        return annotations;
    }

    @Override
    public T read(InputStream stream) throws StruckReadException {
        try {
            byte[] bytes = new byte[dataLength];
            int read = stream.read(bytes);
            if (read == -1) {
                return null;
            }
            if (read < dataLength) {
                throw new StruckReadException(format("Couldn't read structure. Only ''{0}'' bytes are available", read));
            }
            Constructor<T> declaredConstructor = type.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            T result = declaredConstructor.newInstance();

            for (Field field : handlers.keySet()) {
                Handler handler = handlers.get(field);
                if(handler != null) {
                    Object value = handler.getValue(bytes);
                    field.set(result, value);
                }
            }
            return result;
        } catch (IOException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
           throw new StruckReadException("Could not read structure.", e);
        }
    }
}
