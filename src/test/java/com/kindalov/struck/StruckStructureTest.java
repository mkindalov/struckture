package com.kindalov.struck;

import com.kindalov.struck.annotations.StruckField;
import com.kindalov.struck.annotations.Structure;
import com.kindalov.struck.exceptions.FieldConfigurationException;
import com.kindalov.struck.exceptions.StructureConfigurationException;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * TODO comment.
 */
public class StruckStructureTest extends StruckTest {

    @Structure(len = 0x10)
    private static class StruckTestStructure {
        @StruckField(offset = 0, size = 2)
        private boolean[] array;

        private String string;
    }

    @Test
    public void shouldNotSetNotAnnotatedField() {
        //given


        InputStream stream = stream(
                x(0x7, 0, 0, 0x10, 0, 0, 0, 0), x(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                x(0x20, 0, 0, 0, 0, 0, 0, 0), x(0x7, 0, 0x1, 0x5, 0, 0, 0, 0));
        Struck<StruckTestStructure> struck = Struck.forClass(StruckTestStructure.class);

        //when
        StruckTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.string, is(nullValue()));
    }

    @Structure(len = 0x10)
    public static class NegativeOffsetTestStructure {
        @StruckField(offset = -1)
        private int integer;
    }

    @Test(expected = FieldConfigurationException.class)
    public void shouldNotAllowNegativeOffset() {
        //given

        //when
        Struck.forClass(NegativeOffsetTestStructure.class);

        //then

    }

    @Structure(len = 0x5)
    private static class OutOfBoundsTestStructure {
        @StruckField(offset = 4)
        private int integer;
    }

    @Test(expected = FieldConfigurationException.class)
    public void shouldNotAllowOutOfBoundsFields() {
        //given


        //when
        Struck.forClass(OutOfBoundsTestStructure.class);

        //then

    }


    @Structure(len = 0x8, allowOverlap = true)
    private static class OverlappingWithAllowTestStructure {
        @StruckField(offset = 4)
        private int integer;
        @StruckField(offset = 4, size = 4)
        private byte bytes;
    }
    @Test
    public void shouldAllowOverlappingFieldsWhenOverlappingEnabled() {
        //given


        //when
        Struck<OverlappingWithAllowTestStructure> structure = Struck.forClass(OverlappingWithAllowTestStructure.class);

        //then
        assertThat(structure, is(notNullValue()));
    }

    @Structure(len = 0x8)
    private static class OverlappingTestStructure {
        @StruckField(offset = 4)
        private int integer;
        @StruckField(offset = 4, size = 4)
        private byte bytes;
    }

    @Test(expected = StructureConfigurationException.class)
    public void shouldNotAllowOverlappingFieldsWhenOverlappingDisabled() {
        //given

        //when
        Struck.forClass(OverlappingTestStructure.class);

        //then
    }

    private static class NoAnnotationTestStructure {
        @StruckField(offset = 4)
        private int integer;
    }

    @Test(expected = StructureConfigurationException.class)
    public void shouldNotReadStructureWithoutAnnotation() {
        //given

        //when
        Struck<NoAnnotationTestStructure> struck = Struck.forClass(NoAnnotationTestStructure.class);

        //then
        fail();
    }

    @Structure(len = -5)
    private static class NegativeLenTestStructure {
        @StruckField(offset = 4)
        private int integer;
    }

    @Test(expected = StructureConfigurationException.class)
    public void shouldNotReadStructureWithNonPositiveLength() {
        //given


        //when
        Struck.forClass(NegativeLenTestStructure.class);

        //then
        fail();
    }


    @Structure(len = 10)
    private static class NoConstructorTestStructure {
        @StruckField(offset = 4)
        private int integer;
        public NoConstructorTestStructure(int integer) {
            this.integer = integer;
        }
    }

    @Test(expected = StructureConfigurationException.class)
    public void shouldThrowExceptionWhenDefaultConstructorAbsent() {
        //given

        //when
        Struck.forClass(NoConstructorTestStructure.class);

        //then
    }

    @Structure(len = 0x8)
    private static class UnknownTypeTestStructure {
        @StruckField(offset = 4)
        private Object object;
    }
    @Test(expected = FieldConfigurationException.class)
    public void shouldThrowExceptionWhenUnknownType() {
        //given


        //when
        Struck.forClass(UnknownTypeTestStructure.class);

        //then
        fail();
    }
}
