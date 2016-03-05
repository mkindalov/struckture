# Struckture - Binary structure reader for Java

## Target
This java framework is for those who want to read binary structures in java. Especialy usefull for reading structures we don't fully know.

### This framework is for you
- You want to read some of the data

### This framework is **not** for you
- You want to have super fast reading of binary structures

## Licence
[Appache Licence version 2](http://www.apache.org/licenses/LICENSE-2.0)

## Examples
``` java
 @Struckture(length = 0x10, allowOverlapping = true)
    private static class BooleanTestStructure {
        @StruckField(offset = 0x3)
        private Boolean wrapper;
        @StruckField(offset = 0xa)
        private boolean primitive;
        @StruckField(offset = 0xf) @BitPosition(3)
        private boolean bit3;
        @StruckField(offset = 0xf) @BitPosition(4)
        private boolean bit4;
    }
```



