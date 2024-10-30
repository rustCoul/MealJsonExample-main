package com.example.mealjsonexample

import android.util.Log

data class CategoriesState(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var error: String? = null,
    var result: List<Category> = listOf()
)

data class MealsState(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var error: String? = null,
    var result: List<Meal> = listOf()
)
data class DishState(
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var error: String? = null,
    var result: List<Dish> = listOf()
)
class MealsRepository {
    private var _categoryState = CategoriesState()

    val categoriesState get() = _categoryState

    private var _mealsState = MealsState()

    val mealsState get() = _mealsState

    private  var _dishState= DishState()

    val dishState get() = _dishState

    suspend fun getAllCategories(): CategoriesResponse {
        return mealService.getAllCategories()
    }

    suspend fun getAllMealsByCategoryName(categoryName: String): MealsResponse{
        return mealService.getAllDishesByCategoryName(categoryName)
    }
    suspend fun getAllMealsByArea(areaName: String): MealsResponse{
        return mealService.getAllDishesByArea(areaName)
    }
    suspend fun getDishById(Id: String): DishResponse{
        return mealService.getDishById(Id)
    }
}