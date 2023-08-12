package com.example.shared.analytics

import android.util.Log
import com.example.shared.CounterEvent
import com.example.shared.CounterEvent.CounterCommandsResultEvent.CounterTick
import com.example.shared.CounterEvent.CounterUiEvent
import com.example.shared.CounterEvent.CounterUiEvent.ResetClicked
import com.example.shared.CounterEvent.CounterUiEvent.StartClicked
import com.example.shared.CounterEvent.CounterUiEvent.StopClicked
import com.example.shared.CounterState

class AnalyticsTracker {

    operator fun invoke(state: CounterState, event: CounterEvent) {
        when (event) {
            CounterTick -> Unit
            is CounterUiEvent -> trackUiEvent(state, event)
        }
    }

    private fun trackUiEvent(state: CounterState, event: CounterUiEvent) {
        when (event) {
            ResetClicked -> Log.d("CounterAnalytics", "Counter reset")
            StartClicked -> Log.d("CounterAnalytics", "Counter started with value: ${state.count}")
            StopClicked -> Log.d("CounterAnalytics", "Counter stopped with value: ${state.count}")
        }
    }
}