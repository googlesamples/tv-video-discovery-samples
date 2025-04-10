package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundationsProvider

@Composable
fun LoginScreen(
    performRegistration: () -> Unit,
    performLogin: () -> Unit,
) {
    val foundations = LocalFoundationsProvider.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        foundations.Text("Login to App", style = TextStyle(fontSize = 24.sp))
        FormField(fieldName = "Username", fieldValue = "Champ")
        FormField(
            fieldName = "Password",
            fieldValue = "*********",
        )
        foundations.Text(
            "Note: Values above are dummy and cannot be modified",
            style = TextStyle(
                fontSize = 12.sp,
                color = foundations.onSurfaceVariant,
                fontStyle = FontStyle.Italic
            ),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)) {
            foundations.Button(onClick = performLogin) {
                foundations.Text("Login")
            }

            foundations.Button(onClick = performRegistration) {
                foundations.Text("Register")
            }
        }
    }
}

@Composable
private fun FormField(
    fieldName: String,
    fieldValue: String,
) {
    val foundations = LocalFoundationsProvider.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        foundations.Text(fieldName, style = TextStyle(color = foundations.onSurfaceVariant))
        Box(
            modifier = Modifier
                .width(300.dp)
                .border(1.dp, foundations.onSurfaceVariant)
                .padding(10.dp),
        ) {
            foundations.Text(fieldValue)
        }
    }
}