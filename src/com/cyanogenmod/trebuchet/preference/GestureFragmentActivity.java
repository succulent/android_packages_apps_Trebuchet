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

import com.cyanogenmod.trebuchet.LauncherApplication;
import com.cyanogenmod.trebuchet.R;

public class GestureFragmentActivity extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Gesture";

    private ListPreference mHomescreenDoubleTap;
    private ListPreference mHomescreenSwipeUp;
    private ListPreference mHomescreenSwipeDown;
    private ListPreference mDrawerSwipeDown;
    private ListPreference mDrawerSwipeUp;
    private ListPreference mAppBarLongClick;

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY, Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.gesture_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mHomescreenDoubleTap = (ListPreference) prefSet.findPreference(PreferenceSettings.HOMESCREEN_DOUBLETAP);
        mHomescreenDoubleTap.setOnPreferenceChangeListener(this);
        mHomescreenSwipeDown = (ListPreference) prefSet.findPreference(PreferenceSettings.HOMESCREEN_SWIPEDOWN);
        mHomescreenSwipeDown.setOnPreferenceChangeListener(this);
        mHomescreenSwipeUp = (ListPreference) prefSet.findPreference(PreferenceSettings.HOMESCREEN_SWIPEUP);
        mHomescreenSwipeUp.setOnPreferenceChangeListener(this);
        mDrawerSwipeDown = (ListPreference) prefSet.findPreference(PreferenceSettings.DRAWER_SWIPEDOWN);
        mDrawerSwipeDown.setOnPreferenceChangeListener(this);
        mDrawerSwipeUp = (ListPreference) prefSet.findPreference(PreferenceSettings.DRAWER_SWIPEUP);
        mDrawerSwipeUp.setOnPreferenceChangeListener(this);
        mAppBarLongClick = (ListPreference) prefSet.findPreference(PreferenceSettings.APP_BAR_LONGCLICK);
        mAppBarLongClick.setOnPreferenceChangeListener(this);

        mHomescreenDoubleTap.setSummary(mHomescreenDoubleTap.getEntry());
        if (mHomescreenDoubleTap.getValue().equals("6")) {
            try {
                mHomescreenDoubleTap.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hdt_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mHomescreenSwipeDown.setSummary(mHomescreenSwipeDown.getEntry());
        if (mHomescreenSwipeDown.getValue().equals("6")) {
            try {
                mHomescreenSwipeDown.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hsd_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mHomescreenSwipeUp.setSummary(mHomescreenSwipeUp.getEntry());
        if (mHomescreenSwipeUp.getValue().equals("6")) {
            try {
                mHomescreenSwipeUp.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("hsu_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mDrawerSwipeDown.setSummary(mDrawerSwipeDown.getEntry());
        if (mDrawerSwipeDown.getValue().equals("6")) {
            try {
                mDrawerSwipeDown.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("dsd_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mDrawerSwipeUp.setSummary(mDrawerSwipeUp.getEntry());
        if (mDrawerSwipeUp.getValue().equals("6")) {
            try {
                mDrawerSwipeUp.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("dsu_application", ""), 0)));
            } catch (Exception e) {
            }
        }
        mAppBarLongClick.setSummary(mAppBarLongClick.getEntry());
        if (mAppBarLongClick.getValue().equals("6")) {
            try {
                mAppBarLongClick.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("app_bar_longclick_application", ""), 0)));
            } catch (Exception e) {
            }
        }

         // Remove some preferences on small screens
        if (!LauncherApplication.isScreenLarge()) {
            prefSet.removePreference(findPreference("ui_app_bar_gestures"));
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mHomescreenDoubleTap) {
            CharSequence doubleTapIndex[] = mHomescreenDoubleTap.getEntries();
            int doubleTapValue = Integer.parseInt((String) newValue);
            if (doubleTapValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 0);
            } else {
                mHomescreenDoubleTap.setIcon(null);
            }
            //if (doubleTapValue > 3) doubleTapValue = doubleTapValue - 2;
            //if (doubleTapValue == 6) doubleTapValue--;
            CharSequence doubleTapSummary = doubleTapIndex[doubleTapValue];
            mHomescreenDoubleTap.setSummary(doubleTapSummary);
            return true;
        } else if (preference == mHomescreenSwipeDown) {
            CharSequence homeSwipeDownIndex[] = mHomescreenSwipeDown.getEntries();
            int hSDValue = Integer.parseInt((String) newValue);
            if (hSDValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 2);
            } else {
                mHomescreenSwipeDown.setIcon(null);
            }
            CharSequence homeSDSummary = homeSwipeDownIndex[Integer.parseInt((String) newValue)];
            mHomescreenSwipeDown.setSummary(homeSDSummary);
            return true;
        } else if (preference == mHomescreenSwipeUp) {
            CharSequence homeSwipeUpIndex[] = mHomescreenSwipeUp.getEntries();
            int hSUValue = Integer.parseInt((String) newValue);
            if (hSUValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 1);
            } else {
                mHomescreenSwipeUp.setIcon(null);
            }
            CharSequence homeSUSummary = homeSwipeUpIndex[Integer.parseInt((String) newValue)];
            mHomescreenSwipeUp.setSummary(homeSUSummary);
            return true;
        } else if (preference == mDrawerSwipeDown) {
            CharSequence drawerSwipeDownIndex[] = mDrawerSwipeDown.getEntries();
            int swipeDownValue = Integer.parseInt((String) newValue);
            if (swipeDownValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 4);
            } else {
                mDrawerSwipeDown.setIcon(null);
            }
            if (swipeDownValue > 2) swipeDownValue--;
            if (swipeDownValue >= 7) swipeDownValue--;
            CharSequence drawerSDSummary = drawerSwipeDownIndex[swipeDownValue];
            mDrawerSwipeDown.setSummary(drawerSDSummary);
            return true;
        } else if (preference == mDrawerSwipeUp) {
            CharSequence drawerSwipeUpIndex[] = mDrawerSwipeUp.getEntries();
            int swipeUpValue = Integer.parseInt((String) newValue);
            if (swipeUpValue == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 3);
            } else {
                mDrawerSwipeUp.setIcon(null);
            }
            if (swipeUpValue > 2) swipeUpValue--;
            if (swipeUpValue >= 7) swipeUpValue--;
            CharSequence drawerSUSummary = drawerSwipeUpIndex[swipeUpValue];
            mDrawerSwipeUp.setSummary(drawerSUSummary);
            return true;
        } else if (preference == mAppBarLongClick) {
            CharSequence index[] = mAppBarLongClick.getEntries();
            int value = Integer.parseInt((String) newValue);
            if (value == 6) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 13);
            } else {
                mAppBarLongClick.setIcon(null);
            }
            mAppBarLongClick.setSummary(index[value]);
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
            } else if (requestCode == 3) {
                mPrefs.edit().putString("dsu_application", data.toUri(0)).commit();
                try {
                    mDrawerSwipeUp.setIcon(getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 4) {
                mPrefs.edit().putString("dsd_application", data.toUri(0)).commit();
                try {
                    mDrawerSwipeDown.setIcon(getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 13) {
                mPrefs.edit().putString("app_bar_longclick_application", data.toUri(0)).commit();
                try {
                    mAppBarLongClick.setIcon(getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            }
        }
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
