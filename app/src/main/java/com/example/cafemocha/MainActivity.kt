package com.example.cafemocha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cafemocha.ui.theme.CafeMochaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeMochaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    HomeLayout()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "profile") {
        composable("home") { HomeLayout() }
//        composable("friendslist") { FriendsList(/*...*/) }
        /*...*/
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout(modifier: Modifier = Modifier) {
    val logo = painterResource(id = R.drawable.cafe)
    Scaffold { innerPadding ->
        Column(
            modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Color.LightGray)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = logo, contentDescription = "App Logo", modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text("Cafe Mocha")
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Welcome to Cafe Mocha")
                Spacer(modifier = Modifier.size(12.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(128.dp)
                        .padding(all = 16.dp)
                        .border(width = 2.dp, color = Color(0xFFB97149), shape = CircleShape)
                ) {
                    Text("MENU")
                }
            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_7_pro")
@Composable
fun GreetingPreview() {
    CafeMochaTheme {
        HomeLayout()
    }
}
