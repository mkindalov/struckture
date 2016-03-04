package org.struckture.handlers;

import org.struckture.base.Struck;
import org.struckture.base.Strucktor;
import org.struckture.base.StrucktureTest;
import org.struckture.base.annotations.StringEncoding;
import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.struckture.base.exceptions.FieldConfigurationException;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

/**
 * Test.
 */
public class StringTest extends StrucktureTest {

    @Struckture(length = 0x20, allowOverlapping = true)
    private static class StringTestStructure {
        @StruckField(offset = 0x2, size = 5)
        private String string;

        @StruckField(offset = 0, size = 0x11) @StringEncoding("utf-8")
        private String utf8string;
    }

    @Test
    public void shouldReadString() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 't', 'e', 's', 't', '1', 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<StringTestStructure> struck = Strucktor.forClass(StringTestStructure.class);

        //when
        StringTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.string, is("test1"));
    }

    @Test
    public void shouldReadUtf8String() {
        //given
        InputStream stream = stream(
                o(0xD0, 0x93, 0xD1, 0x80, 0xD0, 0xBE, 0xD0, 0xBC), o(0x20, 0xD0, 0xA2, 0xD0, 0xB5, 0xD1, 0x81, 0xD1),
                o(0x82, 0, 0, 0, 0, 0, 0, 0), o(0, 0, 0, 0, 0, 0, 0, 0));
        Struck<StringTestStructure> struck = Strucktor.forClass(StringTestStructure.class);

        //when
        StringTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.utf8string, is("Гром Тест"));
    }

    @Struckture(length = 0x20)
    private static class BadEncodingTestStructure {
        @StruckField(offset = 0x2, size = 5)
        private String string;

        @StruckField(offset = 0, size = 0x11) @StringEncoding("unknown-1238")
        private String utf8string;
    }

    @Test(expected = FieldConfigurationException.class)
    public void shouldNotAllowUnknownEncoding() {
        //given

        //when
        Strucktor.forClass(BadEncodingTestStructure.class);

        //then
    }
}
