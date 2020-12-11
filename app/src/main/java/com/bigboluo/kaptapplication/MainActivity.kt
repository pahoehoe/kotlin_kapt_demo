package com.bigboluo.kaptapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bigboluo.annotation.Factory
import com.bigboluo.annotation.MyClass
import com.bigboluo.annotation.MyClass.*
import com.bigboluo.annotation.MyKapt

@MyClazz
class MainActivity : AppCompatActivity() {

    @findView(R.id.text_view)
    var textView: View? = null

    private val factory = MyFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyKapt.bindView(this)
        println(textView)
        factory.create("DogMeal").eat()
        factory.create("BirdMeal").eat()
        factory.create("MyMeal").eat()
    }

}