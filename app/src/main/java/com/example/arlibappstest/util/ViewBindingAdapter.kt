package com.example.arlibappstest.util

import android.view.View
import androidx.databinding.BindingAdapter
@BindingAdapter("android:visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}