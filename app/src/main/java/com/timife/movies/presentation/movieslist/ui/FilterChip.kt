package com.timife.movies.presentation.movieslist.ui

import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChip(isSelected:Boolean, onFilterSelected: () -> Unit, text: String) {
//    val isSelected = remember { mutableStateOf(text == selectedFilter) }

    Chip(
        onClick =onFilterSelected,
        colors = if (isSelected) ChipDefaults.chipColors(
            backgroundColor = Color.Red,
            contentColor = Color.White
        ) else ChipDefaults.chipColors(
            backgroundColor = Color.LightGray,
            contentColor = Color.DarkGray
        )
    ) {
        Text(text = text)
    }
}