package com.bigboluo.kaptapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bigboluo.annotation.MyClass.*
import com.bigboluo.annotation.MyKapt
import com.example.commands.Commands
import com.example.commands.CustomCommand
import com.example.commands.Script
import com.my.factoryspack.App_Factory

@MyClazz
class MainActivity : AppCompatActivity() {

    @findView(R.id.text_view)
    var textView: View? = null

    private val factory = App_Factory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyKapt.bindView(this)
        println(textView)
        factory.create("DogMeal").eat()
        factory.create("BirdMeal").eat()
        factory.create("MyMeal").eat()
        Commands.getCommandByName("OneCommand", null).run(Script())
    }

}