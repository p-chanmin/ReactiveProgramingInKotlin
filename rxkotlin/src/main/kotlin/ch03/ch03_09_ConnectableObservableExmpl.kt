package ch03

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val connectableObservable = listOf("String 1","String 2","String 3","String 4","String 5").toObservable().publish()//1
    connectableObservable.subscribe({ println("Subscription 1: $it") })//2
    connectableObservable.map(String::reversed)//3
        .subscribe({ println("Subscription 2 $it")})//4
    connectableObservable.connect()//5

    connectableObservable.
    subscribe({ println("Subscription 3: $it") })//6 //Will never get called
}