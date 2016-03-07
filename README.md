# Struckture - Binary structure reader for Java

### Target
This java framework is for those who want to read binary structures in java. Usefull for reverse engeneering of file formats.

### Licence
[Appache Licence version 2](http://www.apache.org/licenses/LICENSE-2.0)

### Documentation
#### Usage
You need a annotated class representing the structure to read:
``` java
    @Struckture(length = 10) 
    public class Structure {
        @StruckField(offset = 0)
        int field1;
    }
```

The reading from file goes like this: 
``` java
    InputStream stream = new FileInputStream("filename");
    Struck<Structure> struck = Strucktor.forClass(Structure.class);
    Structure structure;
    while((structure = struck.read(stream)) != null) {
        //do something
    }
    
```
#### `@Struckture(length = 0x10)`
The structure to read must be annotated with `@Struckture` annotation with the `length` in bytes specified. The framework then tries to read chunks of `length` bytes and resolve them into a structure.

#### `@StruckField`
Struckture framework process a field only if it is annonated with `@StruckField`.
``` java
    @Struckture(length = 0x10)
    private static class Structure {
        @StruckField(offset = 0x3)
        private int processed;
        
        private int notProcessed; //always 0
    }
```
`offset` is the only common attribute for all field mappings. It sets the possition(in bytes) of the read chunk from which to read the field. `(offest = 0)` means read from the start.

`size` attribute is optional and ignored if not needed. It is useful for reading bytes, bit arrays and strings.
For example:
``` java
        @StruckField(offset = 0x3)
        private int defaultSizeFour;
    }
```
When reading int, starting the offset the next 4 bytes are used in defining the field value. This is because the int size is 4.
The list goes like this:

| Type | Size(bytes) | Type | Size(bytes) |
| ---- | ---- | ---- | ---- |
| `boolean` | 1 | `Boolean` | 1 |
| `byte` | 1 | `Byte` | 1 |
| `short` | 2 | `Short` | 2 |
| `int` | 4 | `Integer` | 4 |
| `long` | 8 | `Long` | 8 |
| `float` | 4 | `Float` | 4 |
| `double` | 8 | `Double` | 8 |
| `byte[]` | `size` attribute | `boolean[]` | `size` attribute |
| `String` | Look at the [Strings section](#reading-strings) below|

#### `@Reverse`
If `@Reverse` annotation is used then instead of **BigEndian** the value will be resolved as **LittleEndian**
```java
        @StruckField(offset = 0x8) @Reverse
        private int reverse;
```
This Annotation will infulence on all privitives and wrappers with size > 1 and byte[].

#### `@BitPosition`
When reading `boolean` the value is resolved in the following way: `value = byte[offset] > 0`
If we want to get the value of a bit in that byte then we use `@BitPosition` annotation. 

```java
        @StruckField(offset = 0xa)
        private boolean trueIfByteGreaterThan0;
        @StruckField(offset = 0xf) @BitPosition(3)
        private boolean trueIFbit3IsSet;
```

#### Reading Strings
When reading strings `size` means `maxSize`. When reading `String` with size 5, the result will have length between 0 and 5.
Encoding can be changed using `@StringEncoding()` annotation. Default encoding is **"ANSII"**
```java
        @StruckField(offset = 0x2, size = 5)
        private String string;

        @StruckField(offset = 0, size = 0x11) @StringEncoding("utf-8")
        private String utf8string;
```

#### `AllowOverlapping`
When `@Struckture(allowOverlapping = true)` is used, the check if multiple fields depend on the same bytes is disabled, so same bytes can be used in different fields. In the next example bytes 4, 5, 6, 7 are used for the integer and byte[]
```java
    @Struckture(length = 0x8, allowOverlapping = true)
    private static class OverlappingAllowedStructure {
        @StruckField(offset = 4)
        private int integer;
        @StruckField(offset = 4, size = 4)
        private byte[] bytes;
    }
```
