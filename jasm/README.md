## Example JASM Gradle project

This is an example of using the JASM assembler and gradle plugin. 

This project uses [The JASM Gradle plugin](https://plugins.gradle.org/plugin/com.roscopeco.jasm) (which is officially discountined/archived, which makes me sad).

```
./gradlew clean build test run
```

Note that JASM requires Java 11 or above - it will not work with lower versions.
