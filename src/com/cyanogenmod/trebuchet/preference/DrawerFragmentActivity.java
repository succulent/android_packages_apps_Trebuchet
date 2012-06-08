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
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.R;

public class DrawerFragmentActivity extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Drawer";

    private CheckBoxPreference mHideDrawerTab;
    private CheckBoxPreference mJoinWidgets;
    private ListPreference mDrawerTransition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.drawer_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mHideDrawerTab = (CheckBoxPreference) prefSet.findPreference(PreferenceSettings.HIDE_DRAWER_TAB);
        mJoinWidgets = (CheckBoxPreference) prefSet.findPreference(PreferenceSettings.JOIN_WIDGETS);
        mDrawerTransition = (ListPreference) prefSet.findPreference(PreferenceSettings.DRAWER_TRANSITION);
        mDrawerTransition.setOnPreferenceChangeListener(this);
        mDrawerTransition.setSummary(mDrawerTransition.getEntry());

        if (mHideDrawerTab.isChecked()) {
            mJoinWidgets.setChecked(true);
        }

         // Remove some preferences on large screens
        if (LauncherApplication.isScreenLarge()) {
            prefSet.removePreference(findPreference("ui_drawer_indicator"));
        }
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == mHideDrawerTab) {
            if (mHideDrawerTab.isChecked()) {
                mJoinWidgets.setChecked(true);
            }
            return true;
        }
        return false;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mDrawerTransition) {
            CharSequence drawerTransitionIndex[] = mDrawerTransition.getEntries();
            int drawerTransitionValue = mDrawerTransition.findIndexOfValue((String) newValue);
            CharSequence drawerTransitionSummary = drawerTransitionIndex[drawerTransitionValue];
            mDrawerTransition.setSummary(drawerTransitionSummary);
            return true;
        }
        return false;
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
