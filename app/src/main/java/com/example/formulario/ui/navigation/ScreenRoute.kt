package com.example.formulario.ui.navigation

sealed class ScreenRoute(val route: String) {
    object Formulario : ScreenRoute("formulario")
    object Lista : ScreenRoute("lista")
    object Welcome: ScreenRoute("welcome")
}