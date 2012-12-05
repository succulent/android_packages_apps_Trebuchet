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
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class HomescreenFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Homescreen";

    private NumberPickerPreference mHomescreens;
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

        mHomescreens =
                (NumberPickerPreference) prefSet.findPreference(Preferences.HOMESCREENS);

        mDefaultHomescreen = (NumberPickerPreference)
                prefSet.findPreference(Preferences.DEFAULT_HOMESCREEN);

        mHomescreenGrid = (AutoDoubleNumberPickerPreference)
                prefSet.findPreference(Preferences.HOMESCREEN_GRID);
    }

    public void onResume() {
        super.onResume();
        int homescreens = mPrefs.getInt(Preferences.HOMESCREENS, 5);
        int defaultScreen = mPrefs.getInt(Preferences.DEFAULT_HOMESCREEN, 3);
        if (defaultScreen > homescreens) {
            mPrefs.edit().putInt(Preferences.DEFAULT_HOMESCREEN, homescreens).commit();
            defaultScreen = homescreens;
        }
        mHomescreens.setSummary(Integer.toString(homescreens));
        mDefaultHomescreen.setSummary(Integer.toString(defaultScreen));

        String hg = mPrefs.getString(Preferences.HOMESCREEN_GRID,
                LauncherModel.getCellCountY() + "|" + LauncherModel.getCellCountX());
        String[] gridArray = hg.split("\\|");
        mHomescreenGrid.setSummary(gridArray[1] + " x " + gridArray[0]);

        mHomescreenGrid.setMax1(LauncherModel.getMaxCellCountY());
        mHomescreenGrid.setMax2(LauncherModel.getMaxCellCountX());
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
