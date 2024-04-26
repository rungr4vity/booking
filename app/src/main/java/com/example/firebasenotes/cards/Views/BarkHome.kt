package com.example.firebasenotes.cards.Views

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebasenotes.cards.DataProvider

@Preview
@SuppressLint("RememberReturnType")
@Composable
fun BarkHomeContent(){

    val personal = remember { DataProvider.personalList}

    LazyColumn() {
        items(
            items = personal,
            itemContent = {
               PersonalListItem (per = it, navigateToProfile = {})
            }
        )
    }
}