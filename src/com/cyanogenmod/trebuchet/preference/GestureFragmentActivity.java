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
import android.content.Intent;
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

import com.cyanogenmod.trebuchet.R;

public class GestureFragmentActivity extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Launcher_Gesture";

    private ListPreference mHomescreenDoubleTap;
    private ListPreference mHomescreenSwipeUp;
    private ListPreference mHomescreenSwipeDown;

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY, Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.gesture_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mHomescreenDoubleTap = (ListPreference) prefSet.findPreference(Preferences.HOMESCREEN_DOUBLETAP);
        mHomescreenDoubleTap.setOnPreferenceChangeListener(this);
        mHomescreenSwipeDown = (ListPreference) prefSet.findPreference(Preferences.HOMESCREEN_SWIPEDOWN);
        mHomescreenSwipeDown.setOnPreferenceChangeListener(this);
        mHomescreenSwipeUp = (ListPreference) prefSet.findPreference(Preferences.HOMESCREEN_SWIPEUP);
        mHomescreenSwipeUp.setOnPreferenceChangeListener(this);

        mHomescreenDoubleTap.setSummary(mHomescreenDoubleTap.getEntry());
        if (mHomescreenDoubleTap.getValue().equals("5")) {
            try {
                mHomescreenDoubleTap.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hdt_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mHomescreenSwipeDown.setSummary(mHomescreenSwipeDown.getEntry());
        if (mHomescreenSwipeDown.getValue().equals("5")) {
            try {
                mHomescreenSwipeDown.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hsd_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mHomescreenSwipeUp.setSummary(mHomescreenSwipeUp.getEntry());
        if (mHomescreenSwipeUp.getValue().equals("5")) {
            try {
                mHomescreenSwipeUp.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hsu_application", ""), 0)));
            } catch (Exception e) {
            }
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mHomescreenDoubleTap) {
            CharSequence doubleTapIndex[] = mHomescreenDoubleTap.getEntries();
            int doubleTapValue = Integer.parseInt((String) newValue);
            if (doubleTapValue == 5) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 0);
            } else {
                mHomescreenDoubleTap.setIcon(null);
            }
            CharSequence doubleTapSummary = doubleTapIndex[doubleTapValue];
            mHomescreenDoubleTap.setSummary(doubleTapSummary);
            return true;
        } else if (preference == mHomescreenSwipeDown) {
            CharSequence homeSwipeDownIndex[] = mHomescreenSwipeDown.getEntries();
            int hSDValue = Integer.parseInt((String) newValue);
            if (hSDValue == 5) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 2);
            } else {
                mHomescreenSwipeDown.setIcon(null);
            }
            CharSequence homeSDSummary = homeSwipeDownIndex[hSDValue];
            mHomescreenSwipeDown.setSummary(homeSDSummary);
            return true;
        } else if (preference == mHomescreenSwipeUp) {
            CharSequence homeSwipeUpIndex[] = mHomescreenSwipeUp.getEntries();
            int hSUValue = Integer.parseInt((String) newValue);
            if (hSUValue == 5) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 1);
            } else {
                mHomescreenSwipeUp.setIcon(null);
            }
            CharSequence homeSUSummary = homeSwipeUpIndex[hSUValue];
            mHomescreenSwipeUp.setSummary(homeSUSummary);
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 0) {
                mPrefs.edit().putString("hdt_application", data.toUri(0)).commit();
                try {
                    mHomescreenDoubleTap.setIcon(getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 1) {
                mPrefs.edit().putString("hsu_application", data.toUri(0)).commit();
                try {
                    mHomescreenSwipeUp.setIcon(getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 2) {
                mPrefs.edit().putString("hsd_application", data.toUri(0)).commit();
                try {
                    mHomescreenSwipeDown.setIcon(getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            }
        }
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
