package com.myxh.baby.listener;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextInputWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public abstract void afterTextChanged(Editable editable);
}
