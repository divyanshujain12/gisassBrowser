package com.gisass.browser.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.gisass.browser.R;
import com.gisass.browser.databinding.ActivityEducationBinding;
import com.gisass.browser.viewModels.EducationViewModel;

public class EducationActivity extends AppCompatActivity {

    private ActivityEducationBinding activityEducationBinding;
    private EducationViewModel educationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        activityEducationBinding = DataBindingUtil.setContentView(this, R.layout.activity_education);
        educationViewModel = ViewModelProviders.of(this).get(EducationViewModel.class);
        educationViewModel.init();

        activityEducationBinding.setViewModel(educationViewModel);

        educationViewModel.getEducationsFromApi();

    }
}
