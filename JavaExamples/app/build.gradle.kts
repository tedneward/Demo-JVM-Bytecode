plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    alias(libs.plugins.kotlin.jvm)
    
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    testImplementation(libs.junit)

    // This dependency is used by the application.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "com.newardassociates.demo.App"
}

open class Javap: DefaultTask() {
    @Internal var workingDir = "./build/classes/java/main"
    @Internal var outputDir: String = ""
    @Internal lateinit var classFiles: List<String>
    @Internal lateinit var javapArguments: List<String>
    @Internal var projectDir = project.projectDir

    @TaskAction
    fun runCommand() {
        logger.warn("Running 'disasm' replaces the marked javap files with newly-generated content!")
        for (classFile in classFiles) {
            val commandLine = ("javap " + javapArguments.joinToString(" ") + " " + classFile)
            try {
                val workingDir = File(projectDir, workingDir)
                val outputDir = File(projectDir, outputDir)
                val parts = commandLine.split("\\s".toRegex())
                // Transform the classFile string/path to a dotted name
                val dottedClassFile = classFile.split("/").joinToString(".")
                val outputFile = File(outputDir, dottedClassFile + ".bytecode")
                val proc = ProcessBuilder(*parts.toTypedArray())
                        .directory(workingDir)
                        .redirectOutput(outputFile)
                        .redirectError(ProcessBuilder.Redirect.PIPE)
                        .start()

                proc.waitFor(60, TimeUnit.MINUTES)
                println("Success processing ${classFile}")
            } catch(e: Exception) {
                e.printStackTrace()
                println("Failure processing ${classFile}: ${e.message}")
            }
        }
    }
}

tasks.register<Javap>("disasm") {
    group = "build"
    description = "Produce disassembly listings of Java code"
    javapArguments = listOf("-v")
    outputDir = ""
    classFiles = listOf(
        "com/newardassociates/demo/App.class",
        "com/newardassociates/demo/Greeter.class",
        "com/newardassociates/demo/Math.class",
        "com/newardassociates/demo/Outer.class",
        "com/newardassociates/demo/Outer\$Inner.class",
        "com/newardassociates/demo/StringConcat.class",
        "com/newardassociates/demo/Varargs.class",
    )
    dependsOn("compileJava")
}
tasks.register<Javap>("disasm-kotlin") {
    workingDir = "./build/classes/kotlin/main"
    group = "build"
    description = "Produce disassembly listings of Kotlin code"
    javapArguments = listOf("-v")
    outputDir = ""
    classFiles = listOf(
        "com/newardassociates/demo/KotlinApp.class",
        "com/newardassociates/demo/KotlinAppKt.class",
    )
    dependsOn("compileKotlin")
}
