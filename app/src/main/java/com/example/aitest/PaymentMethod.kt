import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodScreen(onBackClick: () -> Unit = {}) {
    var selectedPayment by remember { mutableStateOf("") }

    val paymentMethods = listOf(
        "GoPay", "DANA", "BRI Syariah", "ShopeePay", "Mandiri", "Cash"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Select Payment", fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Choose your preferred payment method:",
                fontSize = 16.sp,
                color = Color.White.copy(0.8f),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            paymentMethods.forEach { method ->
                PaymentOption(
                    method = method,
                    isSelected = selectedPayment == method,
                    onSelected = { selectedPayment = method }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Confirm Button
            Button(
                onClick = { /* TODO: Proceed with Payment */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB86FC),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Confirm Payment", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PaymentOption(method: String, isSelected: Boolean, onSelected: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFBB86FC).copy(alpha = 0.3f) else Color(0xFF1E1E1E),
        label = "Background Color Animation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .clickable { onSelected() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelected,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFFBB86FC),
                    unselectedColor = Color.White.copy(0.6f)
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = method,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodScreen() {
    PaymentMethodScreen()
}
