package ch01

import io.reactivex.subjects.PublishSubject

fun main(){
    var subject = PublishSubject.create<Int>()

    subject.map( { isEven(it) } ).subscribe(
        { println("The number is ${(if (it) "Even" else "Odd")}")}
    )
    subject.onNext(4)
    subject.onNext(9)
}

fun isEven(n:Int):Boolean = ( n % 2 == 0)