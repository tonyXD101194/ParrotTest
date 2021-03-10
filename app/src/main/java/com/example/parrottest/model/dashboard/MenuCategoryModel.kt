package com.example.parrottest.model.dashboard

import com.example.parrottest.database.model.CategoryRoomModel
import com.example.parrottest.database.model.ProductRoomModel

class MenuCategoryModel (

    val category: CategoryRoomModel,
    val listProduct: List<ProductRoomModel>,
    val counter: Int,
    var isExpandable: Boolean = false
)