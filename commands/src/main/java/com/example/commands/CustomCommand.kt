package com.example.commands

import android.os.Bundle

interface CustomCommand {

    val params:Bundle?

    fun run(script: Script)

}