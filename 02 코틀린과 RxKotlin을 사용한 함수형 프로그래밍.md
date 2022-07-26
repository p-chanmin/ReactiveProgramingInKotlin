## 코틀린과 RxKotlin을 사용한 함수형 프로그래밍



**함수형 프로그래밍**

- 불변의 데이터를 사용한 수학적인 함수의 평가를 통해 프로그램을 구조화하는 동시에 상태 변화를 방지한다



### 함수형 프로그래밍의 기초

- 람다
- 순수 함수
- 고차 함수
- 인라인 함수



#### 람다 표현식

일반적으로 이름이 없는 익명 함수를 의미

```kotlin
val sum = { x: Int, y: Int -> x + y }
println("Sum ${sum(12, 14)}")
val anonymousMult = {x:Int -> (Random().nextInt(15)+1) * x}
println("random output ${anonymousMult(2)}")
```



#### 순수 함수

함수의 반환값이 인수/매개 변수에 전적으로 의존한다.

```kotlin
fun square(n:Int):Int {//(1)
    return n*n
}

fun main(args: Array<String>) {
    println("named pure func square = ${square(3)}")
    val qube = {n:Int -> n*n*n}//(2)
    println("lambda pure func qube = ${qube(3)}")
}
```

(1), (2) 모두 순수 함수로서 (1)은 이름이 있는 함수, (2)는 람다이다.

값을 어떤 함수에 n번 전달해도 매번 동일 값이 반환 된다.

**순수 함수에는 부작용<sup>Side Effect</sup>이 없다.**

> 부작용 : 함수 or 표현식은 자신의 범위 외부의 일부 상태를 수정하거나 호출 함수 또는 외부 세계에 영향을 끼치는 상호 작용이 존재하는 경우 부작용이 있다고 한다.



#### 고차 함수

함수를 인자로 받아들이거나 반환하는 함수를 고차 함수라고 부름.

```kotlin
fun highOrderFunc(a:Int, validityCheckFunc:(a:Int)->Boolean) {//(1)
    if(validityCheckFunc(a)) {//(2)
        println("a $a is Valid")
    } else {
        println("a $a is Invalid")
    }
}

fun main(args: Array<String>) {
    highOrderFunc(12,{ a:Int -> isEven(a)})//(3)
    highOrderFunc(19,{ a:Int -> isEven(a)})
}
```



#### 인라인 함수

프로그램의 성능 및 메모리 최적화를 향상시키는 개선된 기능

함수 정의를 호출할 때 마다 그것을 인라인으로 대체할 수 있도록 컴파일러가 지시할 수 있음

```kotlin
inline fun doSomeStuff(a:Int = 0) = a+(a*a)

fun main(args: Array<String>) {
    for (i in 1..10) {
        println("$i Output ${doSomeStuff(i)}")
    }
}
```



### 코루틴

```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun longRunningTsk():Long {//(1)
    val time = measureTimeMillis {//(2)
        println("Please wait")
        delay(2000)//(3)
        println("Delay Over")
    }
    return time
}

fun main(args: Array<String>) {
    runBlocking {//(4)
        val exeTime = longRunningTsk()//(5)
        println("Execution Time is $exeTime")
    }
}
```

- 메인 스레드에서 대기하게 된다.

```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.async

suspend fun longRunningTsk():Long {//(1)
    val time = measureTimeMillis {//(2)
        println("Please wait")
        delay(2000)//(3)
        println("Delay Over")
    }
    return time
}

fun main(args: Array<String>) {
    runBlocking {
        val time = async{ longRunningTsk() }
        println("Print after async ")
        println("printing time ${time.await()}")
    }

}
```

- runBlocking과 aync를 사용



#### 시퀸스 생성

```kotlin
fun main(args: Array<String>) {

    val fibonacciSeries = sequence {
        var a = 0
        var b = 1

        yield(a)
        yield(b)

        while (true){
            val c = a+b
            yield(c)
            a=b
            b=c
        }
    }

    println(fibonacciSeries.take(10).joinToString())
}

```

- 시퀸스를 통한 피보나치 프로그램



> 참고
>
> https://underdog11.tistory.com/entry/Kotlin-%EC%BD%94%EB%A3%A8%ED%8B%B4-Coroutine-async%EA%B3%BC-await-LifecycleScope%EA%B3%BC-ViewModelScope-3%ED%8E%B8



### 모나드<sup>Monad</sup> - Maybe

```kotlin
fun main(args: Array<String>) {
    val maybeValue: Maybe<Int> = Maybe.just(14)//1
    maybeValue.subscribeBy(//2
        onComplete = {println("Completed Empty")},
        onError = {println("Error $it")},
        onSuccess = { println("Completed with value $it")}
    )
    val maybeEmpty:Maybe<Int> = Maybe.empty()//3
    maybeEmpty.subscribeBy(
        onComplete = {println("Completed Empty")},//4
        onError = {println("Error $it")},//5
        onSuccess = { println("Completed with value $it")}//6
    )
}
```

- Maybe는 모나드로서 Int값을 캡슐화하고 추가 기능을 제공한다.
- Maybe는 모나드로서 값을 포함할 수도 있고, 포함하지 않을 수도 있으며 값, 오류와 관계없이 완료된다.
- 오류가 발생 했을 때 onError 호출
- 값이 존재하면 onSuccess 호출
- 값도 없고 오류도 없을 경우 onComplete 호출
