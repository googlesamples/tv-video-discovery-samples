package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    primaryTextColor: Color,
    actionButtons: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        BasicText("Login to App", style = TextStyle(fontSize = 24.sp, color = primaryTextColor))
        FormField(fieldName = "Username", fieldValue = "Champ", primaryTextColor = primaryTextColor)
        FormField(
            fieldName = "Password",
            fieldValue = "*********",
            primaryTextColor = primaryTextColor
        )
        BasicText(
            "Note: Values above are dummy and cannot be modified",
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            ),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)) {
            actionButtons()
        }
    }
}

@Composable
private fun FormField(
    fieldName: String,
    fieldValue: String,
    primaryTextColor: Color,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        BasicText(fieldName, style = TextStyle(color = Color.LightGray))
        Box(
            modifier = Modifier
                .width(300.dp)
                .border(1.dp, Color.Gray)
                .padding(10.dp),
        ) {
            BasicText(fieldValue, style = TextStyle(color = primaryTextColor))
        }
    }
}