package com.gisass.browser;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.gisass.browser.databinding.ActivityGridRecyclerViewBinding;
import com.gisass.browser.viewModels.StaticIconViewModel;

public class GridRecyclerViewActivity extends AppCompatActivity {


    ActivityGridRecyclerViewBinding activityGridRecyclerViewBinding;
    StaticIconViewModel staticIconViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler_view);


        activityGridRecyclerViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_grid_recycler_view);
        staticIconViewModel = ViewModelProviders.of(this).get(StaticIconViewModel.class);

        activityGridRecyclerViewBinding.setViewModel(staticIconViewModel);

    }
}
