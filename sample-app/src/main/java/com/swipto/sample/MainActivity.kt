package com.swipto.sample

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.swipto.android.SwiptoActivity
import com.swipto.navigation.SwiptoNavGraph
import com.swipto.navigation.SwiptoNavHost
import com.swipto.sample.navigation.SampleRoutes
import com.swipto.sample.ui.DetailScreen
import com.swipto.sample.ui.HomeScreen
import com.swipto.ui.theme.SwiptoTheme

class MainActivity : SwiptoActivity() {
    override fun Content() {
        setContent {
            SwiptoTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SwiptoNavHost(
                        start = SampleRoutes.Home,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        graph = SwiptoNavGraph {
                            destination(SampleRoutes.Home) {
                                HomeScreen(navController = navController)
                            }
                            destination(SampleRoutes.Detail) {
                                DetailScreen(navController = navController)
                            }
                        },
                    )
                }
            }
        }
    }
}
