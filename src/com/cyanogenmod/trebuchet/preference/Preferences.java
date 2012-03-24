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
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import com.cyanogenmod.trebuchet.R;

public class Preferences extends PreferenceActivity {

    private static final String TAG = "Launcher.Preferences";

    private static final String SEARCH_BAR = "ui_homescreen_general_search";
    private static final String PHONE_SEARCH_BAR = "ui_homescreen_general_phone_search";
    private static final String COMBINED_BAR = "ui_tablet_workspace_combined_bar";
    private static final String CENTER_ALLAPPS = "ui_tablet_workspace_allapps_center";

    private CheckBoxPreference mSearchBar;
    private CheckBoxPreference mPhoneSearchBar;
    private CheckBoxPreference mCombinedBar;
    private CheckBoxPreference mCenterAllApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences prefs =
            getSharedPreferences(PreferencesProvider.PREFERENCES_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(PreferencesProvider.PREFERENCES_CHANGED, true);
                editor.commit();

        Preference version = findPreference("application_version");
        version.setTitle(getString(R.string.application_name) + " " + getString(R.string.application_version));

        PreferenceScreen prefSet = getPreferenceScreen();

        mSearchBar = (CheckBoxPreference) prefSet.findPreference(SEARCH_BAR);
        mPhoneSearchBar = (CheckBoxPreference) prefSet.findPreference(PHONE_SEARCH_BAR);
        mCombinedBar = (CheckBoxPreference) prefSet.findPreference(COMBINED_BAR);
        mCenterAllApps = (CheckBoxPreference) prefSet.findPreference(CENTER_ALLAPPS);

        if (mSearchBar.isChecked()) {
            mPhoneSearchBar.setChecked(true);
            mCombinedBar.setEnabled(false);
            mCenterAllApps.setEnabled(false);
        }
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mSearchBar) {
            value = mSearchBar.isChecked();
            mPhoneSearchBar.setChecked(value);
            mCombinedBar.setEnabled(!value);
            mCenterAllApps.setEnabled(!value);
            return true;
        } else if (preference == mPhoneSearchBar) {
            value = mPhoneSearchBar.isChecked();
            mSearchBar.setChecked(value);
            mCombinedBar.setEnabled(!value);
            mCenterAllApps.setEnabled(!value);
            return true;
        }
        return false;
    }
}
