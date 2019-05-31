package com.gisass.browser.customViews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fenchtose.tooltip.Tooltip;
import com.fenchtose.tooltip.TooltipAnimation;
import com.gisass.browser.R;
import com.gisass.browser.adapters.AlertLanguageAdapter;

public class CustomDialogs {
    private static final CustomDialogs ourInstance = new CustomDialogs();


    public static CustomDialogs getInstance() {
        return ourInstance;
    }

    private static Tooltip tooltip = null;

    private CustomDialogs() {
    }

    public MutableLiveData<Integer> getAdvertisementTooltip(Context context, View anchorView) {
        final MutableLiveData<Integer> url = new MutableLiveData<>();
        if (tooltip == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.advierisement_popup_layout, null);
            tooltip = getTooltip(context, anchorView, view);


            view.findViewById(R.id.buzzmateTV).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url.postValue(1);
                    onDismissTooltip();
                }
            });

            view.findViewById(R.id.gisassTV).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url.postValue(2);
                    onDismissTooltip();
                }
            });

            view.findViewById(R.id.browserTV).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url.postValue(3);
                    onDismissTooltip();
                }
            });


        } else {
            onDismissTooltip();
        }
        return url;
    }

    private void onDismissTooltip() {
        if (tooltip != null) {
            tooltip.dismiss(true);
            tooltip = null;
        }
    }

    private Tooltip getTooltip(Context context, View anchorView, View view) {
        Resources res = context.getResources();
        int tipSizeRegular = res.getDimensionPixelSize(R.dimen.tip_dimen_regular);
        int tooltipBgColor = res.getColor(R.color.popup_card_bg);
        return new Tooltip.Builder(context).anchor(anchorView).cancelable(false).autoAdjust(true).into((ViewGroup) anchorView.getRootView()).content(view).withTip(new Tooltip.Tip(tipSizeRegular, tipSizeRegular, tooltipBgColor)).animate(new TooltipAnimation(TooltipAnimation.REVEAL, 200, true)).withListener(new Tooltip.Listener() {
            @Override
            public void onDismissed() {
                onDismissTooltip();
            }
        }).withPadding(20).show();
    }


    public void showLanguageDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.alert_language_layout, null);
        setupFullWidthDialog(alertDialog);
        alertDialog.setView(view);
        AlertLanguageAdapter alertLanguageAdapter = new AlertLanguageAdapter(context, alertDialog);
        RecyclerView alertLanguagesRV = view.findViewById(R.id.alertLanguagesRV);
        alertLanguagesRV.setLayoutManager(new GridLayoutManager(context, 2));
        alertLanguagesRV.setAdapter(alertLanguageAdapter);
        alertDialog.show();
    }


    private static void setupFullWidthDialog(AlertDialog alertDialog) {
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

    }
}
