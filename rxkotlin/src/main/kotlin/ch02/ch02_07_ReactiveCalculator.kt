package ch02

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.regex.Matcher
import java.util.regex.Pattern


fun main(){

    println("Initial Out put with a = 15, b = 10")
    var calculator:ReactiveCalculator = ReactiveCalculator(15, 10)

    println("Enter a = <number> or b = <number> in separate lines\nexit to exit the program")
    var line:String?

    do{
        line = readLine()

        GlobalScope.async {
            calculator.handleInput(line)
        }

    } while (line != null && !line.toLowerCase().contains("exit"))
}

class ReactiveCalculator(a:Int, b:Int){

    val subjectCalc: Subject<ReactiveCalculator> = PublishSubject.create()

    var nums: Pair<Int, Int> = Pair(0,0)

    init {
        nums = Pair(a,b)

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

    inline fun calculateAddition(): Int {
        val result = nums.first + nums.second
        println("Add = $result")
        return result
    }
    inline fun calculateSubstraction(): Int{
        val result = nums.first - nums.second
        println("Substract = $result")
        return result
    }
    inline fun calculateMultiplication(): Int{
        val result = nums.first * nums.second
        println("Multiply = $result")
        return result
    }
    inline fun calculateDivision(): Double{
        val result = (nums.first*1.0) / (nums.second*1.0)
        println("Division = $result")
        return result
    }

    inline fun modifyNumber(a: Int = nums.first, b: Int = nums.second){
        nums = Pair(a, b)
        subjectCalc.onNext(this)
    }

    suspend fun handleInput(inputLine:String?){
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