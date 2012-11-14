/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.sbradymobile.launchhome;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.sbradymobile.launchhome.R;
import com.sbradymobile.launchhome.preference.PreferencesProvider;

public class Hotseat extends FrameLayout {
    @SuppressWarnings("unused")
    private static final String TAG = "Hotseat";

    private Launcher mLauncher;
    private CellLayout mContent;

    private int mCellCountX;
    private int mCellCountY;
    private boolean mShowHotseat;
    private boolean mShowLandRightDock;

    private Context mContext;

    public Hotseat(Context context) {
        this(context, null);
    }

    public Hotseat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Hotseat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Hotseat, defStyle, 0);
        mCellCountX = a.getInt(R.styleable.Hotseat_cellCountX, -1);
        mCellCountY = a.getInt(R.styleable.Hotseat_cellCountY, -1);
        mShowHotseat =
                PreferencesProvider.Interface.Dock.getShowHotseat(context);
        mShowLandRightDock =
                PreferencesProvider.Interface.Dock.getShowLandRightDock(context) &&
                context.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;

        mContext = context;
    }

    public void setup(Launcher launcher) {
        mLauncher = launcher;
        setOnKeyListener(new HotseatIconKeyEventListener());
    }

    CellLayout getLayout() {
        return mContent;
    }

    /* Get the orientation invariant order of the item in the hotseat for persistence. */
    int getOrderInHotseat(int x, int y) {
        return mShowLandRightDock ? (mContent.getCountY() - y - 1) : x;
    }
    /* Get the orientation specific coordinates given an invariant order in the hotseat. */
    int getCellXFromOrder(int rank) {
        return mShowLandRightDock ? 0 : rank;
    }
    int getCellYFromOrder(int rank) {
        return mShowLandRightDock ? (mContent.getCountY() - (rank + 1)) : 0;
    }
    public boolean isAllAppsButtonRank(int rank) {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mCellCountX < 0) mCellCountX = LauncherModel.getCellCountX();
        if (mCellCountY < 0) mCellCountY = LauncherModel.getCellCountY();

        // Get cell count from the preferences
        int prefCount = PreferencesProvider.Interface.Dock.getHotseatApps(mContext);

        if (mShowLandRightDock) mCellCountY = prefCount;
        else mCellCountX = prefCount;

        mContent = (CellLayout) findViewById(R.id.layout);
        mContent.setGridSize(mCellCountX, mCellCountY);
        mContent.setIsHotseat(true);

        // Set the visibility of the hotseat if it is hidden
        if (!mShowHotseat) this.setVisibility(View.GONE);

        resetLayout();
    }

    void resetLayout() {
        mContent.removeAllViewsInLayout();
    }
}
