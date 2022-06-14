package com.example.uaep.ui.review

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.uaep.R
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.Player
import com.example.uaep.dto.RateRequestDto
import com.example.uaep.dto.RoomDto
import com.example.uaep.dto.Team
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.ReAuthService
import com.example.uaep.ui.components.Profile
import com.example.uaep.ui.profile.ProfileDto
import com.example.uaep.ui.theme.UaepTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

data class ReviewDto(
    val name: String,
    val position: String,
    val level: String,
)

@Composable
fun ReviewDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    profileDto: ProfileDto,
    pos: String,
    teamB: Boolean,
    room: RoomDto,
    onRefresh: (String) -> Unit,
    context: Context
) {
    val reviewDto = ReviewDto(
        name = profileDto.name,
        position = profileDto.position,
        level= profileDto.level
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
                        text = reviewDto.level,
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
                            val type = if (teamB) "B" else "A"
                            Log.i("review property", room.id +"   "+ type.toString()+ "     " + pos.uppercase())
                            AuthService.getInstance()
                                .reviewOne(room.id, type, pos.uppercase(), RateRequestDto(rating.toString()))
                                .enqueue(object :
                                    Callback<Void> {
                                    override fun onResponse(
                                        call: Call<Void>,
                                        response: Response<Void>
                                    ) {
                                        if (response.isSuccessful) {
                                            Log.i(
                                                "position_success",
                                                response.body().toString()
                                            )
                                            Toast.makeText(context, "리뷰가 성공적으로 등록되었습니다", Toast.LENGTH_SHORT).show()
                                            onRefresh(room.id)
                                            onDismissRequest()
                                        } else {
                                            Log.i(
                                                "position_fail_raw",
                                                response.raw().toString()
                                            )
                                            Log.i(
                                                "position_fail_head",
                                                response.headers().toString()
                                            )
                                            val errorResponse: ErrorResponse? =
                                                Gson().fromJson(
                                                    response.errorBody()!!.charStream(),
                                                    object :
                                                        TypeToken<ErrorResponse>() {}.type
                                                )
                                            if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
                                                ReAuthService.getInstance().reauth()
                                                    .enqueue(object :
                                                        Callback<DummyResponse> {

                                                        override fun onResponse(
                                                            call: Call<DummyResponse>,
                                                            response: Response<DummyResponse>
                                                        ) {
                                                            if (response.isSuccessful) {
                                                                val tokens =
                                                                    CookieChanger<DummyResponse>().change(
                                                                        response
                                                                    )
                                                                AuthService.getCookieJar()
                                                                    .saveToken(tokens)
                                                            }
                                                        }

                                                        override fun onFailure(
                                                            call: Call<DummyResponse>,
                                                            t: Throwable
                                                        ) {
                                                            Log.i("test", "실패$t")
                                                        }
                                                    })
                                            }
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<Void>,
                                        t: Throwable
                                    ) {
                                        Log.i("test", "실패$t")
                                    }
                                })
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
    val room1 = RoomDto(
        id = "2001",
        title = "let's play soccer",
        gender = "남성",
        date = Date(2016,5,4,12,14),
        number = "6v6",
        host="sonny",
        teamA = Team(
            fw = Player(
                email = "test@gmail.com",
                name = "name",
                gender = "남성",
                province = "province",
                town = "town",
                position = "FW",
                level= "비기너1"
            ),
            mf1 = null,
            mf2 = null,
            df1 = null,
            df2 = null,
            gk = null,
            captain = Player(
                email = "test@gmail.com",
                name = "name",
                gender = "남성",
                province = "province",
                town = "town",
                position = "FW",
                level= "비기너1"
            )
        ),
        teamB = Team(
            fw = Player(
                email = "test@gmail.com",
                name = "name",
                gender = "남성",
                province = "province",
                town = "town",
                position = "FW",
                level= "비기너1"
            ),
            mf1 = null,
            mf2 = null,
            df1 = null,
            df2 = null,
            gk = null,
            captain = Player(
                email = "test@gmail.com",
                name = "name",
                gender = "남성",
                province = "province",
                town = "town",
                position = "FW",
                level= "비기너1"
            )
        ),
        status = null,
        level_limit = null,
        level_distribution = null
    )
    UaepTheme {
        ReviewDialog(
            visible = true,
            onDismissRequest = {},
            profileDto = ProfileDto(
                name = "김광진",
                position = "FW",
                province = "province",
                town = "town",
                gender = "남성",
                level = "비기너1",
                positionChangePoint = 30
            ),
            pos = "fw",
            teamB = true,
            room = room1,
            onRefresh = {},
            context = LocalContext.current
        )
    }
}