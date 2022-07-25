import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.regex.Matcher
import java.util.regex.Pattern

fun main(){

    println("Initial Out put with a = 15, b = 10")

    var calculator:ReactiveCalculator = ReactiveCalculator(15, 10)
    println("Enter a = <number> or b = <number> in separate lines\nexit to exit the program")
    var line:String?

    do{
        line = readLine()
        calculator.handleInput(line)
    } while (line != null && !line.toLowerCase().contains("exit"))
}

class ReactiveCalculator(a:Int, b:Int){
    internal val subjectAdd: Subject<Pair<Int, Int>> = PublishSubject.create()
    internal val subjectSub: Subject<Pair<Int, Int>> = PublishSubject.create()
    internal val subjectMult: Subject<Pair<Int, Int>> = PublishSubject.create()
    internal val subjectDiv: Subject<Pair<Int, Int>> = PublishSubject.create()

    internal val subjectCalc: Subject<ReactiveCalculator> = PublishSubject.create()

    internal var nums: Pair<Int, Int> = Pair(0,0)

    init {
        nums = Pair(a,b)
        subjectAdd.map { it.first + it.second }.subscribe { println("Add = $it") }
        subjectSub.map { it.first - it.second }.subscribe { println("Substract = $it") }
        subjectMult.map { it.first * it.second }.subscribe { println("Multiply = $it") }
        subjectDiv.map { it.first / it.second }.subscribe { println("Divide = $it") }

        subjectCalc.subscribe {
            with(it){
                calculateAddition()
                calculateSubstraction()
                calculateMultiplication()
                calculateDivision()
            }
        }
        subjectCalc.onNext(this)
    }

    fun calculateAddition(){
        subjectAdd.onNext(nums)
    }
    fun calculateSubstraction(){
        subjectSub.onNext(nums)
    }
    fun calculateMultiplication(){
        subjectMult.onNext(nums)
    }
    fun calculateDivision(){
        subjectDiv.onNext(nums)
    }

    fun modifyNumber(a: Int = nums.first, b: Int = nums.second){
        nums = Pair(a, b)
        subjectCalc.onNext(this)
    }

    fun handleInput(inputLine:String?){
        if (!inputLine.equals("exit")){
            val pattern: Pattern = Pattern.compile("([a|b])(?:\\s)?=(?:\\s)?(\\d*)")

            var a: Int? = null
            var b: Int? = null

            val matcher: Matcher = pattern.matcher(inputLine)

            if (matcher.matches() && matcher.group(1) != null && matcher.group(2) != null){
                if (matcher.group(1).toLowerCase().equals("a")){
                    a = matcher.group(2).toInt()
                }
                if (matcher.group(1).toLowerCase().equals("b")){
                    b = matcher.group(2).toInt()
                }
            }
            when {
                a != null && b != null -> modifyNumber(a, b)
                a != null -> modifyNumber(a = a)
                b != null -> modifyNumber(b = b)
                else -> println("Invalid Input")
            }


        }
    }

}