package com.javiersc.kotlin.compiler.playground

import com.javiersc.kotlin.compiler.playground.annotations.Playground
import org.jetbrains.kotlin.descriptors.runtime.structure.classId
import org.jetbrains.kotlin.fir.extensions.AnnotationFqn
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name

val playgroundAnnotationClassId = Playground::class.java.classId

val playgroundAnnotationFqName = AnnotationFqn(Playground::class.java.name)

val playgroundAnnotationName = Name.identifier(Playground::class.java.name)

val playgroundAnnotation =
    ClassId(
        AnnotationFqn(Playground::class.java.name),
        Name.identifier(Playground::class.java.name)
    )
