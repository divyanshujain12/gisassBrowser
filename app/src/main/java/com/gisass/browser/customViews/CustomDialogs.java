package com.gisass.browser.customViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.gisass.browser.R;

public class CustomDialogs {
    private static final CustomDialogs ourInstance = new CustomDialogs();


    public static CustomDialogs getInstance() {
        return ourInstance;
    }

    private CustomDialogs() {
    }

    public MutableLiveData<String> getAdvertisementPopup(Context context, final MutableLiveData<String> url) {

        final String[] urls = context.getResources().getStringArray(R.array.advertisement_ads);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.advierisement_popup_layout, null);


        builder.setView(view);
        builder.setTitle("Please Select Option");
        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buzzmateTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.postValue(urls[0]);
                alertDialog.dismiss();

            }
        });

        view.findViewById(R.id.gisassTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.postValue(urls[1]);
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.browserTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.postValue(urls[2]);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();

        return url;
    }

    public MutableLiveData<Integer> getAdPopup(Context context, View anchorView, int menu) {

        final MutableLiveData<Integer> liveData = new MutableLiveData<>();

        PopupMenu popupMenu = new PopupMenu(context, anchorView);
        popupMenu.getMenuInflater().inflate(menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                liveData.postValue(item.getItemId());
                return false;
            }
        });
        popupMenu.show();
        return liveData;
    }
}
