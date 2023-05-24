package com.javiersc.kotlin.compiler.playground

import kotlin.math.exp
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrFile
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrCallableReference
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid

class IrPlaygroundExtension : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {

        moduleFragment.acceptChildrenVoid(
            object : IrElementVisitorVoid {

                var lastFile: IrFile? = null
                var logCalls: MutableList<IrCall> = mutableListOf()

                override fun visitElement(element: IrElement) {
                    super.visitElement(element)
                    element.acceptChildrenVoid(this)
                    element
                }

                override fun visitFile(declaration: IrFile) {
                    super.visitFile(declaration)
                    lastFile = declaration
                    lastFile
                }

                override fun visitCall(expression: IrCall) {
                    super.visitCall(expression)
                    val context = pluginContext
                    val module = moduleFragment
                    context.referenceFunctions(callableId = expression.callableId)

//                    if (expression.symbol.owner.annotations) {
//                        logCall = expression
//                    }
//                    lastFile
//                    expression
                }
            },
        )
    }
}
