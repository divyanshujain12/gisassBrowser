package com.gisass.browser.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.gisass.browser.R;
import com.gisass.browser.databinding.ActivityViewInTabBinding;
import com.gisass.browser.viewModels.ViewInTabViewModel;

public class ViewInTabActivity extends AppCompatActivity {

    ActivityViewInTabBinding activityViewInTabBinding;
    ViewInTabViewModel viewInTabViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_in_tab);

        activityViewInTabBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_in_tab);
        viewInTabViewModel = ViewModelProviders.of(this).get(ViewInTabViewModel.class);
        viewInTabViewModel.init();
        activityViewInTabBinding.setViewModel(viewInTabViewModel);

    }
}
