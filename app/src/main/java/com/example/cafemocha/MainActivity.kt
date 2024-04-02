package com.example.cafemocha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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

//! data class for the view model cart updation.
data class CartItem(
    val foodItem: FoodItem, val quantity: Int
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
                "Butterscotch Crème Cake",
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
                    AppNavigation(navController = navController)
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
                navController = navController, logoIcon = logoIcon, categories = categories
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
    modifier: Modifier = Modifier,
    navController: NavHostController,
    @DrawableRes logoIcon: Int,
    categories: List<FoodCategory>,
    viewModel: OrderViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems.observeAsState(initial = listOf())
    val totalQuantity = cartItems.sumOf { it.quantity }

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
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = innerPadding

            ) {
                items(categories) {
                    FoodCategoryComposable(foodCategory = it, viewModel = viewModel)
                }
            }
            AnimatedVisibility(
                visible = totalQuantity > 0,
                enter = slideInVertically(
                    // Slide in from the bottom of the screen
                    initialOffsetY = { it }),
                exit = slideOutVertically(
                    // Slide out to the bottom of the screen
                    targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter) // Position the button at the bottom center
            ) {
                // The UI for your checkout button
                Button(
                    onClick = { /* Navigate to checkout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Go to Checkout")
                }
            }
        }
    }
}


@Composable
fun FoodCategoryComposable(
    modifier: Modifier = Modifier, foodCategory: FoodCategory, viewModel: OrderViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(foodCategory.title)
        foodCategory.items.forEach {
            FoodItemComposable(foodItem = it, viewModel = viewModel)
        }
    }

}

@Composable
fun FoodItemComposable(
    modifier: Modifier = Modifier, foodItem: FoodItem, viewModel: OrderViewModel
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(foodItem.dietType.iconRes),
                contentDescription = foodItem.dietType.description,
                modifier = Modifier.size(20.dp)
            )
            Text(
                foodItem.dietType.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(foodItem.dietType.textColor)
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = foodItem.imageUrl,
                    contentDescription = "whatever", /* ?TODO: Fix this */
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    placeholder = painterResource(R.drawable.coffee),
                    /* ?TODO: add error placeholder here */
                )
                Column {
                    Text(foodItem.name, softWrap = true, modifier = modifier.width(120.dp))
                    Spacer(modifier = Modifier.size(4.dp))
                    Text("₹" + foodItem.price.toString())
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            AddFoodButton(
                foodItem = foodItem, viewModel = viewModel
            )
        }
    }
}

@Composable
fun AddFoodButton(modifier: Modifier = Modifier, foodItem: FoodItem, viewModel: OrderViewModel) {
    val cartItems by viewModel.cartItems.observeAsState(initial = listOf())
    val itemQuantity = cartItems.firstOrNull { it.foodItem == foodItem }?.quantity ?: 0
    val displayText = if (itemQuantity == 0) "add" else itemQuantity.toString()

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (itemQuantity != 0) {
            TextButton(
                onClick = { viewModel.updateCart(foodItem, -1) },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(32.dp)
            ) {
                Text("-", fontSize = 24.sp, textAlign = TextAlign.Center)

            }
        }
        Text(
            displayText, modifier = Modifier
                .width(36.dp)
                .clickable(enabled = itemQuantity == 0) {
                    viewModel.updateCart(foodItem, 1)
                }, textAlign = TextAlign.Center, textDecoration = TextDecoration.Underline
        )
        TextButton(
            onClick = { viewModel.updateCart(foodItem, 1) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(32.dp)
        ) {
            Text(if (itemQuantity != 0) "+" else "", fontSize = 20.sp, textAlign = TextAlign.Center)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_7_pro")
@Composable
fun GreetingPreview() {
    CafeMochaTheme {
        val navController = rememberNavController()
        AppNavigation(navController = navController)
    }
}
