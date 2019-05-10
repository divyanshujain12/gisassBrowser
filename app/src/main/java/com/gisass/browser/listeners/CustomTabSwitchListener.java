package com.gisass.browser.listeners;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gisass.browser.R;
import com.gisass.browser.activities.Decorator;
import com.gisass.browser.utils.TabUtils;

import de.mrapp.android.tabswitcher.Animation;
import de.mrapp.android.tabswitcher.Tab;
import de.mrapp.android.tabswitcher.TabSwitcher;
import de.mrapp.android.tabswitcher.TabSwitcherListener;

public class CustomTabSwitchListener implements TabSwitcherListener {
    Context context;
    Decorator decorator;
    public CustomTabSwitchListener(Context context,Decorator decorator){
        this.context = context;
        this.decorator = decorator;

    }
    @Override
    public final void onSwitcherShown(@NonNull final TabSwitcher tabSwitcher) {

    }

    @Override
    public final void onSwitcherHidden(@NonNull final TabSwitcher tabSwitcher) {
        if (TabUtils.getInstance().getSnackbar() != null) {
            TabUtils.getInstance().getSnackbar().dismiss();
        }
    }

    @Override
    public final void onSelectionChanged(@NonNull final TabSwitcher tabSwitcher,
                                         final int selectedTabIndex,
                                         @Nullable final Tab selectedTab) {

    }

    @Override
    public final void onTabAdded(@NonNull final TabSwitcher tabSwitcher, final int index,
                                 @NonNull final Tab tab, @NonNull final Animation animation) {
        TabUtils.getInstance().inflateMenu(tabSwitcher);
        TabSwitcher.setupWithMenu(tabSwitcher, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
    }

    @Override
    public final void onTabRemoved(@NonNull final TabSwitcher tabSwitcher, final int index,
                                   @NonNull final Tab tab, @NonNull final Animation animation) {
        CharSequence text = context.getString(R.string.removed_tab_snackbar, tab.getTitle());
        TabUtils.getInstance().showUndoSnackbar(text, index, decorator, tabSwitcher, context, tab);
        TabUtils.getInstance().inflateMenu(tabSwitcher);
        TabSwitcher.setupWithMenu(tabSwitcher, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
    }

    @Override
    public final void onAllTabsRemoved(@NonNull final TabSwitcher tabSwitcher,
                                       @NonNull final Tab[] tabs,
                                       @NonNull final Animation animation) {
        CharSequence text = context.getString(R.string.cleared_tabs_snackbar);
        TabUtils.getInstance().showUndoSnackbar(text, 0, decorator, tabSwitcher, context, tabs);
        TabUtils.getInstance().inflateMenu(tabSwitcher);
        TabSwitcher.setupWithMenu(tabSwitcher, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
    }
}
