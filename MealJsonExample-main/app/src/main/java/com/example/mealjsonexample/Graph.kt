package com.example.mealjsonexample

object Graph {
    val mainScreen: Screen = Screen("MainScreen")
    val secondScreen: Screen = Screen("SecondScreen")
    val thirdScreen: Screen = Screen("ThirdScreen")
}

data class Screen(
    val route: String,
)