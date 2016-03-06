package org.struckture.base;

import org.struckture.base.annotations.StruckField;
import org.struckture.base.annotations.Struckture;
import org.struckture.base.exceptions.FieldConfigurationException;
import org.struckture.base.exceptions.StrucktureReadException;
import org.struckture.base.exceptions.StructureConfigurationException;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

/**
 * Test.
 */
public class StrucktureStruckTest extends StrucktureTest {

    @Struckture(length = 0x10)
    private static class StruckTestStructure {
        @StruckField(offset = 0, size = 2)
        private boolean[] array;

        private String string;
    }

    @Test
    public void shouldNotSetNotAnnotatedField() {
        //given
        InputStream stream = stream(
                o(0x7, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0, 0, 0, 0, 0), o(0x7, 0, 0x1, 0x5, 0, 0, 0, 0));
        Struck<StruckTestStructure> struck = Strucktor.forClass(StruckTestStructure.class);

        //when
        StruckTestStructure structure = struck.read(stream);

        //then
        assertThat(structure.string, is(nullValue()));
    }

    @Struckture(length = 0x10)
    private static class NegativeOffsetTestStructure {
        @StruckField(offset = -1)
        private int integer;
    }

    @Test(expected = FieldConfigurationException.class)
    public void shouldNotAllowNegativeOffset() {
        //given

        //when
        Strucktor.forClass(NegativeOffsetTestStructure.class);

        //then
        //expect exception
    }

    @Struckture(length = 0x5)
    private static class OutOfBoundsTestStructure {
        @StruckField(offset = 4)
        private int integer;
    }

    @Test(expected = FieldConfigurationException.class)
    public void shouldNotAllowOutOfBoundsFields() {
        //given

        //when
        Strucktor.forClass(OutOfBoundsTestStructure.class);

        //then
        //expect exception
    }


    @Struckture(length = 0x8, allowOverlapping = true)
    private static class OverlappingWithAllowTestStructure {
        @StruckField(offset = 4)
        private int integer;
        @StruckField(offset = 4, size = 4)
        private byte[] bytes;
    }
    @Test
    public void shouldAllowOverlappingFieldsWhenOverlappingEnabled() {
        //given

        //when
        Struck<OverlappingWithAllowTestStructure> structure = Strucktor.forClass(OverlappingWithAllowTestStructure.class);

        //then
        assertThat(structure, is(notNullValue()));
    }

    @Struckture(length = 0x8)
    private static class OverlappingTestStructure {
        @StruckField(offset = 4)
        private int integer;
        @StruckField(offset = 4, size = 4)
        private byte[] bytes;
    }

    @Test(expected = StructureConfigurationException.class)
    public void shouldNotAllowOverlappingFieldsWhenOverlappingDisabled() {
        //given

        //when
        Strucktor.forClass(OverlappingTestStructure.class);

        //then
        //expect exception
    }

    private static class NoAnnotationTestStructure {
        @StruckField(offset = 4)
        private int integer;
    }

    @Test(expected = StructureConfigurationException.class)
    public void shouldNotReadStructureWithoutAnnotation() {
        //given

        //when
        Strucktor.forClass(NoAnnotationTestStructure.class);

        //then
        //expect exception
    }

    @Struckture(length = -5)
    private static class NegativeLenTestStructure {
        @StruckField(offset = 4)
        private int integer;
    }

    @Test(expected = StructureConfigurationException.class)
    public void shouldNotReadStructureWithNonPositiveLength() {
        //given


        //when
        Strucktor.forClass(NegativeLenTestStructure.class);

        //then
        //expect exception
    }


    @Struckture(length = 10)
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
        Strucktor.forClass(NoConstructorTestStructure.class);

        //then
        //expect exception
    }

    @Struckture(length = 0x8)
    private static class UnknownTypeFieldTestStructure {
        @StruckField(offset = 4)
        private Object object;
    }

    @Test(expected = FieldConfigurationException.class)
    public void shouldThrowExceptionWhenUnknownType() {
        //given

        //when
        Strucktor.forClass(UnknownTypeFieldTestStructure.class);

        //then
        //expect exception
    }

    @Struckture(length = 0x8)
    private static class BadConstructorStructure {
        @StruckField(offset = 4)
        private int integer;

        public BadConstructorStructure () {
            throw new NotImplementedException();
        }
    }

    @Test(expected = StrucktureReadException.class)
    public void shouldThrowExceptionWhenConstructorThrowsException() {
        //given
        InputStream stream = stream(
                o(0, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0),
                o(0x20, 0, 0, 0x10, 0, 0, 0, 0), o(0x7, 0, 0, 0x5, 0, 0, 0, 0));

        Struck<BadConstructorStructure> struck = Strucktor.forClass(BadConstructorStructure.class);

        //when
        struck.read(stream);

        //then
        //expect exception
    }
}
