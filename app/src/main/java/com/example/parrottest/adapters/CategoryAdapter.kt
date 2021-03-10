package com.example.parrottest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parrottest.R
import com.example.parrottest.interfaces.ChangeStatusInterface
import com.example.parrottest.model.dashboard.MenuCategoryModel

class CategoryAdapter(
    private val list: List<MenuCategoryModel>,
    private val callback: ChangeStatusInterface
) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)

        return CategoryViewHolder(
            view = view,
            callback = callback
        )
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        holder.bind(list[position])
    }

    fun updateStatusProduct(indexCategory: Int, indexProduct: Int, status: String) {

        val menuCategory = list[indexCategory]
        val product = menuCategory.listProduct[indexProduct]
        product.availability = status

        this.notifyDataSetChanged()
    }
}