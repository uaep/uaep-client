package com.example.uaep.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uaep.ui.review.ReviewDto
import com.example.uaep.ui.theme.md_theme_light_error
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary

@Composable
fun Profile(reviewDto: ReviewDto) {
    Image(
        imageVector = Icons.Filled.Person,
        contentDescription = "Contact profile picture",
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(4.dp, Color.Black, CircleShape)
    )
    Spacer(Modifier.padding(top = 10.dp))
    Row {
        Text(
            text = reviewDto.name,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = reviewDto.position,
            color = selectColorByPosition(reviewDto.position),
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun selectColorByPosition(pos: String): Color {
    var color: Color = Color.Black

    when (pos) {
        "FW" -> color = md_theme_light_error
        "MF" -> color = md_theme_light_secondary
        "DF" -> color = Color.Green
        "GK" -> color = md_theme_light_primary
    }

    return color
}