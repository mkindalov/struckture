package org.struckture.handlers;

import org.struckture.base.annotations.StringEncoding;
import org.struckture.base.annotations.StruckField;
import org.struckture.base.exceptions.FieldConfigurationException;
import org.struckture.base.exceptions.StrucktureReadException;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * String Handler.
 */
public class StringHandler extends AbstractHandler<String> {

    private int size = 0;
    private String encoding = "ASCII";

    @Override
    public void init(Set<Annotation> annotations) {
        super.init(annotations);
        StruckField struckField = getAnnotation(StruckField.class);
        size = struckField.size();

        StringEncoding stringEncoding = getAnnotation(StringEncoding.class);
        if (stringEncoding != null) {
            if (Charset.isSupported(stringEncoding.value())) {
                encoding = stringEncoding.value();
            } else {
                throw new FieldConfigurationException("Encoding '" + encoding + "' is not supported.");
            }
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getValue(byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, getOffset(), size);
        byte[] bytes = new byte[size];
        byteBuffer.get(bytes);
        try {
            return new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new StrucktureReadException("Cannot create string.", e);
        }
    }
}
