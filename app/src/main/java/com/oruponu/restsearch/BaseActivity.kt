package com.oruponu.restsearch

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        fun showSnackbar(
            view: View,
            textLabel: String,
            actionStringId: Int = 0,
            listener: View.OnClickListener? = null
        ) {
            val snackbar = Snackbar.make(view, textLabel, LENGTH_INDEFINITE)
            if (actionStringId != 0 && listener != null) {
                snackbar.setAction(view.context.getString(actionStringId), listener)
            } else {
                snackbar.duration = 5000
            }
            snackbar.show()
        }

        fun showSnackbar(
            view: View,
            snackStringId: Int,
            actionStringId: Int = 0,
            listener: View.OnClickListener? = null
        ) = showSnackbar(view, view.context.getString(snackStringId), actionStringId, listener)
    }
}
