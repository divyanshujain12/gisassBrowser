package com.gisass.browser.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gisass.browser.R;
import com.gisass.browser.animations.ResizeWidthAnimation;
import com.gisass.browser.customFontViews.CustomEditTextRegular;
import com.gisass.browser.databinding.ActivityViewInTabBinding;
import com.gisass.browser.utils.KeyboardUtils;
import com.gisass.browser.utils.TabUtils;
import com.gisass.browser.utils.Utils;
import com.gisass.browser.viewModels.ViewInTabViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import de.mrapp.android.tabswitcher.StatefulTabSwitcherDecorator;
import de.mrapp.android.tabswitcher.Tab;
import de.mrapp.android.tabswitcher.TabSwitcher;

public class Decorator extends StatefulTabSwitcherDecorator {
    private MainActivity mainActivity;
    private TabSwitcher tabSwitcher;
    private ViewInTabViewModel viewInTabViewModel;
    private ProgressBar contentLoadingPB;
    private CustomEditTextRegular toolbarET;
    private ConstraintLayout typeOne, typeTwo;
    private FrameLayout webViewContainer;
    private Menu menu;
    private int toolbarETInitialWidth = 0;

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
                registerBottomSheetIconClick();
                break;
            case 2:

                view = inflater.inflate(R.layout.web_view_layout, parent, false);
                //WebViewLayoutBinding    webViewLayoutBinding = DataBindingUtil.bind(view);
                break;
        }

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.tab);
        toolbar.setOnMenuItemClickListener(TabUtils.getInstance().createToolbarMenuListener(tabSwitcher));
        menu = toolbar.getMenu();
        TabSwitcher.setupWithMenu(tabSwitcher, menu, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
        return view;
    }

    @Override
    protected void onShowTab(@NonNull Context context, @NonNull TabSwitcher tabSwitcher, @NonNull View view, @NonNull Tab tab, int index, int viewType, @Nullable Object state, @Nullable Bundle savedInstanceState) {
        String url = tab.getParameters().getString(SELECTED_ICON_URL);
        initViews(view, savedInstanceState);
        registerKeyboardVisible();
        registerOnImeOptionClick();
        view.findViewById(R.id.externalET).setOnClickListener(new CustomViewClick());
        if (url != null && !url.equals("")) {
            showTypeTwo(true);
            setupTabForWebView(tab, url);
        } else {
            showTypeTwo(false);
        }
    }

    private void initViews(@NonNull View view, @Nullable Bundle savedInstanceState) {
        typeOne = view.findViewById(R.id.typeOne);
        typeTwo = view.findViewById(R.id.typeTwo);
        toolbarET = view.findViewById(R.id.toolbarET);
        contentLoadingPB = view.findViewById(R.id.contentLoadingPB);
        webViewContainer = view.findViewById(R.id.webViewContainer);
        if (savedInstanceState == null) {
            toolbarET.setText(null);
        }
        contentLoadingPB.setVisibility(View.GONE);
        contentLoadingPB.setMax(100);
    }

    private void setupTabForWebView(@NonNull Tab tab, String url) {
        WebView webView = tab.getWebView();
        if (webView == null) {
            webView = settingWebView(url);
            webView.setTransitionGroup(true);
            tab.setWebView(webView);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if (webView.getParent() != null) {
            ((ViewGroup) webView.getParent()).removeView(webView); // <- fix
        }
        webViewContainer.removeAllViews();
        webViewContainer.addView(webView, 0, layoutParams);
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
    private WebView settingWebView(@NonNull String url) {
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

        toolbarET.setText(url);
        loadUrlToWebView(url, webView);


        return webView;
    }

    private void loadUrlToWebView(String url, WebView webView) {
        if (url != null && !url.equals("")) {
            webView.loadUrl(url);
        }
    }

    private void onIconSelect() {
        viewInTabViewModel.getUrl().observe(mainActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), s, 1), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
            }
        });
    }

    private void registerBottomSheetIconClick() {
        viewInTabViewModel.getBottomSheetItemClick().observe(mainActivity, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), s, 1), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
            }
        });
    }

    public void showTypeTwo(boolean show) {
        typeOne.setVisibility(show ? View.GONE : View.VISIBLE);
        typeTwo.setVisibility(show ? View.VISIBLE : View.GONE);
        toolbarET.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    class WebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            toolbarET.setText(url);
            contentLoadingPB.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            contentLoadingPB.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            contentLoadingPB.setVisibility(View.GONE);
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
        }

    }

    private class CustomViewClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            showTypeTwo(true);
            toolbarET.requestFocus();
        }
    }

    private void registerKeyboardVisible() {
        KeyboardUtils.addKeyboardToggleListener(mainActivity, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if (!isVisible) {
                    onKeyboardHide();
                } else {

                    onKeyboardShow();
//
                }
            }
        });
    }

    private void onKeyboardHide() {
        animateToolbarET(toolbarETInitialWidth);
        Utils.getInstance().showToolbarItems(menu, true, mainActivity);
        String toolbarETUrl = Objects.requireNonNull(toolbarET.getText()).toString();
        if (toolbarETUrl.equals("")) {
            showTypeTwo(false);
        } else {
            showTypeTwo(true);
        }
    }

    private void onKeyboardShow() {
        Utils.getInstance().showToolbarItems(menu, false, mainActivity);
        toolbarETInitialWidth = toolbarET.getWidth();
        animateToolbarET(((View) toolbarET.getParent()).getWidth() - 80);
    }

    private void animateToolbarET(int desiredWith) {
        if (desiredWith > 0) {
            ResizeWidthAnimation resizeWidthAnimation = new ResizeWidthAnimation(toolbarET, desiredWith);
            resizeWidthAnimation.setDuration(200);
            toolbarET.startAnimation(resizeWidthAnimation);
        }
    }

    private void registerOnImeOptionClick() {
        toolbarET.setOnEditorActionListener(new CustomEditTextRegular.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String formattedUrl = Utils.getInstance().getFormattedUrlString(toolbarET.getText().toString().replaceAll(" ", ""));
                    if (URLUtil.isValidUrl(formattedUrl)) {
                        tabSwitcher.getSelectedTab().getParameters().putString(SELECTED_ICON_URL, formattedUrl);
                        toolbarET.setText(formattedUrl);
                        setupTabForWebView(tabSwitcher.getSelectedTab(), formattedUrl);
                        KeyboardUtils.forceCloseKeyboard(toolbarET);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}