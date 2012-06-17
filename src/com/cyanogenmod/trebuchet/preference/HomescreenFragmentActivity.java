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
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.LauncherModel;
import com.cyanogenmod.trebuchet.R;

public class HomescreenFragmentActivity extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Homescreen";

    private ListPreference mHomescreenTransition;
    private NumberPickerPreference mHomescreens;
    private NumberPickerPreference mDefaultHomescreen;
    private NumberPickerPreference mVerticalPadding;
    private NumberPickerPreference mHorizontalPadding;
    private static AutoDoubleNumberPickerPreference mHomescreenGrid;

    private static SharedPreferences mPrefs;
    private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY, Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.homescreen_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mHomescreenTransition = (ListPreference) prefSet.findPreference(PreferenceSettings.HOMESCREEN_TRANSITION);
        mHomescreenTransition.setOnPreferenceChangeListener(this);
        mHomescreenTransition.setSummary(mHomescreenTransition.getEntry());

        mHomescreens = (NumberPickerPreference) prefSet.findPreference(PreferenceSettings.HOMESCREENS);
        mHomescreens.setSummary(Integer.toString(mPrefs.getInt(PreferenceSettings.HOMESCREENS, 5)));

        mDefaultHomescreen = (NumberPickerPreference) prefSet.findPreference(PreferenceSettings.DEFAULT_HOMESCREEN);
        mDefaultHomescreen.setSummary(Integer.toString(mPrefs.getInt(PreferenceSettings.DEFAULT_HOMESCREEN, 3)));

        mHomescreenGrid = (AutoDoubleNumberPickerPreference) prefSet.findPreference(PreferenceSettings.HOMESCREEN_GRID);
        String hg = mPrefs.getString(PreferenceSettings.HOMESCREEN_GRID,
                LauncherApplication.isScreenLarge() ? "0|0" : "4|4");
        mHomescreenGrid.setSummary(hg.equals("0|0") ? getActivity().getString(R.string.preferences_auto_number_picker) :
                hg.replace("|", " x "));
        if (LauncherApplication.isScreenLarge()) {
            mHomescreenGrid.setMax1(LauncherModel.getCellCountY());
            mHomescreenGrid.setMax2(LauncherModel.getCellCountX());
        } else if (getResources().getConfiguration().smallestScreenWidthDp <= 480) {
            boolean smallIcons = mPrefs.getBoolean(PreferenceSettings.SMALLER_ICONS, false);
            mHomescreenGrid.setMax1(smallIcons ? 5 : 4);
            mHomescreenGrid.setMax2(smallIcons ? 5 : 4);
        }

        mVerticalPadding = (NumberPickerPreference) prefSet.findPreference(PreferenceSettings.VERTICAL_PADDING);
        mHorizontalPadding = (NumberPickerPreference) prefSet.findPreference(PreferenceSettings.HORIZONTAL_PADDING);
        mVerticalPadding.setSummary(Integer.toString(mPrefs.getInt(PreferenceSettings.VERTICAL_PADDING, 0)));
        mHorizontalPadding.setSummary(Integer.toString(mPrefs.getInt(PreferenceSettings.HORIZONTAL_PADDING, 0)));

         // Remove some preferences on large screens
        if (LauncherApplication.isScreenLarge()) {
            prefSet.removePreference(findPreference("ui_homescreen_padding"));
            prefSet.removePreference(findPreference("ui_homescreen_indicator"));
            PreferenceCategory homescreenGeneral = (PreferenceCategory) findPreference("ui_homescreen_general");
            homescreenGeneral.removePreference(findPreference(PreferenceSettings.PHONE_SEARCH_BAR));
        } else {
            PreferenceCategory scrolling = (PreferenceCategory) findPreference("ui_homescreen_scrolling");
            scrolling.removePreference(findPreference("ui_homescreen_page_outline"));
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mHomescreenTransition) {
            CharSequence homeTransitionIndex[] = mHomescreenTransition.getEntries();
            int homeTransitionValue = mHomescreenTransition.findIndexOfValue((String) newValue);
            CharSequence homeTransitionSummary = homeTransitionIndex[homeTransitionValue];
            mHomescreenTransition.setSummary(homeTransitionSummary);
            return true;
        }
        return false;
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void updateMaxValue() {
        boolean smallUi = mContext.getResources().getConfiguration().smallestScreenWidthDp <= 480;
        boolean smallIcons = mPrefs.getBoolean(PreferenceSettings.SMALLER_ICONS, false);
        if (!smallUi && mHomescreenGrid.getMax1() > LauncherModel.getCellCountY()) {
            if (!mPrefs.getString(PreferenceSettings.HOMESCREEN_GRID, "0|0").equals("0|0")) {
                mPrefs.edit().putString(PreferenceSettings.HOMESCREEN_GRID,
                        LauncherModel.getCellCountY() + "|" + LauncherModel.getCellCountX()).commit();
            }
        } else if (smallUi) {
            mPrefs.edit().putString(PreferenceSettings.HOMESCREEN_GRID, (smallIcons ? "5" : "4")
                   + "|" + (smallIcons ? "5" : "4")).commit();
        }
        mHomescreenGrid.setMax1(smallUi ? (smallIcons ? 5 : 4) : LauncherModel.getCellCountY());
        mHomescreenGrid.setMax2(smallUi ? (smallIcons ? 5 : 4) : LauncherModel.getCellCountY());
        String hg = mPrefs.getString(PreferenceSettings.HOMESCREEN_GRID, "0|0");
        mHomescreenGrid.setSummary(hg.equals("0|0") ? mContext.getString(R.string.preferences_auto_number_picker) :
                hg.replace("|", " x "));
    }
}
