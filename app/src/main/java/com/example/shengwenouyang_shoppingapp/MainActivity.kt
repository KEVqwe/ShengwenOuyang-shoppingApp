package com.example.shengwenouyang_shoppingapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shengwenouyang_shoppingapp.ui.theme.ShengwenOuyangshoppingAppTheme
import androidx.compose.ui.platform.LocalConfiguration


data class Product(val name: String, val price: String, val description: String)

val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShengwenOuyangshoppingAppTheme {
                ShoppingApp()
            }
        }
    }
}

@Composable
fun ShoppingApp() {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    // Check if the screen is in landscape mode
    val isLandscape = isInLandscape()

    if (isLandscape) {
        // Landscape mode: show both panes side by side
        Row(modifier = Modifier.fillMaxSize()) {
            ProductList(onProductSelected = { selectedProduct = it }, modifier = Modifier.weight(1f))
            ProductDetails(product = selectedProduct, modifier = Modifier.weight(1f))
        }
    } else {
        // Portrait mode: show one pane at a time
        if (selectedProduct == null) {
            ProductList(onProductSelected = { selectedProduct = it }, modifier = Modifier.fillMaxSize())
        } else {
            ProductDetails(product = selectedProduct, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun ProductList(onProductSelected: (Product) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(products.size) { index ->
            val product = products[index]
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductSelected(product) }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun ProductDetails(product: Product?, modifier: Modifier = Modifier) {
    if (product == null) {
        Text(
            text = "Select a product to view details.",
            modifier = modifier.padding(16.dp)
        )
    } else {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "Name: ${product.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Price: ${product.price}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Description: ${product.description}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun isInLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}


@Preview(showBackground = true)
@Composable
fun ShoppingAppPreview() {
    ShengwenOuyangshoppingAppTheme {
        ShoppingApp()
    }
}
