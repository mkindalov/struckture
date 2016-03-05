package org.struckture.base;

import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.struckture.base.exceptions.StrucktureReadException;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * Test.
 */
public class StruckReadTest extends StrucktureTest {

    @Struckture(length = 0x10)
    private static class StruckTestStructure {
        @StruckField(offset = 7)
        private byte[] array;

        @StruckField(offset = 0)
        private Integer size;

        @StruckField(offset = 7)
        private int test2;
    }

    @Test
    public void shouldReadMoreStructuresFromStream() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<StruckTestStructure> struck = Strucktor.forClass(StruckTestStructure.class);

        //when
        struck.read(stream);
        struck.read(stream);

        //then
        //Yuppie no exception
    }


    @Test(expected = StrucktureReadException.class)
    public void shouldThrowExceptionWhenStreamEndAndHasUnreadData() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0));
        Struck<StruckTestStructure> struck = Strucktor.forClass(StruckTestStructure.class);

        //when
        struck.read(stream);
        struck.read(stream);

        //then
        //expect exception
    }

    @Test
    public void shouldReturnNullForEndOfStreamAndNoUnreadData() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));
        Struck<StruckTestStructure> struck = Strucktor.forClass(StruckTestStructure.class);

        //when
        struck.read(stream);
        struck.read(stream);
        StruckTestStructure structure = struck.read(stream);

        //than
        assertThat(structure, is(nullValue()));
    }
}
