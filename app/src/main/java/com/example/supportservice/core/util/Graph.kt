package com.example.supportservice.core.util

sealed class Graph(val route: String) {
    object MainGraph : Graph("MainGraph")
}