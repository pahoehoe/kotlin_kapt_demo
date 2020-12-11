package com.bigboluo.kaptapplication

import com.bigboluo.annotation.Factory
import com.bigboluo.annotation.Meal

@Factory(
    id = "BirdMeal",
    value = BirdMeal::class
)
class BirdMeal : Meal {
    override fun eat() {
        println("eat bird meal!")
    }
}