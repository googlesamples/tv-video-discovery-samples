package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle = FontStyle.Normal,
) {
    LocalFoundations.current.Text(
        text = text,
        modifier = modifier,
        style = LocalFoundations.current.localTextStyle.copy(
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
        )
    )
}

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalFoundations.current.localTextStyle
) {
    LocalFoundations.current.Text(
        text = text,
        modifier = modifier,
        style = style
    )
}