package com.example.projectfinal.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.example.projectfinal.R

class DialogUtil {
    fun showDialogWaiting(context: Context): Dialog {
        val progressDialog = Dialog(context)
        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.waiting_layout)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }
}