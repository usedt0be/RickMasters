package com.example.rickmasters

import com.RikMastersApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rickmasters.domain.usecase.GetStatisticUseCase
import com.example.rickmasters.domain.usecase.GetUsersUseCase
import com.example.rickmasters.theme.AppTheme
import com.example.rickmasters.ui.theme.RickMastersTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (applicationContext as RikMastersApp).appContainer
        enableEdgeToEdge()
        setContent {
            AppTheme {
                LaunchedEffect(Unit) {
                    this.launch(Dispatchers.IO) { //testing requests
                        GetStatisticUseCase(statisticRepository = appContainer.statisticRepository)
                            .invoke()

                        GetUsersUseCase(statisticRepository = appContainer.statisticRepository)
                            .invoke()
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        style = AppTheme.typography.title,
        color = AppTheme.colors.textSecondary,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickMastersTheme {
        Greeting("Android")
    }
}