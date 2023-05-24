import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    sourceSets.configureEach {
        languageSettings {
            optIn("kotlin.RequiresOptIn")
            optIn("org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi")
            optIn("org.jetbrains.kotlin.diagnostics.InternalDiagnosticFactoryMethod")
            optIn("org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI")
            optIn("org.jetbrains.kotlin.fir.PrivateForInline")
            optIn("org.jetbrains.kotlin.fir.resolve.dfa.DfaInternals")
            optIn("org.jetbrains.kotlin.fir.resolve.transformers.AdapterForResolveProcessor")
            optIn("org.jetbrains.kotlin.fir.symbols.SymbolInternals")
        }
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            languageVersion.set(KotlinVersion.KOTLIN_2_0)
        }
    }

    create<JavaExec>("generateTests") {
        classpath = sourceSets.test.get().runtimeClasspath
        mainClass.set("com.javiersc.kotlin.compiler.playground.GenerateTestsKt")

        dependsOn(":annotations:jar")
    }

    test {
        testLogging { showStandardStreams = true }

        useJUnitPlatform()
        doFirst {
            setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib", "kotlin-stdlib")
            setLibraryProperty("org.jetbrains.kotlin.test.kotlin-stdlib-jdk8", "kotlin-stdlib-jdk8")
            setLibraryProperty("org.jetbrains.kotlin.test.kotlin-reflect", "kotlin-reflect")
            setLibraryProperty("org.jetbrains.kotlin.test.kotlin-test", "kotlin-test")
            setLibraryProperty(
                "org.jetbrains.kotlin.test.kotlin-script-runtime",
                "kotlin-script-runtime"
            )
            setLibraryProperty(
                "org.jetbrains.kotlin.test.kotlin-annotations-jvm",
                "kotlin-annotations-jvm"
            )
        }

        dependsOn("generateTests")
        dependsOn(":annotations:jar")
    }
}

sourceSets {
    test {
        java.srcDirs("src/testGenerated")
    }
}

dependencies {
    implementation(project(":annotations"))
    implementation(libs.kotlin.compiler)

    testCompileOnly(libs.kotlin.compiler)
    testImplementation(libs.kotlin.compiler)

    testRuntimeOnly(libs.kotlin.test)
    testRuntimeOnly(libs.kotlin.annotationsJvm)

    testImplementation(libs.kotlin.compilerInternalTestFramework)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.platform.commons)
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.junit.platform.runner)
    testImplementation(libs.junit.platform.suiteApi)
}

fun Test.setLibraryProperty(propName: String, jarName: String) {
    val path =
        project.configurations.testRuntimeClasspath
            .get()
            .files
            .find { """$jarName-\d.*jar""".toRegex().matches(it.name) }
            ?.absolutePath
            ?: return
    systemProperty(propName, path)
}
