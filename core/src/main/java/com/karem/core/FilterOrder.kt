package com.karem.core

sealed class FilterOrder {

    object Ascending: FilterOrder()

    object Descending: FilterOrder()
}