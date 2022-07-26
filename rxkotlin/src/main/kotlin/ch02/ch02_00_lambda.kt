package ch02

import java.util.*

fun main(){

    val sum = { x: Int, y: Int -> x + y }
    println("Sum ${sum(12, 14)}")
    val anonymousMult = {x:Int -> (Random().nextInt(15)+1) * x}
    println("random output ${anonymousMult(2)}")

}