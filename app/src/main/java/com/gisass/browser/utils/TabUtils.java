package com.gisass.browser.utils;

import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

import com.gisass.browser.R;
import com.gisass.browser.activities.Decorator;
import com.gisass.browser.activities.MainActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import de.mrapp.android.tabswitcher.AddTabButtonListener;
import de.mrapp.android.tabswitcher.Animation;
import de.mrapp.android.tabswitcher.Layout;
import de.mrapp.android.tabswitcher.PeekAnimation;
import de.mrapp.android.tabswitcher.PullDownGesture;
import de.mrapp.android.tabswitcher.RevealAnimation;
import de.mrapp.android.tabswitcher.SwipeGesture;
import de.mrapp.android.tabswitcher.Tab;
import de.mrapp.android.tabswitcher.TabSwitcher;
import de.mrapp.android.util.ThemeUtil;

import static de.mrapp.android.util.DisplayUtil.getDisplayWidth;

public class TabUtils {
    public static final TabUtils ourInstance = new TabUtils();
    public static final String VIEW_TYPE_EXTRA = MainActivity.class.getName() + "::ViewType";
    public static String SELECTED_ICON_URL = "selected_icon_url";

    public Snackbar getSnackbar() {
        return snackbar;
    }

    public Snackbar snackbar;

    public static TabUtils getInstance() {
        return ourInstance;
    }

    public TabUtils() {
    }

