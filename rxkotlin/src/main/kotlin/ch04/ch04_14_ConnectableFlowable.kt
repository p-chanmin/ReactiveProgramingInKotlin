package ch04

import io.reactivex.rxkotlin.toFlowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val connectableFlowable = listOf("String 1","String 2","String 3","String 4","String 5").toFlowable()
        .publish()
    connectableFlowable.
    subscribe({
        println("Subscription 1: $it")
        runBlocking { delay(1000) }
        println("Subscription 1 delay")
    })
    connectableFlowable
        .subscribe({ println("Subscription 2 $it")})
    connectableFlowable.connect()
}