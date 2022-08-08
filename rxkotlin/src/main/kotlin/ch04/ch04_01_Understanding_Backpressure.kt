package ch04

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val observable = Observable.just(1,2,3,4,5,6,7,8,9)//(1)
    observable
        .map { MyItem(it) }//(2)
        .observeOn(Schedulers.computation())//(3)
        .subscribe({//(4)
            println("Received $it")
            runBlocking { delay(200) }//(5)
        })
    runBlocking { delay(2000) }//(6)
}

data class MyItem (val id:Int) {
    init {
        println("MyItem Created $id")
    }
}