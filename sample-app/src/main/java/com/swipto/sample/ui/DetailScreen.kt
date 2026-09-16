package com.swipto.sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.swipto.ui.SwiptoScreen
import com.swipto.ui.swiptoViewModel

@Composable
fun DetailScreen(
    navController: NavController,
    vm: DetailViewModel = swiptoViewModel(),
) {
    SwiptoScreen(state = vm.state) { state ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = state.item?.title ?: "Detail",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = state.item?.description.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
            )
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }
        }
    }
}
