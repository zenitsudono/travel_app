package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TravelApp()
            }
        }
    }
}

@Composable
fun TravelApp() {
    var selectedTab by remember { mutableStateOf(0) }
    var tripType by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        TopBar()

        //description
        DescriptionSection()

        // Search Section
        SearchSection()

        // Travel Options
        TravelOptions()

        // Flight Booking Section
        FlightBookingSection(tripType) { tripType = it }

        // Best Offers Section
        BestOffersSection()

        // Winter Journey Section
        WinterJourneySection()

        // Popular Location Section
        PopularLocationSection()

        // Achievements Section
        AchievementsSection()

        Spacer(modifier = Modifier.height(80.dp))

        BottomNavigation(selectedTab) { selectedTab = it }

    }

    // Bottom Navigation
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(Icons.Default.Menu, contentDescription = "Menu")
        }
        Image(
            painter = painterResource(id = R.drawable.profile_pic),
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(16))
        )
    }
}

@Composable
fun DescriptionSection() {
    Column {
        Text(
            text = "Good Morning, Shreya...",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Make plan for weekend",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SearchSection() {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = { Text("Search Places") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        trailingIcon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
    )
}

@Composable
fun TravelOptions() {
    val options = listOf(
        "Places" to R.drawable.locationon,
        "Flights" to R.drawable.flight,
        "Trains" to R.drawable.train,
        "Buses" to R.drawable.directionsbus,
        "Taxi" to R.drawable.localtaxi,
    )

    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(options) { (text, Image) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(if (text == "Flights") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = Image),
                        contentDescription = text,
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun FlightBookingSection(selectedType: Int, onTypeSelected: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Book your Flight",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf("One Way", "Round Trip", "Multicity").forEachIndexed { index, text ->
                Button(
                    onClick = { onTypeSelected(index) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(text)
                }
            }
        }

        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Choose Departure from") },
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Choose Arrival at") },
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Choose your Date") },
            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PassengerCounter("Adult (12+)")
            PassengerCounter("Childs (2-12)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Search Flight")
        }
    }
}

@Composable
fun PassengerCounter(title: String) {
    var count by remember { mutableStateOf(0) }

    Column {
        Text(title)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { if (count > 0) count-- }) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = "Decrease",

                )
            }
            Text(
                text = count.toString().padStart(2, '0'),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            IconButton(onClick = { count++ }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = Color.Black
                    )
            }
        }
    }
}

@Composable
fun BestOffersSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Best offers",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { }) {
                Text("See all")
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(2) { index ->
                OfferCard(
                    if (index == 0) "Cupid's Gift for Couples" else "Jaisalmer",
                    if (index == 0) "Up to 30% OFF*" else "FOR YOU"
                )
            }
        }
    }
}

@Composable
fun OfferCard(title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(
                    id = if (title == "Cupid's Gift for Couples")
                        R.drawable.couples_offer
                    else
                        R.drawable.jaisalmer
                ),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = subtitle,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(onClick = { }) {
                    Text("View Detail", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun WinterJourneySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Winter Journey",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { }) {
                Text("See all")
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(2) { index ->
                WinterCard(
                    if (index == 0) "Shimla Best Kept Secret" else "Charming Kasol Vibes"
                )
            }
        }
    }
}

@Composable
fun WinterCard(title: String) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(
                    id = if (title.contains("Shimla"))
                        R.drawable.shimla
                    else
                        R.drawable.kasol
                ),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun PopularLocationSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular Location",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { }) {
                Text("See all")
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(2) { index ->
                LocationCard(
                    if (index == 0)
                        Triple("Eiffel Tower", "Paris Eyfel Kulesi", "2450 KMS")
                    else
                        Triple("Beautiful China", "Shanghai, China", "3450 KMS")
                )
            }
        }
    }
}

@Composable
fun LocationCard(locationInfo: Triple<String, String, String>) {
    val (title, location, distance) = locationInfo
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(160.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(
                    id = if (title == "Eiffel Tower")
                        R.drawable.eiffel_tower
                    else
                        R.drawable.china
                ),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = location,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = distance,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun AchievementsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Achievements",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { }) {
                Text("See all")
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_card_giftcard_24),
                    contentDescription = "Gift",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(16))
                )
                Text(
                    "1/10 Journies",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun BottomNavigation(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val items = listOf(
            Icons.Default.Home to "Home",
            Icons.Default.Search to "Search",
            Icons.Default.Favorite to "Favorites",
            Icons.Default.Person to "Profile"
        )

        items.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}
