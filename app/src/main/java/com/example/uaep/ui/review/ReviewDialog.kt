package com.example.uaep.ui.review

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.uaep.R
import com.example.uaep.ui.components.Profile
import com.example.uaep.ui.profile.ProfileDto
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_error
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary

data class ReviewDto(
    val name: String,
    val position: String,
    val levelPoint: Int,
)

@Composable
fun ReviewDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    profileDto: ProfileDto
) {
    val reviewDto = ReviewDto(
        name = profileDto.name,
        position = profileDto.position,
        levelPoint = profileDto.levelPoint
    )

    val initialRating = 3.0F
    var rating by remember { mutableStateOf(initialRating) }

    if (visible) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            ){
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.review_level),
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = reviewDto.levelPoint.toString(),
                        color = selectColorByLevel(reviewDto.levelPoint),
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(top = 40.dp))
                    Profile(reviewDto = reviewDto)
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    RatingBarWithComments(
                        rating = rating,
                        onValueChange = {
                            Log.d("click", it.toString())
                            rating = when (rating) {
                                1f -> it
                                2f -> it
                                3f -> it
                                4f -> it
                                5f -> it
                                else -> 1f
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(all = 5.dp))
                    Button(
                        onClick = {
                            /* TODO: 리뷰 등록 HTTP */
                            Log.d("Network", "리뷰 등록")
                        },
                        modifier = Modifier.width(270.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSize))
                        Text("리뷰 등록!")
                    }
                }
            }
        }
    }

}

private fun selectColorByLevel(lv: Int): Color {
    var color: Color = Color.Black

    color = when (lv) {
        in 1..3 -> {
            md_theme_light_secondary
        }
        in 4..6 -> {
            md_theme_light_primary
        }
        else -> {
            md_theme_light_error
        }
    }

    return color
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    showBackground = true
)
@Composable
fun PreviewReview() {
    UaepTheme {
        ReviewDialog(
            visible = true,
            onDismissRequest = {},
            profileDto = ProfileDto(
                name = "김광진",
                position = "FW",
                address = "경기도 수원시",
                gender = "남성",
                levelPoint = 3,
                positionChangePoint = 30
            )
        )
    }
}