package com.example.parrottest.interfaces

import com.example.parrottest.enums.StatusProductEnum

interface ChangeStatusInterface {

    fun onChangeStatus(productId: String, status: StatusProductEnum, indexCategory: Int, indexProduct: Int)
}