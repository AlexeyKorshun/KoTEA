package ru.tinkoff.mobile.kotea.sample.view

sealed interface CounterCommand {

    object Start : CounterCommand

    object Stop : CounterCommand
}