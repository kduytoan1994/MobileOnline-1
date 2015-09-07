package com.project.mobileonline.models;

import android.widget.TextView;

/**
 * Created by Nguyen Dinh Duc on 8/28/2015.
 */
public class DrawerItem {
    int iconRes;
    String title;

    public DrawerItem(int iconRes, String title) {
        this.iconRes = iconRes;
        this.title = title;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
