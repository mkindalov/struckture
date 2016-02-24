package com.kindalov.struck.handlers;

import com.kindalov.struck.Struck;
import com.kindalov.struck.StruckTest;
import com.kindalov.struck.annotations.Field;
import com.kindalov.struck.annotations.Structure;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * TODO comment.
 */
public class StringTest extends StruckTest {

    @Structure(len = 0x20)
    public static class StringTestStructure {
        @Field(offset = 0x2, size = 5)
        private String string;
    }

    @Test
    public void shouldReadString() {
        //given
        InputStream stream = stream(
                x(0x7, 0, 't', 'e', 's', 't', '1', 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<StringTestStructure> struck = Struck.forClass(StringTestStructure.class);

        //when
        StringTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.string, is("test1"));
    }
}
