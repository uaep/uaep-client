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
    val province: String,
    val town: String,
    val gender: String,
    val level: String,
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
            text = stringResource(id = R.string.province) + " : " + profileInfoDto.province,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = Int.MAX_VALUE,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = stringResource(id = R.string.town) + " : " + profileInfoDto.town,
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
            text = stringResource(id = R.string.level) + " : " + profileInfoDto.level,
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
                province = "서울",
                town = "강남구 테헤란로",
                gender = "남자",
                level = "비기너1",
                positionChangePoint = 1000
            )
        )
    }
}