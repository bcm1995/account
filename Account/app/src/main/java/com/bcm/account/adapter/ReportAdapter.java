package com.bcm.account.adapter;

/**
 * Created by Bean on 2017/4/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcm.account.R;
import com.bcm.account.newsbean.ReportBean;

import java.text.DecimalFormat;
import java.util.List;

public class ReportAdapter extends BaseAdapter {
    private Context mContext;
    private List<ReportBean> mList;
    // 计算钱数到小数点两位
    DecimalFormat df = new DecimalFormat("0.00");
    public ReportAdapter(Context context, List<ReportBean> detailsBeanList) {
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

    public void dataChange(List<ReportBean> lists){
        this.mList = lists;
        this.notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.report_item, null);
            viewHolder.rMoney = (TextView) view.findViewById(R.id.rMoney);
            viewHolder.rMoneyRate = (TextView) view.findViewById(R.id.rMoneyRate);
            viewHolder.rLogo = (ImageView) view.findViewById(R.id.rLogo);
            viewHolder.rWay = (TextView) view.findViewById(R.id.rWay);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
            viewHolder.rMoneyRate.setText(mList.get(position).rRate);
            viewHolder.rMoney.setText(df.format(mList.get(position).rMoney)+"");
            viewHolder.rLogo.setImageResource(mList.get(position).rLogo);
            viewHolder.rWay.setText(mList.get(position).rWay);
        return view;
    }


    private class ViewHolder {
        private TextView rMoneyRate;
        private  TextView rMoney;
        private ImageView rLogo;
        private TextView rWay;
     }
}
