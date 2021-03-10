package com.example.parrottest.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.parrottest.R

class LoadingDialog: DialogFragment() {

    companion object {

        fun newInstance(): LoadingDialog = LoadingDialog()

        const val DIALOG_LOADING_TAG = "DIALOG_LOADING_TAG"
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog

        if (dialog != null) {

            dialog.setCancelable(false)

            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT

            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_loading, container, false)
    }
}