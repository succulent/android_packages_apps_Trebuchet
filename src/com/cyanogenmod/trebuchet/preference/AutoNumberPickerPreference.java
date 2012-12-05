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
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.cyanogenmod.trebuchet.R;

public class AutoNumberPickerPreference extends DialogPreference implements
            View.OnClickListener {
    private int mMin, mMax, mDefault;

    private String mMaxExternalKey, mMinExternalKey;

    private NumberPicker mNumberPicker;
    private CheckBox mCheckBox;

    private Context mContext;

    public AutoNumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray dialogType = context.obtainStyledAttributes(attrs,
                com.android.internal.R.styleable.DialogPreference, 0, 0);
        TypedArray numberPickerType = context.obtainStyledAttributes(attrs,
                R.styleable.NumberPickerPreference, 0, 0);

        mMaxExternalKey = numberPickerType.getString(R.styleable.NumberPickerPreference_maxExternal);
        mMinExternalKey = numberPickerType.getString(R.styleable.NumberPickerPreference_minExternal);

        mMax = numberPickerType.getInt(R.styleable.NumberPickerPreference_max, 5);
        mMin = numberPickerType.getInt(R.styleable.NumberPickerPreference_min, 0);

        mDefault = dialogType.getInt(com.android.internal.R.styleable.Preference_defaultValue, mMin);

        dialogType.recycle();
        numberPickerType.recycle();
    }

    @Override
    protected View onCreateDialogView() {
        int max = mMax;
        int min = mMin;

        // External values
        if (mMaxExternalKey != null) {
            max = getSharedPreferences().getInt(mMaxExternalKey, mMax);
        }
        if (mMinExternalKey != null) {
            min = getSharedPreferences().getInt(mMinExternalKey, mMin);
        }

        LayoutInflater inflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.auto_number_picker_dialog, null);

        mNumberPicker = (NumberPicker) view.findViewById(R.id.auto_number_picker);

        if (mNumberPicker == null) {
            throw new RuntimeException("mNumberPicker is null!");
        }

        // Initialize state
        mNumberPicker.setWrapSelectorWheel(false);
        mNumberPicker.setMaxValue(max);
        mNumberPicker.setMinValue(min);
        mNumberPicker.setValue(getPersistedInt(mDefault == 0 ? min : mDefault));

        // No keyboard popup
        EditText textInput = (EditText) mNumberPicker.findViewById(com.android.internal.R.id.numberpicker_input);
        if (textInput != null) {
            textInput.setCursorVisible(false);
            textInput.setFocusable(false);
            textInput.setFocusableInTouchMode(false);
        }

        mCheckBox = (CheckBox) view.findViewById(R.id.auto_check_box);
        mCheckBox.setChecked(getPersistedInt(mDefault) < mMin ? true : mNumberPicker.getValue() == mDefault);
        mCheckBox.setOnClickListener(this);
        if (mCheckBox.isChecked()) mNumberPicker.setEnabled(false);

        return view;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            persistInt(mCheckBox.isChecked() ? mDefault : mNumberPicker.getValue());
        }
        if (positiveResult) {
            this.setSummary(mCheckBox.isChecked() ?
                    mContext.getString(R.string.preferences_auto_number_picker) :
                    Integer.toString(mNumberPicker.getValue()));
        }
    }

    @Override
    public void onClick(View v) {
        if (mCheckBox.isChecked()) {
            mNumberPicker.setValue(mDefault);
            mNumberPicker.setEnabled(false);
        } else {
            mNumberPicker.setEnabled(true);
        }
    }

    public void setMaxValue(int max) {
        mMax = max;
    }

    public void setMinValue(int min) {
        mMin = min;
    }

    public int getMaxValue() {
        return mMax;
    }
}
