package com.bigboluo.kaptapplication

import com.bigboluo.annotation.Factory
import com.bigboluo.annotation.Meal

@Factory(
    id = "MyMeal",
    value = MyMeal::class
)
class MyMeal :Meal {
    override fun eat() {

    }
}