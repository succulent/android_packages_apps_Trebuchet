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

package com.cyanogenmod.trebuchet;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.cyanogenmod.trebuchet.preference.PreferencesProvider;

public class Hotseat extends FrameLayout {
    private static final int CHILDREN_OUTLINE_FADE_OUT_DURATION = 375;
    private static final int CHILDREN_OUTLINE_FADE_IN_DURATION = 100;

    private static int sAllAppsButtonRank = 2; // In the middle of the dock

    private Launcher mLauncher;
    private CellLayout mContent;

    private int mCellCountX;
    private int mCellCountY;
    private boolean mIsLandscape;
    private boolean mIsScreenLarge;
    private static boolean mShowAllAppsHotseat;
    private int mPadding;
    private boolean mTopPadding;
    private boolean mShowHotseat;

    // These animators are used to fade the children's outlines
    private ObjectAnimator mChildrenOutlineFadeInAnimation;
    private ObjectAnimator mChildrenOutlineFadeOutAnimation;
    private float mChildrenOutlineAlpha = 0;

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
        mIsLandscape = context.getResources().getConfiguration().orientation ==
            Configuration.ORIENTATION_LANDSCAPE;
        mIsScreenLarge = LauncherApplication.isScreenLarge();
        mShowAllAppsHotseat =
                PreferencesProvider.Interface.Homescreen.getShowAllAppsHotseat(context);
        mShowHotseat =
                PreferencesProvider.Interface.Homescreen.getShowHotseat(context);

        // Padding at the top or the bottom depending on the location of the search drop target bar
        int searchCorner = PreferencesProvider.Interface.Tablet.getSearchBarCorner(context);
        int appsCorner = PreferencesProvider.Interface.Tablet.getAllAppsBarCorner(context);
        boolean showSearch = PreferencesProvider.Interface.Homescreen.getShowSearchBar(context);
        mTopPadding = (showSearch && (searchCorner == 0 || searchCorner == 3)) || (!showSearch &&
                (appsCorner == 0 || appsCorner == 3));
        mPadding = getResources().getDimensionPixelSize(R.dimen.qsb_bar_height);

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
        return mIsLandscape ? (mContent.getCountY() - y - 1) : x;
    }

    /* Get the orientation specific coordinates given an invariant order in the hotseat. */
    int getCellXFromOrder(int rank) {
        return mIsLandscape ? 0 : rank;
    }

    int getCellYFromOrder(int rank) {
        return mIsLandscape ? (mContent.getCountY() - (rank + 1)) : 0;
    }

    // Get the apps button rank if we are showing it on the hotseat
    public static boolean isAllAppsButtonRank(int rank) {
        return mShowAllAppsHotseat ? rank == sAllAppsButtonRank : false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mCellCountX < 0) mCellCountX = LauncherModel.getCellCountX();
        if (mCellCountY < 0) mCellCountY = LauncherModel.getCellCountY();

        // Get cell count from the preferences
        int prefCount = PreferencesProvider.Interface.Homescreen.getHotseatApps(mContext);

        // Get position of all apps in hotseat from preferences
        int allAppsRank =
                PreferencesProvider.Interface.Homescreen.getHotseatAllAppsPosition(mContext);
        if (allAppsRank == 0) sAllAppsButtonRank = (int) prefCount / 2;
        else sAllAppsButtonRank = allAppsRank - 1;

        // Set the cell count vertically in landscape, horizontally in portrait
        if (mIsLandscape) mCellCountY = prefCount;
        else mCellCountX = prefCount;

        mContent = (CellLayout) findViewById(R.id.layout);
        mContent.setGridSize(mCellCountX, mCellCountY);

        // Add padding in landscape large screen mode for the search drop target bar
        if (mIsLandscape && mIsScreenLarge) {
            this.setPadding(0, mTopPadding ? mPadding : 0, 0, mTopPadding ? 0 : mPadding);
        }

        // Set the visibility of the hotseat if it is hidden
        if (!mShowHotseat) this.setVisibility(View.INVISIBLE);
    }

    void resetLayout() {
        mContent.removeAllViewsInLayout();

        // Add the Apps button
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        BubbleTextView allAppsButton = (BubbleTextView)
                inflater.inflate(R.layout.application, mContent, false);
        allAppsButton.setCompoundDrawablesWithIntrinsicBounds(null,
                context.getResources().getDrawable(mIsScreenLarge ?
                R.drawable.all_apps_button_icon_large :
                R.drawable.all_apps_button_icon), null, null);
        if (mIsLandscape && mIsScreenLarge) {
            allAppsButton.setPadding(0, 10, 0, 0);
        }
        allAppsButton.setContentDescription(context.getString(R.string.all_apps_button_label));
        allAppsButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mLauncher != null &&
                    (event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
                    mLauncher.onTouchDownAllAppsButton(v);
                }
                return false;
            }
        });

        allAppsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (mLauncher != null) {
                    mLauncher.onClickAllAppsButton(v);
                }
            }
        });

        // Note: We do this to ensure that the hotseat is always laid out in the orientation of
        // the hotseat in order regardless of which orientation they were added
        int x = getCellXFromOrder(sAllAppsButtonRank);
        int y = getCellYFromOrder(sAllAppsButtonRank);
        mContent.addViewToCellLayout(allAppsButton, -1, 0, new CellLayout.LayoutParams(x,y,1,1),
                true);
    }

    // Show outline when dragging a drop target
    void showOutlines() {
        if (mChildrenOutlineFadeOutAnimation != null) mChildrenOutlineFadeOutAnimation.cancel();
        if (mChildrenOutlineFadeInAnimation != null) mChildrenOutlineFadeInAnimation.cancel();
        mChildrenOutlineFadeInAnimation =
                ObjectAnimator.ofFloat(this, "childrenOutlineAlpha", 1.0f);
        mChildrenOutlineFadeInAnimation.setDuration(CHILDREN_OUTLINE_FADE_IN_DURATION);
        mChildrenOutlineFadeInAnimation.start();
    }

    // Hide outline after finishing dragging a drop target
    void hideOutlines() {
        if (mChildrenOutlineFadeInAnimation != null) mChildrenOutlineFadeInAnimation.cancel();
        if (mChildrenOutlineFadeOutAnimation != null) mChildrenOutlineFadeOutAnimation.cancel();
        mChildrenOutlineFadeOutAnimation =
                ObjectAnimator.ofFloat(this, "childrenOutlineAlpha", 0.0f);
        mChildrenOutlineFadeOutAnimation.setDuration(CHILDREN_OUTLINE_FADE_OUT_DURATION);
        mChildrenOutlineFadeOutAnimation.setStartDelay(0);
        mChildrenOutlineFadeOutAnimation.start();
    }

    public void setChildrenOutlineAlpha(float alpha) {
        mChildrenOutlineAlpha = alpha;
        mContent.setBackgroundAlpha(alpha);
    }

    public float getChildrenOutlineAlpha() {
        return mChildrenOutlineAlpha;
    }
}
