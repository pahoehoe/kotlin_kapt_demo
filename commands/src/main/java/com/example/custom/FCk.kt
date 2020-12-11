package com.example.custom

import kotlin.reflect.KClass

object FCk {

    fun getAllCommands():MutableMap<String,KClass<*>>{
        val map:MutableMap<String,KClass<*>> = HashMap()
        map["ClickCommand"] = ClickCommand::class
        map["BackToMainCommand"] = BackToMainCommand::class
        return map
    }

}