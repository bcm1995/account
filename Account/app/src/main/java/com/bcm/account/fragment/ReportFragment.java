package com.bcm.account.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.adapter.ReportAdapter;
import com.bcm.account.bmobbean.ABill;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.newsbean.DetailsBean;
import com.bcm.account.newsbean.ReportBean;
import com.bcm.account.tools.DataCenter;
import com.bcm.account.tools.SleepListView;
import com.bcm.account.tools.TimeCenter;
import com.bcm.account.tools.MonPickerDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Bean on 2017/4/10.
 */

public class ReportFragment extends Fragment {
    private View view;
    private PieChart mChart;
    // 图标参数值
    private Float Lost = 12.0f;
    private Float Find = 10.0f;
    private Float Lost_Find = 11.0f;
    //各个值对应的属性
    private Float Attitude = 240.0f;
    private Float Ability = 280.f;
    private Float Knowledge = 300.0f;
    private Float Exp = 720.0f;
    private Float Character = 85.0f;
    private Float Honesty = 93.0f;
    private Float Total;
    // SleepView
    private SleepListView mSleepView;
    private ReportAdapter adapter;
    //
    private String[] colorArray = {"#47A7E6", "#5DBF92", "#FD557D", "#B5BA3E", "#9659AE", "#47A7E6", "#5DBF92", "#FD557D", "#B5BA3E", "#9659AE", "#47A7E6", "#5DBF92", "#FD557D", "#B5BA3E", "#9659AE"};
    //    private float[] moneyArray = {14.0f,16.0f,18.0f,19.0f,55.0f};
    private int[] logoArray = {R.mipmap.icon_water, R.mipmap.icon_vegetable, R.mipmap.icon_cloth, R.mipmap.icon_food, R.mipmap.icon_shoe};
    private String[] wayArray = {"水费", "蔬菜", "衣服", "食物", "鞋子"};
    // detailsBean
    private List<DetailsBean> detailsBeanList;
    // 图片点击
    private TextView InOutText;
    private String nowType = "in";
    private String nowMonth = "";
    private TextView nowMonthTextView;
    private String user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_report_fragment, null);
        bindView();
        user_id = myUser.getCurrentUser(myUser.class).getObjectId();
        // 设置当前月份
        String nowDate = TimeCenter.getCurrenceDate();
        nowMonth = nowDate.substring(5,7);
        nowMonthTextView.setText(nowDate.substring(0,7));
        nowMonthTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectMonthTime();
            }
        });
        getDataFromBmob();
        InOutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nowType.equals("in")){
                    nowType = "out";
                }else if(nowType.equals("out")){
                    nowType = "in";
                }
                // 获取数据
                getDataFromBmob();
            }
        });

        return view;
    }

    // 绑定数据源
    private void bindView() {
        mChart = (PieChart) view.findViewById(R.id.pieChart);
        mSleepView = (SleepListView) view.findViewById(R.id.mSleepView);
        InOutText = (TextView) view.findViewById(R.id.inOutText);
        nowMonthTextView = (TextView) view.findViewById(R.id.nowMonthText);
    }

    // showListView
    private void showListView(List<ReportBean> lists) {
        if(adapter == null){
            adapter = new ReportAdapter(getActivity(), lists);
            mSleepView.setAdapter(adapter);
        }else{
            adapter.dataChange(lists);
        }
    }

    /**
     * 时间选择器
     */

    private void selectMonthTime() {
        final Calendar calendar = Calendar.getInstance();
        new MonPickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                String dateStr = TimeCenter.clanderTodatetime(calendar, "yyyy-MM");
                nowMonthTextView.setText(dateStr);
                nowMonth = dateStr.substring(5,7);
                getDataFromBmob();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();

    }

    // 获取比例

    private String getRate(float all , int i,List<ReportBean> lists) {
        float rate = 0.0f;
        rate = lists.get(i).rMoney/all;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(rate * 100) + "%";//format 返回的是字符串
        return p;
    }

    // 获取全部
