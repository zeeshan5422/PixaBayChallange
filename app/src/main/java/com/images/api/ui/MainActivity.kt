package com.images.api.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.images.api.ui.theme.AppTheme
import com.images.api.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ZEESHAN on 5/12/2023.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainNavHost()
            }
        }
    }

    @Composable
    fun MainNavHost() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = ROUTE_MAIN) {
            composable(ROUTE_MAIN) {
                MainScreen(viewModel, navController)
            }
            composable(ROUTE_DETAIL) {
                val imageItem = viewModel.getImage()
                DetailScreen(imageItem)
            }
        }
    }

}
