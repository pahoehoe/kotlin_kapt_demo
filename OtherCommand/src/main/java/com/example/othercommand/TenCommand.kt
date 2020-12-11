package com.example.othercommand

import android.os.Bundle
import com.bigboluo.annotation.Command
import com.example.commands.CustomCommand
import com.example.commands.Script

@Command("TenCommand")
class TenCommand(override val params: Bundle?) :CustomCommand {
    override fun run(script: Script) {
        println("TenCommand run!")
    }
}