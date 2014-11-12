package com.sectionedactionbarlist;

import android.content.Context;

/**
 * @author vgrec, created on 10/15/14.
 */
public class ListConfiguration {
    private int titleColorRes;
    private int actionBarItemColor;
    private int indicator;
    private Context context;
    private int stateActivated;
    private int stateNormal;

    public ListConfiguration(Context context) {
        this.context = context;
        // Default configurations
        titleColorRes = getColorRes(R.color.dark_grey);
        actionBarItemColor = getColorRes(R.color.white);
        indicator = R.drawable.spinner_indicator;
        stateActivated = getColorRes(R.color.indigo);
        stateNormal = getColorRes(R.color.black);
    }

    private int getColorRes(int colorRes) {
        return context.getResources().getColor(colorRes);
    }

    public void setSectionTitleColorResource(int titleColorRes) {
        this.titleColorRes = getColorRes(titleColorRes);
    }

    public void setActionBarItemColorResource(int actionBarItemColor) {
        this.actionBarItemColor = getColorRes(actionBarItemColor);
    }

    public int getSectionTitleColorResource() {
        return titleColorRes;
    }

    public int getActionBarItemColorResource() {
        return actionBarItemColor;
    }

    public void setIndicatorDrawableResource(int indicator) {
        this.indicator = indicator;
    }

    public int getIndicatorDrawableResource() {
        return indicator;
    }

    public void setDropdownItemColorResources(int stateActivated, int stateNormal) {
        this.stateActivated = getColorRes(stateActivated);
        this.stateNormal = getColorRes(stateNormal);
    }

    public int getStateActivatedColor() {
        return stateActivated;
    }

    public int getStateNormalColor() {
        return stateNormal;
    }
}
