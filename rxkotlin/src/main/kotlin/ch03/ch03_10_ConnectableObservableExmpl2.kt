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
    val connectableObservable = Observable.interval(100,TimeUnit.MILLISECONDS)
        .publish()//1
    connectableObservable.
    subscribe({ println("Subscription 1: $it") })//2
    connectableObservable
        .subscribe({ println("Subscription 2 $it")})//3
    connectableObservable.connect()//4
    runBlocking { delay(500) }//5

    connectableObservable.
    subscribe({ println("Subscription 3: $it") })//6
    runBlocking { delay(500) }//7
}