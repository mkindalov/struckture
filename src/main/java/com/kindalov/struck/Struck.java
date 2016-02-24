package com.kindalov.struck;

import com.kindalov.struck.annotations.Structure;
import com.kindalov.struck.exceptions.StructException;
import com.kindalov.struck.handlers.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Main
 */
public class Struck<T> implements IStruck<T> {



    private Class<T> type;
    private Map<Field, Handler> handlers;

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
        handlerTypes.put(String.class, StringHandler.class);

    }

    public static <Q> Struck<Q> forClass(Class<Q> type) {
        return new Struck<>(type);
    }

    private Struck(Class<T> type) {
        handlers = new HashMap<>();
        this.type = type;

        int lastCount = 0;
        for (Field field : type.getDeclaredFields()) {

            Class<? extends Handler> handlerType = handlerTypes.get(field.getType());
            if (handlerType == null) {
                throw new RuntimeException("no handler for " + field.getType());
            }
            try {


                Handler<?> handler =  handlerType.newInstance();
                com.kindalov.struck.annotations.Field fieldAnnotation = field.getDeclaredAnnotation(com.kindalov.struck.annotations.Field.class);
                handler.setOffset(fieldAnnotation.offset());

                field.setAccessible(true);
                handlers.put(field, handler);
            } catch (IllegalAccessException | InstantiationException e) {
               throw new RuntimeException("handler no good");
            }

        }
    }

    @Override
    public T read(InputStream stream) {
        try {
            Structure annotation = type.getAnnotation(Structure.class);
            int len = annotation.len();
            byte[] bytes = new byte[len];
            int read = stream.read(bytes);
            if (read < len) {
                throw new EOFException("");
            }
            T result = type.newInstance();

            for (Field field : handlers.keySet()) {
                Handler handler = handlers.get(field);
                if(handler != null) {
                    Object value = handler.getValue(bytes);
                    field.set(result, value);
                }

            }
            return result;
        } catch (IOException | IllegalAccessException | InstantiationException e) {
           throw new StructException("sdf", e);
        }
    }
}
