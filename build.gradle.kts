plugins {
    `java-library`
    `maven-publish`
}

group = "com.longx.intelligent.lib"
version = "1.0"
val manualBuildTime = "2026 年 9 月 1 日"

val generatedSourcesDir = layout.projectDirectory.dir("src/main/java")

val generateBuildInfoTask = tasks.register("generateBuildInfo") {
    inputs.property("version", project.version.toString())
    inputs.property("buildTime", manualBuildTime)
    outputs.dir(generatedSourcesDir)
    doLast {
        val outDir = generatedSourcesDir.asFile
        val packageDir = File(outDir, "com/longx/intelligent/lib/longsecurity/_build")
        if (!packageDir.exists()) {
            packageDir.mkdirs()
        }
        File(packageDir, "BuildInfo.java").writeText("""
            package com.longx.intelligent.lib.longsecurity._build;

            public class BuildInfo {
                public static final String VERSION = "${project.version}";
                public static final String BUILD_TIME = "$manualBuildTime";
            }
        """.trimIndent())
    }
}

tasks.compileJava {
    dependsOn(generateBuildInfoTask)
}

repositories {
    maven {
        name = "central-aliyun"
        url = uri("https://maven.aliyun.com/repository/central")
    }
    maven {
        name = "jcenter-central-aliyun"
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        name = "google-aliyun"
        url = uri("https://maven.aliyun.com/repository/google")
    }
    mavenCentral()
    maven {
        name = "JetBrainsReleases"
        url = uri("https://www.jetbrains.com/intellij-repository/releases")
    }
    maven("https://cache-redirector.jetbrains.com/intellij-dependencies")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:3.1.5")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    compileOnly("org.springframework:spring-web:6.0.13")
    compileOnly("org.slf4j:slf4j-api:2.0.9")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}