package com.bcm.account.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bcm.account.R;
import com.bcm.account.adapter.ReportAdapter;
import com.bcm.account.newsbean.ReportBean;
import com.bcm.account.tools.SleepListView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    private List<ReportBean> reportBeanList;
    //
    private String[] colorArray =  {"#47A7E6","#5DBF92","#FD557D","#B5BA3E","#9659AE"};
    private float[] moneyArray = {14.0f,16.0f,18.0f,19.0f,55.0f};
    private int[] logoArray = {R.mipmap.icon_water,R.mipmap.icon_vegetable,R.mipmap.icon_cloth,R.mipmap.icon_food,R.mipmap.icon_shoe};
    private String[] wayArray = {"水费","蔬菜","衣服","食物","鞋子"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_report_fragment, null);
        bindView();
        initData();
        return view;
    }

    // 绑定数据源
    private void bindView() {
        mChart = (PieChart) view.findViewById(R.id.pieChart);
        mSleepView = (SleepListView) view.findViewById(R.id.mSleepView);
    }

    // showListView
    private void showListView() {
        adapter = new ReportAdapter(getActivity(), reportBeanList);
        mSleepView.setAdapter(adapter);
    }

    // 初始化数据

    private void initData() {
        reportBeanList = new ArrayList<>();
        ReportBean reportBean;
        for (int i = 0; i < 5; i++) {
            reportBean = new ReportBean();
            reportBean.rColor = colorArray[i];
            reportBean.rMoney = moneyArray[i];
            reportBean.rRate = getRate(i);
            reportBean.rLogo = logoArray[i];
            reportBean.rWay = wayArray[i];
            reportBeanList.add(reportBean);
        }
        showListView();
        PieData mPieData = setPieData(reportBeanList);
        showChart(mChart, mPieData);
    }

    // 获取比例

    private String getRate(int i ){
        float rate = 0.0f;
        rate = moneyArray[i]/getAll();
        DecimalFormat decimalFormat=new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(rate*100)+"%";//format 返回的是字符串
        return p;
    }

    // 获取全部

    private float getAll(){
        float all = 0.0f;
        for(int j = 0 ; j < moneyArray.length ; j++){
            all = all + moneyArray[j];
        }
        return all;
    }

    //加载图谱
    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleRadius(45f); // 半径
        pieChart.setTransparentCircleRadius(50f); // 半透明圈
        pieChart.setDrawCenterText(true); // 饼状图中间可以添加文字
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(90); // 初始旋转角度
        pieChart.setRotationEnabled(true); // 可以手动旋转
        // display percentage values
        pieChart.setUsePercentValues(true); // 显示成百分比
        pieChart.setCenterText("收入" + "\n" + getAll()); // 饼状图中间的文字
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
     设置PieDate
     */
    private PieData setPieData(List<ReportBean> reportBeanList) {
        // y 数据集合
        List<PieEntry> yValues = new ArrayList<PieEntry>(); // yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>();
        // 将数据装载到Y数组集合中去
        for(int i = 0 ; i < reportBeanList.size() ; i ++ ){
            yValues.add(new PieEntry(reportBeanList.get(i).rMoney,i));
            colors.add(Color.parseColor(reportBeanList.get(i).rColor));
        }
        PieDataSet pieDataSet = new PieDataSet(yValues, "");/* 显示在比例图上 */;
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
