package com.example.parrottest.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.parrottest.R
import com.example.parrottest.adapters.CategoryAdapter
import com.example.parrottest.enums.StatusProductEnum
import com.example.parrottest.extensions.alert
import com.example.parrottest.interfaces.ChangeStatusInterface
import com.example.parrottest.model.dashboard.MenuCategoryModel
import com.example.parrottest.ui.dialogs.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category.*

@AndroidEntryPoint
class CategoryFragment : Fragment(), ChangeStatusInterface {

    companion object {

        fun newInstance(idStore: String): CategoryFragment {

            val fragment = CategoryFragment()

            fragment.idStore = idStore

            return fragment
        }
    }

    private val viewModel: CategoryViewModel by viewModels()

    private lateinit var idStore: String

    private val loadingDialog: LoadingDialog by lazy {

        LoadingDialog.newInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initializeObservers()
    }

    private fun initializeObservers() {

        if (this::idStore.isInitialized) {

            this.loadingDialog.show(childFragmentManager, LoadingDialog.DIALOG_LOADING_TAG)

            this.viewModel.getProductsById(idStore)
        }

        this.viewModel.listMenu.observe(viewLifecycleOwner, Observer {

            if (it.isNotEmpty()) {

                this.initializeAdapter(it)

                this.fragmentCategoryRecyclerViewCategories.visibility = View.VISIBLE
                this.fragmentCategoryTextViewError.visibility = View.GONE
            } else {

                this.fragmentCategoryRecyclerViewCategories.visibility = View.GONE
                this.fragmentCategoryTextViewError.visibility = View.VISIBLE
            }

            if (loadingDialog.isAdded) {

                this.loadingDialog.dismiss()
            }
        })

        this.viewModel.message.observe(viewLifecycleOwner, Observer {

            if (loadingDialog.isAdded) {

                this.loadingDialog.dismiss()
            }

            requireActivity().alert(
                messageStringId = it,
                positiveStringId = R.string.button_accept
            )
        })
    }

    private fun initializeAdapter(list: List<MenuCategoryModel>) {

        this.fragmentCategoryRecyclerViewCategories.adapter = CategoryAdapter(
            list = list,
            callback = this
        )
    }

    override fun onChangeStatus(productId: String, status: StatusProductEnum, indexCategory: Int, indexProduct: Int) {

        this.loadingDialog.show(childFragmentManager, LoadingDialog.DIALOG_LOADING_TAG)

        this.viewModel.updateStatusProduct(
            uuid = productId,
            status = status
        )

        (this.fragmentCategoryRecyclerViewCategories.adapter as CategoryAdapter)
            .updateStatusProduct(
                indexCategory = indexCategory,
                indexProduct = indexProduct,
                status = status.name
            )
    }
}