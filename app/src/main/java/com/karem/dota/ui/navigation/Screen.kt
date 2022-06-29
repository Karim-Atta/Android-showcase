package com.karem.dota.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object HeroList: Screen(
        route = "heroList",
        arguments = listOf(),
    )

    object HeroDetails:Screen(
        route = "heroDetails",
        arguments = listOf(
            navArgument("heroId"){
                type= NavType.IntType
            }
        )
    )
}