package com.example.uaep.ui.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun ProfileScreen() {
    val notification = rememberSaveable { mutableStateOf("") }
    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    var name by rememberSaveable { mutableStateOf("default name") }
    var username by rememberSaveable { mutableStateOf("default username") }
    var bio by rememberSaveable { mutableStateOf("default bio") }

    Column(
//        modifier = Modifier
//            .verticalScroll(rememberScrollState())
//            .fillMaxSize()
//            .padding(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Cancel",
                modifier = Modifier.clickable { notification.value = "Cancelled" })
            Text(text = "Save",
                modifier = Modifier.clickable { notification.value = "Profile updated" })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Address", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Position", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Level", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Gender", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = { username = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Bio", modifier = Modifier
                    .width(100.dp)
                    .padding(top = 8.dp)
            )
            TextField(
                value = bio,
                onValueChange = { bio = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                singleLine = false,
                modifier = Modifier.height(150.dp)
            )
        }
    }
}

//@Composable
//fun ProfileCard(userProfile: UserProfile, clickAction: () -> Unit) {//埋一個clickAction作為可按的區域
//    Card(
//        modifier = Modifier
//            .padding(top = 8.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
//            .fillMaxWidth()
//            .wrapContentHeight(align = Alignment.Top)//因為不知道高度
//            .clickable { clickAction.invoke() } ,
//        elevation = 8.dp) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start) {//靠左
//            ProfilePicture(userProfile.pictureUrl, userProfile.status, 72.dp)
//            ProfileContent(userProfile.name, userProfile.status, Alignment.Start)
//        }
//
//    }
//}
//
//
//@Composable
//fun  ProfilePicture(pictureUrl: String, onLineStatus: Boolean, imageSize: Dp) {
//    Card (
//        shape = CircleShape,
//        border = BorderStroke(
//            width = 2.dp,
//            color = if (onLineStatus) Color.Green
//            else Color.Red
//        ),
//        modifier = Modifier
//            .padding(16.dp)
//            .size(imageSize),
//        elevation = 4.dp
//    ) {
//        Image(
//            painter = rememberImagePainter(data = pictureUrl,
//                builder = {
//                    transformations(CircleCropTransformation())
//                }),
//            contentDescription =  "profile",
//            modifier = Modifier.size(72.dp)
//        )
//
//    }
//
//}
//
//
//@Composable
//fun  ProfileContent(userName: String, onLineStatus: Boolean, alignment: Alignment.Horizontal) {
//    Column(
//        modifier = Modifier
//            .padding(8.dp),
//        horizontalAlignment = alignment
//    ) {
//        CompositionLocalProvider(LocalContentAlpha provides
//                if(onLineStatus) 1f
//                else ContentAlpha.medium) {
//
//            Text(
//                userName,
//                style = MaterialTheme.typography.h5)
//
//        }
//        //CompositionLocalProvider Composable 之間互相傳遞資料的方式
//        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//            Text(
//                if(onLineStatus) "Active now"
//                else "Offline",
//                style = MaterialTheme.typography.body2)
//        }
//
//    }
//
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UaepTheme {
        ProfileScreen()
    }
}