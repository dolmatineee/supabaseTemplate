package com.example.myapplication

import kotlinx.serialization.Serializable


@Serializable
data class Sneaker(
    val sneaker_id: Int,
    val category_id: Int,
    val price: Double,
    val description: String,
    val name: String
)

@Serializable
data class SneakerPhoto(
    val photo_id: Int,
    val sneaker_id: Int,
    val photo_url: String
)