package com.example.uaep.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.ui.theme.UaepTheme

data class ProfileDto(
    val name: String,
    val position: String,
    val address: String,
    val gender: String,
    val levelPoint: Int,
    val positionChangePoint: Int?
)

@Composable
fun ProfileInfo(
    profileInfoDto: ProfileDto,
) {
    Column (modifier = Modifier.padding(vertical = 20.dp).padding(end = 60.dp)){
        Row (modifier = Modifier.padding(start = 4.dp)){
            Text(
                text = profileInfoDto.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            // TODO: 포지션 색에 알맞게 텍스트 컬러 수정
            Text(
                text = profileInfoDto.position,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        Text(
            text = stringResource(id = R.string.address) + " : " + profileInfoDto.address,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = Int.MAX_VALUE,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = stringResource(id = R.string.gender) + " : " + profileInfoDto.gender,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = Int.MAX_VALUE,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = stringResource(id = R.string.level) + " : " + profileInfoDto.levelPoint,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = Int.MAX_VALUE,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        if(profileInfoDto.positionChangePoint !=null) {
            Text(
                text = stringResource(id = R.string.position_change_point) + " : " + profileInfoDto.positionChangePoint,
                modifier = Modifier.padding(all = 4.dp),
                maxLines = Int.MAX_VALUE,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.ExtraBold
            )
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
fun PreviewProfileInfo() {
    UaepTheme {
        ProfileInfo(
            profileInfoDto = ProfileDto(
                name = "김광진",
                position = "FK",
                address = "경기도 수원시 어디로 가야하죠 아저씨 119",
                gender = "남자",
                levelPoint = 1,
                positionChangePoint = 1000
            )
        )
    }
}