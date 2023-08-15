package ru.tinkoff.mobile.kotea.sample.compose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shared.CounterEvent
import com.example.shared.CounterNews
import com.example.shared.CounterStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import ru.tinkoff.kotea.android.lifecycle.collectOnCreate
import ru.tinkoff.kotea.android.storeViaViewModel
import ru.tinkoff.mobile.kotea.sample.compose.ui.theme.TinkoffkoteaTheme

class ComposeActivity : ComponentActivity() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainActivity", throwable.message, throwable)
    }

    private val store by storeViaViewModel(coroutineContext = Dispatchers.Default + coroutineExceptionHandler) {
        CounterStore()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        store.collectOnCreate(
            activity = this,
            uiStateMapper = CounterUiStateMapper(),
            stateCollector = ::collect,
            newsCollector = ::handle
        )
    }

    private fun collect(state: CounterUiState) {
        setContent {
            TinkoffkoteaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreen(
                        state = state,
                        onStart = { store.dispatch(CounterEvent.CounterUiEvent.StartClicked) },
                        onStop = { store.dispatch(CounterEvent.CounterUiEvent.StopClicked) },
                        onReset = { store.dispatch(CounterEvent.CounterUiEvent.ResetClicked) },
                    )
                }
            }
        }
    }

    private fun handle(news: CounterNews) {
        when (news) {
            is CounterNews.ShowToast -> Toast.makeText(this, news.text, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    state: CounterUiState,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center,
    ) {

        Text(
            text = state.countText,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = state.progressText)

        Spacer(modifier = Modifier.height(4.dp))

        Button(onClick = onStart) {
            Text(text = "Start")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onStop) {
            Text(text = "Stop")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onReset) {
            Text(text = "Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TinkoffkoteaTheme {
        CounterScreen(
            state = CounterUiState(
                countText = "Count: 0",
                progressText = "Progress: 0%"
            ),
            onStart = {},
            onStop = {},
            onReset = {},
        )
    }
}