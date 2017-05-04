package com.bcm.account.adapter;

/**
 * Created by Bean on 2017/5/4.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcm.account.R;
import com.bcm.account.newsbean.IoBean;

import java.util.List;

public class IoAdapter extends BaseAdapter {
    private Context mContext;
    private List<IoBean> mList;

    public IoAdapter(Context context, List<IoBean> iconBeanList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.io_item, null);
            viewHolder.IoLogo = (ImageView) view.findViewById(R.id.ioLogo);
            viewHolder.IoMoney = (TextView) view.findViewById(R.id.ioMoney);
            viewHolder.IoName = (TextView) view.findViewById(R.id.ioName);
            viewHolder.IoRemark = (TextView) view.findViewById(R.id.ioRemark);
            viewHolder.IoTitle = (TextView) view.findViewById(R.id.ioTitle);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.IoTitle.setText(mList.get(position).io_date);
        viewHolder.IoMoney.setText(mList.get(position).io_money);
        viewHolder.IoName.setText(mList.get(position).io_name);
        viewHolder.IoRemark.setText(mList.get(position).io_remark);
        String remark = mList.get(position).io_remark;
        if (remark == null || remark.equals("")) {
            viewHolder.IoRemark.setVisibility(View.GONE);
        }else {
            viewHolder.IoRemark.setVisibility(View.VISIBLE);
        }
        if(NeedTitle(position)){
            viewHolder.IoTitle.setVisibility(View.VISIBLE);
        }else{
            viewHolder.IoTitle.setVisibility(View.GONE);
        }
        String type = mList.get(position).io_type;
        if(type.equals("join")){
            viewHolder.IoLogo.setImageResource(R.mipmap.icon_money_in);
        }else if(type.equals("loan")){
            viewHolder.IoLogo.setImageResource(R.mipmap.icon_money_out);
        }
//            viewHolder.IoLogo.setImageResource(mList.get(position).io_img_int);
        return view;
    }


    private class ViewHolder {
        private TextView IoTitle, IoName, IoRemark, IoMoney;
        private ImageView IoLogo;
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
        String Cur = mList.get(position).io_date;
        String Pre = mList.get(position - 1).io_date;
        if (Cur == null || Pre == null) {
            return false;
        }
        if (Cur.equals(Pre)) {
            return false;
        }
        return true;
    }
}