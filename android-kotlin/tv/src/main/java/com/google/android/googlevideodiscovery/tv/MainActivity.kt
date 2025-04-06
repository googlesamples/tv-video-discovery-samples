/**
 * Copyright 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/

package com.google.android.googlevideodiscovery.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.tv.material3.Surface
import com.google.android.googlevideodiscovery.common.navigation.NavigationGraph
import com.google.android.googlevideodiscovery.tv.ui.foundations.TvFoundations
import com.google.android.googlevideodiscovery.tv.ui.screens.TvNavigationScreens
import com.google.android.googlevideodiscovery.tv.ui.theme.GoogleTvVideoDiscoverySampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleTvVideoDiscoverySampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val foundations = remember { TvFoundations() }
                    val screens = remember { TvNavigationScreens() }

                    NavigationGraph(
                        foundations = foundations,
                        screens = screens,
                    )
                }
            }
        }
    }
}
