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

package com.sbradymobile.launchhome.preference;

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

import com.sbradymobile.launchhome.R;
import com.sbradymobile.launchhome.LauncherApplication;
import com.sbradymobile.launchhome.LauncherModel;

public class DockFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Dock";

    private static NumberPickerPreference mHotseatPositions;

    private CheckBoxPreference mShowDock;
    private CheckBoxPreference mShowDockDivider;

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

        mShowDock = (CheckBoxPreference)
                prefSet.findPreference(Preferences.SHOW_DOCK);

        mShowDockDivider = (CheckBoxPreference)
                prefSet.findPreference(Preferences.SHOW_DOCK_DIVIDER);

        mShowDockDivider = (CheckBoxPreference)
                prefSet.findPreference(Preferences.SHOW_DOCK_DIVIDER);
    }

    public void onResume() {
        super.onResume();

        int hotseatPositions = mPrefs.getInt(Preferences.HOTSEAT_POSITIONS, 0);

        int hotseatMax = LauncherModel.getCellCountX();

        if (hotseatPositions < 1) {
            hotseatPositions = hotseatMax;
            mPrefs.edit().putInt(Preferences.HOTSEAT_POSITIONS, hotseatPositions).commit();
        }

        mHotseatPositions.setMaxValue(hotseatMax);

        mHotseatPositions.setSummary(Integer.toString(hotseatPositions));

        mShowDock.setChecked(PreferencesProvider.Interface.Dock.getShowHotseat(mContext));

        mShowDockDivider.setChecked(
                PreferencesProvider.Interface.Homescreen.Indicator.getShowDockDivider(mContext));

        if (!mShowDock.isChecked()) {
            mShowDockDivider.setChecked(false);
        }
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
