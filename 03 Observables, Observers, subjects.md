## Observables, Observers, subjects



### Observable

- **옵저버블(Observable)**은 그 **컨슈머(Observer)**가 소비할 수 있는 값을 산출해 내는 기본 계산 작업을 갖고 있다.
- **옵저버블(Observable)**은 **컨슈머(Observer)**에게 값을 푸시<sup>Push</sup>하는 역할을 한다.
- **옵저버블**은 일련의 연산자를 거친 아이템을 **최종 옵저버**로 보내는 **푸시 기반**의 조합 가능한 **이터레이터**이다.



### 옵저버블(Observable)이 동작하는 방법

- onNext : 옵저버블은 모든 아이템을 하나씩 이 메서드에 전달한다.
- onComplete : 모든 아이템이 onNext 메서드를 통과하면 옵저버블은 onComplete 메서드를 호출한다
- onError : 옵저버블에서 에러가 발생하면 onError 메서드가 호출돼 에러를 처리한다.



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



### Observable.from 메서드

- from 메서드의 도움으로 거의 모든 코틀린 구조체로부터 Observable 인스턴스를 생성할 수 있다

```kotlin
val list = listOf("String 1","String 2","String 3","String 4")
val observableFromIterable: Observable<String> = Observable.fromIterable(list)//1
observableFromIterable.subscribe(observer)
```



### toObservable의 확장 함수 이해

- 코틀린의 확장<sup>extension</sup>함수 덕분에 List와 같이 어떠한 Iterable 인스턴스도 Observerbale로 큰 어려움 없이 변경이 가능하다.

```kotlin
val list:List<String> = listOf("String 1","String 2","String 3","String 4")

val observable:Observable<String> = list.toObservable()
```



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



### Observable의 다른 팩토리 메서드

##### Observable.range(start, count)

- 옵저버블을 생성하고 start로부터 시작해 count 만큼의 정수를 내보낸다

##### Observable.empty<T>()

- 옵저버블을 생성하고 onNext() 항목을 내보내지 않고 즉시 onComplete()를 발생시킨다.

##### Observable.interval(period, TIMEUNIT)

- 옵저버블을 생성하고 period 간격만큼의 숫자를 0부터 순차적으로 내보낸다.

##### Observable.timer(delay, TIMEUNIT)

- 옵저버블을 생성하고 delay 시간이 경과한 후에 한 번만 실행된다.



