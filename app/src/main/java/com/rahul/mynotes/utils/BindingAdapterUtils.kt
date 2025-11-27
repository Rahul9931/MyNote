package com.rahul.mynotes.utils

import android.graphics.Color
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("requiredHint")
fun setRequiredHint(view: TextInputLayout, hint: String?) {
    Log.d("check_hint", "hint -> $hint")
    Log.d("check_hint", "view -> $view")

    if (!hint.isNullOrEmpty()) {
        // Method 1: Using SpannableString (Recommended)
        val spannable = SpannableString("$hint*")
        spannable.setSpan(
            ForegroundColorSpan(Color.RED), // Red color for star
            hint.length,                // Start position of star
            hint.length + 1,                // End position of star
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        view.hint = spannable

        // Method 2: Simple approach (if Spannable doesn't work)
        // view.hint = "$hint *"
    } else {
        view.hint = null
    }
}
