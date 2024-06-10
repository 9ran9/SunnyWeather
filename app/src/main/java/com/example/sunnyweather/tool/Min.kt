package com.example.androidkotlin.tool

/**
 * 求多个数的最小数：
 * val largest = max(a,b,c,d)
 */
fun <T:Comparable<T>> min(vararg numbers:T):T{
    if (numbers.isEmpty()) throw RuntimeException("Params can not be empty")
    var maxNum = numbers[0]
    for (num in numbers){
        if (num < maxNum)
            maxNum = num
    }
    return maxNum
}