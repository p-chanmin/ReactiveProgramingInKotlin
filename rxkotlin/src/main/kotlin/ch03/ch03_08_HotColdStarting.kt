package ch03

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

fun main(args: Array<String>) {
    val observable: Observable<String> = listOf("String 1","String 2","String 3","String 4").toObservable()//1

    observable.subscribe({//2
        println("Received $it")
    },{
        println("Error ${it.message}")
    },{
        println("Done")
    })

    observable.subscribe({//3
        println("Received $it")
    },{
        println("Error ${it.message}")
    },{
        println("Done")
    })

}