package com.javiersc.kotlin.compiler.playground

import com.javiersc.kotlin.compiler.playground.runners.AbstractBoxTest
import com.javiersc.kotlin.compiler.playground.runners.AbstractDiagnosticTest
import org.jetbrains.kotlin.generators.generateTestGroupSuiteWithJUnit5

fun main() {
    generateTestGroupSuiteWithJUnit5 {
        testGroup(testDataRoot = "src/testData", testsRoot = "src/testGenerated") {
            testClass<AbstractDiagnosticTest> { model("diagnostics") }

            testClass<AbstractBoxTest> { model("box") }
        }
    }
}
