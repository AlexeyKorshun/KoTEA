package ru.tinkoff.mobile.kotea.sample.view

sealed interface CounterNews {

    data class ShowToast(
        val text: String,
    ) : CounterNews
}