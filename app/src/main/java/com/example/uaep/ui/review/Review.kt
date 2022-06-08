package com.example.uaep.ui.review

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.ui.theme.UaepTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig


@Composable
fun Review(
) {
    val initialRating = 3.0F
    var rating by remember { mutableStateOf(initialRating) }

    RatingBar(
        value = rating,
        config = RatingBarConfig()
            .size(50.dp),
        onValueChange = {
            rating = it
        },
        onRatingChanged = {
            Log.d("TAG", "onRatingChanged: $it")
        }
    )
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
        Review()
    }
}