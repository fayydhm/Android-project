package com.example.aitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventApp(onEventClick: (String, String, String, String) -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF497D74),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onPrimary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("GoVent 🎫", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White
                    ),
                    actions = {
                        IconButton(onClick = { /* Handle Favorites */ }) {
                            Icon(Icons.Default.Favorite, contentDescription = "Favorites", tint = Color.White)
                        }
                    }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                SearchBar()
                Spacer(modifier = Modifier.height(20.dp))
                FeaturedEvent(onEventClick)
                Spacer(modifier = Modifier.height(20.dp))
                TagSection()
                Spacer(modifier = Modifier.height(20.dp))
                EventGrid(onEventClick)
            }
        }
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text("Search events...", color = Color.Gray, fontSize = 14.sp) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1E1E1E))
            .padding(4.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp)
    )
}

@Composable
fun FeaturedEvent(onEventClick: (String, String, String, String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onEventClick(
                    "Super Bowl LIX",
                    "12 February 2025",
                    "New Orleans Stadium",
                    "🏈 Super Bowl LIX – The Ultimate Showdown Awaits! 🔥"
                )
            },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFFBB86FC), Color(0xFF3700B3))
                    )
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🔥 Featured Event", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Super Bowl LIX", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Don't miss the upcoming event!", color = Color.White.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
fun TagSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("🎵 Concert", "🏀 Sports", "📢 Class", "🤝 Meetup").forEach { tag ->
            AssistChip(
                onClick = { /* TODO */ },
                label = { Text(tag) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = Color(0xFF2C2C2C),
                    labelColor = Color.White
                )
            )
        }
    }
}

@Composable
fun EventGrid(onEventClick: (String, String, String, String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EventCard(
                eventName = "Concert in LA",
                date = "15 April 2025",
                location = "Los Angeles Arena",
                description = "Join us for an unforgettable night of music!",
                onEventClick = onEventClick
            )
            EventCard(
                eventName = "Tech Conference",
                date = "10 May 2025",
                location = "San Francisco Center",
                description = "The biggest tech event of the year with leading experts",
                onEventClick = onEventClick
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EventCard(
                eventName = "Food Festival",
                date = "20 June 2025",
                location = "Central Park",
                description = "Taste cuisines from around the world",
                onEventClick = onEventClick
            )
            EventCard(
                eventName = "Art Exhibition",
                date = "5 July 2025",
                location = "Modern Art Museum",
                description = "Featuring works from renowned contemporary artists",
                onEventClick = onEventClick
            )
        }
    }
}

@Composable
fun EventCard(
    eventName: String,
    date: String,
    location: String,
    description: String,
    onEventClick: (String, String, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(180.dp)
            .shadow(10.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onEventClick(eventName, date, location, description)
            },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lix),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                eventName,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 1
            )
            Text(
                date,
                color = Color.White.copy(0.7f),
                fontSize = 12.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onEventClick(eventName, date, location, description)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB86FC),
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View", fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEventApp() {
    EventApp { _, _, _, _ -> }
}