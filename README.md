# Demo-JVM-Bytecode
Code exploring JVM bytecode

## jasm
This is an example showing how to use the "jasm" Java assembler. Note that the Gradle plugin (and the assembler itself, I think) is officially archived, so don't expect anything by way of support for it. That said, the JVM bytecode set hasn't changed much, so it's not like there needs to be much, if you ask me.

## asm
This is an example of using the OWeb ASM library to generate a class from pure ether, then feeding it into the JVM through a dirt-simple "ByteArrayClassLoader", who maintains a flat namespace of name-to-bytearray pairs of bytecode to load.

