package com.example.mealjsonexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MealsViewModel: ViewModel() {
    private var mealsRepository = MealsRepository()

    private var categories = MutableStateFlow(mealsRepository.categoriesState)

    val categoriesState = categories.asStateFlow()

    private var meals = MutableStateFlow(mealsRepository.mealsState)

    val mealsState = meals.asStateFlow()

    private var dish = MutableStateFlow(mealsRepository.dishState)

    val dishState = dish.asStateFlow()


    private var _chosenId = MutableStateFlow("")

    val chosenId = _chosenId.asStateFlow()

    private var _chosenCategoryName = MutableStateFlow("")

    val chosenCategoryName = _chosenCategoryName.asStateFlow()

    private var _chosenAreaName = MutableStateFlow("")

    val chosenAreaName = _chosenAreaName.asStateFlow()

    private var _textFieldAreaName = MutableStateFlow("")

    val textFieldAreaName = _textFieldAreaName.asStateFlow()

    init {
        getAllCategories()
    }
    fun setChosenId(Id:String){
        _chosenId.value=Id
    }
    fun setChosenCategory(name: String){
        _chosenCategoryName.value = name
    }
    fun setChosenArea(name: String){
        _chosenAreaName.value = name
    }
    fun settextFieldAreaName(name: String){
        _textFieldAreaName.update {name}
    }
    fun getAllDishesByCategoryName(categoryName: String){
        viewModelScope.launch {
            try {
                meals.value = meals.value.copy(
                    isLoading = true
                )
                val response = mealsRepository.getAllMealsByCategoryName(categoryName)
                meals.value = meals.value.copy(
                    isLoading = false,
                    isError = false,
                    result = response.meals
                )

            }
            catch (e: Exception){
                meals.value = meals.value.copy(
                    isError = true,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    fun getAllDishesByArea(areaName: String){
        viewModelScope.launch {
            try {
                meals.value = meals.value.copy(
                    isLoading = true
                )
                val response = mealsRepository.getAllMealsByArea(areaName)
                meals.value = meals.value.copy(
                    isLoading = false,
                    isError = false,
                    result = response.meals
                )

            }
            catch (e: Exception){
                meals.value = meals.value.copy(
                    isError = true,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    fun getDishById(Id: String){
        viewModelScope.launch {
            try {
                dish.value = dish.value.copy(
                    isLoading = true
                )
                val response = mealsRepository.getDishById(Id)
                dish.value = dish.value.copy(
                    isLoading = false,
                    isError = false,
                    result = response.dish
                )

            }
            catch (e: Exception){
                dish.value = dish.value.copy(
                    isError = true,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun getAllCategories(){
        viewModelScope.launch {
            try {
                categories.value = categories.value.copy(
                    isLoading = true
                )
                val response = mealsRepository.getAllCategories()

                categories.value = categories.value.copy(
                    isLoading = false,
                    isError = false,
                    result = response.categories
                )

            }
            catch (e: Exception){
                categories.value = categories.value.copy(
                    isError = true,
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}