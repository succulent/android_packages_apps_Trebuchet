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
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import com.cyanogenmod.trebuchet.R;
import com.cyanogenmod.trebuchet.LauncherApplication;

public class Preferences extends PreferenceActivity implements OnPreferenceChangeListener {

    private static final String TAG = "Launcher.Preferences";

    private static final String SEARCH_BAR = "ui_homescreen_general_search";
    private static final String COMBINED_BAR = "ui_tablet_workspace_combined_bar";
    private static final String CENTER_ALLAPPS = "ui_tablet_workspace_allapps_center";
    private static final String HIDE_DRAWER_TAB = "ui_drawer_hide_topbar";
    private static final String JOIN_WIDGETS = "ui_drawer_widgets_join_apps";
    private static final String SMALLER_ICONS = "ui_tablet_smaller_icons";
    private static final String DOCK_LABELS = "ui_tablet_show_dock_icon_labels";
    private static final String HOTSEAT_POSITIONS = "ui_hotseat_apps";
    private static final String HOTSEAT_ALLAPPS_POSITION = "ui_hotseat_all_apps";
    private static final String ALLAPPS_POSITION = "ui_tablet_all_apps_corner";
    private static final String SEARCH_POSITION = "ui_tablet_search_corner";
    private static final String HOMESCREENS = "ui_homescreen_screens";
    private static final String DEFAULT_HOMESCREEN = "ui_homescreen_default_screen";
    private static final String PORTRAIT_WIDTH = "ui_width_portrait_apps";
    private static final String PORTRAIT_HEIGHT = "ui_height_portrait_apps";
    private static final String LANDSCAPE_WIDTH = "ui_width_landscape_apps";
    private static final String LANDSCAPE_HEIGHT = "ui_height_landscape_apps";
    private static final String HOMESCREEN_TRANSITION = "ui_homescreen_scrolling_transition_effect";
    private static final String VERTICAL_PADDING = "ui_homescreen_screen_padding_vertical";
    private static final String HORIZONTAL_PADDING = "ui_homescreen_screen_padding_horizontal";
    private static final String DRAWER_TRANSITION = "ui_drawer_scrolling_transition_effect";

    private CheckBoxPreference mSearchBar;
    private CheckBoxPreference mCombinedBar;
    private CheckBoxPreference mCenterAllApps;
    private CheckBoxPreference mHideDrawerTab;
    private CheckBoxPreference mJoinWidgets;
    private CheckBoxPreference mSmallerIcons;
    private CheckBoxPreference mDockLabels;
    private AutoNumberPickerPreference mHotseatAllAppsPosition;
    private ListPreference mAllAppsPosition;
    private ListPreference mSearchPosition;
    private ListPreference mHomescreenTransition;
    private ListPreference mDrawerTransition;
    private NumberPickerPreference mHotseatPositions;
    private NumberPickerPreference mHomescreens;
    private NumberPickerPreference mDefaultHomescreen;
    private NumberPickerPreference mVerticalPadding;
    private NumberPickerPreference mHorizontalPadding;
    private AutoNumberPickerPreference mLandscapeWidth;
    private AutoNumberPickerPreference mLandscapeHeight;
    private AutoNumberPickerPreference mPortraitWidth;
    private AutoNumberPickerPreference mPortraitHeight;

    private Context mContext;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        mContext = getApplicationContext();

