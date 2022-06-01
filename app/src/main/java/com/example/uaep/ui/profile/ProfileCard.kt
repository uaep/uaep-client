package com.example.uaep.ui.profile

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun ProfileCard(
    profileDto: ProfileDto
) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Surface(
        shape = MaterialTheme.shapes.large,
        tonalElevation = 100.dp,
        color = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth(),
        shadowElevation = 40.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded = !isExpanded
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Filled.Person,
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            ProfileInfo(
                profileInfoDto = profileDto
            )
            AnimatedVisibility(visible = isExpanded) {
                ProfileUpdateForm()
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewProfileCard() {
    UaepTheme {
        ProfileCard(ProfileDto(
            name = "김광진",
            position = "FK",
            address = "경기도 수원시 00대로",
            gender = "남자"
        ))
    }
}