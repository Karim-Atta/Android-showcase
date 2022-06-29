package com.karem.dota.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import coil.ImageLoader
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.karem.dota.ui.navigation.Screen
import com.karem.dota.ui.theme.DotaTheme
import com.karem.ui_herodetails.presentation.HeroDetailsViewModel
import com.karem.ui_herodetails.ui.HeroDetails
import com.karem.ui_herolist.ui.HeroList
import com.karem.ui_herolist.presentation.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageLoader = ImageLoader.Builder(applicationContext)
            .error(com.karem.ui_herolist.R.drawable.error_image)
            .placeholder(com.karem.ui_herolist.R.drawable.white_background)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()

        setContent {
            DotaTheme {
                val navController = rememberAnimatedNavController()

                BoxWithConstraints {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.HeroList.route,
                        builder = {
                            addHeroList(
                                navController,
                                imageLoader,
                                width = constraints.maxWidth
                            )
                            addHeroDetails(
                                imageLoader,
                                width = constraints.maxWidth
                                )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHeroList(
    navController: NavController,
    imageLoader: ImageLoader,
    width: Int,
    ){
    composable(
        route = Screen.HeroList.route,
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
    ) {
        val viewModel: HeroListViewModel = hiltViewModel()
        HeroList(
            state = viewModel.state.value,
            events = viewModel::onTriggerEvents,
            imageLoader
        ) { heroId ->
            navController.navigate("${Screen.HeroDetails.route}/$heroId")
        }
    }
}
@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHeroDetails(
    imageLoader: ImageLoader,
    width: Int
) {
    composable(
        route = Screen.HeroDetails.route + "/{heroId}",
        arguments = Screen.HeroDetails.arguments,
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -width },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(300))
        },
    ) {
        val viewModel: HeroDetailsViewModel = hiltViewModel()
        HeroDetails(
            state = viewModel.state.value,
            imageLoader = imageLoader,
            viewModel::onTriggerEvent
        )
    }
}
