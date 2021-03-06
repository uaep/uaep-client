package com.example.uaep.ui.signup

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.SignUpRequestDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.enums.Level
import com.example.uaep.enums.Position
import com.example.uaep.enums.Province
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.components.CustomOutlinedTextField
import com.example.uaep.ui.components.GenderExposedDropDownMenu
import com.example.uaep.ui.components.PasswordOutlinedTextField
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.ui.theme.UaepTheme
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignUpScreen (
    vm: SignUpViewModel,
    navController: NavController,
    email: String,
    token: String
) {
    val context = LocalContext.current

    val levelList = listOf(
        Level.STARTER,
        Level.BEGINNER,
        Level.AMATEUR,
        Level.SEMIPRO,
        Level.PRO,
    )

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(800.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(
                modifier = Modifier.padding(20.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomOutlinedTextField(
                    value = vm.name.value,
                    onValueChange = { vm.updateName(it) },
                    labelText = stringResource(id = R.string.name),
                    placeholderText = stringResource(id = R.string.name)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { vm.updateEmail(it) },
                    enabled = false,
                    readOnly = true,
                    placeholder = { Text(stringResource(R.string.email)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
                )
                PasswordOutlinedTextField(
                    password = vm.password.value,
                    onValueChange = { vm.updatePassword(it) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = MaterialTheme.colorScheme.primary
                )
                PasswordOutlinedTextField(
                    password = vm.matchingPassword.value,
                    onValueChange = { vm.updateMatchingPassword(it) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.matching_password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.matching_password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = MaterialTheme.colorScheme.primary
                )
                Row (
                    modifier = Modifier.fillMaxWidth(0.8f)
                ){
                    Column (modifier = Modifier.weight(0.45f)){
                        androidx.compose.material3.OutlinedTextField(
                            value = vm.province.value,
                            onValueChange = { vm.updateProvince(it) },
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
                            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                                textColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = vm.icon3,
                                    contentDescription = null,
                                    Modifier.clickable {
                                        vm.onProvinceEnabled(!vm.provinceEnabled.value)
                                    },
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                        androidx.compose.material3.DropdownMenu(
                            expanded = vm.provinceEnabled.value,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.onBackground)
                                .verticalScroll(rememberScrollState())
                                .height(200.dp),
                            onDismissRequest = { vm.onProvinceEnabled(false) }
                        ) {
                            vm.provinceList.forEach {
                                androidx.compose.material3.DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.value,
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    },
                                    onClick = {
                                        vm.updateProvince(it.value)
                                        vm.onProvinceEnabled(false)
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
                        androidx.compose.material3.OutlinedTextField(
                            value = vm.town.value,
                            onValueChange = { vm.updateTown(it) },
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
                            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                                textColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = vm.icon5,
                                    contentDescription = null,
                                    Modifier.clickable {
                                        vm.onTownEnabled(!vm.townEnabled.value)
                                    },
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                        androidx.compose.material3.DropdownMenu(
                            expanded = vm.townEnabled.value,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.onBackground)
                                .verticalScroll(rememberScrollState())
                                .height(200.dp),
                            onDismissRequest = { vm.onTownEnabled(false) }
                        ) {
                            when (vm.province.value) {
                                Province.SEOUL.value -> {
                                    vm.townSeoulList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                   vm.townGyeonggiList.forEach {
                                       androidx.compose.material3.DropdownMenuItem(
                                           text = {
                                               Text(
                                                   text = it.town,
                                                   fontWeight = FontWeight.ExtraBold
                                               )
                                           },
                                           onClick = {
                                               vm.updateTown(it.town)
                                               vm.onTownEnabled(false)
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
                                    vm.townIncheonList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townGwangjuList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townDaejeonList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townDaeguList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townBusanList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townUlsanList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townGyeonggiList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townGangwonList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townChungBukList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townChungNamList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townJeonBukList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townJeonNamList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townGyungBukList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townGyungNamList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                                    vm.townJejuList.forEach {
                                        androidx.compose.material3.DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = it.town,
                                                    fontWeight = FontWeight.ExtraBold
                                                )
                                            },
                                            onClick = {
                                                vm.updateTown(it.town)
                                                vm.onTownEnabled(false)
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
                GenderExposedDropDownMenu(
                    gender = vm.gender.value,
                    label = {
                        Text(
                            text = stringResource(id = R.string.gender),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.gender),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = MaterialTheme.colorScheme.primary,
                    vm = vm
                )
                Column {
                    Box {
                        OutlinedTextField(
                            value = vm.position.value,
                            readOnly = true,
                            onValueChange = {},
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
                                    text = stringResource(id = R.string.position),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .clickable {
                                    vm.onPosEnabled(!vm.posEnabled.value)
                                },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = vm.icon2,
                                    contentDescription = null,
                                    Modifier.clickable {
                                        vm.onPosEnabled(!vm.posEnabled.value)
                                    },
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                    DropdownMenu(
                        expanded = vm.posEnabled.value,
                        onDismissRequest = {
                            vm.onPosEnabled(false)
                        },
                        modifier = Modifier
                            .width(300.dp)
                            .background(MaterialTheme.colorScheme.onBackground),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.GK)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.GK.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.DF)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.DF.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.MF)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.MF.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.FW)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.FW.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight(1000)
                            )
                        }

                    }
                }
                Column {
                    androidx.compose.material3.OutlinedTextField(
                        value = vm.level.value,
                        onValueChange = { vm.updateLevel(it) },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        readOnly = true,
                        label = {
                            Text(
                                text = stringResource(id = R.string.level),
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.level),
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = vm.icon4,
                                contentDescription = null,
                                Modifier.clickable {
                                    vm.onLevelEnabled(!vm.levelEnabled.value)
                                },
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    androidx.compose.material3.DropdownMenu(
                        expanded = vm.levelEnabled.value,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(MaterialTheme.colorScheme.onBackground),
                        onDismissRequest = { vm.onLevelEnabled(false) }
                    ) {
                        levelList.forEach {
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it.value,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                },
                                onClick = {
                                    vm.updateLevel(it.value)
                                    vm.onLevelEnabled(false)
                                },
                                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        if (!vm.isSamePassword(vm.password.value, vm.matchingPassword.value)) {
                            mToast(context, "??????????????? ???????????? ????????????.")
                        } else {
                            mToast(context, "??????????????? ?????? ????????????.")

                            val signUpRequestDto = SignUpRequestDto(
                                name = vm.name.value,
                                password = vm.password.value,
                                matchingPassword = vm.matchingPassword.value,
                                gender = vm.gender.value,
                                province =  vm.province.value,
                                town = vm.town.value,
                                position = vm.position.value,
                                level = vm.level.value
                            )
                            Log.i("signUpRequestDto", signUpRequestDto.toString())
                            val userApiService = UserApiService.getInstance()
                            userApiService.signup(
                                signUpRequestDto = signUpRequestDto,
                                token = token
                            ).enqueue(object :
                                Callback<UrlResponseDto> {
                                override fun onResponse(
                                    call: Call<UrlResponseDto>,
                                    response: Response<UrlResponseDto>
                                ) {
                                    if(response.isSuccessful) {
                                        navController.navigate(Screen.Login.route)
                                    } else {
                                        // TODO: ????????? ????????? json ?????? ??? ????????????
                                        val jObjError = JSONObject(
                                            response.errorBody()!!.string()
                                        )
                                        Log.d("Error Test", jObjError.toString())

                                    }
                                }

                                override fun onFailure(call: Call<UrlResponseDto>, t: Throwable) {
                                    Log.e("test", "??????$t")
                                }
                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

        }
    }
}

private fun mToast(context: Context, msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
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
fun SignUpScreenPreview() {
    UaepTheme {
        SignUpScreen(
            vm = SignUpViewModel(),
            navController = rememberNavController(),
            email = "test@gmail.com",
            token = "32904823904"
        )
    }
}