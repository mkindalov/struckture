package com.kindalov.struck;

import com.kindalov.struck.annotations.StruckField;
import com.kindalov.struck.annotations.Structure;
import com.kindalov.struck.exceptions.StruckException;
import com.kindalov.struck.exceptions.StruckReadException;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * TODO comment.
 */
public class StruckReadTest extends StruckTest {

    @Structure(len = 0x10)
    public static class TestStructure1 {
        @StruckField(offset = 7)
        private byte[] array;

        @StruckField(offset = 0)
        private Integer size;

        @StruckField(offset = 7)
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


    @Test(expected = StruckReadException.class)
    public void shouldThrowExceptionWhenStreamEndAndHasUnreadData() {


        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0));
        Struck<TestStructure1> struck = Struck.forClass(TestStructure1.class);

        //when
        struck.read(stream);
        struck.read(stream);

        //then everything is good
    }

    @Test
    public void shouldReturnNullForEndOfStreamAndNoUnreadData() {


        //given
        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<TestStructure1> struck = Struck.forClass(TestStructure1.class);

        struck.read(stream);
        struck.read(stream);

        //when
        TestStructure1 structure = struck.read(stream);

        //than
        assertThat(structure, is(nullValue()));
    }



}
