package com.project.mobileonline.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mobileonline.R;
import com.project.mobileonline.models.DrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyen Dinh Duc on 8/29/2015.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {
    ArrayList<DrawerItem> items;
    int layoutId;
    Context context;

    public DrawerAdapter(Context context, int resource, ArrayList<DrawerItem> objects) {
        super(context, resource, objects);
        this.context = context;
        layoutId = resource;
        items = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutId, null);
            viewHolder.section = (TextView) convertView.findViewById(R.id.section);
            viewHolder.contentLayout = (LinearLayout) convertView.findViewById(R.id.content_layout);
            viewHolder.item_icon = (ImageView) convertView.findViewById(R.id.item_icon_drawer);
            viewHolder.item_content = (TextView) convertView.findViewById(R.id.item_content_drawer);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();
        DrawerItem item = items.get(position);
        if (item.getIconRes() == 0) {
            viewHolder.contentLayout.setVisibility(View.GONE);
            viewHolder.section.setText(item.getTitle());
        } else {
            viewHolder.section.setVisibility(View.GONE);
            viewHolder.item_icon.setImageResource(item.getIconRes());
            viewHolder.item_content.setText(item.getTitle());
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (items.get(position).getIconRes() == 0) {
            return false;
        }
        return super.isEnabled(position);
    }

    private class ViewHolder {
        TextView section;
        LinearLayout contentLayout;
        ImageView item_icon;
        TextView item_content;
    }
}
