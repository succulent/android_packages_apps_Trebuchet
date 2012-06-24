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
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.LauncherModel;
import com.cyanogenmod.trebuchet.R;

public class DockFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Dock";

    private static NumberPickerPreference mHotseatPositions;
    private static AutoNumberPickerPreference mHotseatAllAppsPosition;

    private CheckBoxPreference mShowDock;
    private CheckBoxPreference mShowDockBackground;
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
                prefSet.findPreference(PreferenceSettings.HOTSEAT_POSITIONS);

        mHotseatAllAppsPosition = (AutoNumberPickerPreference)
                prefSet.findPreference(PreferenceSettings.HOTSEAT_ALLAPPS_POSITION);

        mShowDock = (CheckBoxPreference)
                prefSet.findPreference(PreferenceSettings.SHOW_DOCK);

        mShowDockBackground = (CheckBoxPreference)
                prefSet.findPreference(PreferenceSettings.SHOW_DOCK_BACKGROUND);

        mShowDockDivider = (CheckBoxPreference)
                prefSet.findPreference(PreferenceSettings.SHOW_DOCK_DIVIDER);

        mShowDockAppsButton = (CheckBoxPreference)
                prefSet.findPreference(PreferenceSettings.SHOW_DOCK_APPS_BUTTON);

        mShowDockDivider = (CheckBoxPreference)
                prefSet.findPreference(PreferenceSettings.SHOW_DOCK_DIVIDER);

        PreferenceCategory dock = (PreferenceCategory) prefSet.findPreference("ui_dock");
         // Remove some preferences on small screens
        if (!LauncherApplication.isScreenLarge()) {
            dock.removePreference(findPreference("ui_homescreen_tablet_dock_divider_two"));
        } else {
            dock.removePreference(mShowDockBackground);
        }
    }

    public void onResume() {
        super.onResume();
        if (LauncherApplication.isScreenLarge()) {
            boolean max = mPrefs.getBoolean("ui_homescreen_maximize", false);
            if (max) mShowDock.setChecked(false);
            mShowDock.setEnabled(!max);
        }
        boolean smallIcons = mPrefs.getBoolean(PreferenceSettings.SMALLER_ICONS, false);
        if (!LauncherApplication.isScreenLarge()) {
            mHotseatPositions.setMaxValue(smallIcons &&
                    LauncherApplication.isLayoutLarge() ? 6 : 5);
            mHotseatAllAppsPosition.setMaxValue(smallIcons &&
                    LauncherApplication.isLayoutLarge() ? 6 : 5);
        } else {
            mHotseatPositions.setMaxValue(LauncherModel.getCellCountY());
            mHotseatAllAppsPosition.setMaxValue(LauncherModel.getCellCountY());
        }
        if (mPrefs.getInt(PreferenceSettings.HOTSEAT_POSITIONS, 5) >
                mHotseatPositions.getMaxValue()) {
            mPrefs.edit().putInt(PreferenceSettings.HOTSEAT_POSITIONS,
                    LauncherApplication.isScreenLarge() ?
                    LauncherModel.getCellCountY() : 5).commit();
        }
        mHotseatPositions.setSummary(Integer.toString(mPrefs.getInt(
                PreferenceSettings.HOTSEAT_POSITIONS, 5)));

        if (mPrefs.getInt(PreferenceSettings.HOTSEAT_ALLAPPS_POSITION, 3) >
                mHotseatAllAppsPosition.getMaxValue()) {
            mPrefs.edit().putInt(PreferenceSettings.HOTSEAT_ALLAPPS_POSITION,
                    LauncherApplication.isScreenLarge() ?
                    LauncherModel.getCellCountY() : 5).commit();
        }
        int hp = mPrefs.getInt(PreferenceSettings.HOTSEAT_ALLAPPS_POSITION, 3);
        mHotseatAllAppsPosition.setSummary(hp == 0 ? mContext.getString(
                R.string.preferences_auto_number_picker) : Integer.toString(hp));

        mShowDock.setChecked(PreferencesProvider.Interface.Dock.getShowHotseat(mContext));

        mShowDockDivider.setChecked(
                PreferencesProvider.Interface.Homescreen.Indicator.getShowDockDivider(mContext));

        mShowDockAppsButton.setChecked(
                PreferencesProvider.Interface.Dock.getShowAllAppsHotseat(mContext));
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mShowDock) {
            if (!mShowDock.isChecked() && !LauncherApplication.isScreenLarge()) {
                mShowDockBackground.setChecked(false);
            }
            return true;
        }
        return false;
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
