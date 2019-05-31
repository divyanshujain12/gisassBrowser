package com.gisass.browser.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.gisass.browser.R;
import com.gisass.browser.animations.ResizeWidthAnimation;
import com.gisass.browser.customFontViews.CustomEditTextRegular;
import com.gisass.browser.customViews.CustomDialogs;
import com.gisass.browser.databinding.ActivityEducationBinding;
import com.gisass.browser.databinding.ActivityJobBinding;
import com.gisass.browser.databinding.ActivityViewInTabBinding;
import com.gisass.browser.utils.KeyboardUtils;
import com.gisass.browser.utils.TabUtils;
import com.gisass.browser.utils.Utils;
import com.gisass.browser.viewModels.EducationViewModel;
import com.gisass.browser.viewModels.JobViewModel;
import com.gisass.browser.viewModels.SearchResultViewModel;
import com.gisass.browser.viewModels.ViewInTabViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import de.mrapp.android.tabswitcher.StatefulTabSwitcherDecorator;
import de.mrapp.android.tabswitcher.Tab;
import de.mrapp.android.tabswitcher.TabSwitcher;
import rx.functions.Action1;

public class Decorator extends StatefulTabSwitcherDecorator {
    private MainActivity mainActivity;
    private TabSwitcher tabSwitcher;
    private ProgressBar contentLoadingPB;
    private CustomEditTextRegular toolbarET;
    private LinearLayout toolbarLL;
    private ConstraintLayout typeOne, typeTwo;
    private FrameLayout webViewContainer;
    private Menu menu;
    private int toolbarETInitialWidth = 0;
    private View showDrawerIV;
    private int viewType = 1;

    private static final String VIEW_TYPE_EXTRA = MainActivity.class.getName() + "::ViewType";
    private static final String EDUCATION = "Education";
    private static final String JOBS = "Jobs";
    private static String SELECTED_ICON_URL = "selected_icon_url";
    private DrawerLayout drawerLayout;
    private Action1<String> action1;

    private SearchResultViewModel searchResultViewModel;

    public Decorator(MainActivity mainActivity, TabSwitcher tabSwitcher, DrawerLayout drawerLayout) {
        this.mainActivity = mainActivity;
        this.tabSwitcher = tabSwitcher;
        this.drawerLayout = drawerLayout;
        registerKeyboardVisible();
        createAction1ToReadUrl();
    }

