package com.gisass.browser.customViews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;

import com.fenchtose.tooltip.Tooltip;
import com.fenchtose.tooltip.TooltipAnimation;
import com.gisass.browser.R;

import me.piruin.quickaction.ActionItem;
import me.piruin.quickaction.QuickAction;

public class CustomDialogs {
    private static final CustomDialogs ourInstance = new CustomDialogs();


    public static CustomDialogs getInstance() {
        return ourInstance;
    }

    private static Tooltip tooltip = null;

    private CustomDialogs() {
    }

    public AlertDialog getLoadingDialog(Context context, final MutableLiveData<String> url) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.advierisement_popup_layout, null);
        final AlertDialog alertDialog = builder.create();
        return alertDialog;
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

    public MutableLiveData<Integer> getQuickAction(Context context, View anchorView) {

        final MutableLiveData<Integer> liveData = new MutableLiveData<>();

        ActionItem buzzmateAdItem = new ActionItem(1, context.getString(R.string.buzzmate_ads));
        ActionItem gisassAdItem = new ActionItem(2, context.getString(R.string.gisass_advertisement));
        ActionItem browserAdItem = new ActionItem(3, context.getString(R.string.browser_ads));


        QuickAction quickAction = new QuickAction(context, QuickAction.VERTICAL);

        quickAction.addActionItem(buzzmateAdItem, gisassAdItem, browserAdItem);
        quickAction.setColor(Color.WHITE);
        quickAction.setTextColor(Color.BLACK);
        quickAction.setEnabledDivider(true);
        quickAction.setDividerColor(Color.BLACK);

        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(ActionItem item) {
                liveData.postValue(item.getActionId());
            }
        });

        quickAction.show(anchorView);

        return liveData;
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

}
