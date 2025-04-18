open class Javap: DefaultTask() {
    @Internal var workingDir = "./build/classes/java/main"
    @Internal var outputDir: String = ""
    @Internal lateinit var classFile: String
    @Internal lateinit var javapArguments: List<String>
    @Internal var projectDir = project.projectDir

    @TaskAction
    fun runCommand() {
        val cl = ("javap " + javapArguments.joinToString(" ") + " " + classFile + ".class")
        println("Executing ${cl}")
        val res = runCommand(cl)
        println("Result: ${res}")
    }
    fun runCommand(commandLine: String): String? {
        return try {
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
            proc.inputStream.bufferedReader().readText()
        } catch(e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

plugins {
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

tasks.register<Javap>("disasm") {
    group = "build"
    description = "Produce listings of disassembled classes"
    javapArguments = listOf("-v")
    outputDir = "src/main/java/com/newardassociates/demo"
    classFile = "com/newardassociates/demo/App.class"
    dependsOn("compileJava")
}
