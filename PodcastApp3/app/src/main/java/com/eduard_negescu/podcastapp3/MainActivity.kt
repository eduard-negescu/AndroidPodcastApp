package com.eduard_negescu.podcastapp3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.eduard_negescu.podcastapp3.data.room.NoteDatabase
import com.eduard_negescu.podcastapp3.ui.screens.AboutScreen
import com.eduard_negescu.podcastapp3.ui.screens.BestPodcastsScreen
import com.eduard_negescu.podcastapp3.ui.screens.NoteScreen
import com.eduard_negescu.podcastapp3.ui.screens.PodcastDetailsScreen
import com.eduard_negescu.podcastapp3.ui.screens.WelcomeScreen
import com.eduard_negescu.podcastapp3.ui.theme.PodcastApp3Theme
import com.eduard_negescu.podcastapp3.ui.view_models.BestPodcastsViewModel
import com.eduard_negescu.podcastapp3.ui.view_models.BestPodcastsViewModelFactory
import com.eduard_negescu.podcastapp3.ui.view_models.NoteViewModel
import com.eduard_negescu.podcastapp3.ui.view_models.PodcastDetailsViewModel
import com.eduard_negescu.podcastapp3.ui.view_models.PodcastDetailsViewModelFactory

class MainActivity : ComponentActivity() {

    private val bestPodcastsViewModel: BestPodcastsViewModel by viewModels { BestPodcastsViewModelFactory() }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            name = "notes.db"
        ).build()
    }

    private val noteViewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(db.noteDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PodcastApp3Theme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {  ->
                    NavHost(navController, startDestination = "welcome") {
                        composable("welcome") {
                            WelcomeScreen(navController)
                        }
                        composable("best_podcasts") {
                            BestPodcastsScreen(bestPodcastsViewModel, navController)
                        }
                        composable(
                            route = "podcast_details/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: return@composable
                            val podcastDetailsViewModel: PodcastDetailsViewModel by viewModels { PodcastDetailsViewModelFactory(id) }
                            PodcastDetailsScreen(podcastDetailsViewModel, navController)
                        }
                        composable(route = "notes") {
                            val state = noteViewModel.state.collectAsState().value
                            NoteScreen(state, noteViewModel::onEvent, navController)
                        }
                        composable("about") {
                            AboutScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
