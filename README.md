# Demo-JVM-Bytecode
Code exploring JVM bytecode

## JavaExamples
This is simply a collection of Java (and Kotlin!) source code that exhibit interesting Java language constructs or patterns, so that they get compiled to .class files and thus viewable via the `javap` tool. 

The root of this project directory has a script, "disasm"/"disasm.bat", that knows where the files are compiled to (in the app's `build` directory) and sets the classpath to `javap` accordingly. So, for example, to see what varargs parameters look like, do a `./gradlew build` and then `disasm Varargs`. (The script uses the `-v` command-line param to `javap`, so brace yourself for quite the voluminous output.)

The app Gradle script (`build.gradle.kts`) also has two targets, `disasm` and `disasm-kotlin`, that will invoke `javap` on the compiled classfiles and pump the output into fully-qualified-classname "bytecode" files in the root of the app directory. (There's also a similar set in the "src" directories, but only because I use them as references for my presentations--they might be slightly out-of-sync at any given time.)

## jasm
This is an example showing how to use the "jasm" Java assembler. Note that the Gradle plugin (and the assembler itself, I think) is officially archived, so don't expect anything by way of support for it. That said, the JVM bytecode set hasn't changed much, so it's not like there needs to be much, if you ask me.

## asm
This is an example of using the OWeb ASM library to generate a class from pure ether, then feeding it into the JVM through a dirt-simple "ByteArrayClassLoader", who maintains a flat namespace of name-to-bytearray pairs of bytecode to load.

## Javassist
Hacking away at Java classes using the Javassist library.
