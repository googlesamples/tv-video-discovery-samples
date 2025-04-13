package com.google.android.googlevideodiscovery.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.googlevideodiscovery.common.ui.navigation.NavigationGraph
import com.google.android.googlevideodiscovery.mobile.ui.foundations.MobileFoundations
import com.google.android.googlevideodiscovery.mobile.ui.theme.GoogleTvVideoDiscoverySampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoogleTvVideoDiscoverySampleTheme {
                Scaffold { paddings ->
                    val foundations = remember { MobileFoundations() }

                    NavigationGraph(
                        modifier = Modifier.padding(paddings),
                        foundations = foundations
                    )
                }
            }
        }
    }
}
