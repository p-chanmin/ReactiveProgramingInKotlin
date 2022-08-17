package ch04

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    val flowable = Flowable.generate<Int> {
        it.onNext(GenerateFlowableItem.item)
    }//(1)

    flowable
        .map { MyItemFlowable(it) }
        .observeOn(Schedulers.io())
        .subscribe {
            runBlocking { delay(100) }
            println("Next $it")
        }//(2)

    runBlocking { delay(700000) }
}

data class MyItemFlowable(val id:Int) {
    init {
        println("MyItemFlowable Created $id")
    }
}

object GenerateFlowableItem {
    var item:Int = 0//(3)
    get() {
        field+=1
        return field//(4)
    }
}