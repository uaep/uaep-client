package com.example.uaep.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.ui.theme.md_theme_light_primary

//var selectedItem by remember { mutableStateOf(0) }
//val items = listOf("Songs", "Artists", "Playlists")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreenView() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("UAEP") },
                navigationIcon = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(md_theme_light_primary),
            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        },
//        bottomBar = {
//            NavigationBar(
//
//            ) {
//                items.forEachIndexed { index, item ->
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
//                        label = { Text(item) },
//                        selected = selectedItem == index,
//                        onClick = { selectedItem = index }
//                    )
//                }
//            }
//        }
    )
}

@Preview
@Composable
fun PreviewMainScreen() {
    CommonScreenView()
}