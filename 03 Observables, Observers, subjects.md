## Observables, Observers, subjects



### Observable

- **옵저버블(Observable)** 은 그 **컨슈머(Observer)** 가 소비할 수 있는 값을 산출해 내는 기본 계산 작업을 갖고 있다.
- **옵저버블(Observable)** 은 **컨슈머(Observer)** 에게 값을 푸시<sup>Push</sup>하는 역할을 한다.
- **옵저버블**은 일련의 연산자를 거친 아이템을 **최종 옵저버**로 보내는 **푸시 기반**의 조합 가능한 **이터레이터**이다.



### 옵저버블(Observable)이 동작하는 방법

- onNext : 옵저버블은 모든 아이템을 하나씩 이 메서드에 전달한다.
- onComplete : 모든 아이템이 onNext 메서드를 통과하면 옵저버블은 onComplete 메서드를 호출한다
- onError : 옵저버블에서 에러가 발생하면 onError 메서드가 호출돼 에러를 처리한다.
- onSubscriber : Observable이 새로운 Observer를 구독할 때마다 호출된다.



### Observable.create 메서드

- Observable.create 메서드로 옵저버블을 생성한다.

```kotlin
val observable:Observable<String> = Observable.create<String> {//1
        it.onNext("Emit 1")
        it.onNext("Emit 2")
        it.onNext("Emit 3")
        it.onNext("Emit 4")
        it.onComplete()
    }
```

[**ch03_01_ObservableCreateExmpl.kt**](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_01_ObservableCreateExmpl.kt)



### Observable.from 메서드

- from 메서드의 도움으로 거의 모든 코틀린 구조체로부터 Observable 인스턴스를 생성할 수 있다

```kotlin
val list = listOf("String 1","String 2","String 3","String 4")
val observableFromIterable: Observable<String> = Observable.fromIterable(list)//1
observableFromIterable.subscribe(observer)
```

**[ch03_02_ObservableFromExmpl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_02_ObservableFromExmpl.kt)**



### toObservable의 확장 함수 이해

- 코틀린의 확장<sup>extension</sup>함수 덕분에 List와 같이 어떠한 Iterable 인스턴스도 Observerbale로 큰 어려움 없이 변경이 가능하다.

```kotlin
val list:List<String> = listOf("String 1","String 2","String 3","String 4")

val observable:Observable<String> = list.toObservable()
```

**[ch03_03_ToObservable.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_03_ToObservable.kt)**



### Observable.just 메서드 이해

- 넘겨진 인자만을 배출하는 옵저버블을 생성한다.
- Iterable 인스턴스를 Observable.just에 단일 인자로 넘기면 전체 목록을 하나의 아이템으로 배출하는데, 이는 Iterable 내부의 각각의 아이템을 Observable로 생성하는 Observable.from과는 다르다.

```kotlin
Observable.just("A String").subscribe(observer)
Observable.just(54).subscribe(observer)
Observable.just(listOf("String 1","String 2","String 3","String 4")).subscribe(observer)
Observable.just(mapOf(Pair("Key 1","Value 1"),Pair("Key 2","Value 2"),Pair("Key 3","Value 3"))).subscribe(observer)
Observable.just(arrayListOf(1,2,3,4,5,6)).subscribe(observer)
Observable.just("String 1","String 2","String 3").subscribe(observer)//1
```

**[ch03_04_ObservaleJustExmpl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_04_ObservaleJustExmpl.kt)**



### Observable의 다른 팩토리 메서드

**[ch03_05_ObservableFactoryExampl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_05_ObservableFactoryExampl.kt)**

##### Observable.range(start, count)

- 옵저버블을 생성하고 start로부터 시작해 count 만큼의 정수를 내보낸다

##### Observable.empty<T>()

- 옵저버블을 생성하고 onNext() 항목을 내보내지 않고 즉시 onComplete()를 발생시킨다.

##### Observable.interval(period, TIMEUNIT)

- 옵저버블을 생성하고 period 간격만큼의 숫자를 0부터 순차적으로 내보낸다.

##### Observable.timer(delay, TIMEUNIT)

- 옵저버블을 생성하고 delay 시간이 경과한 후에 한 번만 실행된다.



### 구독자 : Observer 인터페이스

- RxKotlin 1.x의 구독자(Subscriber)는 RxKotlin 2.x에서 옵저버(Observer)로 변경됐다.



### 구독과 해지

```kotlin
val observer:Observer<Long> = object : Observer<Long> {

            lateinit var disposable:Disposable

            override fun onSubscribe(d: Disposable) {
                disposable = d//3
            }

            override fun onNext(item: Long) {
                println("Received $item")
                if(item>=10 && !disposable.isDisposed) {
                    disposable.dispose()//5
                    println("Disposed")
                }
            }
```

**[ch03_07_DisposableExample.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_07_DisposableExample.kt)**

- onSubscribe 메서드 내에서 수신된 매개 변수 값을 disposable 변수에 할당한다
- onNext 메서드 내부에 검사 로직을 통해 해지 상태를 확인하고 disposable.dispose()를 통해 해지한다.



### 핫, 콜드 옵저버블

**[ch03_08_HotColdStarting.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_08_HotColdStarting.kt)**

#### 콜드 옵저버블

- 옵저버블은 특징적인 기능을 갖고 있는데, 각 구독마다 처음부터 아이템을 배출하는 것을 **콜드 옵저버블**이라고 한다.
- 콜드 옵저버블은 구독 시에 실행을 시작하고 subscribe가 호출되면 아이템을 푸시하기 시작하는데 각 구독 시에 실행을 시작하고 subscribe가 호출되면 아이템을 푸시하기 시작하는데 각 구독에서 아이템의 동일한 순서를 푸시한다.



