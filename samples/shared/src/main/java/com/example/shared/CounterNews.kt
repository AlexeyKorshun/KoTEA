package com.example.shared

sealed interface CounterNews {

    data class ShowToast(
        val text: String,
    ) : CounterNews
}