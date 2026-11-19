package com.example.sportsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportsapp.ui.theme.SportsAppTheme

private val SportsPurple = Color(0xFF7000F5)

private data class BottomDestination(
    val label: String,
    val icon: @Composable () -> Unit
)

private val destinations = listOf(
    BottomDestination("Home") { Icon(Icons.Filled.Home, contentDescription = null) },
    BottomDestination("Account") { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
    BottomDestination("Profile") { Icon(Icons.Filled.Person, contentDescription = null) },
    BottomDestination("My Sports") { Icon(Icons.Filled.SportsSoccer, contentDescription = null) }
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SportsAppTheme(dynamicColor = false) {
                SportsApp()
            }
        }
    }
}

@Composable
fun SportsApp() {
    var selectedDestination by rememberSaveable { mutableIntStateOf(0) }
    val currentDestination = destinations[selectedDestination]

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Surface(
                    color = SportsPurple,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = currentDestination.label,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp)
                    )
                }
            }
        },
        bottomBar = {
            SportsBottomNavigation(
                selectedDestination = selectedDestination,
                onDestinationSelected = { selectedDestination = it }
            )
        }
    ) { innerPadding ->
        DestinationContent(
            destination = currentDestination.label,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
private fun DestinationContent(destination: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$destination Fragment",
            color = Color(0xFF202124),
            fontSize = 18.sp
        )
    }
}

@Composable
private fun SportsBottomNavigation(
    selectedDestination: Int,
    onDestinationSelected: (Int) -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 4.dp,
        modifier = Modifier.navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEachIndexed { index, destination ->
                val selected = selectedDestination == index
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .padding(vertical = 7.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                     verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        color = Color.Transparent,
                        onClick = { onDestinationSelected(index) }
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            androidx.compose.runtime.CompositionLocalProvider(
                                androidx.compose.material3.LocalContentColor provides
                                    if (selected) SportsPurple else Color(0xFF303030)
                            ) {
                                androidx.compose.foundation.layout.Box(modifier = Modifier.size(24.dp)) {
                                    destination.icon()
                                }
                            }
                            Text(
                                text = destination.label,
                                color = if (selected) SportsPurple else Color(0xFF303030),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun SportsAppPreview() {
    SportsAppTheme(dynamicColor = false) {
        SportsApp()
    }
}
