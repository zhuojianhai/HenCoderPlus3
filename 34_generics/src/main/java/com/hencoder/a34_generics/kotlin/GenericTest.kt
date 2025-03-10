package com.hencoder.a34_generics.kotlin

import com.hencoder.a34_generics.fruit.Apple
import com.hencoder.a34_generics.fruit.Banana
import com.hencoder.a34_generics.fruit.Fruit
import java.util.*

/**
 * author zjh
 * date 2025/3/6 09:02
 * desc  泛型类型参数实例化的上限、下限
 *
 *
 * java 中 ？extends xxx    是生产者  即 P  E      out
 *      ？super xxx         是消费者  即 C  S      in
 *
 */
class GenericTest {
    fun main(){
       var fruit: List<out Fruit>  = ArrayList<Apple>()
        var fruitIndex = fruit.get(0)

        val fruitIn:Array<in Apple> = Array<Fruit>(10,{Apple()})

    }

    fun addFruit(fruitInd:Array<in Apple>){
        fruitInd.set(0, Apple())
//        fruitInd.set(1, Fruit {  })

    }
}