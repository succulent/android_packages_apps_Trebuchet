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

package com.android.launcher2.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.android.launcher.R;

public class HomescreenFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Homescreen";

    private NumberPickerPreference mDefaultHomescreen;
    private static AutoDoubleNumberPickerPreference mHomescreenGrid;

    private static SharedPreferences mPrefs;
    private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.homescreen_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mDefaultHomescreen = (NumberPickerPreference)
                prefSet.findPreference(Preferences.DEFAULT_HOMESCREEN);

        mHomescreenGrid = (AutoDoubleNumberPickerPreference)
                prefSet.findPreference(Preferences.HOMESCREEN_GRID);
    }

    public void onResume() {
        super.onResume();
        mDefaultHomescreen.setSummary(
                Integer.toString(mPrefs.getInt(Preferences.DEFAULT_HOMESCREEN, 3)));

        String hg = mPrefs.getString(Preferences.HOMESCREEN_GRID, "6|6");
        mHomescreenGrid.setSummary(hg.equals("0|0") ?
                getActivity().getString(R.string.preferences_auto_number_picker) :
                hg.replace("|", " x "));
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
