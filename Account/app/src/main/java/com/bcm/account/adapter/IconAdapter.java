package com.bcm.account.adapter;

/**
 * Created by Bean on 2017/4/25.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcm.account.R;
import com.bcm.account.newsbean.IconBean;

import java.util.List;

public class IconAdapter extends BaseAdapter {
    private Context mContext;
    private List<IconBean> mList;

    public IconAdapter(Context context, List<IconBean> iconBeanList) {
        this.mContext = context;
        this.mList = iconBeanList;

    }

    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.icon_item, null);
            viewHolder.IconImg = (ImageView) view.findViewById(R.id.iconImg);
            viewHolder.IconText = (TextView) view.findViewById(R.id.iconText);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.IconImg.setImageResource(mList.get(position).icon);
        viewHolder.IconText.setText(mList.get(position).name);
        return view;
    }


    private class ViewHolder {
        private TextView IconText;
        private ImageView IconImg;
    }
}