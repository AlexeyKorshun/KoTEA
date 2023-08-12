package com.example.shared

import com.example.shared.CounterEvent.CounterCommandsResultEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import ru.tinkoff.kotea.core.CommandsFlowHandler

@OptIn(ExperimentalCoroutinesApi::class)
class CounterCommandsFlowHandler : CommandsFlowHandler<CounterCommand, CounterCommandsResultEvent> {

    override fun handle(commands: Flow<CounterCommand>): Flow<CounterCommandsResultEvent> {
        return commands.flatMapLatest { command ->
            when (command) {
                CounterCommand.Start -> createFlow()
                CounterCommand.Stop -> emptyFlow<CounterCommandsResultEvent>()
            }
        }
    }

    private fun createFlow(): Flow<CounterCommandsResultEvent> = flow {
        while (true) {
            delay(1_000)
            emit(CounterCommandsResultEvent.CounterTick)
        }
    }
}