    @NonNull
    @Override
    public View onInflateView(@NonNull final LayoutInflater inflater,
                              @Nullable final ViewGroup parent, final int viewType) {
        View view = null;
        switch (viewType) {
            case 1:
                view = inflater.inflate(R.layout.activity_view_in_tab, parent, false);
                //View Models
                ViewInTabViewModel viewInTabViewModel = ViewModelProviders.of(mainActivity).get(ViewInTabViewModel.class);
                ActivityViewInTabBinding activityViewInTabBinding = DataBindingUtil.bind(view);
                activityViewInTabBinding.setViewModel(viewInTabViewModel);
                viewInTabViewModel.init();
                viewInTabViewModel.setActionUrlToAdapters(action1);
                break;
            case 2:
                view = inflater.inflate(R.layout.activity_education, parent, false);
                ActivityEducationBinding educationBinding = DataBindingUtil.bind(view);
                EducationViewModel educationViewModel = ViewModelProviders.of(mainActivity).get(EducationViewModel.class);
                educationViewModel.init();
                Objects.requireNonNull(educationBinding).setViewModel(educationViewModel);
                educationViewModel.getEducationsFromApi();
                educationViewModel.setUrl(action1);
                break;
            case 3:
                view = inflater.inflate(R.layout.activity_job, parent, false);
                ActivityJobBinding jobBinding = DataBindingUtil.bind(view);
                JobViewModel jobViewModel = ViewModelProviders.of(mainActivity).get(JobViewModel.class);
                jobViewModel.init();
                Objects.requireNonNull(jobBinding).setViewModel(jobViewModel);
                jobViewModel.getJobsFromApi();
                jobViewModel.setUrl(action1);
                break;
            case 4:
                view = inflater.inflate(R.layout.search_result_view, parent, false);


                break;
        }

        assert view != null;
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.inflateMenu(R.menu.tab);
            toolbar.setOnMenuItemClickListener(TabUtils.getInstance().createToolbarMenuListener(tabSwitcher));
            menu = toolbar.getMenu();
            TabSwitcher.setupWithMenu(tabSwitcher, menu, TabUtils.getInstance().createTabSwitcherButtonListener(tabSwitcher));
        }
        return view;
    }

    @Override
    protected void onShowTab(@NonNull Context context, @NonNull TabSwitcher tabSwitcher, @NonNull View view, @NonNull Tab tab, int index, int viewType, @Nullable Object state, @Nullable Bundle savedInstanceState) {
        initViewForAllTab(view);
        this.viewType = viewType;
        switch (viewType) {
            case 1:
                homeTab(view, tab, savedInstanceState);
                break;
            case 4:

                SearchResultViewModel searchResultViewModel = new SearchResultViewModel(mainActivity.getApplication());
                searchResultViewModel.init(view);
                searchResultViewModel.setUrl(action1);
                String searchQuery = tab.getParameters().getString(SELECTED_ICON_URL);
                searchResultViewModel.getSearchResultFromApi(searchQuery);
                searchResultViewModel.setSearch_key(searchQuery);
                toolbarET.setText(searchQuery);

                registerOnImeOptionClick((CustomEditTextRegular) view.findViewById(R.id.customSearchET));
                break;
            default:
                defaultTab();
                break;
        }


    }

    private void initViewForAllTab(@NonNull View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        menu = toolbar.getMenu();
        toolbarET = view.findViewById(R.id.toolbarET);
        toolbarLL = view.findViewById(R.id.toolbarLL);
        showDrawerIV = view.findViewById(R.id.showDrawer);
        view.findViewById(R.id.searchIV).setOnClickListener(new CustomViewClick());
        registerOnImeOptionClick(toolbarET);
    }

    private void homeTab(@NonNull View view, @NonNull Tab tab, @Nullable Bundle savedInstanceState) {
        String url = tab.getParameters().getString(SELECTED_ICON_URL);
        initViewsForTabTypeOne(view, savedInstanceState);

        if (url != null && !url.equals("")) {
            showTypeTwo(true);
            setupTabForWebView(tab, url);
        } else {
            webViewContainer.removeAllViews();

            showTypeTwo(false);
        }
    }

    private void defaultTab() {
        showDrawerIV.setVisibility(View.GONE);
        toolbarLL.setVisibility(View.VISIBLE);
    }


    private void initViewsForTabTypeOne(@NonNull View view, @Nullable Bundle savedInstanceState) {
        typeOne = view.findViewById(R.id.typeOne);
        typeTwo = view.findViewById(R.id.typeTwo);
        contentLoadingPB = view.findViewById(R.id.contentLoadingPB);
        webViewContainer = view.findViewById(R.id.webViewContainer);
        if (savedInstanceState == null) {
            toolbarET.setText(null);
        }
        contentLoadingPB.setVisibility(View.GONE);
        contentLoadingPB.setMax(100);
        view.findViewById(R.id.externalET).setOnClickListener(new CustomViewClick());

        showDrawerIV.setOnClickListener(new CustomViewClick());
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


    private void addTabOnIconClick(String s) {
        if (URLUtil.isValidUrl(s))
            tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), s, 1), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
        else {

            switch (s) {
                case EDUCATION:
                    tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), "", 2), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
                    break;
                case JOBS:
                    tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), "", 3), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
                    break;
                default:
                    tabSwitcher.addTab(TabUtils.getInstance().createTab(tabSwitcher.getCount(), s, 4), 0, TabUtils.getInstance().createRevealAnimation(tabSwitcher));
                    break;
            }


        }
    }


    public void showTypeTwo(boolean show) {
        typeOne.setVisibility(show ? View.GONE : View.VISIBLE);
        typeTwo.setVisibility(show ? View.VISIBLE : View.GONE);
        toolbarLL.setVisibility(show ? View.VISIBLE : View.GONE);
        showDrawerIV.setVisibility(show ? View.GONE : View.VISIBLE);
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
            switch (v.getId()) {
                case R.id.externalET:

                    showTypeTwo(true);
                    toolbarET.requestFocus();
                    break;
                case R.id.showDrawer:
                    drawerLayout.openDrawer(Gravity.LEFT, true);
                    break;
                case R.id.searchIV:
                    onToolbarETClick();
                    break;

                case R.id.changeLanguageTV:
                    CustomDialogs.getInstance().showLanguageDialog(mainActivity);
                    break;
            }

        }
    }

    private void registerKeyboardVisible() {
        KeyboardUtils.addKeyboardToggleListener(mainActivity, new KeyboardUtils.SoftKeyboardToggleListener() {
            @Override
            public void onToggleSoftKeyboard(boolean isVisible) {
                if (toolbarET != null) {
                    if (!isVisible) {
                        onKeyboardHide();
                    } else {
                        onKeyboardShow();
//
                    }
                }
            }
        });
    }

    private void onKeyboardHide() {
        animateToolbarET(toolbarETInitialWidth);
        Utils.getInstance().showToolbarItems(menu, true, mainActivity);
        String toolbarETUrl = Objects.requireNonNull(toolbarET.getText()).toString();
        if (viewType == 1) {
            if (toolbarETUrl.equals("")) {
                showTypeTwo(false);
            } else {
                showTypeTwo(true);
            }
        }
    }

    private void onKeyboardShow() {
        Utils.getInstance().showToolbarItems(menu, false, mainActivity);
        toolbarETInitialWidth = toolbarET.getWidth();
        animateToolbarET(((View) toolbarET.getParent()).getWidth() - 100);
    }

    private void animateToolbarET(int desiredWith) {
        if (desiredWith > 0) {
            ResizeWidthAnimation resizeWidthAnimation = new ResizeWidthAnimation(toolbarET, desiredWith);
            resizeWidthAnimation.setDuration(200);
            toolbarET.startAnimation(resizeWidthAnimation);
        }
    }

    private void registerOnImeOptionClick(CustomEditTextRegular customEditTextRegular) {
        customEditTextRegular.setOnEditorActionListener(new CustomImeOptions());
    }


    private class CustomImeOptions implements CustomEditTextRegular.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                switch (v.getId()) {
                    case R.id.toolbarET:
                        onToolbarETClick();
                        break;
                    case R.id.customSearchET:
                        String customSearchText = v.getText().toString();
                        searchResultViewModel.getSearchResultFromApi(customSearchText);
                        break;
                }
            }
            return false;
        }
    }


    private boolean onToolbarETClick() {
        String toolbarText = Objects.requireNonNull(toolbarET.getText()).toString();
        if (toolbarText.contains("http://") || toolbarText.contains("https://")) {
            String formattedUrl = Utils.getInstance().getFormattedUrlString(toolbarET.getText().toString().replaceAll(" ", ""));
            if (URLUtil.isValidUrl(formattedUrl)) {
                if (viewType == 1) {
                    tabSwitcher.getSelectedTab().getParameters().putString(SELECTED_ICON_URL, formattedUrl);
                    toolbarET.setText(formattedUrl);
                    setupTabForWebView(tabSwitcher.getSelectedTab(), formattedUrl);
                } else {
                    resetToolbar();
                    addTabOnIconClick(formattedUrl);
                }

                KeyboardUtils.forceCloseKeyboard(toolbarET);
            } else {
                resetToolbar();
                addTabOnIconClick(formattedUrl);
            }
        } else {
            if (viewType != 4) {
                resetToolbar();
                addTabOnIconClick(toolbarText);
            } else {
                searchResultViewModel.getSearchResultFromApi(toolbarText);
            }
        }
        return true;
    }

    private void resetToolbar() {
        toolbarET.setText(null);
        onKeyboardHide();
    }

    private void createAction1ToReadUrl() {
        action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                addTabOnIconClick(s);
            }
        };
    }

}