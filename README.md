# Struckture - Binary structure reader for Java

## Target
This java framework is for those who want to read binary structures in java. Especialy usefull for reading structures we don't fully know.

### This framework is for you
- You want to read some of the data

### This framework is **not** for you
- You want to have super fast reading of binary structures

## Licence
[Appache Licence version 2](http://www.apache.org/licenses/LICENSE-2.0)

## Documentation
### General usage
You need a well annotated class representing the structure to read:
``` java
    @Struckture(length = 10) 
    public class Structure {
        @StruckField(offset = 0)
        int field1;
    }
```

The reading goes like this: 
``` java
    InputStream stream = new FileInputStream("filename");
    Struck<Structure> struck = Strucktor.forClass(Structure.class);
    Structure structure = struck.read(stream);
```
### @Struckture(length = 0x10)
The structure to process must be annotated with @Struckture annotation with the length in bytes specified. The framework then tries to read chunks of *length* bytes and resolve them into a structure.

### @StruckField
Struckture framework process a field only if it is annonated with @StruckField. If not is ignored.
``` java
    @Struckture(length = 0x10)
    private static class BooleanTestStructure {
        @StruckField(offset = 0x3)
        private int processed;
        
        private int notProcessed; //always 0
    }
```
*offset* is the only common attribute for all mappings. It sets the possition(in bytes) from which to read the field. (offest = 0) means read from start.

*size* attribute is optional and ignored if not needed. It is useful for reading bytes, bit arrays and strings.
For example:
``` java
        @StruckField(offset = 0x3)
        private int defaultSizeFour;
    }
```
reading int means that starting the offset the next 4 bytes are used in defining the field value.
The list goes like this:

| Type | Size(bytes) |
| ---- | ---- |
| boolean | 1 |
| Boolean | 1 |
| byte | 1 |
| Byte | 1 |
| short | 2 |
| Short | 2 |
| int | 4 |
| Integer | 4 |
| long | 8 |
| Long | 8 |
| float | 4 |
| Float | 4 |
| double | 8 |
| Double | 8 |
| byte[] | size attribute |
| boolean[] | size attribute |
| String | size attribute?? Look at the Strings section|

### @Reverse
If @Reverse annotation is used then instead of BigEndian the value will be resolved as LittleEndian
```java
        @StruckField(offset = 0x8) @Reverse
        private int reverse;
```
This Annotation will infulence on all privitive and wrapper types with size > 1 and byte[].
### @BitPosition
When reading boolean the value is resolved in the following way: value = byte[offset] > 0
If we want to get the value of a bit in that byte then we use @BitPosition annotation. 

```java
        @StruckField(offset = 0xa)
        private boolean trueIfByteGreaterThan0;
        @StruckField(offset = 0xf) @BitPosition(3)
        private boolean trueIFbit3IsSet;
```
### Reading strings
When reading strings size means maxSize. If the string is terminated before then the length of the string can be less than 5.
Encoding can be changed using @StringEncoding() annotation. Default encoding is "ANSII"
```java
        @StruckField(offset = 0x2, size = 5)
        private String string;

        @StruckField(offset = 0, size = 0x11) @StringEncoding("utf-8")
        private String utf8string;
```
### allowOverlapping
When @Struckture(allowOverlapping = true) the check if multiple fields depend on the same bytes is disabled, so same bytes can be used in different fields. In the next example bytes 4, 5, 6, 7 are used for the integer and byte[]
```java
    @Struckture(length = 0x8, allowOverlapping = true)
    private static class OverlappingWithAllowTestStructure {
        @StruckField(offset = 4)
        private int integer;
        @StruckField(offset = 4, size = 4)
        private byte[] bytes;
    }
```
