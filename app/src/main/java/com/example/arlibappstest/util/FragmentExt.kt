package com.example.arlibappstest.util

import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

object FragmentExt {
    private var toast: Toast? = null
    fun Fragment.showToast(message: String) {
        if (toast != null)
            toast!!.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun Fragment.showToast(@StringRes message: Int) {
        if (toast != null)
            toast!!.cancel()
        toast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onSdk29()
        } else null
    }
}