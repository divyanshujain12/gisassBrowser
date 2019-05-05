package com.gisass.browser.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gisass.browser.R;
import com.gisass.browser.customViews.FocusEditText;

public class FloatingSearchBar extends AppCompatActivity {

    FocusEditText mFocusEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_search_bar);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        toolbar.setTitle("some title");
        toolbar.inflateMenu(R.menu.tab);
        final View logoLayout = findViewById(R.id.logo_layout);
        mFocusEditText = (FocusEditText) findViewById(R.id.search_bar);
        mFocusEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                // this is the main interesting point, you can extend this with animations.
                if (v.isFocused()) {
                    logoLayout.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);


                } else {
                    logoLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mFocusEditText.clearFocus();
    }
}

