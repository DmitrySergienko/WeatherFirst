package ru.ds.weatherfirst.presentation.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginSCreen() {
    //context
    val context = LocalContext.current
    // a coroutine scope
    val scope = rememberCoroutineScope()
    // we instantiate the saveEmail class
    val dataStore = SaveDataStore(context)


    val mainScreenViewModel = hiltViewModel<HomeViewModel>()
    val state by mainScreenViewModel.stateMain.collectAsState()


    mainScreenViewModel.getWeather("Dubai")

    //==================================================
    val test = state.uv





    //==================================================

    Column(modifier = Modifier.wrapContentSize()) {
        var email by remember { mutableStateOf("") }
        //
        Text(
            modifier = Modifier
                .padding(16.dp, 0.dp)
                .alpha(0.6f),
            text = "EMAIL",
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            fontSize = 12.sp
        )
        //email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },

            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType
                = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .fillMaxWidth(),

            )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                //launch the class in a coroutine scope
                scope.launch {
                     if(dataStore.internetCheck(context))dataStore.save(test.toString())

                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
        ) {
            Text(
                style = MaterialTheme.typography.subtitle1,
                color = Color.White,
                text = "Save Email",

                )
        }
        Spacer(modifier = Modifier.height(32.dp))


        val userEmail = dataStore.read.collectAsState(initial = "")

        Text(text = userEmail.value!!)

    }
}