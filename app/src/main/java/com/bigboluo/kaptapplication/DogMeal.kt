package com.bigboluo.kaptapplication

import com.bigboluo.annotation.Factory
import com.bigboluo.annotation.Meal

@Factory(
    id = "DogMeal",
    value = DogMeal::class
)
class DogMeal : Meal {
    override fun eat() {
        println("eat dog meal!")
    }
}