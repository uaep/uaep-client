package com.example.uaep.ui.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.uaep.enums.Position
import com.example.uaep.uitmp.*


@Composable
fun Formation(
    reverse: Boolean,
    modifier: Modifier
//    vm: FormationViewModel
) {
    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ){
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){
            PositionButton(
                color = if (!reverse) md_theme_light_primary else md_theme_light_error,
                position = if (!reverse) Position.GK else Position.FW
            )
        }
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){
            PositionButton(
                color = if (!reverse) md_theme_light_tertiary else md_theme_light_secondary,
                position = if (!reverse) Position.DF else Position.MF
            )
            PositionButton(
                color = if (!reverse) md_theme_light_tertiary else md_theme_light_secondary,
                position = if (!reverse) Position.DF else Position.MF
            )
        }
        Row (
            horizontalArrangement = Arrangement.SpaceAround,

            modifier = Modifier.fillMaxWidth()
        ){
            PositionButton(
                color = if (!reverse) md_theme_light_secondary else md_theme_light_tertiary,
                position = if (!reverse) Position.MF else Position.DF
            )
            PositionButton(
                color = if (!reverse) md_theme_light_secondary else md_theme_light_tertiary,
                position = if (!reverse) Position.MF else Position.DF
            )
        }
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){
            PositionButton(
                color = if (!reverse) md_theme_light_error else md_theme_light_primary,
                position = if (!reverse) Position.FW else Position.GK
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun FormationPreview() {
    UaepTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Formation(
                reverse = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}