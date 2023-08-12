package com.example.shared

import com.example.shared.analytics.AnalyticsTracker
import ru.tinkoff.kotea.core.Store
import ru.tinkoff.kotea.logging.KoteaLoggingStore

class CounterStore : Store<CounterState, CounterEvent, CounterNews> by KoteaLoggingStore(
    initialState = CounterState(count = 0, isProgress = false),
    update = CounterUpdate(AnalyticsTracker()),
    commandsFlowHandlers = listOf(CounterCommandsFlowHandler())
)