package com.nerodev.blockbuster.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nerodev.blockbuster.ui.theme.Background


@Composable
fun SearchMovieScreen(
    currentFilter: Boolean,
    onSearch: (String) -> Unit,
    onFilterChange:(Boolean) -> Unit
){
    var inputSearch by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Background
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            FavoritesChip(
                currentFilter = currentFilter,
                onFilterChange = onFilterChange
            )
            OutlinedTextField(
                value = inputSearch,
                onValueChange = {
                    inputSearch = it
                },
                label = { Text("Search") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            IconButton(
                onClick = { onSearch(inputSearch) },
                enabled = inputSearch.isNotBlank(),
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Filled.Search, contentDescription = null)
            }

        }
    }

}