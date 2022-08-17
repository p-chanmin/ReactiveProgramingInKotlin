package ch04

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val source = Observable.range(1, 1000)
    source.toFlowable(BackpressureStrategy.MISSING)//(1)
        .onBackpressureDrop{ println("Dropped $it;\t") }//(2)
        .map { MyItem12(it) }
        .observeOn(Schedulers.io())
        .subscribe{
            println("Rec. $it;\t")
            runBlocking { delay(100) }
        }
    runBlocking { delay(600000) }
}

data class MyItem12 (val id:Int) {
    init {
        println("MyItem init $id\t")
    }
}