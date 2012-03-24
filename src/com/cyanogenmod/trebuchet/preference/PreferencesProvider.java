/*
 * Copyright (C) 2011 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.trebuchet.preference;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.Integer;

import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.Workspace;
import com.cyanogenmod.trebuchet.AppsCustomizePagedView;

public final class PreferencesProvider {
    public static final String PREFERENCES_KEY = "com.cyanogenmod.trebuchet_preferences";

    public static final String PREFERENCES_CHANGED = "preferences_changed";

    public static class Interface {
        public static class Homescreen {
            public static int getNumberHomescreens(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_homescreen_screens", 5);
            }
            public static int getDefaultHomescreen(Context context, int def) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_homescreen_default_screen", def + 1) - 1;
            }
            public static int getLandscapeAppsWidth(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_width_landscape_apps", 0);
            }
            public static int getLandscapeAppsHeight(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_height_landscape_apps", 0);
            }
            public static int getPortraitAppsWidth(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_width_portrait_apps", 0);
            }
            public static int getPortraitAppsHeight(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_height_portrait_apps", 0);
            }
            public static int getHotseatApps(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_hotseat_apps", 5);
            }
            public static int getHotseatAllAppsPosition(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getInt("ui_hotseat_all_apps", 0);
            }
            public static int getScreenPaddingVertical(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return (int)((float) preferences.getInt("ui_homescreen_screen_padding_vertical", 0) * 3.0f *
                        LauncherApplication.getScreenDensity());
            }
            public static int getScreenPaddingHorizontal(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return (int)((float) preferences.getInt("ui_homescreen_screen_padding_horizontal", 0) * 3.0f *
                        LauncherApplication.getScreenDensity());
            }
            public static boolean getShowSearchBar(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_homescreen_general_search", true);
            }
            public static boolean getShowAllAppsHotseat(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_homescreen_general_show_hotseat_allapps",
                        !LauncherApplication.isScreenLarge());
            }
            public static boolean getShowHotseat(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_homescreen_general_show_hotseat",
                        !LauncherApplication.isScreenLarge());
            }
            public static boolean getResizeAnyWidget(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_homescreen_general_resize_any_widget", false);
            }
            public static boolean getHideIconLabels(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_homescreen_general_hide_icon_labels", false);
            }
            public static class Scrolling {
                public static boolean getScrollWallpaper(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_homescreen_scrolling_scroll_wallpaper", true);
                }
                public static Workspace.TransitionEffect getTransitionEffect(Context context, String def) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return Workspace.TransitionEffect.valueOf(
                            preferences.getString("ui_homescreen_scrolling_transition_effect", def));
                }
                public static boolean getFadeInAdjacentScreens(Context context, boolean def) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_homescreen_scrolling_fade_adjacent_screens", def);
                }
            }
            public static class Indicator {
                public static boolean getShowScrollingIndicator(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_homescreen_indicator_enable", true);
                }
                public static boolean getFadeScrollingIndicator(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_homescreen_indicator_fade", true);
                }
                public static boolean getShowDockDivider(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_homescreen_indicator_background",
                            !LauncherApplication.isScreenLarge());
                }
                public static boolean getShowDockDividerTwo(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_homescreen_tablet_dock_divider_two", false);
                }
            }
        }

        public static class Drawer {
            public static boolean getJoinWidgetsApps(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_drawer_widgets_join_apps", true);
            }
            public static class Scrolling {
                public static AppsCustomizePagedView.TransitionEffect getTransitionEffect(Context context, String def) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return AppsCustomizePagedView.TransitionEffect.valueOf(
                            preferences.getString("ui_drawer_scrolling_transition_effect", def));
                }
                public static boolean getFadeInAdjacentScreens(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_drawer_scrolling_fade_adjacent_screens", false);
                }
            }
            public static class Indicator {
                public static boolean getShowScrollingIndicator(Context context) {
                   final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_drawer_indicator_enable", true);
                }
                public static boolean getFadeScrollingIndicator(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_drawer_indicator_fade", true);
                }
            }
            public static class Background {
                public static boolean getBackgroundShowWallpaper(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_drawer_background_show_wallpaper", false);
                }
            }
            public static class TopBar {
                public static boolean getHideTopbar(Context context) {
                    final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                    return preferences.getBoolean("ui_drawer_hide_topbar", false);
                }
            }
        }

        public static class Dock {

        }

        public static class Tablet {
            public static boolean getShowAllAppsWorkspace(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_tablet_workspace_allapps", true);
            }
            public static boolean getCenterAllAppsWorkspace(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_tablet_workspace_allapps_center", false);
            }
            public static boolean getCombinedBar(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_tablet_workspace_combined_bar", false);
            }
            public static int getAllAppsBarCorner(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return Integer.parseInt(preferences.getString("ui_tablet_all_apps_corner", "0"));
            }
            public static int getSearchBarCorner(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return Integer.parseInt(preferences.getString("ui_tablet_search_corner", "3"));
            }
            public static boolean getShowPageOutlines(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("preferences_interface_tablet_page_outline", true);
            }
            public static boolean getShowPageControls(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("preferences_interface_tablet_page_controls", true);
            }
            public static boolean getShowMarketLeft(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("preferences_interface_icons_market_left", false);
            }
            public static boolean getShowSettingsRight(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("preferences_interface_icons_settings_right", false);
            }
        }

        public static class Icons {
            public static boolean getHotseatButton(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("preferences_interface_icons_hotseat", false);
            }
            public static boolean getShowMarketButton(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("preferences_interface_icons_market", false);
            }
            public static boolean getShowSettingsButton(Context context) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("preferences_interface_icons_settings", false);
            }
        }

        public static class General {
            public static boolean getAutoRotate(Context context, boolean def) {
                final SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_KEY, 0);
                return preferences.getBoolean("ui_general_orientation", def);
            }
        }
    }

    public static class Application {

    }
}
