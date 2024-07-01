package com.example.firebasenotes.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.firebasenotes.viewModel.LoginViewModel

@Composable

fun TabsViews(navController: NavController,loginVM: LoginViewModel){
    var selectTab by remember { mutableStateOf(0) }
    val tabs = listOf("Inicio de sesión","Nuevo usuario")

    Column {
        TabRow(selectedTabIndex = selectTab,
            indicator = {tabPositions ->
                TabRowDefaults.Indicator(Modifier.tabIndicatorOffset(tabPositions[selectTab])
                )
            }
        )
        {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectTab == index ,
                    onClick = { selectTab = index },
                    text = { Text(text = title) }
                )

            }
        }

        when(selectTab){
            0 -> LoginView(navController, loginVM)
            1 -> RegisterView(navController , loginVM)
        }
    }

}