package com.bcm.account.adapter;

/**
 * Created by Bean on 2017/5/4.
 */


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.account.R;
import com.bcm.account.newsbean.TransBean;

import java.util.List;

public class TransAdapter extends BaseAdapter {
    private Context mContext;
    private List<TransBean> mList;

    public TransAdapter(Context context, List<TransBean> iconBeanList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.trans_item, null);
            viewHolder.TransDay = (TextView) view.findViewById(R.id.transDay);
            viewHolder.TransMonthLayout = (LinearLayout) view.findViewById(R.id.transMonthLayout);
            viewHolder.TransLogo = (ImageView) view.findViewById(R.id.transLogo);
            viewHolder.TransMonth = (TextView) view.findViewById(R.id.transMonth);
            viewHolder.TransMonthIn = (TextView) view.findViewById(R.id.transMonthIn);
            viewHolder.TransMonthOut = (TextView) view.findViewById(R.id.transMonthOut);
            viewHolder.TransType = (TextView) view.findViewById(R.id.transType);
            viewHolder.TransIntro = (TextView) view.findViewById(R.id.transIntro);
            viewHolder.TransMoney = (TextView) view.findViewById(R.id.transMoney);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // 是否需要月份
        if (NeedMonth(position)) {
            viewHolder.TransMonthLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.TransMonthLayout.setVisibility(View.GONE);
        }
        // 是否需要天
        if (NeedDay(position)) {
            viewHolder.TransDay.setVisibility(View.VISIBLE);
        } else {
            viewHolder.TransDay.setVisibility(View.GONE);
        }

        viewHolder.TransMonth.setText(mList.get(position).trans_month+"月");
        viewHolder.TransType.setText(mList.get(position).trans_name);
        String remark = mList.get(position).trans_remark;
        if(remark.equals("")){
            viewHolder.TransIntro.setVisibility(View.GONE);
        }else{
            viewHolder.TransIntro.setText(remark);
        }
        viewHolder.TransLogo.setImageResource(mList.get(position).trans_logo_int);
        String way = mList.get(position).trans_way;
        if(way.equals("in")){
            viewHolder.TransMoney.setTextColor(Color.parseColor("#4183d7"));
            viewHolder.TransMoney.setText("+"+mList.get(position).trans_money);
        }else if(way.equals("out")){
            viewHolder.TransMoney.setTextColor(Color.parseColor("#ea5514"));
            viewHolder.TransMoney.setText("-"+mList.get(position).trans_money);
        }
        // 设置当月的收入和支出
        viewHolder.TransMonthIn.setText("收入: "+mList.get(position).trans_month_in);
        viewHolder.TransMonthOut.setText("支出: "+mList.get(position).trans_month_out);
        // 设置天数
        viewHolder.TransDay.setText(mList.get(position).trans_day);
        return view;
    }

    // 是否需要Month
    private boolean NeedMonth(int position) {
        if (position == 0) {
            return true;
        }
        // 异常处理
        if (position < 0) {
            return false;
        }
        String Cur = mList.get(position).trans_month;
        String Pre = mList.get(position - 1).trans_month;
        if (Cur == null || Pre == null) {
            return false;
        }
        if (Cur.equals(Pre)) {
            return false;
        }
        return true;
    }

    // 是否需要Day
    private boolean NeedDay(int position) {
        if (position == 0) {
            return true;
        }
        // 异常处理
        if (position < 0) {
            return false;
        }
        String Cur = mList.get(position).trans_day;
        String Pre = mList.get(position - 1).trans_day;
        if (Cur == null || Pre == null) {
            return false;
        }
        if (Cur.equals(Pre)) {
            return false;
        }
        return true;
    }


    private class ViewHolder {
        private TextView TransDay;
        private LinearLayout TransMonthLayout;
        private TextView TransMonth;
        private TextView TransMonthIn,TransMonthOut;
        private TextView TransType,TransIntro;
        private ImageView TransLogo;
        private TextView TransMoney;
    }
}