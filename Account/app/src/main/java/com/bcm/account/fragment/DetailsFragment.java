package com.bcm.account.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.adapter.DetailsAdapter;
import com.bcm.account.bmobbean.ABill;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.newsbean.DetailsBean;
import com.bcm.account.tools.DataCenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Bean on 2017/4/10.
 */

public class DetailsFragment extends Fragment {
    private View view;
    private ListView mListView;
    private DetailsAdapter adapter;
    private List<DetailsBean> detailsBeanList;
    // 模拟数据
    private String[] moneyWay = {"mIn", "mIn", "mIn", "mOut", "mOut", "mIn", "mIn", "mOut", "mOut", "mOut"};
    private String[] dayArray = {"10日", "10日", "10日", "11日", "11日", "11日", "13日", "13日", "13日", "13日"};
    // 计算钱数到小数点两位
    DecimalFormat df = new DecimalFormat("0.00");
    private TextView AllInTextView;
    private TextView AllOutTextView;
    private myUser user;
    private String user_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_details_fragment, null);
        bindView();
        user = myUser.getCurrentUser(myUser.class);
        user_id = user.getObjectId();
        getDateFromBmob();
        return view;
    }

    // 绑定数据源
    private void bindView() {
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setDividerHeight(0);
        AllInTextView = (TextView) view.findViewById(R.id.allInTextView);
        AllOutTextView = (TextView) view.findViewById(R.id.allOutTextView);
    }

    // 初始化数据
    private void initData() {
        detailsBeanList = new ArrayList<>();
        DetailsBean detailsBean;
        for (int i = 0; i < 10; i++) {
            detailsBean = new DetailsBean();
            detailsBean.way = moneyWay[i];
            detailsBean.day = dayArray[i];
            detailsBeanList.add(detailsBean);
        }
        showListView();
    }

    // showListView
    private void showListView() {
        if (adapter == null) {
            adapter = new DetailsAdapter(getActivity(), detailsBeanList);
            mListView.setAdapter(adapter);
        } else {
            adapter.changeDate(detailsBeanList);
        }

    }

    // 计算指定天数的收入钱数

    private void getCertainDayMoney() {
        float allInCount = 0;
        float allOutCount = 0;
        for (int i = 0; i < detailsBeanList.size(); i++) {
            // 计算总钱数
            float inCount = 0;
            float outCount = 0;
            String day = detailsBeanList.get(i).day;
            String allmoney = detailsBeanList.get(i).money;
            String alltype = detailsBeanList.get(i).way;
            float aMoney = Float.parseFloat(allmoney);
            if (alltype.equals("in")) {
                allInCount = allInCount + aMoney;
            } else if (alltype.equals("out")) {
                allOutCount = allOutCount + aMoney;
            }
            // 计算每天的钱数
            for (DetailsBean detailsBean : detailsBeanList) {

                String days = detailsBean.day;
                String money = detailsBean.money;
                String type = detailsBean.way;
                float dmoney = Float.parseFloat(money);
                if (days.equals(day)) {
                    if (type.equals("in")) {
                        inCount = inCount + dmoney;
                    } else if (type.equals("out")) {
                        outCount = outCount + dmoney;
                    }
                }
            }
            detailsBeanList.get(i).daysInMoney = df.format(inCount) + " 收入";
            detailsBeanList.get(i).daysOutMoney = "支出 "+ df.format(outCount) ;
        }
        AllInTextView.setText(df.format(allInCount));
        AllOutTextView.setText(df.format(allOutCount));

    }

    // 获取数据
    public  void getDateFromBmob() {
//        Toast.makeText(getActivity(),"获取数据",Toast.LENGTH_SHORT).show();
        BmobQuery<ABill> query = new BmobQuery("ABill");
        query.addWhereEqualTo("user_id",user_id);
        query.order("-createdAt");
//        query.order("-createdAt");
        detailsBeanList = new ArrayList<>();
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                // 没有异常
                if (e == null) {
//                    Log.i("message:",jsonArray.toString());
                    DetailsBean detailsBean;
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject bill = jsonArray.getJSONObject(i);
                                String money = bill.optString("bill_money");
                                String type = bill.optString("bill_type");
                                String dayStr = bill.optString("bill_date");
                                String logo = bill.optString("bill_logo");
                                detailsBean = new DetailsBean();
                                detailsBean.type = DataCenter.getImageUri(logo);
                                detailsBean.money = money;
                                detailsBean.way = type;
                                detailsBean.day = dayStr.substring(8, 10) + "日";
                                detailsBeanList.add(detailsBean);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        // 统计每天钱数
                        getCertainDayMoney();
                        // 展示ListView
                        showListView();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
