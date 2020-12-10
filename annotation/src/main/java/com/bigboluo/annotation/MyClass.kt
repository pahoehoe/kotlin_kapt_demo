package com.bigboluo.annotation

class MyClass {

    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.SOURCE)
    annotation class MyClazz


    @Target(AnnotationTarget.FIELD)
    @Retention(AnnotationRetention.SOURCE)
    annotation class findView(val value: Int = -1)

}