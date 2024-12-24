package com.nerodev.blockbuster.presentation.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.nerodev.blockbuster.presentation.viewmodel.MovieViewModel

@Composable
fun FavoritesChip(
    currentFilter: Boolean,
    onFilterChange:(Boolean) -> Unit
) {

    FilterChip(
        onClick = {
            onFilterChange(!currentFilter) },
        label = {
            Text("Favorites")
        },
        selected = currentFilter,
        leadingIcon = if (currentFilter) {
            {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}