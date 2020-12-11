package com.example.custom

import android.os.Bundle
import com.bigboluo.annotation.Command
import com.example.commands.CustomCommand
import com.example.commands.Script

@Command(name = "ClickCommand")
class ClickCommand(override val params: Bundle?) : CustomCommand {

    override fun run(script: Script) {
        println("clickCommand run!")
    }

}