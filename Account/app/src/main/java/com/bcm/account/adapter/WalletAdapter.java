package com.bcm.account.adapter;

/**
 * Created by Bean on 2017/4/14.
 */


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.account.R;
import com.bcm.account.newsbean.WalletBean;

import java.util.List;

public class WalletAdapter extends BaseAdapter {
    private Context mContext;
    private List<WalletBean> mList;

    public WalletAdapter(Context context, List<WalletBean> detailsBeanList) {
        this.mContext = context;
        this.mList = detailsBeanList;

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


    // 改变数据源

    public void changeDate(List<WalletBean> list){
        this.mList = list;
        this.notifyDataSetChanged();
    }


    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.wallet_item, null);
            viewHolder.walletLeft = (TextView) view.findViewById(R.id.walletLeft);
            viewHolder.wallerRightLayout = (LinearLayout) view.findViewById(R.id.walletRight);
            viewHolder.walletMainLayout = (LinearLayout) view.findViewById(R.id.walletMain);
            viewHolder.walletType = (TextView) view.findViewById(R.id.walletType);
            viewHolder.walletIntro = (TextView) view.findViewById(R.id.walletIntro);
            viewHolder.walletLogo = (ImageView) view.findViewById(R.id.walletLogo);
            viewHolder.walletMoney = (TextView) view.findViewById(R.id.walletMoney);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.walletType.setText(mList.get(position).type);
        viewHolder.walletIntro.setText(mList.get(position).intro);
        // 设置左侧颜色
        GradientDrawable leftColor = (GradientDrawable)
                viewHolder.walletLeft.getBackground();
        String tagLeft = mList.get(position).leftColor;
        leftColor.setColor(Color.parseColor(tagLeft));
        // 设置右侧颜色
        GradientDrawable rightColor = (GradientDrawable)
                viewHolder.wallerRightLayout.getBackground();
        String tagRight = mList.get(position).rightColor;
        rightColor.setColor(Color.parseColor(tagRight));
        // 设置Logo
        viewHolder.walletLogo.setImageResource(mList.get(position).Logo);
        viewHolder.walletMoney.setText(mList.get(position).money);
        return view;
    }


    private class ViewHolder {
        private LinearLayout walletMainLayout;
        private LinearLayout wallerRightLayout;
        private TextView walletLeft;
        private TextView walletType;
        private TextView walletIntro;
        private ImageView walletLogo;
        private TextView walletMoney;
    }
}
