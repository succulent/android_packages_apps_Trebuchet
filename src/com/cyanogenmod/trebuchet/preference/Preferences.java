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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.PreferenceGroup;

import com.cyanogenmod.trebuchet.LauncherApplication;

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
    private static final String HOMESCREEN_TRANSITION = "ui_homescreen_scrolling_transition_effect";
    private static final String VERTICAL_PADDING = "ui_homescreen_screen_padding_vertical";
    private static final String HORIZONTAL_PADDING = "ui_homescreen_screen_padding_horizontal";
    private static final String DRAWER_TRANSITION = "ui_drawer_scrolling_transition_effect";
    private static final String HOMESCREEN_GRID = "ui_homescreen_grid";
    private static final String HOMESCREEN_DOUBLETAP = "ui_homescreen_doubletap";
    private static final String HOMESCREEN_SWIPEUP = "ui_homescreen_swipe_up";
    private static final String HOMESCREEN_SWIPEDOWN = "ui_homescreen_swipe_down";
    private static final String DRAWER_SWIPEUP = "ui_drawer_swipe_up";
    private static final String DRAWER_SWIPEDOWN = "ui_drawer_swipe_down";
    private static final String HDT_APPLICATION = "hdt_application";
    private static final String CUSTOM_BUTTON_ONE = "ui_tablet_custom_button_one";
    private static final String CUSTOM_BUTTON_TWO = "ui_tablet_custom_button_two";
    private static final String CUSTOM_BUTTON_THREE = "ui_tablet_custom_button_three";
    private static final String CUSTOM_BUTTON_FOUR = "ui_tablet_custom_button_four";
    private static final String CUSTOM_BUTTON_FIVE = "ui_tablet_custom_button_five";
    private static final String CUSTOM_BUTTON_SIX = "ui_tablet_custom_button_six";
    private static final String CUSTOM_BUTTON_SEVEN = "ui_tablet_custom_button_seven";
    private static final String CUSTOM_BUTTON_EIGHT = "ui_tablet_custom_button_eight";

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
    private ListPreference mHomescreenDoubleTap;
    private ListPreference mHomescreenSwipeUp;
    private ListPreference mHomescreenSwipeDown;
    private ListPreference mDrawerSwipeDown;
    private ListPreference mDrawerSwipeUp;
    private ListPreference mCustomButtonOne;
    private ListPreference mCustomButtonTwo;
    private ListPreference mCustomButtonThree;
    private ListPreference mCustomButtonFour;
    private ListPreference mCustomButtonFive;
    private ListPreference mCustomButtonSix;
    private ListPreference mCustomButtonSeven;
    private ListPreference mCustomButtonEight;
    private NumberPickerPreference mHotseatPositions;
    private NumberPickerPreference mHomescreens;
    private NumberPickerPreference mDefaultHomescreen;
    private NumberPickerPreference mVerticalPadding;
    private NumberPickerPreference mHorizontalPadding;
    private AutoDoubleNumberPickerPreference mHomescreenGrid;

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
        mHomescreenDoubleTap = (ListPreference) prefSet.findPreference(HOMESCREEN_DOUBLETAP);
        mHomescreenDoubleTap.setOnPreferenceChangeListener(this);
        mHomescreenSwipeDown = (ListPreference) prefSet.findPreference(HOMESCREEN_SWIPEDOWN);
        mHomescreenSwipeDown.setOnPreferenceChangeListener(this);
        mHomescreenSwipeUp = (ListPreference) prefSet.findPreference(HOMESCREEN_SWIPEUP);
        mHomescreenSwipeUp.setOnPreferenceChangeListener(this);
        mDrawerSwipeDown = (ListPreference) prefSet.findPreference(DRAWER_SWIPEDOWN);
        mDrawerSwipeDown.setOnPreferenceChangeListener(this);
        mDrawerSwipeUp = (ListPreference) prefSet.findPreference(DRAWER_SWIPEUP);
        mDrawerSwipeUp.setOnPreferenceChangeListener(this);
        mCustomButtonOne = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_ONE);
        mCustomButtonOne.setOnPreferenceChangeListener(this);
        mCustomButtonTwo = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_TWO);
        mCustomButtonTwo.setOnPreferenceChangeListener(this);
        mCustomButtonThree = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_THREE);
        mCustomButtonThree.setOnPreferenceChangeListener(this);
        mCustomButtonFour = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_FOUR);
        mCustomButtonFour.setOnPreferenceChangeListener(this);
        mCustomButtonFive = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_FIVE);
        mCustomButtonFive.setOnPreferenceChangeListener(this);
        mCustomButtonSix = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_SIX);
        mCustomButtonSix.setOnPreferenceChangeListener(this);
        mCustomButtonSeven = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_SEVEN);
        mCustomButtonSeven.setOnPreferenceChangeListener(this);
        mCustomButtonEight = (ListPreference) prefSet.findPreference(CUSTOM_BUTTON_EIGHT);
        mCustomButtonEight.setOnPreferenceChangeListener(this);
        mHomescreens = (NumberPickerPreference) prefSet.findPreference(HOMESCREENS);
        mDefaultHomescreen = (NumberPickerPreference) prefSet.findPreference(DEFAULT_HOMESCREEN);
        mVerticalPadding = (NumberPickerPreference) prefSet.findPreference(VERTICAL_PADDING);
        mHorizontalPadding = (NumberPickerPreference) prefSet.findPreference(HORIZONTAL_PADDING);
        mHomescreenGrid = (AutoDoubleNumberPickerPreference) prefSet.findPreference(HOMESCREEN_GRID);

        if (mSearchBar.isChecked()) {
            mCombinedBar.setEnabled(false);
            mCenterAllApps.setEnabled(false);
            mCustomButtonSeven.setEnabled(false);
            mCustomButtonSeven.setValueIndex(0);
            mCustomButtonEight.setEnabled(false);
            mCustomButtonEight.setValueIndex(0);
        }

        if (mCombinedBar.isChecked()) {
            mCustomButtonSeven.setEnabled(false);
            mCustomButtonSeven.setValueIndex(0);
            mCustomButtonEight.setEnabled(false);
            mCustomButtonEight.setValueIndex(0);
        }

        if (mHideDrawerTab.isChecked()) {
            mJoinWidgets.setChecked(true);
        }

        if (!mSmallerIcons.isChecked()) {
            mDockLabels.setChecked(false);
            if (LauncherApplication.getScreenDensity() == 1f) {
                mHotseatPositions.setMaxValue(5);
                mHotseatAllAppsPosition.setMaxValue(5);
                mHomescreenGrid.setMax1(5);
                mHomescreenGrid.setMax2(6);
            } else if (LauncherApplication.getScreenDensity() == 0.75f) {
                mHotseatPositions.setMaxValue(7);
                mHotseatAllAppsPosition.setMaxValue(7);
                mHomescreenGrid.setMax1(7);
                mHomescreenGrid.setMax2(8);
            }
        } else {
            if (LauncherApplication.getScreenDensity() == 1f) {
                mHotseatPositions.setMaxValue(6);
                mHotseatAllAppsPosition.setMaxValue(6);
                mHomescreenGrid.setMax1(6);
                mHomescreenGrid.setMax2(8);
            } else if (LauncherApplication.getScreenDensity() == 0.75f) {
                mHotseatPositions.setMaxValue(9);
                mHotseatAllAppsPosition.setMaxValue(9);
                mHomescreenGrid.setMax1(9);
                mHomescreenGrid.setMax2(11);
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
        mHomescreenDoubleTap.setSummary(mHomescreenDoubleTap.getEntry());
        if (mHomescreenDoubleTap.getValue().equals("6")) {
            try {
                mHomescreenDoubleTap.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hdt_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mHomescreenSwipeDown.setSummary(mHomescreenSwipeDown.getEntry());
        if (mHomescreenSwipeDown.getValue().equals("6")) {
            try {
                mHomescreenSwipeDown.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hsd_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mHomescreenSwipeUp.setSummary(mHomescreenSwipeUp.getEntry());
        if (mHomescreenSwipeUp.getValue().equals("6")) {
            try {
                mHomescreenSwipeUp.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hsu_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mDrawerSwipeDown.setSummary(mDrawerSwipeDown.getEntry());
        if (mDrawerSwipeDown.getValue().equals("6")) {
            try {
                mDrawerSwipeDown.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("dsd_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mDrawerSwipeUp.setSummary(mDrawerSwipeUp.getEntry());
        if (mDrawerSwipeUp.getValue().equals("6")) {
            try {
                mDrawerSwipeUp.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("dsu_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mHomescreenTransition.setSummary(mHomescreenTransition.getEntry());
        mDrawerTransition.setSummary(mDrawerTransition.getEntry());
        mCustomButtonOne.setSummary(mCustomButtonOne.getEntry());
        if (mCustomButtonOne.getValue().equals("3")) {
            try {
                mCustomButtonOne.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_one", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonTwo.setSummary(mCustomButtonTwo.getEntry());
        if (mCustomButtonTwo.getValue().equals("3")) {
            try {
                mCustomButtonTwo.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_two", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonThree.setSummary(mCustomButtonThree.getEntry());
        if (mCustomButtonThree.getValue().equals("3")) {
            try {
                mCustomButtonThree.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_three", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonFour.setSummary(mCustomButtonFour.getEntry());
        if (mCustomButtonFour.getValue().equals("3")) {
            try {
                mCustomButtonFour.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_four", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonFive.setSummary(mCustomButtonFive.getEntry());
        if (mCustomButtonFive.getValue().equals("3")) {
            try {
                mCustomButtonFive.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_five", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonSix.setSummary(mCustomButtonSix.getEntry());
        if (mCustomButtonSix.getValue().equals("3")) {
            try {
                mCustomButtonSix.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_six", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonSeven.setSummary(mCustomButtonSeven.getEntry());
        if (mCustomButtonSeven.getValue().equals("3")) {
            try {
                mCustomButtonSeven.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_seven", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonEight.setSummary(mCustomButtonEight.getEntry());
        if (mCustomButtonEight.getValue().equals("3")) {
            try {
                mCustomButtonEight.setIcon(this.getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_eight", ""), 0)));
            } catch (Exception e) {
            }
        }

        mHomescreens.setSummary(Integer.toString(mPrefs.getInt(HOMESCREENS, 5)));
        mDefaultHomescreen.setSummary(Integer.toString(mPrefs.getInt(DEFAULT_HOMESCREEN, 3)));
        mHotseatPositions.setSummary(Integer.toString(mPrefs.getInt(HOTSEAT_POSITIONS, 5)));
        mVerticalPadding.setSummary(Integer.toString(mPrefs.getInt(VERTICAL_PADDING, 0)));
        mHorizontalPadding.setSummary(Integer.toString(mPrefs.getInt(HORIZONTAL_PADDING, 0)));

        int hp = mPrefs.getInt(HOTSEAT_ALLAPPS_POSITION, 3);
        mHotseatAllAppsPosition.setSummary(hp == 0 ? mContext.getString(R.string.preferences_auto_number_picker) :
                Integer.toString(hp));
        String hg = mPrefs.getString(HOMESCREEN_GRID, "0|0");
        mHomescreenGrid.setSummary(hg.equals("0|0") ? mContext.getString(R.string.preferences_auto_number_picker) :
                hg.replace("|", " x "));

         // Remove some preferences on large screens
        if (LauncherApplication.isScreenLarge()) {
            PreferenceGroup homescreen = (PreferenceGroup) findPreference("ui_homescreen");
            homescreen.removePreference(findPreference("ui_homescreen_padding"));
            homescreen.removePreference(findPreference("ui_homescreen_indicator"));
            PreferenceGroup drawer = (PreferenceGroup) findPreference("ui_drawer");
            drawer.removePreference(findPreference("ui_drawer_indicator"));
        } else {
            PreferenceCategory scrolling = (PreferenceCategory) findPreference("ui_homescreen_scrolling");
            scrolling.removePreference(findPreference("ui_homescreen_page_outline"));
            prefSet.removePreference(findPreference("ui_tablet"));
            PreferenceGroup dock = (PreferenceGroup) findPreference("ui_dock");
            dock.removePreference(findPreference("ui_homescreen_tablet_dock_divider_two"));
            PreferenceGroup icons = (PreferenceGroup) findPreference("ui_icons");
            icons.removePreference(findPreference("ui_icons_small"));
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
            int allAppsValue = Integer.parseInt((String) newValue);
            if (allAppsIndex.length < 3) {
                if (allAppsValue == 3) allAppsValue = allAppsValue - 2;
                else if (allAppsValue == 1 || allAppsValue == 2) allAppsValue--;
            }
            CharSequence allAppsSummary = allAppsIndex[allAppsValue];
            mAllAppsPosition.setSummary(allAppsSummary);
            return true;
        } else if (preference == mHomescreenTransition) {
            CharSequence homeTransitionIndex[] = mHomescreenTransition.getEntries();
            int homeTransitionValue = mHomescreenTransition.findIndexOfValue((String) newValue);
            CharSequence homeTransitionSummary = homeTransitionIndex[homeTransitionValue];
            mHomescreenTransition.setSummary(homeTransitionSummary);
            return true;
        } else if (preference == mDrawerTransition) {
            CharSequence drawerTransitionIndex[] = mDrawerTransition.getEntries();
            int drawerTransitionValue = mDrawerTransition.findIndexOfValue((String) newValue);
            CharSequence drawerTransitionSummary = drawerTransitionIndex[drawerTransitionValue];
            mDrawerTransition.setSummary(drawerTransitionSummary);
            return true;
        } else if (preference == mHomescreenDoubleTap) {
            CharSequence doubleTapIndex[] = mHomescreenDoubleTap.getEntries();
            int doubleTapValue = Integer.parseInt((String) newValue);
            if (doubleTapValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 0);
            } else {
                mHomescreenDoubleTap.setIcon(null);
            }
            //if (doubleTapValue > 3) doubleTapValue = doubleTapValue - 2;
            //if (doubleTapValue == 6) doubleTapValue--;
            CharSequence doubleTapSummary = doubleTapIndex[doubleTapValue];
            mHomescreenDoubleTap.setSummary(doubleTapSummary);
            return true;
        } else if (preference == mHomescreenSwipeDown) {
            CharSequence homeSwipeDownIndex[] = mHomescreenSwipeDown.getEntries();
            int hSDValue = Integer.parseInt((String) newValue);
            if (hSDValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 2);
            } else {
                mHomescreenSwipeDown.setIcon(null);
            }
            CharSequence homeSDSummary = homeSwipeDownIndex[Integer.parseInt((String) newValue)];
            mHomescreenSwipeDown.setSummary(homeSDSummary);
            return true;
        } else if (preference == mHomescreenSwipeUp) {
            CharSequence homeSwipeUpIndex[] = mHomescreenSwipeUp.getEntries();
            int hSUValue = Integer.parseInt((String) newValue);
            if (hSUValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 1);
            } else {
                mHomescreenSwipeUp.setIcon(null);
            }
            CharSequence homeSUSummary = homeSwipeUpIndex[Integer.parseInt((String) newValue)];
            mHomescreenSwipeUp.setSummary(homeSUSummary);
            return true;
        } else if (preference == mDrawerSwipeDown) {
            CharSequence drawerSwipeDownIndex[] = mDrawerSwipeDown.getEntries();
            int swipeDownValue = Integer.parseInt((String) newValue);
            if (swipeDownValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 4);
            } else {
                mDrawerSwipeDown.setIcon(null);
            }
            if (swipeDownValue > 2) swipeDownValue--;
            if (swipeDownValue == 7) swipeDownValue--;
            CharSequence drawerSDSummary = drawerSwipeDownIndex[swipeDownValue];
            mDrawerSwipeDown.setSummary(drawerSDSummary);
            return true;
        } else if (preference == mDrawerSwipeUp) {
            CharSequence drawerSwipeUpIndex[] = mDrawerSwipeUp.getEntries();
            int swipeUpValue = Integer.parseInt((String) newValue);
            if (swipeUpValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 3);
            } else {
                mDrawerSwipeUp.setIcon(null);
            }
            if (swipeUpValue > 2) swipeUpValue--;
            if (swipeUpValue == 7) swipeUpValue--;
            CharSequence drawerSUSummary = drawerSwipeUpIndex[swipeUpValue];
            mDrawerSwipeUp.setSummary(drawerSUSummary);
            return true;
        } else if (preference == mCustomButtonOne) {
            CharSequence customOneIndex[] = mCustomButtonOne.getEntries();
            int customOneValue = Integer.parseInt((String) newValue);
            if (customOneValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 5);
            }
            CharSequence customOneSummary = customOneIndex[customOneValue];
            mCustomButtonOne.setSummary(customOneSummary);
            if (customOneValue != 3) mCustomButtonOne.setIcon(null);
            return true;
        } else if (preference == mCustomButtonTwo) {
            CharSequence customTwoIndex[] = mCustomButtonTwo.getEntries();
            int customTwoValue = Integer.parseInt((String) newValue);
            if (customTwoValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 6);
            }
            CharSequence customTwoSummary = customTwoIndex[customTwoValue];
            mCustomButtonTwo.setSummary(customTwoSummary);
            if (customTwoValue != 3) mCustomButtonTwo.setIcon(null);
            return true;
        } else if (preference == mCustomButtonThree) {
            CharSequence customThreeIndex[] = mCustomButtonThree.getEntries();
            int customThreeValue = Integer.parseInt((String) newValue);
            if (customThreeValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 7);
            }
            CharSequence customThreeSummary = customThreeIndex[customThreeValue];
            mCustomButtonThree.setSummary(customThreeSummary);
            if (customThreeValue != 3) mCustomButtonThree.setIcon(null);
            return true;
        } else if (preference == mCustomButtonFour) {
            CharSequence customFourIndex[] = mCustomButtonFour.getEntries();
            int customFourValue = Integer.parseInt((String) newValue);
            if (customFourValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 8);
            }
            CharSequence customFourSummary = customFourIndex[customFourValue];
            mCustomButtonFour.setSummary(customFourSummary);
            if (customFourValue != 3) mCustomButtonFour.setIcon(null);
            return true;
        } else if (preference == mCustomButtonFive) {
            CharSequence customFiveIndex[] = mCustomButtonFive.getEntries();
            int customFiveValue = Integer.parseInt((String) newValue);
            if (customFiveValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 9);
            }
            CharSequence customFiveSummary = customFiveIndex[customFiveValue];
            mCustomButtonFive.setSummary(customFiveSummary);
            if (customFiveValue != 3) mCustomButtonFive.setIcon(null);
            return true;
        } else if (preference == mCustomButtonSix) {
            CharSequence customSixIndex[] = mCustomButtonSix.getEntries();
            int customSixValue = Integer.parseInt((String) newValue);
            if (customSixValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 10);
            }
            CharSequence customSixSummary = customSixIndex[customSixValue];
            mCustomButtonSix.setSummary(customSixSummary);
            if (customSixValue != 3) mCustomButtonSix.setIcon(null);
            return true;
        } else if (preference == mCustomButtonSeven) {
            CharSequence customSevenIndex[] = mCustomButtonSeven.getEntries();
            int customSevenValue = Integer.parseInt((String) newValue);
            if (customSevenValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 11);
            }
            CharSequence customSevenSummary = customSevenIndex[customSevenValue];
            mCustomButtonSeven.setSummary(customSevenSummary);
            if (customSevenValue != 3) mCustomButtonSeven.setIcon(null);
            return true;
        } else if (preference == mCustomButtonEight) {
            CharSequence customEightIndex[] = mCustomButtonEight.getEntries();
            int customEightValue = Integer.parseInt((String) newValue);
            if (customEightValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 12);
            }
            CharSequence customEightSummary = customEightIndex[customEightValue];
            mCustomButtonEight.setSummary(customEightSummary);
            if (customEightValue != 3) mCustomButtonEight.setIcon(null);
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
            mCustomButtonSeven.setEnabled(!value && !mCombinedBar.isChecked());
            mCustomButtonEight.setEnabled(!value && !mCombinedBar.isChecked());
            if (value) {
                mCustomButtonSeven.setValueIndex(0);
                mCustomButtonEight.setValueIndex(0);
                mCombinedBar.setChecked(false);
            }
            return true;
        } else if (preference == mCombinedBar) {
            value = mCombinedBar.isChecked();
            mCustomButtonSeven.setEnabled(!value && !mSearchBar.isChecked());
            mCustomButtonEight.setEnabled(!value && !mSearchBar.isChecked());
            if (value) {
                mCustomButtonSeven.setValueIndex(0);
                mCustomButtonEight.setValueIndex(0);
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
                    mHotseatAllAppsPosition.setMaxValue(5);
                    if (mPrefs.getInt(HOTSEAT_POSITIONS, 5) == 6) {
                        mPrefs.edit().putInt(HOTSEAT_POSITIONS, 5).commit();
                        mHotseatPositions.setSummary("5");
                    }
                    if (mPrefs.getInt(HOTSEAT_ALLAPPS_POSITION, 5) == 6) {
                        mPrefs.edit().putInt(HOTSEAT_ALLAPPS_POSITION, 5).commit();
                        mHotseatAllAppsPosition.setSummary("5");
                    }
                }
            } else {
                if (LauncherApplication.getScreenDensity() == 1f) {
                    mHotseatPositions.setMaxValue(6);
                    mHotseatAllAppsPosition.setMaxValue(6);
                }
            }
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 0) {
                mPrefs.edit().putString("hdt_application", data.toUri(0)).commit();
                try {
                    mHomescreenDoubleTap.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 1) {
                mPrefs.edit().putString("hsu_application", data.toUri(0)).commit();
                try {
                    mHomescreenSwipeUp.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 2) {
                mPrefs.edit().putString("hsd_application", data.toUri(0)).commit();
                try {
                    mHomescreenSwipeDown.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 3) {
                mPrefs.edit().putString("dsu_application", data.toUri(0)).commit();
                try {
                    mDrawerSwipeUp.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 4) {
                mPrefs.edit().putString("dsd_application", data.toUri(0)).commit();
                try {
                    mDrawerSwipeDown.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 5) {
                mPrefs.edit().putString("custom_application_one", data.toUri(0)).commit();
                try {
                    mCustomButtonOne.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 6) {
                mPrefs.edit().putString("custom_application_two", data.toUri(0)).commit();
                try {
                    mCustomButtonTwo.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 7) {
                mPrefs.edit().putString("custom_application_three", data.toUri(0)).commit();
                try {
                    mCustomButtonThree.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 8) {
                mPrefs.edit().putString("custom_application_four", data.toUri(0)).commit();
                try {
                    mCustomButtonFour.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 9) {
                mPrefs.edit().putString("custom_application_five", data.toUri(0)).commit();
                try {
                    mCustomButtonFive.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 10) {
                mPrefs.edit().putString("custom_application_six", data.toUri(0)).commit();
                try {
                    mCustomButtonSix.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 11) {
                mPrefs.edit().putString("custom_application_seven", data.toUri(0)).commit();
                try {
                    mCustomButtonSeven.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 12) {
                mPrefs.edit().putString("custom_application_eight", data.toUri(0)).commit();
                try {
                    mCustomButtonEight.setIcon(this.getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            }
        }
    }
}
