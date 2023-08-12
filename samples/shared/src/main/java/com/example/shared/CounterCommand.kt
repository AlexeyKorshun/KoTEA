package com.example.shared

sealed interface CounterCommand {

    object Start : CounterCommand

    object Stop : CounterCommand
}