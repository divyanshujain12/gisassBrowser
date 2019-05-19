package com.gisass.browser.customViews;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.gisass.browser.R;
import com.victor.loading.book.BookLoading;

public class CustomDialog extends Dialog {

    BookLoading bookLoading;

    public CustomDialog(@NonNull Context context) {
        super(context);


        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dialog, null);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        bookLoading = (BookLoading) view.findViewById(R.id.bookloading);


        getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        wlmp.height = 200;
        wlmp.width = 400;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        if (bookLoading != null && !bookLoading.isStart())
            bookLoading.start();
    }

    @Override
    public void hide() {
        super.hide();
        if (bookLoading != null && bookLoading.isStart())
            bookLoading.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bookLoading.stop();
    }
}
