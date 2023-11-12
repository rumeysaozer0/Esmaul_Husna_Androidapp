package com.arbolesyazilim.esmaulhusna


import java.io.Serializable

data class Name(
    val id: Int,
    val nameArabic: String,
    val name: String,
    val meaning: String,
    val number: Int,
    val intention: String
) : Serializable

