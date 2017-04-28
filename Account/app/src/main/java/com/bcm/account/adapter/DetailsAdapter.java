package com.bcm.account.adapter;

/**
 * Created by Bean on 2017/4/14.
 */


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcm.account.R;
import com.bcm.account.newsbean.DetailsBean;

import java.util.List;

public class DetailsAdapter extends BaseAdapter {
    private Context mContext;
    private List<DetailsBean> mList;

    public DetailsAdapter(Context context, List<DetailsBean> detailsBeanList) {
        this.mContext = context;
        this.mList = detailsBeanList;

    }

    public int getCount() {
        return mList.size() + 1;
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

    public void changeDate(List<DetailsBean> list){
        this.mList = list;
        this.notifyDataSetChanged();
    }

    public View getView(int position, View view, ViewGroup parent) {

        if (position < mList.size()) {
            ViewHolder viewHolder;

            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.details_item, null);
            viewHolder.mInLayout = (LinearLayout) view.findViewById(R.id.mInLayout);
            viewHolder.mOutLayout = (LinearLayout) view.findViewById(R.id.mOutLayout);
            viewHolder.moneyType = (ImageView) view.findViewById(R.id.moneyType);
            viewHolder.moneyInNum = (TextView) view.findViewById(R.id.mInTextView);
            viewHolder.moneyOutNum = (TextView) view.findViewById(R.id.mOutTextView);
            viewHolder.dayLayout = (LinearLayout) view.findViewById(R.id.dayLayout);
            viewHolder.dayInTextView = (TextView) view.findViewById(R.id.dayInText);
            viewHolder.dayOutTextView = (TextView) view.findViewById(R.id.dayOutText);
            viewHolder.day = (TextView) view.findViewById(R.id.day);
            // 设置
            String way = mList.get(position).way;
            Log.i("way", way);
            if (way.equals("in")) {
                viewHolder.moneyOutNum.setVisibility(View.INVISIBLE);
                viewHolder.moneyType.setImageResource(mList.get(position).type);
                viewHolder.moneyInNum.setText(mList.get(position).money+" 收入");
            } else if (way.equals("out")) {
                viewHolder.moneyInNum.setVisibility(View.INVISIBLE);
                viewHolder.moneyType.setImageResource(mList.get(position).type);
                viewHolder.moneyOutNum.setText("支出 "+mList.get(position).money);
            }
            // 是否需要日期
            if(NeedTitle(position)){
                viewHolder.dayLayout.setVisibility(View.VISIBLE);
                viewHolder.day.setText(mList.get(position).day);
            }else{
                viewHolder.dayLayout.setVisibility(View.GONE);
            }
            viewHolder.dayInTextView.setText(mList.get(position).daysInMoney);
            viewHolder.dayOutTextView.setText(mList.get(position).daysOutMoney);
            //
            return view;
        } else {
            // 返回底部布局
            View bottom = LayoutInflater.from(mContext).inflate(R.layout.details_bottom, null);
            return bottom;

        }


    }

    // 是否需要Title
    private boolean NeedTitle(int position) {
        if (position == 0) {
            return true;
        }
        // 异常处理
        if (position < 0) {
            return false;
        }
        String Cur = mList.get(position).day;
        String Pre = mList.get(position - 1).day;
        if (Cur == null || Pre == null) {
            return false;
        }
        if (Cur.equals(Pre)) {
            return false;
        }
        return true;
    }

    private class ViewHolder {
        // 钱数
        private TextView moneyInNum;
        private TextView moneyOutNum;
        // 收入Layout
        private LinearLayout mInLayout;
        // 支出Layout
        private LinearLayout mOutLayout;
        // 类型
        private ImageView moneyType;
        // 日期Layout
        private LinearLayout dayLayout;
        // 日期
        private TextView day;
        // 单日收入
        private TextView dayInTextView;
        private TextView dayOutTextView;
    }
}
