package ru.ds.weatherfirst.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun DropDownMenu() {

    var city by rememberSaveable { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    val suggestions = mutableListOf("1", "2")
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(Modifier.padding(20.dp)) {

        OutlinedTextField(
            value = city,
            onValueChange = { newCity ->
                city = newCity
            },

            modifier = Modifier
                .padding(start = 5.dp, bottom = 5.dp)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text(text = "City") },
            placeholder = { Text(text = "Enter city") },
            singleLine = true,
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current){textfieldSize.width.toDp()})
    ) {
        suggestions.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText = label
                expanded = false

            }) {
                Text(text = label)
            }
        }
    }
}

