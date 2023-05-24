package com.javiersc.kotlin.compiler.playground

import org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy.Companion.DEFAULT
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1
import org.jetbrains.kotlin.diagnostics.error1
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirBasicDeclarationChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction
import org.jetbrains.kotlin.fir.declarations.hasAnnotation
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.psi.KtElement

class FirPlaygroundExtensionRegistrar : FirExtensionRegistrar() {

    override fun ExtensionRegistrarContext.configurePlugin() {
        +::Foo
    }
}

private class Foo(session: FirSession) : FirAdditionalCheckersExtension(session) {

    override val declarationCheckers: DeclarationCheckers =
        object : DeclarationCheckers() {

            override val basicDeclarationCheckers: Set<FirBasicDeclarationChecker> =
                setOf(
                    object : FirBasicDeclarationChecker() {

                        override fun check(
                            declaration: FirDeclaration,
                            context: CheckerContext,
                            reporter: DiagnosticReporter
                        ) {

                            if (declaration.hasAnnotation(playgroundAnnotationClassId, session)) {
                                reporter.reportOn(
                                    source =
                                        (declaration as FirSimpleFunction).returnTypeRef.source,
                                    factory = PLAYGROUND_ERROR,
                                    a = "Playground test",
                                    context = context,
                                    positioningStrategy = DEFAULT,
                                )
                            }
                        }
                    }
                )
        }
}

val PLAYGROUND_ERROR: KtDiagnosticFactory1<String> by error1<KtElement, String>()
