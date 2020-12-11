package com.example.commands

import com.bigboluo.annotation.Factory
import com.bigboluo.annotation.Meal

@Factory(id = "GoodMeal",value = GoodMeal::class)
class GoodMeal : Meal {

    override fun eat() {

    }

}