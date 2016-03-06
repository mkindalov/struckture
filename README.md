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
|Type|Size|
|---|---|
|byte|1|
|byte|1|
|byte|1|
|byte|1|
|byte|1|

