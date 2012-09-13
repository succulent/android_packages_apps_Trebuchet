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

public class HomescreenFragmentActivity extends PreferenceFragment {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Homescreen";

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

        if (!LauncherApplication.isScreenLarge()) {
            PreferenceCategory general = (PreferenceCategory) getPreferenceScreen()
                    .findPreference("ui_homescreen_general");
            general.removePreference(findPreference("ui_show_apps_button"));
        }
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
                LauncherApplication.isScreenLarge() ? "0|0" : "6|6");
        mHomescreenGrid.setSummary(hg.equals("0|0") ?
                getActivity().getString(R.string.preferences_auto_number_picker) :
                hg.replace("|", " x "));

        Resources r = getActivity().getResources();
        int cellWidth = r.getDimensionPixelSize(R.dimen.workspace_cell_width);
        int cellHeight = r.getDimensionPixelSize(R.dimen.workspace_cell_height);
        DisplayMetrics displayMetrics = r.getDisplayMetrics();
        final float smallestScreenDim = r.getConfiguration().smallestScreenWidthDp *
                displayMetrics.density;
        mHomescreenGrid.setMax1((int) (smallestScreenDim / cellHeight));
        mHomescreenGrid.setMax2((int) (smallestScreenDim / cellWidth));
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
