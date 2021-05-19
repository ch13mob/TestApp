package com.ch13mob.testapp.common

import android.text.TextWatcher

abstract class SimpleTextChangedListener : TextWatcher {
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}