package com.example.mealjsonexample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width


@Composable
fun Navigation(
    modifier: Modifier,
    navigationController: NavHostController,
){
    val viewModel: MealsViewModel = viewModel()

    NavHost(
        modifier = modifier,
        navController = navigationController,
        startDestination = Graph.mainScreen.route
    ){
        composable(route = Graph.secondScreen.route){
            SecondScreen(viewModel, navigationController)
        }
        composable(route = Graph.mainScreen.route){
            MainScreen(viewModel, navigationController)
        }
        composable(route = Graph.thirdScreen.route){
            ThirdScreen(viewModel, navigationController)
        }
        composable(route = Graph.dishScreen.route){
            DishScreen(viewModel, navigationController)
        }
    }
}

@Composable
fun SecondScreen(viewModel: MealsViewModel ,navigationController: NavHostController) {
    val categoryName = viewModel.chosenCategoryName.collectAsState()
    val dishesState = viewModel.mealsState.collectAsState()
    viewModel.getAllDishesByCategoryName(categoryName.value)
    Column{
        if (dishesState.value.isLoading){
            LoadingScreen()
        }
        if (dishesState.value.isError){
            ErrorScreen(dishesState.value.error!!)
        }
        if (dishesState.value.result.isNotEmpty()){
            DishesScreen(dishesState.value.result,viewModel, navigationController)
        }
    }
}
@Composable
fun ThirdScreen(viewModel: MealsViewModel, navigationController: NavHostController) {
    val areaName = viewModel.chosenAreaName.collectAsState()
    val dishesState = viewModel.mealsState.collectAsState()
    viewModel.getAllDishesByArea(areaName.value)
    Column{
        if (dishesState.value.isLoading){
            LoadingScreen()
        }
        if (dishesState.value.isError){
            ErrorScreen(dishesState.value.error!!)
        }
        if (dishesState.value.result.isNotEmpty()){
            DishesScreen(dishesState.value.result, viewModel, navigationController)
        }
    }
}
@Composable
fun DishesScreen(result: List<Meal>,viewModel: MealsViewModel, navigationController: NavHostController) {
    LazyColumn(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        item { SearchTextField(viewModel, navigationController) }
        items(result){
            DishItem(it,viewModel,navigationController)
        }
    }
}

@Composable
fun DishItem(meal: Meal,viewModel: MealsViewModel, navigationController: NavHostController) {

    Box(
        modifier = Modifier.background(color = Color.DarkGray).clickable {
           viewModel.setChosenId(meal.idMeal)
            Log.v("${meal.idMeal}","${meal.idMeal}")

            navigationController.navigate(Graph.dishScreen.route)
        }
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier.height(150.dp),
                model = meal.strMealThumb,
                contentDescription = null
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = meal.mealName
            )
        }
    }
}
@Composable
fun DishScreen(viewModel: MealsViewModel, navigationController: NavHostController){
    val Id = viewModel.chosenId.collectAsState()
    val dishState = viewModel.dishState.collectAsState()
    viewModel.getDishById(Id.value)
    Column{
        if (dishState.value.isLoading){
            LoadingScreen()
        }
        if (dishState.value.isError){
            ErrorScreen(dishState.value.error!!)
        }
        if (!dishState.value.result.equals(null)){
            DishCompose(dishState.value.result,viewModel,navigationController)
        }
    }
}
@Composable
fun DishCompose(result: List<Dish>,viewModel: MealsViewModel, navigationController: NavHostController){
    LazyColumn(modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        items(result){
            DishComposeItem(it,viewModel,navigationController)
        }
    }
}
@Composable
fun DishComposeItem(dish: Dish,viewModel: MealsViewModel, navigationController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier.height(150.dp),
            model = dish.strMealThumb,
            contentDescription = null
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = dish.strMeal
        )
        Text(
            text = dish.strInstructions
        )
        Button(onClick = {navigationController.navigateUp()}) {
            Text(text="Back")
        }
    }
}
@Composable
fun MainScreen(viewModel: MealsViewModel, navigationController: NavHostController){


    val categoriesState = viewModel.categoriesState.collectAsState()

    if (categoriesState.value.isLoading){
        LoadingScreen()
    }
    if (categoriesState.value.isError){
        ErrorScreen(categoriesState.value.error!!)
    }
    if (categoriesState.value.result.isNotEmpty()){
        CategoriesScreen(viewModel, categoriesState.value.result, navigationController)
    }

}

@Composable
fun CategoriesScreen(viewModel: MealsViewModel, result: List<Category>, navigationController: NavHostController) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(1)
    ) {
        item { SearchTextField(viewModel,navigationController) }
        items(result){
            CategoryItem(viewModel, it, navigationController)
        }
    }
}

@Composable
fun CategoryItem(viewModel: MealsViewModel, category: Category, navigationController: NavHostController) {
    Box(
        modifier = Modifier.height(200.dp).background(color = Color.DarkGray)
            .clickable {
                viewModel.setChosenCategory(category.strCategory)
                navigationController.navigate("${Graph.secondScreen.route}")
            }
    ){
       Column(
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ) {

           AsyncImage(
               model = category.strCategoryThumb,
               contentDescription = null
           )
           Spacer(Modifier.height(5.dp))
           Text(
               text = category.strCategory
           )
       }
    }
}

@Composable
fun ErrorScreen(error: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error
        )
    }
}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}
@Composable
fun SearchTextField(viewModel: MealsViewModel, navigationController: NavHostController){
    val searchString by viewModel.textFieldAreaName.collectAsState()
    Spacer(modifier=Modifier.height(16.dp))
    Row (verticalAlignment = Alignment.CenterVertically){
        OutlinedTextField(
            value =  searchString,
            modifier = Modifier.padding(16.dp),
            onValueChange = {
            viewModel.settextFieldAreaName(it)
            },
            label = {
                Text(text="Enter Area")
            }
         )

        Button(onClick = {
            viewModel.setChosenArea(
                viewModel.run { textFieldAreaName.value}
            )
            navigationController.navigate("${Graph.thirdScreen.route}")
        }
        ) {
            Text(text="Confirm")
        }
    }

    Spacer(modifier=Modifier.height(16.dp))
}