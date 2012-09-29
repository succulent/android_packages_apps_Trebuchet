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
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.DisplayMetrics;
import android.util.Log;

import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.LauncherModel;
import com.cyanogenmod.trebuchet.R;

public class DrawerFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Launcher_Drawer";

    private CheckBoxPreference mHideDrawerTab;
    private static AutoDoubleNumberPickerPreference mPortraitAppGrid;
    private static AutoDoubleNumberPickerPreference mLandscapeAppGrid;
    private static AutoDoubleNumberPickerPreference mPortraitWidgetGrid;
    private static AutoDoubleNumberPickerPreference mLandscapeWidgetGrid;

    private static SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.drawer_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mHideDrawerTab = (CheckBoxPreference) prefSet.findPreference(Preferences.HIDE_DRAWER_TAB);

        mPortraitAppGrid = (AutoDoubleNumberPickerPreference)
                prefSet.findPreference(Preferences.PORT_APP_GRID);
        mLandscapeAppGrid = (AutoDoubleNumberPickerPreference)
                prefSet.findPreference(Preferences.LAND_APP_GRID);
        mPortraitWidgetGrid = (AutoDoubleNumberPickerPreference)
                prefSet.findPreference(Preferences.PORT_WIDGET_GRID);
        mLandscapeWidgetGrid = (AutoDoubleNumberPickerPreference)
                prefSet.findPreference(Preferences.LAND_WIDGET_GRID);
    }

    public void onResume() {
        super.onResume();

        boolean landscape = LauncherApplication.isScreenLandscape(getActivity());

        mLandscapeAppGrid.setEnabled(landscape);
        mLandscapeWidgetGrid.setEnabled(landscape);
        mPortraitAppGrid.setEnabled(!landscape);
        mPortraitWidgetGrid.setEnabled(!landscape);

        Resources r = getActivity().getResources();

        boolean largeIcons = PreferencesProvider.Interface.Homescreen.getLargeIconSize(getActivity());
        int cellWidth = r.getDimensionPixelSize(largeIcons ? R.dimen.apps_customize_cell_width_large :
                R.dimen.apps_customize_cell_width);
        int cellHeight = r.getDimensionPixelSize(largeIcons ? R.dimen.apps_customize_cell_height_large :
                R.dimen.apps_customize_cell_height);
        DisplayMetrics displayMetrics = r.getDisplayMetrics();
        final float screenWidth = r.getConfiguration().screenWidthDp * displayMetrics.density;
        final float screenHeight = r.getConfiguration().screenHeightDp * displayMetrics.density;

        int tabBarHeight = r.getDimensionPixelSize(R.dimen.apps_customize_tab_bar_height);

        int cellCountXPort = (int) ((landscape ? screenHeight : screenWidth) / cellWidth);
        int cellCountYPort = (int) (((landscape ? screenWidth : screenHeight) - tabBarHeight) / cellHeight);

        int cellCountXLand = (int) ((landscape ? screenWidth : screenHeight) / cellWidth);
        int cellCountYLand = (int) (((landscape ? screenHeight : (screenWidth - tabBarHeight)) - tabBarHeight) / cellHeight);

        mPortraitAppGrid.setMax1(cellCountYPort);
        mPortraitAppGrid.setMax2(cellCountXPort);

        mLandscapeAppGrid.setMax1(cellCountYLand);
        mLandscapeAppGrid.setMax2(cellCountXLand);

        if (cellCountXPort > LauncherModel.getMaxCellCountY()) cellCountXPort = LauncherModel.getMaxCellCountY();
        if (cellCountYPort > LauncherModel.getMaxCellCountX()) cellCountYPort = LauncherModel.getMaxCellCountX();
        if (cellCountXLand > LauncherModel.getMaxCellCountX()) cellCountXLand = LauncherModel.getMaxCellCountX();
        if (cellCountYLand > LauncherModel.getMaxCellCountY()) cellCountYLand = LauncherModel.getMaxCellCountY();

        String pag = mPrefs.getString(Preferences.PORT_APP_GRID,
                cellCountYPort + "|" + cellCountXPort);
        String[] gridArray = pag.split("\\|");
        mPortraitAppGrid.setSummary(landscape ? "" : (gridArray[1] + " x " + gridArray[0]));

        pag = mPrefs.getString(Preferences.LAND_APP_GRID,
                cellCountYLand + "|" + cellCountXLand);
        gridArray = pag.split("\\|");
        mLandscapeAppGrid.setSummary(landscape ? (gridArray[1] + " x " + gridArray[0]) : "");

        pag = mPrefs.getString(Preferences.PORT_WIDGET_GRID, "3|2");
        gridArray = pag.split("\\|");
        mPortraitWidgetGrid.setSummary(landscape ? "" : (gridArray[1] + " x " + gridArray[0]));

        pag = mPrefs.getString(Preferences.LAND_WIDGET_GRID, "2|3");
        gridArray = pag.split("\\|");
        mLandscapeWidgetGrid.setSummary(landscape ? (gridArray[1] + " x " + gridArray[0]) : "");
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
