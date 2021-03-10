package com.example.parrottest.extensions

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.parrottest.R
import com.example.parrottest.interfaces.ActionDialogInterface

// region generic dialogs

private fun getAlertDialog(
    context: Context,
    message: String = "",
    positiveText: String = ""
): AlertDialog {

    val dialogBuilder = AlertDialog.Builder(context)

    if (message.isNotEmpty()) {

        dialogBuilder.setMessage(message)
    }

    if (positiveText.isNotEmpty()) {

        dialogBuilder.setPositiveButton(positiveText) { dialog, _ ->

            dialog.dismiss()
        }
    }

    return dialogBuilder.create()
}

fun Activity.alert(
    messageStringId: Int,
    positiveStringId: Int
) {

    val message: String = this.getString(messageStringId)
    val positiveText: String = this.getString(positiveStringId)

    this.alert(
        message = message,
        positiveText = positiveText
    )
}

fun Activity.alert(
    message: String,
    positiveText: String
) {

    val alertDialog: AlertDialog =
        getAlertDialog(
            context = this,
            message = message,
            positiveText = positiveText
        )

    alertDialog.show()
}

// endregion


// region alert dialog close session

fun Activity.alertCloseSession(
    callback: ActionDialogInterface
): AlertDialog {

    val message: String = this.getString(R.string.message_close_session)
    val positiveText: String = this.getString(R.string.button_accept)
    val negativeText: String = this.getString(R.string.button_cancel)

    val dialogBuilder = AlertDialog.Builder(this)

    dialogBuilder.setMessage(message)

    dialogBuilder.setPositiveButton(positiveText) { dialog, _ ->

        callback.onAccept()

        dialog.dismiss()
    }

    dialogBuilder.setNegativeButton(negativeText) { dialog, _ ->

        callback.onCancel()

        dialog.dismiss()
    }


    return dialogBuilder.create()
}

// endregion