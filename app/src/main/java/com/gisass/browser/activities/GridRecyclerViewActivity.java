package com.gisass.browser.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.gisass.browser.R;
import com.gisass.browser.databinding.ActivityGridRecyclerViewBinding;
import com.gisass.browser.viewModels.SocialViewModel;

public class GridRecyclerViewActivity extends AppCompatActivity {


    ActivityGridRecyclerViewBinding activityGridRecyclerViewBinding;
    SocialViewModel staticIconViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityGridRecyclerViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_grid_recycler_view);
        staticIconViewModel = ViewModelProviders.of(this).get(SocialViewModel.class);
        staticIconViewModel.init();
        activityGridRecyclerViewBinding.setViewModel(staticIconViewModel);

    }
}
