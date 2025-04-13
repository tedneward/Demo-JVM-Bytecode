# Demo-JVM-Bytecode
Code exploring JVM bytecode

## JavaExamples
This is simply a collection of Java classes that exhibit interesting Java language constructs or patterns, so that they get compiled to .class files and thus viewable via the `javap` tool. The root of this project directory has a script, "disasm", that knows where the files are compiled to (in the app's `build` directory) and sets the classpath to `javap` accordingly. So, for example, to see what varargs parameters look like, do a `./gradlew build` and then `disasm Varargs`. (The script uses the `-v` command-line param to `javap`, so brace yourself for quite the voluminous output.)

## jasm
This is an example showing how to use the "jasm" Java assembler. Note that the Gradle plugin (and the assembler itself, I think) is officially archived, so don't expect anything by way of support for it. That said, the JVM bytecode set hasn't changed much, so it's not like there needs to be much, if you ask me.

## asm
This is an example of using the OWeb ASM library to generate a class from pure ether, then feeding it into the JVM through a dirt-simple "ByteArrayClassLoader", who maintains a flat namespace of name-to-bytearray pairs of bytecode to load.

## Javassist
Hacking away at Java classes using the Javassist library.

## ByteBuddy (FORTHCOMING)
Hacking away at Java classes using the ByteBuddy library.
