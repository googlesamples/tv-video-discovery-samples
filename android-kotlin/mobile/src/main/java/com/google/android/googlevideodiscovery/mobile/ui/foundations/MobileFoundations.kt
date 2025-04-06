package com.google.android.googlevideodiscovery.mobile.ui.foundations

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations

class MobileFoundations : Foundations {
    @Composable
    override fun Text(text: String) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Text(text: String, modifier: Modifier) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Text(text: String, style: TextStyle) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Text(text: String, modifier: Modifier, style: TextStyle) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Button(onClick: () -> Unit, content: @Composable (RowScope.() -> Unit)) {
        androidx.compose.material3.Button(
            onClick = onClick,
            content = content
        )
    }

    @Composable
    override fun Button(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable() (RowScope.() -> Unit)
    ) {
        androidx.compose.material3.Button(
            onClick = onClick,
            modifier = modifier,
            content = content
        )
    }

    @Composable
    override fun Surface(content: @Composable() (RowScope.() -> Unit)) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Surface(modifier: Modifier, content: @Composable() (RowScope.() -> Unit)) {
        Surface(content = content)
    }

    @Composable
    override fun Surface(onClick: () -> Unit, content: @Composable() (RowScope.() -> Unit)) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Surface(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable() (RowScope.() -> Unit)
    ) {
        TODO("Not yet implemented")
    }

}