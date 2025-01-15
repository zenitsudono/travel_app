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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextFieldDefaults

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

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigation(selectedTab) { selectedTab = it } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
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

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
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
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Good Morning, Shreya....",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Gray,
                fontSize = 14.sp
            )
        )
        Text(
            text = "Make plan for weekend",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        )
    }
}

@Composable
fun SearchSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.weight(1f),
            placeholder = { 
                Text(
                    "Search Places",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    )
                ) 
            },
            leadingIcon = { 
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            singleLine = true
        )
        
        IconButton(
            onClick = { },
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(12.dp)
                )
                .size(56.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Settings",
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun TravelOptions() {
    val options = listOf(
        TravelOption("Places", R.drawable.locationon, Color(0xFFFFF6E5)),
        TravelOption("Flights", R.drawable.flight, Color(0xFFE5EEFF)),
        TravelOption("Trains", R.drawable.train, Color(0xFFFFE5E5)),
        TravelOption("Buses", R.drawable.directionsbus, Color(0xFFE5FFF6)),
        TravelOption("Taxi", R.drawable.localtaxi, Color(0xFFF6E5FF))
    )

    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(options) { option ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(64.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(option.bgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = option.iconRes),
                        contentDescription = option.text,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(8.dp),
                        colorFilter = if (option.text == "Flights") ColorFilter.tint(MaterialTheme.colorScheme.primary) else null
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = option.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = if (option.text == "Flights") FontWeight.Bold else FontWeight.Normal
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

private data class TravelOption(
    val text: String,
    val iconRes: Int,
    val bgColor: Color
)

@Composable
fun PassengerCounter(title: String) {
    var count by remember { mutableStateOf(0) }

    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray,
                fontSize = 14.sp
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 4.dp)
        ) {
            IconButton(
                onClick = { if (count > 0) count-- },
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = "Decrease",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
            Text(
                text = count.toString().padStart(2, '0'),
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(
                onClick = { count++ },
                modifier = Modifier
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
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
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            listOf("One Way", "Round Trip", "Multicity").forEachIndexed { index, text ->
                Button(
                    onClick = { onTypeSelected(index) },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == index)
                            MaterialTheme.colorScheme.primary
                        else
                            Color(0xFFF5F5F5)
                    ),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (selectedType == index) Color.White else Color.Gray,
                            fontSize = 13.sp
                        )
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(top = 24.dp)) {
            Text(
                text = "From",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            )
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                placeholder = { 
                    Text(
                        "Choose Departure from",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.LightGray
                        )
                    ) 
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF5F5F5),
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.swap),
                    contentDescription = "Swap locations",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Column {
            Text(
                text = "To",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            )
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                placeholder = { 
                    Text(
                        "Choose Arrival at",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.LightGray
                        )
                    ) 
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF5F5F5),
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Departure Date",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            )
            OutlinedTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                placeholder = { 
                    Text(
                        "Choose your Date",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.LightGray
                        )
                    ) 
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Select date",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFF5F5F5),
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PassengerCounter("Adult (12+)")
            PassengerCounter("Childs (2-12)")
        }

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                "Search Flight",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            )
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
