package com.javiersc.kotlin.compiler.playground

import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.name.CallableId
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe

val IrCall.callableId: CallableId
    get() {
        val packageFqName = symbol.descriptor.containingDeclaration.fqNameSafe
        val name = symbol.owner.name
        return CallableId(packageFqName, name)
    }
