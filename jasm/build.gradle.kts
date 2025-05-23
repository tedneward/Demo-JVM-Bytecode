plugins {
    java
    id("com.roscopeco.jasm") version "0.7.0"
    application
}

application {
    mainClass.set("com.newardassociates.demo.App")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
