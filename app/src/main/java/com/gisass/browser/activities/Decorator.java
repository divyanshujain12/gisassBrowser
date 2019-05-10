package com.gisass.browser.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gisass.browser.R;
import com.gisass.browser.customFontViews.CustomEditTextRegular;
import com.gisass.browser.databinding.ActivityViewInTabBinding;
import com.gisass.browser.utils.TabUtils;
import com.gisass.browser.viewModels.ViewInTabViewModel;

import org.jetbrains.annotations.NotNull;

import de.mrapp.android.tabswitcher.StatefulTabSwitcherDecorator;
import de.mrapp.android.tabswitcher.Tab;
import de.mrapp.android.tabswitcher.TabSwitcher;

public class Decorator extends StatefulTabSwitcherDecorator {
    private MainActivity mainActivity;
    private TabSwitcher tabSwitcher;
    ViewInTabViewModel viewInTabViewModel;
    ProgressBar contentLoadingPB;
    CustomEditTextRegular toolbarET;
    private static final String VIEW_TYPE_EXTRA = MainActivity.class.getName() + "::ViewType";
    private static String SELECTED_ICON_URL = "selected_icon_url";

    public Decorator(MainActivity mainActivity, TabSwitcher tabSwitcher) {
        this.mainActivity = mainActivity;
        this.tabSwitcher = tabSwitcher;
    }

    @NonNull
    @Override
    public View onInflateView(@NonNull final LayoutInflater inflater,
                              @Nullable final ViewGroup parent, final int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.activity_view_in_tab, parent, false);
                viewInTabViewModel = ViewModelProviders.of(mainActivity).get(ViewInTabViewModel.class);
                ActivityViewInTabBinding activityViewInTabBinding = DataBindingUtil.bind(view);
                activityViewInTabBinding.setViewModel(viewInTabViewModel);
                viewInTabViewModel.init();
                onIconSelect();
                break;
            case 2:

                view = inflater.inflate(R.layout.web_view_layout, parent, false);
                //WebViewLayoutBinding    webViewLayoutBinding = DataBindingUtil.bind(view);
                break;
        }

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.tab);
        toolbar.setOnMenuItemClickListener(TabUtils.getInstance().createToolbarMenuListener(tabSwitcher));
        Menu menu = toolbar.getMenu();
        TabSwitcher.setupWithMenu(tabSwitcher, menu, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
        return view;
    }

    @Override
    protected void onShowTab(@NonNull Context context, @NonNull TabSwitcher tabSwitcher, @NonNull View view, @NonNull Tab tab, int index, int viewType, @Nullable Object state, @Nullable Bundle savedInstanceState) {
        switch (viewType) {

            case 2:
                toolbarET = view.findViewById(R.id.toolbarET);
                if (savedInstanceState == null)
                    toolbarET.setText(null);

                contentLoadingPB = view.findViewById(R.id.contentLoadingPB);
                FrameLayout webViewContainer = view.findViewById(R.id.webViewContainer);
                contentLoadingPB.setVisibility(View.GONE);
                contentLoadingPB.setMax(100);
                WebView webView = tab.getWebView();
                if (webView == null) {
                    webView = settingWebView(tab);
                }

                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                if (webView.getParent() != null) {
                    ((ViewGroup) webView.getParent()).removeView(webView); // <- fix
                }
                webViewContainer.removeAllViews();
                webViewContainer.addView(webView, 0, layoutParams);

                break;
        }


    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getViewType(@NonNull final Tab tab, final int index) {
        Bundle parameters = tab.getParameters();
        return parameters != null ? parameters.getInt(VIEW_TYPE_EXTRA) : 0;
    }

    @Nullable
    @Override
    protected Object onCreateState(@NonNull Context context, @NonNull TabSwitcher tabSwitcher, @NonNull View view, @NonNull Tab tab, int index, int viewType, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @NotNull
    private WebView settingWebView(@NonNull Tab tab) {
        WebView webView;
        webView = new WebView(mainActivity);
        webView.setWebViewClient(new WebClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setUseWideViewPort(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        webView.setWebChromeClient(new CustomWebChromeClient());
        String url = tab.getParameters().getString(SELECTED_ICON_URL);
        toolbarET.setText(url);
        loadUrlToWebview(url, webView);

        tab.setWebView(webView);
        return webView;
    }

    private void loadUrlToWebview(String url, WebView webView) {
        if (url != null && !url.equals("")) {
            webView.loadUrl(url);
        }
    }

    private void onIconSelect() {
        viewInTabViewModel.getUrl().observe(mainActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), s, 2), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
            }
        });
    }

    class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            toolbarET.setText(url);
            contentLoadingPB.setVisibility(View.VISIBLE);

        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int progress) {
            contentLoadingPB.setProgress(progress);
            if (progress == 100) {
                contentLoadingPB.setVisibility(View.GONE);
            }
        }

    }
}