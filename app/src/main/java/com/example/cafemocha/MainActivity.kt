package com.example.cafemocha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.cafemocha.ui.theme.CafeMochaTheme

enum class DietType(val iconRes: Int, val description: String, val textColor: Long) {
    VEG(R.drawable.veg, "Veg", 0xFF65CF70), NON_VEG(
        R.drawable.non, "Non Veg", 0xFFCF6565
    ),
    EGG(R.drawable.egg, "Egg", 0xFFCF8B65),
}

data class FoodItem(
    val name: String,
    val price: Int,
    val imageUrl: String,
    val dietType: DietType,
)

data class FoodCategory(
    val title: String, val items: List<FoodItem>
)

val categories = listOf(
    FoodCategory(
        title = "All Day Breakfasts", items = listOf(
            FoodItem(
                "Egg Muffin",
                134,
                "https://www.allrecipes.com/thmb/1s9kA8EE79FTle7RuOhbvcJ1gxQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/5747733-94f33cdd7a4f4ecb9efe46ea7438bf82.jpg",
                DietType.EGG
            ), FoodItem(
                "Egg Muffin",
                234,
                "https://www.allrecipes.com/thmb/1s9kA8EE79FTle7RuOhbvcJ1gxQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/5747733-94f33cdd7a4f4ecb9efe46ea7438bf82.jpg",
                DietType.VEG
            ), FoodItem(
                "Egg Muffin",
                864,
                "https://www.allrecipes.com/thmb/1s9kA8EE79FTle7RuOhbvcJ1gxQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/5747733-94f33cdd7a4f4ecb9efe46ea7438bf82.jpg",
                DietType.NON_VEG
            )
        )
    ), FoodCategory(
        title = "Classic Coffees", items = listOf(
            FoodItem(
                "Mocha",
                93,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/mocha-001-8301418.jpg",
                DietType.VEG
            ), FoodItem(
                "Cappuccino",
                74,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/mocha-001-8301418.jpg",
                DietType.VEG
            ), FoodItem(
                "Americana",
                64,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/mocha-001-8301418.jpg",
                DietType.VEG
            )
        )
    ), FoodCategory(
        title = "Specialty Cakes", items = listOf(
            FoodItem(
                "Tiramisu",
                993,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/bundt_cake-76e50c9.jpg",
                DietType.NON_VEG
            ), FoodItem(
                "Bundt Cake",
                1174,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/bundt_cake-76e50c9.jpg",
                DietType.EGG
            ), FoodItem(
                "Butterscotch Crème",
                3164,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/bundt_cake-76e50c9.jpg",
                DietType.VEG
            )
        )
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CafeMochaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
//                    AppNavigation(navController = navController)
                    FoodItemComposable(foodItem = categories[0].items[0])
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val logoIcon = R.drawable.cafe

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeLayout(navController = navController, logoIcon = logoIcon) }
        composable("orders") {
            OrderLayout(
                navController = navController, logoIcon = logoIcon
            )
        }
    }
}

@Composable
fun HomeLayout(
    modifier: Modifier = Modifier, navController: NavHostController, @DrawableRes logoIcon: Int
) {
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
                    painter = painterResource(id = logoIcon),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(64.dp)
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
                    onClick = { navController.navigate("orders") },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderLayout(
    modifier: Modifier = Modifier, navController: NavHostController, @DrawableRes logoIcon: Int
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(painter = painterResource(id = logoIcon),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { navController.navigate("home") })
                Text(text = "Cafe Mocha")
            }
        }, actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Image(
                    painter = painterResource(id = logoIcon),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(48.dp)
                )
            }
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

        }
    }
}

@Composable
fun FoodItemComposable(modifier: Modifier = Modifier, foodItem: FoodItem) {
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(foodItem.dietType.iconRes),
                contentDescription = foodItem.dietType.description,
                modifier = Modifier.size(28.dp)
            )
            Text(
                foodItem.dietType.description,
                fontSize = 20.sp,
                color = Color(foodItem.dietType.textColor)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/mocha-001-8301418.jpg?quality=90&webp=true&resize=375,341",
                contentDescription = "whatever", /* ?TODO: Fix this */
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp)),
                placeholder = painterResource(R.drawable.coffee),
                /* ?TODO: add error placeholder here */
            )
            Column {
                Text(foodItem.name)
                Spacer(modifier = Modifier.size(4.dp))
                Text("₹" + foodItem.price.toString())
            }
            AddFoodButton()

        }
    }
}

@Composable
fun AddFoodButton(modifier: Modifier = Modifier) {
    var itemAmount by remember { mutableIntStateOf(0) }
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { if (itemAmount > 0) itemAmount-- else itemAmount = 0 }) {
            Text("-")
        }
        Text(if (itemAmount == 0) "add" else itemAmount.toString())
        IconButton(onClick = { itemAmount++ }) {
            Text("+")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_7_pro")
@Composable
fun GreetingPreview() {
    CafeMochaTheme {
        val navController = rememberNavController()
//        AppNavigation(navController = navController)
        val logoIcon = R.drawable.cafe
//        OrderLayout(navController = navController, logoIcon = logoIcon)
        FoodItemComposable(foodItem = categories[0].items[0])
    }
}
