package com.example.uaep.ui.rank

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.uaep.dto.UserDto
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.navigate.BottomNavigationBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingScreen (
    viewModel: RankingViewModel = RankingViewModel(context = LocalContext.current),
    navController: NavController
){
    Scaffold(
        Modifier.height(800.dp),
        topBar = {
            CommonTopAppBar(navController = navController, openDrawer = {})
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        LazyColumn {
            itemsIndexed(
                viewModel.mList.value
            ) { index, item ->
                RankCardSimple(index = index, user = item)
            }
        }
    }
}

@Composable
fun RankCardSimple(
    index: Int,
    user: UserDto
){
    Row(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(15)
            )
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = (index+1).toString()+"등",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "이름: " + user.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "레벨: " + user.level,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )

    }
}