package com.example.rickmasters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.RikMastersApp
import com.example.rickmasters.presentation.screens.statistics.StatisticsScreen
import com.example.rickmasters.presentation.screens.statistics.StatisticsViewModelFactory
import com.example.rickmasters.theme.AppTheme
import com.example.rickmasters.ui.theme.RickMastersTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (applicationContext as RikMastersApp).appContainer
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val factory = StatisticsViewModelFactory(
                    getStatisticUseCase =  appContainer.statisticUseCase,
                    getUsersUseCase = appContainer.getUserUseCase
                )
                StatisticsScreen(factory = factory)
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