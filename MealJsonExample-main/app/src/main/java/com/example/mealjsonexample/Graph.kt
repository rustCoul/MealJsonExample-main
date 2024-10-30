package com.example.mealjsonexample

object Graph {
    val mainScreen: Screen = Screen("MainScreen")
    val secondScreen: Screen = Screen("SecondScreen")
    val thirdScreen: Screen = Screen("ThirdScreen")
    val dishScreen: Screen = Screen("DishScreen")
}

data class Screen(
    val route: String,
)