package com.example.a30daysapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a30daysapp.data.Datasource
import com.example.a30daysapp.model.DayItem
import com.example.a30daysapp.ui.theme.A30DaysAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A30DaysAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    A30DaysAppContent()
                }
            }
        }
    }
}

// Composable function for the main content of the app
@Composable
fun A30DaysAppContent() {
    // Load items from Datasource
    val items = Datasource().loadItems()

    // Scaffold composable for the overall app structure
    Scaffold(
        topBar = {
            // FoodTopAppBar composable for the top app bar
            FoodTopAppBar()
        },
        content = {
            // LazyColumn for displaying a list of items
            LazyColumn(
                contentPadding = it
            ) {
                items(items) { item ->
                    // DayItemRow composable for each item in the list
                    DayItemRow(item = item)
                }
            }
        }
    )
}



// Composable function for the top app bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Image and Text components in the top app bar
                Image(
                    painter = painterResource(id = R.drawable.chef),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(4.dp)
                )
                Text(
                    text = "30 Days Delicious Food",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    )
}

// Composable function for displaying each DayItem in a Card
@Composable
fun DayItemRow(item: DayItem) {
    // State variable to control visibility of item description
    var isDescriptionVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column {
            // Row for displaying item title and toggle icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // Text component for item title
                Text(
                    text = stringResource(id = item.subtitleResId),
                    modifier = Modifier.weight(1f), // Occupy available space
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                )

                // Icon for toggling item description visibility
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            isDescriptionVisible = !isDescriptionVisible
                        }
                        .size(24.dp)
                        .rotate(if (isDescriptionVisible) 180f else 0f)
                )
            }

            // Image component for displaying item image
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f/9f),
                contentScale = ContentScale.Crop
            )

            // AnimatedVisibility for displaying item description when visible
            AnimatedVisibility(visible = isDescriptionVisible) {
                Text(
                    text = stringResource(id = item.descriptionResId),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// Preview composable for DayItemRow
@Preview(showBackground = true)
@Composable
fun DayItemRowPreview() {
    A30DaysAppTheme {
        // Sample DayItem for preview
        val sampleItem = DayItem(
            subtitleResId = R.string.day_1_subtitle,
            imageResId = R.drawable.day_1,
            descriptionResId = R.string.day_1_description
        )
        // Display DayItemRow in preview
        DayItemRow(item = sampleItem)
    }
}