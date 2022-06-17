package com.example.uaep.ui.review

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig

@Composable
fun RatingBarWithComments(
    rating: Float,
    onValueChange: (Float) -> Unit
) {
    Column (
        horizontalAlignment =  Alignment.CenterHorizontally,
    ){
        RatingBar(
            value = rating,
            config = RatingBarConfig()
                .size(50.dp),
            onValueChange = onValueChange,
            onRatingChanged = {
                Log.d("TAG", "onRatingChanged: $it")
            }
        )
        Spacer(modifier = Modifier.padding(all = 10.dp))
        Text(
            text = displayCommentByRating(rating),
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun displayCommentByRating(rating: Float): String {
    var comment = "";

    comment = when(rating) {
        0f -> "0점은 반드시 노쇼일 때만!"
        1f -> "왼발의 맙소사!!"
        2f -> "이 레벨은 아닌거 같은데~"
        3f -> "적절한 레벨인 듯?"
        4f -> "내가 쫌~ 더 잘하지만 인정~!"
        5f -> "UAEP 말고 UEFA로..?"
        else -> "올바른 별점 부탁해요!"
    }

    return comment;
}