package foo.bar

import com.javiersc.kotlin.compiler.playground.annotations.Playground

@Playground
fun greetings(): <!PLAYGROUND_ERROR!>String<!> {
    return "Hi"
}
