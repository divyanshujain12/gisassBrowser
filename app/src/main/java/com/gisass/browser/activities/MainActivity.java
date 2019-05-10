package com.gisass.browser.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.gisass.browser.R;
import com.gisass.browser.listeners.CustomTabSwitchListener;
import com.gisass.browser.utils.TabUtils;

import de.mrapp.android.tabswitcher.TabSwitcher;


public class MainActivity extends AppCompatActivity {

    private static final int TAB_COUNT = 1;
    private TabSwitcher tabSwitcher;
    private Decorator decorator;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabSwitcher = findViewById(R.id.tab_switcher);
        tabSwitcher.clearSavedStatesWhenRemovingTabs(false);
        decorator = new Decorator(this, tabSwitcher);
        ViewCompat.setOnApplyWindowInsetsListener(tabSwitcher, TabUtils.getInstance().createWindowInsetsListener(tabSwitcher, this));
        tabSwitcher.setDecorator(decorator);
        tabSwitcher.addListener(new CustomTabSwitchListener(this, decorator));
        tabSwitcher.showToolbars(true);

        for (int i = 0; i < TAB_COUNT; i++) {
            tabSwitcher.addTab(TabUtils.getInstance().createTab(i, "", 1));
        }

        tabSwitcher.showAddTabButton(TabUtils.getInstance().createAddTabButtonListener());
        tabSwitcher.setToolbarNavigationIcon(R.drawable.ic_plus_24dp, TabUtils.getInstance().createAddTabListener(tabSwitcher));
        TabSwitcher.setupWithMenu(tabSwitcher, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
        TabUtils.getInstance().inflateMenu(tabSwitcher);
    }

    @Override
    public void onBackPressed() {
        if (tabSwitcher.getSelectedTab() != null) {
            WebView currentWebView = tabSwitcher.getSelectedTab().getWebView();
            if (currentWebView != null && currentWebView.canGoBack()) {
                currentWebView.goBack();
            } else if (tabSwitcher.getCount() > 1) {
                tabSwitcher.removeTab(tabSwitcher.getSelectedTab());
            } else
                super.onBackPressed();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public final void setTheme(final int resid) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String themeKey = getString(R.string.theme_preference_key);
        String themeDefaultValue = getString(R.string.theme_preference_default_value);
        int theme = Integer.valueOf(sharedPreferences.getString(themeKey, themeDefaultValue));

        if (theme != 0) {
            super.setTheme(R.style.AppTheme_Translucent_Dark);
        } else {
            super.setTheme(R.style.AppTheme_Translucent_Light);
        }
    }
}