//
    private float getAll(List<ReportBean> moneyArrayList){
        float all = 0.0f;
        for(int j = 0 ; j < moneyArrayList.size() ; j++){
            all = all + moneyArrayList.get(j).rMoney;
        }
        return all;
    }

    // 从Bmob中获取指定月份的数据

    public void getDataFromBmob() {
        BmobQuery<ABill> query = new BmobQuery("ABill");
        query.addWhereEqualTo("bill_type",nowType);
        query.addWhereEqualTo("bill_month",nowMonth);
        query.addWhereEqualTo("user_id",user_id);
        detailsBeanList = new ArrayList<>();
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    if (jsonArray.length() > 0) {
                        DetailsBean detailsBean;
                        // 装载数据
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject bill = null;
                            try {
                                bill = jsonArray.getJSONObject(i);
                                String money = bill.optString("bill_money");
                                String type = bill.optString("bill_type");
                                String dayStr = bill.optString("bill_date");
                                String logo = bill.optString("bill_logo");
                                detailsBean = new DetailsBean();
                                detailsBean.logo = logo;
                                detailsBean.money = money;
                                detailsBean.way = type;
                                detailsBean.day = dayStr;
                                detailsBeanList.add(detailsBean);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }

                    }
                } else {
                    Log.i("Error:", e.getMessage());
                    Toast.makeText(getActivity(), "错误", Toast.LENGTH_SHORT).show();
                }
                // 对数据进行分类
                sortData(detailsBeanList);
            }
        });
    }

    // 数据分类

    private void sortData(List<DetailsBean> detailsBeanList) {
        // 对不同的类型进行挑选
//        HashSet<String> hs = new HashSet<String>(detailsBeanList); //此时已经去掉重复的数据保存在hashset中
        List<String> logoList = new ArrayList<>();
        for (int i = 0; i < detailsBeanList.size(); i++) {
            logoList.add(detailsBeanList.get(i).logo);
        }
        HashSet<String> hs = new HashSet<String>(logoList);
        Log.i("set-size:", hs.size() + "");
        List<String> reList = new ArrayList<>(hs);
        List<ReportBean> logoListSort = new ArrayList<>();
        ReportBean reportBean;
        for(int i = 0 ; i < reList.size();i++){
            reportBean = new ReportBean();
            reportBean.rLogoStr = reList.get(i);
            reportBean.rWay = DataCenter.getImageText(reList.get(i));
            reportBean.rColor = DataCenter.getImageColor(reList.get(i));
            reportBean.rLogo = DataCenter.getImageUri(reList.get(i));
            reportBean.rMoney = 0 ;
            reportBean.rRate = "1";
            logoListSort.add(reportBean);
        }
        // 根据类型计算出每一种类型的钱数
        for (int i = 0; i < detailsBeanList.size(); i++) {
            String money = detailsBeanList.get(i).money;
            Float moneyF = Float.parseFloat(money);
            // 遍历报表集合
            for (int j = 0; j < logoListSort.size(); j++) {
                Float rMoney = logoListSort.get(j).rMoney;
                if (detailsBeanList.get(i).logo.equals(logoListSort.get(j).rLogoStr)) {
                    logoListSort.get(j).rMoney = rMoney + moneyF;
                }
            }
        }
        // 总钱数
        float all = getAll(logoListSort);
        // 获取整体的比例
        for (int i = 0; i < logoListSort.size(); i++) {
            logoListSort.get(i).rRate = getRate(all,i,logoListSort);
        }
        // 加载图表数据
        PieData mPieData = setPieData(logoListSort);
        if(nowType.equals("in")){
            showChart(mChart, mPieData,"收入",all+"");
        }else if(nowType.equals("out")){
            showChart(mChart, mPieData,"支出",all+"");
        }
        showListView(logoListSort);
    }

    //加载图谱
    private void showChart(PieChart pieChart, PieData pieData,String type,String total) {
        pieChart.setHoleRadius(45f); // 半径
        pieChart.setTransparentCircleRadius(50f); // 半透明圈
        pieChart.setDrawCenterText(true); // 饼状图中间可以添加文字
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90); // 初始旋转角度
        pieChart.setRotationEnabled(true); // 可以手动旋转
        // display percentage values
        pieChart.setUsePercentValues(true); // 显示成百分比
        pieChart.setCenterText(type+ "\n"+"￥"+total); // 饼状图中间的文字
        pieChart.setCenterTextSize(11);
        pieChart.setCenterTextColor(Color.parseColor("#606060"));
        pieChart.setDrawCenterText(true);
        pieChart.setNoDataText("没有数据");

        // 设置数据
        pieChart.setData(pieData);

        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();

        Legend mLegend = pieChart.getLegend(); // 设置比例图
//      mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER); // 最右边显示
        mLegend.setForm(Legend.LegendForm.CIRCLE); // 设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        mLegend.setEnabled(false);


        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);//设置描述文字
        pieChart.animateXY(1000, 1000); // 设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     * 设置PieDate
     */
    private PieData setPieData(List<ReportBean> reportBeanList) {
        // y 数据集合
        List<PieEntry> yValues = new ArrayList<PieEntry>(); // yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>();
        // 将数据装载到Y数组集合中去
        for (int i = 0; i < reportBeanList.size(); i++) {
            yValues.add(new PieEntry(reportBeanList.get(i).rMoney, i));
            colors.add(Color.parseColor(reportBeanList.get(i).rColor));
        }
        PieDataSet pieDataSet = new PieDataSet(yValues, "");/* 显示在比例图上 */
        ;
        pieDataSet.setSliceSpace(1f); // 设置个饼状图之间的距离
        // 设置颜色
        pieDataSet.setColors(colors);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        pieDataSet.setValueFormatter(new PercentFormatter());
        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(12);
        pieData.setValueTextColor(Color.rgb(255, 255, 255));
        return pieData;
    }

}
