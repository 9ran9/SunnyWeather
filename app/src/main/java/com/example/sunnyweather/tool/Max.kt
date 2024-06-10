package com.example.androidkotlin.tool

/**
 * 求多个数的最大数：
 * val largest = max(a,b,c,d)
 */
fun <T:Comparable<T>> max(vararg numbers:T):T{
    //vararg:集合声明 ，Comparable<T>：表示：所声明的泛型可以比较（传入Int和Float都是可以比较的）

    if (numbers.isEmpty()) throw RuntimeException("Params can not be empty")
    var maxNum = numbers[0]
    for (num in numbers){
        if (num > maxNum)
            maxNum = num
    }
    return maxNum
}