package com.ch13mob.testapp.common.extension

import android.text.Editable
import androidx.annotation.StringRes
import com.ch13mob.testapp.common.SimpleTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.showError(@StringRes messageId: Int) {
    error = context.getString(messageId)
    isErrorEnabled = true
}

fun TextInputLayout.configure(
    editText: TextInputEditText,
    counterMaxLength: Int? = null,
    afterTextChanged: (String) -> Unit
) {
    if (counterMaxLength != null) {
        setCounterMaxLength(counterMaxLength)
    }

    editText.addTextChangedListener(object : SimpleTextChangedListener() {
        override fun afterTextChanged(s: Editable) {
            afterTextChanged(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            isErrorEnabled = false
        }
    })
}