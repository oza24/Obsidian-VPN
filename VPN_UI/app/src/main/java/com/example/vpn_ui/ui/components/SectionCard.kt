package com.example.vpn_ui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SectionCard(
    title: String,
    items: List<String>,
    showTag: Boolean = false
) {

    Column {

        Text(title, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1F24), RoundedCornerShape(16.dp))
        ) {

            Column {

                items.forEachIndexed { index, item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row {
                            Text(item, color = Color.White)

                            if (showTag && item == "Privacy & Security") {
                                Spacer(Modifier.width(10.dp))
                                Text("STRICT", color = Color(0xFF00E5A8))
                            }
                        }

                        Text(">", color = Color.Gray)
                    }

                    if (index != items.lastIndex) {
                        Divider(color = Color(0xFF222222))
                    }
                }
            }
        }
    }
}