package com.bigboluo.kaptapplication.commands

import android.os.Bundle
import com.bigboluo.annotation.Command
import com.example.commands.CustomCommand
import com.example.commands.Script

@Command(name = "ShowCommand")
class ShowCommand(override val params: Bundle?) : CustomCommand {
    override fun run(script: Script) {

    }
}