    @NonNull
    public Toolbar.OnMenuItemClickListener createToolbarMenuListener(final TabSwitcher tabSwitcher) {
        return new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.remove_tab_menu_item:
                        Tab selectedTab = tabSwitcher.getSelectedTab();

                        if (selectedTab != null) {
                            tabSwitcher.removeTab(selectedTab);
                        }

                        return true;
                    case R.id.add_tab_menu_item:
                        int index = tabSwitcher.getCount();
                        Tab tab = createTab(index, "", 1);

                        if (tabSwitcher.isSwitcherShown()) {
                            tabSwitcher.addTab(tab, 0, createRevealAnimation(tabSwitcher));
                        } else {
                            tabSwitcher.addTab(tab, 0, createPeekAnimation(tabSwitcher));
                        }

                        return true;
                    case R.id.clear_tabs_menu_item:
                        tabSwitcher.clear();
                        return true;

                    default:
                        return false;
                }
            }

        };
    }

    @NonNull
    public Tab createTab(final int index, String url, int type) {

        CharSequence title = "";
        if (!url.equals(""))
            title = url;
        else
            title = "Home";

        Tab tab = new Tab(title);
        Bundle parameters = new Bundle();
        parameters.putInt(VIEW_TYPE_EXTRA, type);
        parameters.putString(SELECTED_ICON_URL, url);
        tab.setParameters(parameters);
        return tab;
    }

    @NonNull
    public View.OnClickListener createTabSwitcherButtonListener(final TabSwitcher tabSwitcher) {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                tabSwitcher.toggleSwitcherVisibility();
            }

        };
    }

    @NonNull
    public Animation createRevealAnimation(TabSwitcher tabSwitcher) {
        float x = 0;
        float y = 0;
        View view = getNavigationMenuItem(tabSwitcher);

        if (view != null) {
            int[] location = new int[2];
            view.getLocationInWindow(location);
            x = location[0] + (view.getWidth() / 2f);
            y = location[1] + (view.getHeight() / 2f);
        }

        return new RevealAnimation.Builder().setX(x).setY(y).create();
    }

    @NonNull
    public Animation createPeekAnimation(TabSwitcher tabSwitcher) {
        return new PeekAnimation.Builder().setX(tabSwitcher.getWidth() / 2f).create();
    }

    @Nullable
    public View getNavigationMenuItem(TabSwitcher tabSwitcher) {
        Toolbar[] toolbars = tabSwitcher.getToolbars();

        if (toolbars != null) {
            Toolbar toolbar = toolbars.length > 1 ? toolbars[1] : toolbars[0];
            int size = toolbar.getChildCount();

            for (int i = 0; i < size; i++) {
                View child = toolbar.getChildAt(i);

                if (child instanceof ImageButton) {
                    return child;
                }
            }
        }

        return null;
    }

    public void inflateMenu(final TabSwitcher tabSwitcher) {
        tabSwitcher
                .inflateToolbarMenu(tabSwitcher.getCount() > 0 ? R.menu.tab_switcher : R.menu.tab,
                        createToolbarMenuListener(tabSwitcher));
    }

    @NonNull
    public View.OnClickListener createAddTabListener(final TabSwitcher tabSwitcher) {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                int index = tabSwitcher.getCount();
                Animation animation = createRevealAnimation(tabSwitcher);
                tabSwitcher.addTab(createTab(index, "", 1), 0, animation);
            }

        };
    }


    @NonNull
    public AddTabButtonListener createAddTabButtonListener() {
        return new AddTabButtonListener() {

            @Override
            public void onAddTab(@NonNull final TabSwitcher tabSwitcher) {
                int index = tabSwitcher.getCount();
                Tab tab = createTab(index, "", 1);
                tabSwitcher.addTab(tab, 0);
            }

        };
    }


    @NonNull
    public View.OnClickListener createUndoSnackbarListener(@NonNull final Snackbar snackbar,
                                                           final int index, final TabSwitcher tabSwitcher,
                                                           @NonNull final Tab... tabs) {
        return new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                snackbar.setAction(null, null);

                if (tabSwitcher.isSwitcherShown()) {
                    tabSwitcher.addAllTabs(tabs, index);
                } else if (tabs.length == 1) {
                    tabSwitcher.addTab(tabs[0], 0, createPeekAnimation(tabSwitcher));
                }

            }

        };
    }

    @NonNull
    public BaseTransientBottomBar.BaseCallback<Snackbar> createUndoSnackbarCallback(
            final Decorator decorator, final TabSwitcher tabSwitcher, final Tab... tabs) {
        return new BaseTransientBottomBar.BaseCallback<Snackbar>() {

            @Override
            public void onDismissed(final Snackbar snackbar, final int event) {
                if (event != DISMISS_EVENT_ACTION) {
                    for (Tab tab : tabs) {
                        tabSwitcher.clearSavedState(tab);
                        decorator.clearState(tab);
                    }
                }
            }
        };
    }


    public void showUndoSnackbar(@NonNull final CharSequence text, final int index,
                                 final Decorator decorator, final TabSwitcher tabSwitcher, Context context, @NonNull final Tab... tabs) {
        snackbar = Snackbar.make(tabSwitcher, text, Snackbar.LENGTH_LONG).setActionTextColor(
                ContextCompat.getColor(context, R.color.snackbar_action_text_color));
        snackbar.setAction(R.string.undo, createUndoSnackbarListener(snackbar, index, tabSwitcher, tabs));
        snackbar.addCallback(createUndoSnackbarCallback(decorator, tabSwitcher, tabs));
        snackbar.show();
    }

    @NonNull
    public OnApplyWindowInsetsListener createWindowInsetsListener(final TabSwitcher tabSwitcher, final Context context) {
        return new OnApplyWindowInsetsListener() {

            @Override
            public WindowInsetsCompat onApplyWindowInsets(final View v,
                                                          final WindowInsetsCompat insets) {
                int left = insets.getSystemWindowInsetLeft();
                int top = insets.getSystemWindowInsetTop();
                int right = insets.getSystemWindowInsetRight();
                int bottom = insets.getSystemWindowInsetBottom();
                tabSwitcher.setPadding(left, top, right, bottom);
                float touchableAreaTop = top;

                if (tabSwitcher.getLayout() == Layout.TABLET) {
                    touchableAreaTop += context.getResources()
                            .getDimensionPixelSize(R.dimen.tablet_tab_container_height);
                }

                RectF touchableArea = new RectF(left, touchableAreaTop,
                        getDisplayWidth(context) - right, touchableAreaTop +
                        ThemeUtil.getDimensionPixelSize(context, R.attr.actionBarSize));
                tabSwitcher.addDragGesture(
                        new SwipeGesture.Builder().setTouchableArea(touchableArea).create());
                tabSwitcher.addDragGesture(
                        new PullDownGesture.Builder().setTouchableArea(touchableArea).create());
                return insets;
            }

        };
    }
}
