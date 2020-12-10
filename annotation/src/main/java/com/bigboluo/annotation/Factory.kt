package com.bigboluo.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Factory(val value: KClass<*>, val id: String)
