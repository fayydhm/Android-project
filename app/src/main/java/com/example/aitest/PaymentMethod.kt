package com.example.aitest

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
fun PaymentMethodScreen(
    onBackClick: () -> Unit = {},
    onPaymentConfirmed: () -> Unit = {}
) {
    var selectedPayment by remember { mutableStateOf("") }

    // Structured payment method data
    val paymentMethods = listOf(
        "GoPay" to "Digital Wallet",
        "DANA" to "Digital Wallet",
        "BRI Syariah" to "Bank Transfer",
        "ShopeePay" to "Digital Wallet",
        "Mandiri" to "Bank Transfer",
        "Cash" to "Pay at Counter"
    )

    // Group payment methods by category
    val groupedPayments = paymentMethods.groupBy { it.second }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Select Payment Method",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
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
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Choose your preferred payment method:",
                fontSize = 16.sp,
                color = Color.White.copy(0.8f),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Using LazyColumn for better performance with many items
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Iterate through grouped payment methods
                groupedPayments.forEach { (category, methods) ->
                    item {
                        Text(
                            text = category,
                            fontSize = 14.sp,
                            color = Color(0xFFBB86FC),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                        )
                    }

                    items(methods) { (method, _) ->
                        PaymentOption(
                            method = method,
                            isSelected = selectedPayment == method,
                            onSelected = { selectedPayment = method }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // Fixed button at the bottom
            Button(
                onClick = { onPaymentConfirmed() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBB86FC),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFBB86FC).copy(alpha = 0.3f),
                    disabledContentColor = Color.White.copy(alpha = 0.6f)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = selectedPayment.isNotEmpty()
            ) {
                Text(text = "Confirm Payment", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PaymentOption(method: String, isSelected: Boolean, onSelected: () -> Unit) {
    // Animate both color and elevation changes
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFBB86FC).copy(alpha = 0.2f) else Color(0xFF1E1E1E),
        label = "Background Color Animation"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 2.dp,
        animationSpec = tween(durationMillis = 200),
        label = "Elevation Animation"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFFBB86FC) else Color.Transparent,
        label = "Border Color Animation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(elevation = elevation, shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onSelected() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = method,
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

            if (isSelected) {
                // Show checkmark icon when selected
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color(0xFFBB86FC),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                // Show radio button when not selected
                RadioButton(
                    selected = isSelected,
                    onClick = onSelected,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFFBB86FC),
                        unselectedColor = Color.White.copy(0.6f)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaymentMethodScreen() {
    PaymentMethodScreen()
}