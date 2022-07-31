package ch02

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun longRunningTsk():Long {//(1)
    val time = measureTimeMillis {//(2)
        println("Please wait")
        delay(2000)//(3)
        println("Delay Over")
    }
    return time
}

fun main(args: Array<String>) {
    runBlocking {
        val time = async{ longRunningTsk() }
        println("Print after async ")
        println("printing time ${time.await()}")
    }
}
