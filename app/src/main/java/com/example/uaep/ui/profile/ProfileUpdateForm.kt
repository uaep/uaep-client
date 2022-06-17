package com.example.uaep.ui.profile

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.UserUpdateDto
import com.example.uaep.enums.*
import com.example.uaep.ui.theme.UaepTheme
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileUpdateForm(
    onUpdateUserInfo: (UserUpdateDto) -> Unit,
    onError: () -> Unit,
    onErrorMessage: (String) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var position by rememberSaveable { mutableStateOf("") }
    var province by rememberSaveable { mutableStateOf("") }
    var town by rememberSaveable { mutableStateOf("") }
    var positionEnabled by rememberSaveable { mutableStateOf(false) }
    var provinceEnabled by rememberSaveable { mutableStateOf(false) }
    var townEnabled by rememberSaveable { mutableStateOf(false) }
    val positionList = listOf(
        Position.GK,
        Position.DF,
        Position.MF,
        Position.FW,
    )

    val regionFilterLists = listOf(
        RegionFilter.SEOUL,
        RegionFilter.GG,
        RegionFilter.INCHEON,
        RegionFilter.DSC,
        RegionFilter.DG,
        RegionFilter.BUG,
        RegionFilter.GJ,
        RegionFilter.JEJU
    )

    Column(
        modifier = Modifier.padding(start = 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = stringResource(id = R.string.name),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.update_name),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
        Column {
            OutlinedTextField(
                value = position,
                onValueChange = { position = it },
                readOnly = true,
                label = {
                    Text(
                        text = stringResource(id = R.string.position),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.update_position),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                ),
                trailingIcon = {
                    Icon(
                        imageVector = if (positionEnabled) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        Modifier.clickable { positionEnabled = !positionEnabled },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            DropdownMenu(
                expanded = positionEnabled,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .background(MaterialTheme.colorScheme.onBackground),
                onDismissRequest = { positionEnabled = false }
            ) {
                positionList.forEach{
                    DropdownMenuItem(
                        text = {
                            Text(
                                text= it.value,
                                fontWeight = FontWeight.ExtraBold)
                        },
                        onClick = {
                            position = it.value
                            positionEnabled = false
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Filled.SportsSoccer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary)
                        },
                        colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                    )
                }
            }
        }
        Row (
            modifier = Modifier.fillMaxWidth(0.8f)
        ){
            Column (modifier = Modifier.weight(0.45f)){
                OutlinedTextField(
                    value = province,
                    onValueChange = { province = it },
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.province),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.province),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = if (provinceEnabled) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            Modifier.clickable {
                                provinceEnabled = !provinceEnabled
                            },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                DropdownMenu(
                    expanded = provinceEnabled,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onBackground)
                        .verticalScroll(rememberScrollState())
                        .height(200.dp),
                    onDismissRequest = { provinceEnabled = false }
                ) {
                    provinceList.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it.value,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            },
                            onClick = {
                                province = it.value
                                provinceEnabled = false
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Filled.PinDrop,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Column (modifier = Modifier.weight(0.45f)){
                OutlinedTextField(
                    value = town,
                    onValueChange = { town = it },
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.town),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.town),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = if (townEnabled) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            Modifier.clickable {
                                townEnabled = !townEnabled
                            },
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                DropdownMenu(
                    expanded = townEnabled,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onBackground)
                        .verticalScroll(rememberScrollState())
                        .height(200.dp),
                    onDismissRequest = { townEnabled = false }
                ) {
                    when (province) {
                        Province.SEOUL.value -> {
                            townSeoulList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.GYEONGGI.value -> {
                            townGyeonggiList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.INCHEON.value -> {
                            townIncheonList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.GWANGJU.value -> {
                            townGwangjuList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.DAEJEON.value -> {
                            townDaejeonList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.DAEGU.value -> {
                            townDaeguList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.BUSAN.value -> {
                            townBusanList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.ULSAN.value -> {
                            townUlsanList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.SEJONG.value -> {
                            townGyeonggiList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.GANGWON.value -> {
                            townGangwonList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.CHUNGCHEONGBUK.value -> {
                            townChungBukList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.CHUNGCHEONGNAM.value -> {
                            townChungNamList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.JEOLLABUK.value -> {
                            townJeonBukList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.JEOLLANAM.value -> {
                            townJeonNamList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.GYEONGSANGBUK.value -> {
                            townGyungBukList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.GYEONGSANGNAM.value -> {
                            townGyungNamList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                        Province.JEJU.value -> {
                            townJejuList.forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.town,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        town = it.town
                                        townEnabled = false
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Filled.PinDrop,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                                )
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                val userUpdateDto = UserUpdateDto(name, position, province, town)
                ProfileViewModel().userApiService.updateUserInfo(userUpdateDto).enqueue(object:
                    Callback<UserUpdateDto> {
                    override fun onResponse(
                        call: Call<UserUpdateDto>,
                        response: Response<UserUpdateDto>
                    ) {
                        if (!response.isSuccessful) {
                            val errorResponse = Gson().fromJson(
                                response.errorBody()?.string(),
                                ErrorResponse::class.java
                            )

                            Log.d("profile update", errorResponse.message.toString())
                            when (errorResponse.message) {
                                is String -> {
                                    onError()
                                    var message = "";

                                    if (errorResponse.message == "Precondition : Deselect positions in all participating games") {
                                        message = "참가 중인 경기가 있으면 포지션 변경이 불가능합니다."
                                    } else if (errorResponse.message.contains("Not enough points")) {
                                        message = "포지션 변경 포인트가 부족합니다."
                                    } else if (errorResponse.message == "No detailed region selected.") {
                                        message = "상세 주소를 선택해주세요."
                                    }

                                    onErrorMessage(message)
                                }
                                is List<*> -> {
                                    onError()
                                    var message = "";

                                    if (errorResponse.message[0] == "province must be a valid enum value") {
                                        message = "변경할 도시를 선택해주세요."
                                    } else if (errorResponse.message[0] == "position must be a valid enum value") {
                                        message = "변경할 포지션을 선택해주세요."
                                    }

                                    onErrorMessage(message)
                                }
                                else -> {
                                    Log.d("Any", errorResponse.message.toString())
                                }
                            }
                        }
                    }
                    override fun onFailure(call: Call<UserUpdateDto>, t: Throwable) {
                        Log.i("test", "실패$t")
                    }
                })
                onUpdateUserInfo(userUpdateDto)
            }
        ) {
            Text(text = stringResource(id = R.string.update_profile))
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
fun PreviewProfileUpdateForm() {
    UaepTheme {
        ProfileUpdateForm(
            onUpdateUserInfo = {},
            onError = {},
            onErrorMessage = {}
        )
    }
}