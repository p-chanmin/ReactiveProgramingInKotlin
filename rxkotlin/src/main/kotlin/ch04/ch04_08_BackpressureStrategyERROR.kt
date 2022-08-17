package ch04

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.ERROR)
        .map { MyItem8(it) }
        .observeOn(Schedulers.computation())
        .subscribe{
            println("Rec $it;\t")
            runBlocking { delay(100) }
        }
    runBlocking { delay(70000) }
}

data class MyItem8 (val id:Int) {
    init {
        println("init $id;\t")
    }
}