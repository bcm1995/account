package com.bcm.account.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bcm.account.R;
import com.bcm.account.details.DetailsAdapter;
import com.bcm.account.newsbean.DetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bean on 2017/4/10.
 */

public class DetailsFragment extends Fragment {
    private View view;
    private ListView mListView;
    private DetailsAdapter adapter;
    private List<DetailsBean> detailsBeanList;
    // 模拟数据
    private String[] moneyWay ={"mIn","mIn","mIn","mOut","mOut","mIn","mIn","mOut","mOut","mOut"};
    private String[] dayArray = {"10日","10日","10日","11日","11日","11日","13日","13日","13日","13日"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_details_fragment, null);
        bindView();
        initData();
        return view;
    }
    // 绑定数据源
    private void bindView(){
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setDividerHeight(0);
    }
    // 初始化数据
    private void initData(){
        detailsBeanList = new ArrayList<>();
        DetailsBean detailsBean;
        for(int i = 0 ; i < 10 ; i ++ ){
            detailsBean = new DetailsBean();
            detailsBean.way = moneyWay[i];
            detailsBean.day = dayArray[i];
            detailsBeanList.add(detailsBean);
        }
        showListView();
    }
    // showListView
    private void showListView(){
        adapter = new DetailsAdapter(getActivity(),detailsBeanList);
        mListView.setAdapter(adapter);
    }
}
