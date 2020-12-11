package com.example.one

import android.os.Bundle
import com.bigboluo.annotation.Command
import com.example.commands.Commands
import com.example.commands.CustomCommand
import com.example.commands.Script

@Command(name = "OneCommand")
class OneCommand(override val params: Bundle?) :CustomCommand {
    override fun run(script: Script) {
        println("run OneCommand!")
        println("run TenCommand In OneCommand")
        Commands.getCommandByName("TenCommand", null).run(Script())
    }

}