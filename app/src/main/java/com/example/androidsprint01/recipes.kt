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
}