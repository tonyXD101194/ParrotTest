package com.example.parrottest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.parrottest.R
import com.example.parrottest.database.model.ProductRoomModel
import com.example.parrottest.enums.StatusProductEnum
import com.example.parrottest.interfaces.ChangeStatusInterface
import com.example.parrottest.model.dashboard.MenuCategoryModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class CategoryViewHolder(
    val view: View,
    private val callback: ChangeStatusInterface
) : RecyclerView.ViewHolder(view) {

    fun bind(model: MenuCategoryModel) {

        view.itemCategoryTextViewNameCategory.text = String.format(
            view.context.resources.getString(R.string.fragment_category_item_category)
            , model.category.name
            , model.counter
        )

        view.itemCategoryImageViewExpandableAction.setOnClickListener {

            if (view.itemCategoryLinearLayoutProducts.visibility == View.GONE) {

                view.itemCategoryImageViewExpandableAction.setImageResource(R.drawable.ic_baseline_remove_circle_24)
                view.itemCategoryLinearLayoutProducts.visibility = View.VISIBLE

                model.isExpandable = true
            } else {

                view.itemCategoryImageViewExpandableAction.setImageResource(R.drawable.ic_baseline_add_circle_36)
                view.itemCategoryLinearLayoutProducts.visibility = View.GONE

                model.isExpandable = false
            }
        }

        if (model.isExpandable) {

            view.itemCategoryImageViewExpandableAction.setImageResource(R.drawable.ic_baseline_remove_circle_24)
            view.itemCategoryLinearLayoutProducts.visibility = View.VISIBLE
        } else {

            view.itemCategoryImageViewExpandableAction.setImageResource(R.drawable.ic_baseline_add_circle_36)
            view.itemCategoryLinearLayoutProducts.visibility = View.GONE
        }


        // inflate products

        model.listProduct.forEachIndexed { index,  product ->

            val viewProduct = createProductView(index, product)

            view.itemCategoryLinearLayoutProducts.addView(viewProduct)
        }
    }

    private fun createProductView(index: Int, product: ProductRoomModel): View {

        val inflater = view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val viewProduct: View = inflater.inflate(R.layout.item_product, null)

        Picasso.with(view.context).load(product.imageUrl)
            .into(viewProduct.itemProductImageViewUrl)

        viewProduct.itemProductTextViewNameProduct.text = product.name

        viewProduct.itemProductTextViewNamePrice.text = String.format(
            view.context.resources.getString(R.string.item_product_price), product.price
        )

        if (product.availability == StatusProductEnum.AVAILABLE.name) {

            viewProduct.itemProductSwitchStatus.isChecked = true
            viewProduct.itemProductImageViewUrl.setColorFilter(ContextCompat.getColor(view.context, R.color.transparent))
        } else {

            viewProduct.itemProductSwitchStatus.isChecked = false
            viewProduct.itemProductImageViewUrl.setColorFilter(ContextCompat.getColor(view.context, R.color.opacitiy))
        }

        //viewProduct.itemProductSwitchStatus.isChecked = product.availability == StatusProductEnum.AVAILABLE.name

        viewProduct.itemProductSwitchStatus.setOnCheckedChangeListener { _, isChecked ->

            callback.onChangeStatus(
                productId = product.uuid,
                status = if (isChecked) StatusProductEnum.AVAILABLE else StatusProductEnum.UNAVAILABLE,
                indexCategory = adapterPosition,
                indexProduct = index
            )
        }

        return viewProduct
    }
}