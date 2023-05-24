package foo.bar

import com.javiersc.kotlin.compiler.playground.annotations.log

fun box(): String {
    val x = greetings()
    return if (x == "a") {
        "OK"
    } else {
        "Fail"
    }
}

fun greetings(): String {
    log("hello")
    return "a"
}
