package com.example.commands

import android.content.Context
import android.os.Bundle
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions

object Commands {

    private val PACKAGE_NAME: String = "com.custom.commands"
    private val commandsMap: MutableMap<String, KClass<*>> = HashMap()

    fun init(context: Context) {
        println("Commands init")
        ClassUtils.getFileNameByPackageName(context, PACKAGE_NAME).forEach { it ->
            println(it)
            val k = Reflection.createKotlinClass(Class.forName(it))
            k.declaredFunctions
                .let { it.forEach { if (it.name == "getAllCommand") return@let it } }
                .let { it as KFunction<*> }
                .call(k.objectInstance)
                .let { it as MutableMap<String, KClass<*>> }
                .let { commandsMap.putAll(it) }
        }

        println("Commands done,commandsMap.size:" + commandsMap.size)
        commandsMap.forEach { println("key:${it.key}   value:${it.value}") }
    }

    fun getCommandByName(name: String, params: Bundle?): CustomCommand {
        println("getCommandByName:$name")
        return commandsMap[name]?.let { it.constructors.toMutableList()[0].call(params) } as CustomCommand
    }
}