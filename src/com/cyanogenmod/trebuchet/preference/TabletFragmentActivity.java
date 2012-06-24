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
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.cyanogenmod.trebuchet.R;

public class TabletFragmentActivity extends PreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String PREF_ENABLED = "1";
    private static final String TAG = "Trebuchet_Tablet";

    private CheckBoxPreference mSearchBar;
    private CheckBoxPreference mAllAppsBar;
    private CheckBoxPreference mCombinedBar;
    private CheckBoxPreference mCenterAllApps;
    private ListPreference mAllAppsPosition;
    private ListPreference mSearchPosition;
    private ListPreference mCustomButtonOne;
    private ListPreference mCustomButtonTwo;
    private ListPreference mCustomButtonThree;
    private ListPreference mCustomButtonFour;
    private ListPreference mCustomButtonFive;
    private ListPreference mCustomButtonSix;
    private ListPreference mCustomButtonSeven;
    private ListPreference mCustomButtonEight;

    private boolean mMaximize;

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = getActivity().getSharedPreferences(PreferencesProvider.PREFERENCES_KEY,
                Context.MODE_PRIVATE);

        addPreferencesFromResource(R.xml.tablet_preferences);

        PreferenceScreen prefSet = getPreferenceScreen();

        mSearchBar = (CheckBoxPreference) prefSet.findPreference(PreferenceSettings.SEARCH_BAR);
        mAllAppsBar = (CheckBoxPreference) prefSet.findPreference(PreferenceSettings.ALL_APPS_BAR);
        mCombinedBar = (CheckBoxPreference) prefSet.findPreference(PreferenceSettings.COMBINED_BAR);
        mCenterAllApps = (CheckBoxPreference)
                prefSet.findPreference(PreferenceSettings.CENTER_ALLAPPS);
        mAllAppsPosition = (ListPreference)
                prefSet.findPreference(PreferenceSettings.ALLAPPS_POSITION);
        mAllAppsPosition.setOnPreferenceChangeListener(this);
        mSearchPosition = (ListPreference)
                prefSet.findPreference(PreferenceSettings.SEARCH_POSITION);
        mSearchPosition.setOnPreferenceChangeListener(this);
        mCustomButtonOne = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_ONE);
        mCustomButtonOne.setOnPreferenceChangeListener(this);
        mCustomButtonTwo = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_TWO);
        mCustomButtonTwo.setOnPreferenceChangeListener(this);
        mCustomButtonThree = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_THREE);
        mCustomButtonThree.setOnPreferenceChangeListener(this);
        mCustomButtonFour = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_FOUR);
        mCustomButtonFour.setOnPreferenceChangeListener(this);
        mCustomButtonFive = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_FIVE);
        mCustomButtonFive.setOnPreferenceChangeListener(this);
        mCustomButtonSix = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_SIX);
        mCustomButtonSix.setOnPreferenceChangeListener(this);
        mCustomButtonSeven = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_SEVEN);
        mCustomButtonSeven.setOnPreferenceChangeListener(this);
        mCustomButtonEight = (ListPreference)
                prefSet.findPreference(PreferenceSettings.CUSTOM_BUTTON_EIGHT);
        mCustomButtonEight.setOnPreferenceChangeListener(this);
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mSearchPosition) {
            String searchValue = (String) newValue;
            String allAppsValue = mAllAppsPosition.getValue();
            if (mSearchBar.isChecked() && (searchValue.equals("1") || searchValue.equals("2"))) {
                mAllAppsPosition.setEntries(
                        R.array.preferences_interface_search_button_bottom_entries);
                mAllAppsPosition.setEntryValues(
                        R.array.preferences_interface_search_button_bottom_values);
                if (allAppsValue.equals("0") || allAppsValue.equals("3")) {
                    mAllAppsPosition.setValueIndex(0);
                    mAllAppsPosition.setSummary("");
                }
            } else if (mSearchBar.isChecked()) {
                mAllAppsPosition.setEntries(
                        R.array.preferences_interface_search_button_top_entries);
                mAllAppsPosition.setEntryValues(
                        R.array.preferences_interface_search_button_top_values);
                if (allAppsValue.equals("1") || allAppsValue.equals("2")) {
                    mAllAppsPosition.setValueIndex(0);
                    mAllAppsPosition.setSummary("");
                }
            } else {
                mAllAppsPosition.setEntries(R.array.preferences_interface_button_corner_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_button_corner_values);
            }
            CharSequence searchIndex[] = mSearchPosition.getEntries();
            CharSequence searchSummary = searchIndex[Integer.parseInt((String) newValue)];
            mSearchPosition.setSummary(searchSummary);
            return true;
        } else if (preference == mAllAppsPosition) {
            CharSequence allAppsIndex[] = mAllAppsPosition.getEntries();
            int allAppsValue = Integer.parseInt((String) newValue);
            if (allAppsIndex.length < 3) {
                if (allAppsValue == 3) allAppsValue = allAppsValue - 2;
                else if (allAppsValue == 1 || allAppsValue == 2) allAppsValue--;
            }
            CharSequence allAppsSummary = allAppsIndex[allAppsValue];
            mAllAppsPosition.setSummary(allAppsSummary);
            return true;
        } else if (preference == mCustomButtonOne) {
            CharSequence customOneIndex[] = mCustomButtonOne.getEntries();
            int customOneValue = Integer.parseInt((String) newValue);
            if (customOneValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 5);
            }
            CharSequence customOneSummary = customOneIndex[mMaximize && customOneValue > 1
                    ? customOneValue - 1 : customOneValue];
            mCustomButtonOne.setSummary(customOneSummary);
            if (customOneValue != 3) mCustomButtonOne.setIcon(null);
            return true;
        } else if (preference == mCustomButtonTwo) {
            CharSequence customTwoIndex[] = mCustomButtonTwo.getEntries();
            int customTwoValue = Integer.parseInt((String) newValue);
            if (customTwoValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 6);
            }
            CharSequence customTwoSummary = customTwoIndex[mMaximize && customTwoValue > 1
                    ? customTwoValue - 1 : customTwoValue];
            mCustomButtonTwo.setSummary(customTwoSummary);
            if (customTwoValue != 3) mCustomButtonTwo.setIcon(null);
            return true;
        } else if (preference == mCustomButtonThree) {
            CharSequence customThreeIndex[] = mCustomButtonThree.getEntries();
            int customThreeValue = Integer.parseInt((String) newValue);
            if (customThreeValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 7);
            }
            CharSequence customThreeSummary = customThreeIndex[mMaximize && customThreeValue > 1
                    ? customThreeValue - 1 : customThreeValue];
            mCustomButtonThree.setSummary(customThreeSummary);
            if (customThreeValue != 3) mCustomButtonThree.setIcon(null);
            return true;
        } else if (preference == mCustomButtonFour) {
            CharSequence customFourIndex[] = mCustomButtonFour.getEntries();
            int customFourValue = Integer.parseInt((String) newValue);
            if (customFourValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 8);
            }
            CharSequence customFourSummary = customFourIndex[mMaximize && customFourValue > 1
                    ? customFourValue - 1 : customFourValue];
            mCustomButtonFour.setSummary(customFourSummary);
            if (customFourValue != 3) mCustomButtonFour.setIcon(null);
            return true;
        } else if (preference == mCustomButtonFive) {
            CharSequence customFiveIndex[] = mCustomButtonFive.getEntries();
            int customFiveValue = Integer.parseInt((String) newValue);
            if (customFiveValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 9);
            }
            CharSequence customFiveSummary = customFiveIndex[mMaximize && customFiveValue > 1
                    ? customFiveValue - 1 : customFiveValue];
            mCustomButtonFive.setSummary(customFiveSummary);
            if (customFiveValue != 3) mCustomButtonFive.setIcon(null);
            return true;
        } else if (preference == mCustomButtonSix) {
            CharSequence customSixIndex[] = mCustomButtonSix.getEntries();
            int customSixValue = Integer.parseInt((String) newValue);
            if (customSixValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 10);
            }
            CharSequence customSixSummary = customSixIndex[mMaximize && customSixValue > 1
                    ? customSixValue - 1 : customSixValue];
            mCustomButtonSix.setSummary(customSixSummary);
            if (customSixValue != 3) mCustomButtonSix.setIcon(null);
            return true;
        } else if (preference == mCustomButtonSeven) {
            CharSequence customSevenIndex[] = mCustomButtonSeven.getEntries();
            int customSevenValue = Integer.parseInt((String) newValue);
            if (customSevenValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 11);
            }
            CharSequence customSevenSummary = customSevenIndex[mMaximize && customSevenValue > 1
                    ? customSevenValue - 1 : customSevenValue];
            mCustomButtonSeven.setSummary(customSevenSummary);
            if (customSevenValue != 3) mCustomButtonSeven.setIcon(null);
            return true;
        } else if (preference == mCustomButtonEight) {
            CharSequence customEightIndex[] = mCustomButtonEight.getEntries();
            int customEightValue = Integer.parseInt((String) newValue);
            if (customEightValue == 3) {
                // Pick an application
                Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
                mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY);
                pickIntent.putExtra(Intent.EXTRA_INTENT, mainIntent);
                startActivityForResult(pickIntent, 12);
            }
            CharSequence customEightSummary = customEightIndex[mMaximize && customEightValue > 1
                    ? customEightValue - 1 : customEightValue];
            mCustomButtonEight.setSummary(customEightSummary);
            if (customEightValue != 3) mCustomButtonEight.setIcon(null);
            return true;
        }
        return false;
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;
        if (preference == mSearchBar) {
            value = mSearchBar.isChecked();
            mCombinedBar.setEnabled(!value);
            mCenterAllApps.setEnabled(!value);
            if (value && (mSearchPosition.getValue().equals("1") ||
                    mSearchPosition.getValue().equals("2"))) {
                mAllAppsPosition.setEntries(
                        R.array.preferences_interface_search_button_bottom_entries);
                mAllAppsPosition.setEntryValues(
                        R.array.preferences_interface_search_button_bottom_values);
            } else if (value) {
                mAllAppsPosition.setEntries(
                        R.array.preferences_interface_search_button_top_entries);
                mAllAppsPosition.setEntryValues(
                        R.array.preferences_interface_search_button_top_values);
            } else {
                mAllAppsPosition.setEntries(R.array.preferences_interface_button_corner_entries);
                mAllAppsPosition.setEntryValues(R.array.preferences_interface_button_corner_values);
            }
            mCustomButtonSeven.setEnabled(!value && !mCombinedBar.isChecked());
            mCustomButtonEight.setEnabled(!value && !mCombinedBar.isChecked());
            if (value) {
                mCustomButtonSeven.setValueIndex(0);
                mCustomButtonEight.setValueIndex(0);
                mCombinedBar.setChecked(false);
            } else {
                mSearchPosition.setSummary("");
            }
            return true;
        } else if (preference == mAllAppsBar) {
            value = mCombinedBar.isChecked();
            mCustomButtonSeven.setEnabled(!value && !mSearchBar.isChecked());
            mCustomButtonEight.setEnabled(!value && !mSearchBar.isChecked());
            if (value) {
                mCustomButtonSeven.setValueIndex(0);
                mCustomButtonEight.setValueIndex(0);
            } else {
                mAllAppsPosition.setSummary("");
            }
            return true;
        } else if (preference == mCombinedBar) {
            value = mCombinedBar.isChecked();
            mCustomButtonSeven.setEnabled(!value && !mSearchBar.isChecked());
            mCustomButtonEight.setEnabled(!value && !mSearchBar.isChecked());
            if (value) {
                mCustomButtonSeven.setValueIndex(0);
                mCustomButtonEight.setValueIndex(0);
            }
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 5) {
                mPrefs.edit().putString("custom_application_one", data.toUri(0)).commit();
                try {
                    mCustomButtonOne.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 6) {
                mPrefs.edit().putString("custom_application_two", data.toUri(0)).commit();
                try {
                    mCustomButtonTwo.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 7) {
                mPrefs.edit().putString("custom_application_three", data.toUri(0)).commit();
                try {
                    mCustomButtonThree.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 8) {
                mPrefs.edit().putString("custom_application_four", data.toUri(0)).commit();
                try {
                    mCustomButtonFour.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 9) {
                mPrefs.edit().putString("custom_application_five", data.toUri(0)).commit();
                try {
                    mCustomButtonFive.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 10) {
                mPrefs.edit().putString("custom_application_six", data.toUri(0)).commit();
                try {
                    mCustomButtonSix.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 11) {
                mPrefs.edit().putString("custom_application_seven", data.toUri(0)).commit();
                try {
                    mCustomButtonSeven.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            } else if (requestCode == 12) {
                mPrefs.edit().putString("custom_application_eight", data.toUri(0)).commit();
                try {
                    mCustomButtonEight.setIcon(
                            getActivity().getPackageManager().getActivityIcon(data));
                } catch (Exception e) {
                }
            }
        }
    }

    public void onResume() {
        super.onResume();

        mMaximize = mPrefs.getBoolean("ui_homescreen_maximize", false);

        if (mSearchBar.isChecked()) {
            mCombinedBar.setEnabled(false);
            mCenterAllApps.setEnabled(false);
            mCustomButtonSeven.setEnabled(false);
            mCustomButtonSeven.setValueIndex(0);
            mCustomButtonEight.setEnabled(false);
            mCustomButtonEight.setValueIndex(0);
        }

        if (mCombinedBar.isChecked()) {
            mCustomButtonSeven.setEnabled(false);
            mCustomButtonSeven.setValueIndex(0);
            mCustomButtonEight.setEnabled(false);
            mCustomButtonEight.setValueIndex(0);
        }

        String searchValue = mSearchPosition.getValue();
        String allAppsValue = mAllAppsPosition.getValue();

        if (mSearchBar.isChecked() && (searchValue.equals("1") || searchValue.equals("2"))) {
            mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_bottom_entries);
            mAllAppsPosition.setEntryValues(
                    R.array.preferences_interface_search_button_bottom_values);
            if (allAppsValue.equals("0") || allAppsValue.equals("3")) {
                mAllAppsPosition.setValueIndex(0);
            }
        } else if (mSearchBar.isChecked()) {
            mAllAppsPosition.setEntries(R.array.preferences_interface_search_button_top_entries);
            mAllAppsPosition.setEntryValues(R.array.preferences_interface_search_button_top_values);
            if (allAppsValue.equals("1") || allAppsValue.equals("2")) {
                mAllAppsPosition.setValueIndex(0);
            }
        } else {
            mAllAppsPosition.setEntries(R.array.preferences_interface_button_corner_entries);
            mAllAppsPosition.setEntryValues(R.array.preferences_interface_button_corner_values);
        }

        mAllAppsPosition.setSummary(mAllAppsBar.isChecked() ? mAllAppsPosition.getEntry() : "");
        mSearchPosition.setSummary(mSearchBar.isChecked() ? mSearchPosition.getEntry() : "");

        if (mMaximize) {
            if (mCustomButtonOne.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_ONE, "0").commit();
            }
            if (mCustomButtonTwo.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_TWO, "0").commit();
            }
            if (mCustomButtonThree.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_THREE, "0").commit();
            }
            if (mCustomButtonFour.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_FOUR, "0").commit();
            }
            if (mCustomButtonFive.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_FIVE, "0").commit();
            }
            if (mCustomButtonSix.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_SIX, "0").commit();
            }
            if (mCustomButtonSeven.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_SEVEN, "0").commit();
            }
            if (mCustomButtonEight.getValue().equals("2")) {
                mPrefs.edit().putString(PreferenceSettings.CUSTOM_BUTTON_EIGHT, "0").commit();
            }
            mCustomButtonOne.setEntries(R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonOne.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
            mCustomButtonTwo.setEntries(
                    R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonTwo.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
            mCustomButtonThree.setEntries(R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonThree.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
            mCustomButtonFour.setEntries(R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonFour.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
            mCustomButtonFive.setEntries(R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonFive.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
            mCustomButtonSix.setEntries(R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonSix.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
            mCustomButtonSeven.setEntries(R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonSeven.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
            mCustomButtonEight.setEntries(R.array.preferences_interface_apps_bar_maximized_entries);
            mCustomButtonEight.setEntryValues(
                    R.array.preferences_interface_apps_bar_maximized_values);
        }

        mCustomButtonOne.setSummary(mCustomButtonOne.getEntry());
        if (mCustomButtonOne.getValue().equals("3")) {
            try {
                mCustomButtonOne.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_one", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonTwo.setSummary(mCustomButtonTwo.getEntry());
        if (mCustomButtonTwo.getValue().equals("3")) {
            try {
                mCustomButtonTwo.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_two", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonThree.setSummary(mCustomButtonThree.getEntry());
        if (mCustomButtonThree.getValue().equals("3")) {
            try {
                mCustomButtonThree.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_three", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonFour.setSummary(mCustomButtonFour.getEntry());
        if (mCustomButtonFour.getValue().equals("3")) {
            try {
                mCustomButtonFour.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_four", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonFive.setSummary(mCustomButtonFive.getEntry());
        if (mCustomButtonFive.getValue().equals("3")) {
            try {
                mCustomButtonFive.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_five", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonSix.setSummary(mCustomButtonSix.getEntry());
        if (mCustomButtonSix.getValue().equals("3")) {
            try {
                mCustomButtonSix.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_six", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonSeven.setSummary(mCustomButtonSeven.getEntry());
        if (mCustomButtonSeven.getValue().equals("3")) {
            try {
                mCustomButtonSeven.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_seven", ""), 0)));
            } catch (Exception e) {
            }
        }
        mCustomButtonEight.setSummary(mCustomButtonEight.getEntry());
        if (mCustomButtonEight.getValue().equals("3")) {
            try {
                mCustomButtonEight.setIcon(getActivity().getPackageManager().getActivityIcon(
                        Intent.parseUri(mPrefs.getString("custom_application_eight", ""), 0)));
            } catch (Exception e) {
            }
        }
    }

    public static void restore(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
