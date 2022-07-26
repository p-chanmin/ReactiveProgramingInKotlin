import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.async

suspend fun longRunningTsk():Long {//(1)
    val time = measureTimeMillis {//(2)
        println("Please wait")
        delay(2000)//(3)
        println("Delay Over")
    }
    return time
}

fun main(args: Array<String>) {
    val time = async(CommonPool) { longRunningTsk() }
    println("Print after async ")
    runBlocking { println("printing time ${time.await()}") }
}
