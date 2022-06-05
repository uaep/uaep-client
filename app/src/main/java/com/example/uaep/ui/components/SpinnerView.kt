package com.example.uaep.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.uaep.R

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun SpinnerView(
    viewModel: SpinnerViewModel
) {
    val expanded by remember { mutableStateOf(false) }
    val transitionState = remember { MutableTransitionState(expanded).apply {
        targetState = !expanded
    }}
    val context = LocalContext.current
    val dateTime = viewModel.time.observeAsState()

    TextButton(
        onClick = {
            viewModel.selectDateTime(context)
        },
        modifier = Modifier
            .fillMaxWidth(0.8f),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Text(
            text = "경기 날짜 선택하기",
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = FontFamily(Font(R.font.jua_regular)),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
        )
    }
    TextButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth(0.8f),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (!viewModel.selected.value) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary,
        )
    ) {
        Text (
            text = dateTime.value ?: "경기 일정 없음",
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = FontFamily(Font(R.font.jua_regular)),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
        )
    }
}