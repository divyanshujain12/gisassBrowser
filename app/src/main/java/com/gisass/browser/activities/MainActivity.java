package com.gisass.browser.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.gisass.browser.R;
import com.gisass.browser.globalClass.MyApp;
import com.gisass.browser.listeners.CustomTabSwitchListener;
import com.gisass.browser.utils.TabUtils;
import com.google.android.material.navigation.NavigationView;

import de.mrapp.android.tabswitcher.TabSwitcher;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int TAB_COUNT = 1;
    private TabSwitcher tabSwitcher;
    private Decorator decorator;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((MyApp) getApplication()).setCurrentActivity(this);
        initViews();

        new LoadUI(this).execute();

    }

    private void renderUI() {
        for (int i = 0; i < TAB_COUNT; i++) {
            tabSwitcher.addTab(TabUtils.getInstance().createTab(i, "", 1));
        }

        tabSwitcher.showAddTabButton(TabUtils.getInstance().createAddTabButtonListener());
        tabSwitcher.setToolbarNavigationIcon(R.drawable.ic_plus_24dp, TabUtils.getInstance().createAddTabListener(tabSwitcher));
        TabSwitcher.setupWithMenu(tabSwitcher, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
        TabUtils.getInstance().inflateMenu(tabSwitcher);
    }

    private void initViews() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tabSwitcher = findViewById(R.id.tab_switcher);
        tabSwitcher.clearSavedStatesWhenRemovingTabs(false);
        decorator = new Decorator(this, tabSwitcher,drawer);
        ViewCompat.setOnApplyWindowInsetsListener(tabSwitcher, TabUtils.getInstance().createWindowInsetsListener(tabSwitcher, this));
        tabSwitcher.setDecorator(decorator);
        tabSwitcher.addListener(new CustomTabSwitchListener(this, decorator));
        tabSwitcher.showToolbars(true);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sliding_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        CharSequence condensedTitle = item.getTitleCondensed();

        if (condensedTitle != null && !condensedTitle.equals("null")) {
            String url = condensedTitle.toString();
            tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), url, 1), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
        } else {
            tabSwitcher.getSelectedTab().getParameters().putString(TabUtils.SELECTED_ICON_URL, "");
            decorator.showTypeTwo(false);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    private class LoadUI extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        LoadUI(Context mContext) {
            pd = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            renderUI();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pd.isShowing())
                pd.cancel();
        }
    }
}