        mPrefs = getSharedPreferences(PreferencesProvider.PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
                editor.putBoolean(PreferencesProvider.PREFERENCES_CHANGED, true);
                editor.commit();

        PreferenceScreen prefSet = getPreferenceScreen();

        mSearchBar = (CheckBoxPreference) prefSet.findPreference(SEARCH_BAR);
        mCombinedBar = (CheckBoxPreference) prefSet.findPreference(COMBINED_BAR);
        mCenterAllApps = (CheckBoxPreference) prefSet.findPreference(CENTER_ALLAPPS);
        mHideDrawerTab = (CheckBoxPreference) prefSet.findPreference(HIDE_DRAWER_TAB);
        mJoinWidgets = (CheckBoxPreference) prefSet.findPreference(JOIN_WIDGETS);
        mSmallerIcons = (CheckBoxPreference) prefSet.findPreference(SMALLER_ICONS);
        mDockLabels = (CheckBoxPreference) prefSet.findPreference(DOCK_LABELS);
        mHotseatPositions = (NumberPickerPreference) prefSet.findPreference(HOTSEAT_POSITIONS);
        mHotseatAllAppsPosition = (AutoNumberPickerPreference) prefSet.findPreference(HOTSEAT_ALLAPPS_POSITION);
        mAllAppsPosition = (ListPreference) prefSet.findPreference(ALLAPPS_POSITION);
        mAllAppsPosition.setOnPreferenceChangeListener(this);
        mSearchPosition = (ListPreference) prefSet.findPreference(SEARCH_POSITION);
        mSearchPosition.setOnPreferenceChangeListener(this);
        mHomescreenTransition = (ListPreference) prefSet.findPreference(HOMESCREEN_TRANSITION);
        mHomescreenTransition.setOnPreferenceChangeListener(this);
        mDrawerTransition = (ListPreference) prefSet.findPreference(DRAWER_TRANSITION);
        mDrawerTransition.setOnPreferenceChangeListener(this);
        mHomescreens = (NumberPickerPreference) prefSet.findPreference(HOMESCREENS);
        mDefaultHomescreen = (NumberPickerPreference) prefSet.findPreference(DEFAULT_HOMESCREEN);
        mVerticalPadding = (NumberPickerPreference) prefSet.findPreference(VERTICAL_PADDING);
        mHorizontalPadding = (NumberPickerPreference) prefSet.findPreference(HORIZONTAL_PADDING);
        mLandscapeHeight = (AutoNumberPickerPreference) prefSet.findPreference(LANDSCAPE_HEIGHT);
        mLandscapeWidth = (AutoNumberPickerPreference) prefSet.findPreference(LANDSCAPE_WIDTH);
        mPortraitHeight = (AutoNumberPickerPreference) prefSet.findPreference(PORTRAIT_HEIGHT);
        mPortraitWidth = (AutoNumberPickerPreference) prefSet.findPreference(PORTRAIT_WIDTH);

        if (mSearchBar.isChecked()) {
            mCombinedBar.setEnabled(false);
            mCenterAllApps.setEnabled(false);
        }

        if (mHideDrawerTab.isChecked()) {
            mJoinWidgets.setChecked(true);
        }

        if (!mSmallerIcons.isChecked()) {
            mDockLabels.setChecked(false);
            if (LauncherApplication.getScreenDensity() == 1f) {
                mHotseatPositions.setMaxValue(5);
            }
        } else {
            if (LauncherApplication.getScreenDensity() == 1f) {
                mHotseatPositions.setMaxValue(6);
            }
        }

        String searchValue = mSearchPosition.getValue();
        String allAppsValue = mAllAppsPosition.getValue();

        if (mSearchBar.isChecked() && (searchValue.equals("1") || searchValue.equals("2"))) {
            mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_bottom_entries);
            mAllAppsPosition.setEntryValues(R.array.preferences_interface_search_button_bottom_values);
            if (allAppsValue.equals("0") || allAppsValue.equals("3")) {
                mAllAppsPosition.setValueIndex(0);
            }
        } else if (mSearchBar.isChecked()) {
            mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_top_entries);
            mAllAppsPosition.setEntryValues(R.array.preferences_interface_search_button_top_values);
            if (allAppsValue.equals("1") || allAppsValue.equals("2")) {
                mAllAppsPosition.setValueIndex(0);
            }
        } else {
            mAllAppsPosition.setEntries(R.array.preferences_interface_button_corner_entries);
            mAllAppsPosition.setEntryValues(R.array.preferences_interface_button_corner_values);
        }

        mAllAppsPosition.setSummary(mAllAppsPosition.getEntry());
        mSearchPosition.setSummary(mSearchPosition.getEntry());

        mHomescreens.setSummary(Integer.toString(mPrefs.getInt(HOMESCREENS, 5)));
        mDefaultHomescreen.setSummary(Integer.toString(mPrefs.getInt(DEFAULT_HOMESCREEN, 3)));
        mHotseatPositions.setSummary(Integer.toString(mPrefs.getInt(HOTSEAT_POSITIONS, 5)));
        mVerticalPadding.setSummary(Integer.toString(mPrefs.getInt(VERTICAL_PADDING, 0)));
        mHorizontalPadding.setSummary(Integer.toString(mPrefs.getInt(HORIZONTAL_PADDING, 0)));

        int hp = mPrefs.getInt(HOTSEAT_ALLAPPS_POSITION, 3);
        mHotseatAllAppsPosition.setSummary(hp == 0 ? mContext.getString(R.string.preferences_auto_number_picker) :
                Integer.toString(hp));
        int lh = mPrefs.getInt(LANDSCAPE_HEIGHT, 5);
        mLandscapeHeight.setSummary(lh == 0 ? mContext.getString(R.string.preferences_auto_number_picker) :
                Integer.toString(lh));
        int lw = mPrefs.getInt(LANDSCAPE_WIDTH, 6);
        mLandscapeWidth.setSummary(lw == 0 ? mContext.getString(R.string.preferences_auto_number_picker) :
                Integer.toString(lw));
        int ph = mPrefs.getInt(PORTRAIT_HEIGHT, 5);
        mPortraitHeight.setSummary(ph == 0 ? mContext.getString(R.string.preferences_auto_number_picker) :
                Integer.toString(ph));
        int pw = mPrefs.getInt(PORTRAIT_WIDTH, 6);
        mPortraitWidth.setSummary(pw == 0 ? mContext.getString(R.string.preferences_auto_number_picker) :
                Integer.toString(pw));
        mHomescreenTransition.setSummary(mPrefs.getString(HOMESCREEN_TRANSITION, ""));
        mDrawerTransition.setSummary(mPrefs.getString(DRAWER_TRANSITION, ""));

        if (LauncherApplication.isScreenLarge()) {
            PreferenceScreen homescreen = (PreferenceScreen) findPreference("ui_homescreen");
            PreferenceCategory indicator = (PreferenceCategory) findPreference("ui_indicator_category");
            homescreen.removePreference(indicator);
            PreferenceScreen drawer = (PreferenceScreen) findPreference("ui_drawer");
            PreferenceCategory drawerIndicator = (PreferenceCategory) findPreference("ui_drawer_indicator");
            drawer.removePreference(drawerIndicator);
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSearchPosition) {
            String searchValue = (String) newValue;
            String allAppsValue = mAllAppsPosition.getValue();
            if (mSearchBar.isChecked() && (searchValue.equals("1") || searchValue.equals("2"))) {
                mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_bottom_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_search_button_bottom_values);
                if (allAppsValue.equals("0") || allAppsValue.equals("3")) {
                    mAllAppsPosition.setValueIndex(0);
                }
            } else if (mSearchBar.isChecked()) {
                mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_top_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_search_button_top_values);
                if (allAppsValue.equals("1") || allAppsValue.equals("2")) {
                    mAllAppsPosition.setValueIndex(0);
                }
            } else {
                mAllAppsPosition.setEntries(R.array.preferences_interface_button_corner_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_button_corner_values);
            }
            CharSequence searchIndex[] = mSearchPosition.getEntries();
            CharSequence searchSummary = searchIndex[Integer.parseInt((String) newValue)];
            mSearchPosition.setSummary(searchSummary);
            return true;
        } else if (preference == mAllAppsPosition) {
            CharSequence allAppsIndex[] = mAllAppsPosition.getEntries();
            CharSequence allAppsSummary = allAppsIndex[Integer.parseInt((String) newValue)];
            mAllAppsPosition.setSummary(allAppsSummary);
            return true;
        } else if (preference == mHomescreenTransition) {
            mHomescreenTransition.setSummary((String) newValue);
            return true;
        } else if (preference == mDrawerTransition) {
            mDrawerTransition.setSummary((String) newValue);
            return true;
        }
        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mSearchBar) {
            value = mSearchBar.isChecked();
            mCombinedBar.setEnabled(!value);
            mCenterAllApps.setEnabled(!value);
            if (value && (mSearchPosition.getValue().equals("1") || mSearchPosition.getValue().equals("2"))) {
                mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_bottom_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_search_button_bottom_values);
            } else if (value) {
                mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_top_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_search_button_top_values);
            } else {
                mAllAppsPosition.setEntries(R.array.preferences_interface_button_corner_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_button_corner_values);
            }
            return true;
        } else if (preference == mHideDrawerTab) {
            if (mHideDrawerTab.isChecked()) {
                mJoinWidgets.setChecked(true);
            }
            return true;
        } else if (preference == mSmallerIcons) {
            if (!mSmallerIcons.isChecked()) {
                mDockLabels.setChecked(false);
                if (LauncherApplication.getScreenDensity() == 1f) {
                    mHotseatPositions.setMaxValue(5);
                }
            } else {
                if (LauncherApplication.getScreenDensity() == 1f) {
                    mHotseatPositions.setMaxValue(6);
                }
            }
            return true;
        }
        return false;
    }
}
