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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.ui.foundation.Button
import com.google.android.googlevideodiscovery.common.ui.foundation.MaterialTheme
import com.google.android.googlevideodiscovery.common.ui.foundation.Text

@Composable
internal fun LoginScreen(
    performRegistration: () -> Unit,
    performLogin: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        Text("Login to App", fontSize = 24.sp)
        FormField(fieldName = "Username", fieldValue = "Champ")
        FormField(
            fieldName = "Password",
            fieldValue = "*********",
        )
        Text(
            "Note: Values above are dummy and cannot be modified",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontStyle = FontStyle.Italic
        )
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)) {
            Button(onClick = performLogin) {
                Text("Login")
            }

            Button(onClick = performRegistration) {
                Text("Register")
            }
        }
    }
}

@Composable
private fun FormField(
    fieldName: String,
    fieldValue: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(fieldName, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Box(
            modifier = Modifier
                .width(300.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurfaceVariant)
                .padding(10.dp),
        ) {
            Text(fieldValue)
        }
    }
}