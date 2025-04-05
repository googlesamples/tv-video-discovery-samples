package com.google.android.googlevideodiscovery.tv.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.Text

@Composable
internal fun TvLoginScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text("Login to App", fontSize = 20.sp)
        FormField(fieldName = "Username", fieldValue = "Champ")
        FormField(fieldName = "Password", fieldValue = "*********")
        Button(onClick = { }) {
            Text("Login")
        }
    }
}

@Composable
private fun FormField(
    fieldName: String,
    fieldValue: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(fieldName, color = Color.LightGray)
        Box(
            modifier = Modifier
                .width(300.dp)
                .border(1.dp, Color.Gray)
                .padding(10.dp),
        ) {
            Text(fieldValue)
        }
    }
}