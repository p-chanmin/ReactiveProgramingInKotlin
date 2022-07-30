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
    runBlocking {
        val observale:Observable<Long> = Observable.interval(100,TimeUnit.MILLISECONDS)//1
        val observer:Observer<Long> = object : Observer<Long> {

            lateinit var disposable:Disposable//2

            override fun onSubscribe(d: Disposable) {
                disposable = d//3
            }

            override fun onNext(item: Long) {
                println("Received $item")
                if(item>=10 && !disposable.isDisposed) {//4
                    disposable.dispose()//5
                    println("Disposed")
                }
            }

            override fun onError(e: Throwable) {
                println("Error ${e.message}")
            }

            override fun onComplete() {
                println("Complete")
            }

        }

        observale.subscribe(observer)
        delay(1500)//6
    }
}