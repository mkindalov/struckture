package com.kindalov.struck;

import com.kindalov.struck.annotations.Field;
import com.kindalov.struck.annotations.Structure;
import com.kindalov.struck.exceptions.StructException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * TODO comment.
 */
public class StructureTest extends StruckTest {

    @Structure(len = 0x10)
    public static class TestStructure1 {
        @Field(offset = 7, length = "size - 2")
        private byte[] array;

        @Field(offset = 0)
        private Integer size;

        @Field(offset = 7)
        private int test2;
    }

    @Test
    public void shouldReadMoreTimes() {
        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<TestStructure1> struck = Struck.forClass(TestStructure1.class);

        //when
        struck.read(stream);
        struck.read(stream);

        //than everything is good
    }

    @Test(expected = StructException.class)
    public void shouldThrowExceptionWhenReadWhenNoMoreData() {
        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<TestStructure1> struck = Struck.forClass(TestStructure1.class);

        //when
        struck.read(stream);
        struck.read(stream);

        struck.read(stream);

        //than everything is good
    }



}
