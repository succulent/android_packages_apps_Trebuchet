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
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.DisplayMetrics;
import android.util.Log;

import com.cyanogenmod.trebuchet.R;
import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.LauncherModel;

public class DockFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Launcher2_Dock";

    private static NumberPickerPreference mHotseatPositions;
    private static NumberPickerPreference mHotseatScale;
    private static AutoNumberPickerPreference mHotseatAllAppsPosition;

    private CheckBoxPreference mShowDock;
    private CheckBoxPreference mShowDockDivider;
    private CheckBoxPreference mShowDockAppsButton;

    private SharedPreferences mPrefs;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.dock_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mHotseatPositions = (NumberPickerPreference)
                prefSet.findPreference(Preferences.HOTSEAT_POSITIONS);

        mHotseatScale = (NumberPickerPreference)
                prefSet.findPreference(Preferences.HOTSEAT_SCALE);

        mHotseatAllAppsPosition = (AutoNumberPickerPreference)
                prefSet.findPreference(Preferences.HOTSEAT_ALLAPPS_POSITION);

        mShowDock = (CheckBoxPreference)
                prefSet.findPreference(Preferences.SHOW_DOCK);

        mShowDockDivider = (CheckBoxPreference)
                prefSet.findPreference(Preferences.SHOW_DOCK_DIVIDER);

        mShowDockAppsButton = (CheckBoxPreference)
                prefSet.findPreference(Preferences.SHOW_DOCK_APPS_BUTTON);

        mShowDockDivider = (CheckBoxPreference)
                prefSet.findPreference(Preferences.SHOW_DOCK_DIVIDER);
    }

    public void onResume() {
        super.onResume();

        mHotseatPositions.setSummary(Integer.toString(mPrefs.getInt(
                Preferences.HOTSEAT_POSITIONS, 7)));

        mHotseatScale.setSummary(Integer.toString(mPrefs.getInt(
                Preferences.HOTSEAT_SCALE, 100)));

        int hp = mPrefs.getInt(Preferences.HOTSEAT_ALLAPPS_POSITION, 0);
        mHotseatAllAppsPosition.setSummary(hp == 0 ? mContext.getString(
                R.string.preferences_auto_number_picker) : Integer.toString(hp));

        mShowDock.setChecked(PreferencesProvider.Interface.Dock.getShowHotseat(mContext));

        mShowDockDivider.setChecked(
                PreferencesProvider.Interface.Homescreen.Indicator.getShowDockDivider(mContext));

        mShowDockAppsButton.setChecked(
                PreferencesProvider.Interface.Dock.getShowAllAppsHotseat(mContext));

        if (!mShowDock.isChecked()) {
            mShowDockDivider.setChecked(false);
        }
/*
        Resources r = getActivity().getResources();
        boolean largeIcons = PreferencesProvider.Interface.Homescreen.getLargeIconSize(mContext);
        int cellWidth = r.getDimensionPixelSize(largeIcons ? R.dimen.hotseat_cell_width_large :
                R.dimen.hotseat_cell_width);
        DisplayMetrics displayMetrics = r.getDisplayMetrics();
        final float screenWidth = r.getConfiguration().screenWidthDp * displayMetrics.density;
        final float screenHeight = r.getConfiguration().screenHeightDp * displayMetrics.density;
        final float smallestScreenDim = screenHeight > screenWidth ? screenWidth : screenHeight;

        mHotseatPositions.setMaxValue((int) (smallestScreenDim / cellWidth));*/
        mHotseatPositions.setMaxValue(LauncherModel.getCellCountX());
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mShowDock) {
            if (!mShowDock.isChecked()) {
                mShowDockDivider.setChecked(false);
            }
            return true;
        }
        return false;
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
