package com.example.shared

import com.example.shared.CounterEvent.CounterCommandsResultEvent
import com.example.shared.CounterEvent.CounterUiEvent
import com.example.shared.analytics.AnalyticsTracker
import ru.tinkoff.kotea.core.dsl.DslUpdate

class CounterUpdate(
    private val analyticsTracker: AnalyticsTracker,
) : DslUpdate<CounterState, CounterEvent, CounterCommand, CounterNews>() {

    override fun NextBuilder.update(event: CounterEvent) {
        analyticsTracker(state, event)
        when (event) {
            is CounterCommandsResultEvent -> handleResult(event)
            is CounterUiEvent -> handleUiEvent(event)
        }
    }

    private fun NextBuilder.handleResult(event: CounterCommandsResultEvent) {
        when (event) {
            is CounterCommandsResultEvent.CounterTick -> state { copy(count = state.count + 1) }
        }
    }

    private fun NextBuilder.handleUiEvent(event: CounterUiEvent) {
        when (event) {
            CounterUiEvent.ResetClicked -> {
                state { copy(count = 0) }
                news(CounterNews.ShowToast("Counter reset"))
            }

            CounterUiEvent.StartClicked -> {
                commands(CounterCommand.Start)
                state { copy(isProgress = true) }
                news(CounterNews.ShowToast("Counter started"))
            }

            CounterUiEvent.StopClicked -> {
                commands(CounterCommand.Stop)
                state { copy(isProgress = false) }
                news(CounterNews.ShowToast("Counter stopped"))
            }
        }
    }
}