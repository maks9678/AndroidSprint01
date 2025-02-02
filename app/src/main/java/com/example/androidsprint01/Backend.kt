package com.example.androidsprint01


object BackendSingleton {
    private val categories: List<Categories> = listOf(
        Categories(0, "Бургеры", "Рецепты всех популярных видов бургеров", "burger.png"),
        Categories(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.png"
        ),
        Categories(2, "Пицца", "Пицца на любой вкус и цвет. Лучшая подборка для тебя", "pizza.png"),
        Categories(3, "Рыба", "Печеная, жареная, сушеная, любая рыба на твой вкус", "fish.png"),
        Categories(4, "Супы", "От классики до экзотики: мир в одной тарелке", "soup.png"),
        Categories(5, "Салаты", "Хрустящий калейдоскоп под соусом вдохновения", "salad.png")
    )

    fun getCategories(): List<Categories> = categories
    private val burgerRecipes = listOf(
        Dish("класический гамбургер","burger-hamburger.png"),
         Dish("чизбургер","burger-cheeseburger.png"),
         Dish("бургер с грибами и сыром","burger-mushrooms.png"),
         Dish("бургер с курицей и авокадо","burger-avocado.png"),
         Dish("бургер с рыбой","burger-fish.png"),
         Dish("бургер с беконом","burger-bacon.png"),
         Dish("веганский бургер","burger-vegan.png"),
         Dish("острый гамбургер","burger-chili.png"),
    )
    fun getRecipesByCategoryId(categoryId:Int): List<Dish> = if(categoryId==0) burgerRecipes else emptyList()
}