#### 핫 옵저버블

- 핫 옵저버블은 콜드 옵저버블과 반대로, 배출을 시작하기 위해 구독할 필요가 없다.
- 핫 옵저버블은 데이터보다는 이벤트와 유사하다.
- 이벤트에는 데이터가 포함될 수 있지만, 시간에 민감한 특징을 가지는데 최근 가입한 Observer가 이전에 내보낸 데이터를 놓칠 수 있기 때문이다.



##### ConnectableObservable

- 가장 유용한 핫 옵저버블 중 하나이다.
- ConnectableObservable은 옵저버블, 심지어 콜드 옵저버블을 핫 옵저버블로 바꿀 수 있다.
- subscribe 호출로 배출을 시작하는 대신 connect 메서드를 호출한 후에 활성화 된다.
- connect를 호출하기 전에 반드시 subscribe를 호출해야 한다.
- connect를 호출한 후 구독하는 모든 호출은 이전에 생성된 배출을 놓치게 된다.

```kotlin
val connectableObservable = listOf("String 1","String 2","String 3","String 4","String 5").toObservable().publish()//1
connectableObservable.subscribe({ println("Subscription 1: $it") })//2
connectableObservable.map(String::reversed)//3
.subscribe({ println("Subscription 2 $it")})//4
connectableObservable.connect()//5
```

**[ch03_09_ConnectableObservableExmpl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_09_ConnectableObservableExmpl.kt)**

**[ch03_10_ConnectableObservableExmpl2.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_10_ConnectableObservableExmpl2.kt)**

- ConnectableObservable은 connect 메서드 이전에 호출된 모든 subscriptions를 연결하고 모든 옵저버에 단일 푸시를 전달한다.
- toObservable() 메서드로 옵저버블을 생성하고 publish() 연산자로 콜드 옵저버블을 ConnectableObservable로 변환했다.
- 옵저버블에서 단 한 번의 배출로 모든 구독/관찰자에게 배출을 전달하는 메커니즘을 **멀티캐스팅**이라고 한다.



##### Subjects

- 기본적으로 옵저버블과 옵저버의 조합으로 두 가지 모두의 공통된 동작을 갖고 있다.
  - 옵저버블이 가져야 하는 모든 연산자를 갖고 있다.
  - 옵저버와 마찬가지로 배출된 모든 값에 접근할 수 있다.
  - Subject가 완료/오류/구독 해지된 후에는 재사용 할 수 없다.
  - onNext를 사용해 값을 Subject에 전달하면 Observable에서 접근 가능하게 된다.
- 핫 옵저버블과 마찬가지로 내부 Observer 목록을 유지하고 배출 시에 가입한 모든 옵저버에게 단일 푸시를 전달한다.

```kotlin
val observable = Observable.interval(100, TimeUnit.MILLISECONDS)//1
val subject = PublishSubject.create<Long>()//2
observable.subscribe(subject)//3
subject.subscribe({//4
println("Received $it")
})
runBlocking { delay(1100) }//5
```

**[ch03_11_SubjectExmpl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_11_SubjectExmpl.kt)**

```kotlin
val observable = Observable.interval(100, TimeUnit.MILLISECONDS)//1
val subject = PublishSubject.create<Long>()//2
observable.subscribe(subject)//3
subject.subscribe({//4
println("Subscription 1 Received $it")
})
runBlocking { delay(1100) }//5
subject.subscribe({//6
println("Subscription 2 Received $it")
})
runBlocking { delay(1100) }//7
```

**[ch03_12_SubjectExmpl2.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_12_SubjectExmpl2.kt)**

- Subject는 콜드 옵저버블과 같이 행동을 반복하지 않는다.
- Subject는 모든 옵저버에게 전달된 배출을 중계하고, 콜드 옵저버블을 핫 옵저버블로 변경시킨다.



### 다양한 구독자



#### AsyncSubject

- AsyncSubject는 수신 대기 중인 소스 옵저버블의 마지막 값과 배출만 전달한다.
- 다시말해 AsyncSubject는 마지막 값을 한 번만 배출한다.

```kotlin
val observable = Observable.just(1,2,3,4)//1
val subject = AsyncSubject.create<Int>()//2
observable.subscribe(subject)//3
subject.subscribe({//4
    //onNext
    println("Received $it")
},{
    //onError
    it.printStackTrace()
},{
    //onComplete
    println("Complete")
})
subject.onComplete()//5
```

**[ch03_13_AsyncSubjectExmpl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_13_AsyncSubjectExmpl.kt)**

**[ch03_14_AsyncSubjectExmpl2.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_14_AsyncSubjectExmpl2.kt)**

#### PublishSubject

- onNext 메서드 또는 다른 구독을 통해 값을 받았는지 여부에 관계없이 구독 시점에서 이어지는 모든 값을 배출한다.
- 가장 많이 사용되는 Subject 변형



#### BehaviorSubject

- BehaviorSubject는 멀티 캐스팅으로 동작하는데, 구독 전의 마지막 아이템과 구독 후 모든 아이템을 배출한다.

**[ch03_15_BehaviorSubjectExmpl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_15_BehaviorSubjectExmpl.kt)**



#### ReplaySubject

- ReplaySubject는 갖고 있는 모든 아이템을 옵저버의 구독 시점과 상관없이 다시 전달하는데 콜드 옵저버블과 유사하다.

**[ch03_16_ReplaySubjectExmpl.kt](https://github.com/p-chanmin/ReactiveProgramingInKotlin/blob/main/rxkotlin/src/main/kotlin/ch03/ch03_16_ReplaySubjectExmpl.kt)**
