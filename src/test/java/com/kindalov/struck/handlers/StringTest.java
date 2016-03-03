package com.kindalov.struck.handlers;

import com.kindalov.struck.Struck;
import com.kindalov.struck.StruckTest;
import com.kindalov.struck.annotations.StringEncoding;
import com.kindalov.struck.annotations.StruckField;
import com.kindalov.struck.annotations.Structure;
import com.kindalov.struck.exceptions.FieldConfigurationException;
import com.kindalov.struck.exceptions.StructureConfigurationException;
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
        @StruckField(offset = 0x2, size = 5)
        private String string;

        @StruckField(offset = 0, size = 0x11) @StringEncoding("utf-8")
        private String utf8string;
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

    @Test
    public void shouldReadUtf8String() {
        //given
        InputStream stream = stream(
                x(0xD0, 0x93, 0xD1, 0x80, 0xD0, 0xBE, 0xD0, 0xBC), x(0x20, 0xD0, 0xA2, 0xD0, 0xB5, 0xD1, 0x81, 0xD1),
                x(0x82, 0, 0, 0, 0, 0, 0, 0), x(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<StringTestStructure> struck = Struck.forClass(StringTestStructure.class);

        //when
        StringTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.utf8string, is("Гром Тест"));
    }

    @Structure(len = 0x20)
    public static class BadEncodingTestStructure {
        @StruckField(offset = 0x2, size = 5)
        private String string;

        @StruckField(offset = 0, size = 0x11) @StringEncoding("unknown-1238")
        private String utf8string;
    }

    @Test(expected = FieldConfigurationException.class)
    public void shouldNotAllowUnknownEncoding() {
        //given

        //when
        Struck.forClass(BadEncodingTestStructure.class);

        //then
    }
}
