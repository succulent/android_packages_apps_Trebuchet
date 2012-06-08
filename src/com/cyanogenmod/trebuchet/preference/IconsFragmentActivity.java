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
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.LauncherModel;
import com.cyanogenmod.trebuchet.R;
import com.cyanogenmod.trebuchet.Workspace;

public class IconsFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Icons";

    private CheckBoxPreference mSmallerIcons;
    private CheckBoxPreference mDockLabels;

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.icon_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mSmallerIcons =
                (CheckBoxPreference) prefSet.findPreference(PreferenceSettings.SMALLER_ICONS);
        mDockLabels = (CheckBoxPreference) prefSet.findPreference(PreferenceSettings.DOCK_LABELS);

        if (!mSmallerIcons.isChecked()) {
            mDockLabels.setChecked(false);
        }

         // Remove some preferences on small screens
        if (!LauncherApplication.isScreenLarge()) {
            prefSet.removePreference(findPreference("ui_icons_small"));
        }
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mSmallerIcons) {
            if (!mSmallerIcons.isChecked()) {
                mDockLabels.setChecked(false);
            }
            if (LauncherApplication.isScreenLarge()) {
                mPrefs.edit().putBoolean("ui_tablet_smaller_icons",
                        mSmallerIcons.isChecked()).commit();
                int[] cellCount = Workspace.getCellCountsForLarge(getActivity());
                LauncherModel.updateWorkspaceLayoutCells(cellCount[0], cellCount[1]);
                DockFragmentActivity.updateMaxValue();
                HomescreenFragmentActivity.updateMaxValue();
            }
            return true;
        }
        return false;
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
