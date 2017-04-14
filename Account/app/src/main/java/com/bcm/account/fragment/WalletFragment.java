package com.bcm.account.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bcm.account.R;
import com.bcm.account.adapter.WalletAdapter;
import com.bcm.account.newsbean.WalletBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bean on 2017/4/10.
 */

public class WalletFragment extends Fragment {
    private View view;
    private ListView mListView;
    private List<WalletBean> walletBeanList;
    private WalletAdapter adapter;
    private String[] typeArray = {"储蓄卡","借出","借入","信用卡","现金"};
    private String[] introArray = {"储蓄卡余额","别人欠我的钱","我欠别人的钱","未设置信用额度","现金余额"};
    private String[] leftColorArray = {"#519DDD","#F3955D","#EB7EBB","#48BEB4","#2FB2E8"};
    private String[] rightColorArray = {"#72AFE2","#F4A97C","#EE9DCA","#69C9C1","#57C0EB"};
    private int[] logo = {R.mipmap.wallet_save_card,R.mipmap.wallet_out,R.mipmap.wallet_get,R.mipmap.wallet_idencard,R.mipmap.wallet_cash};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_wallet_fragment, null);
        bindView();
        initData();
        return view;
    }
    // 绑定数据源
    private void bindView(){
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setDividerHeight(0);
    }
    // showListView
    private void showListView(){
        adapter = new WalletAdapter(getActivity(),walletBeanList);
        mListView.setAdapter(adapter);
    }
    // 初始化数据
    private void initData(){
        walletBeanList = new ArrayList<>();
        WalletBean walletBean;
        for(int i = 0 ; i < 5 ; i ++){
            walletBean = new WalletBean();
            walletBean.type = typeArray[i];
            walletBean.intro = introArray[i];
            walletBean.leftColor = leftColorArray[i];
            walletBean.rightColor = rightColorArray[i];
            walletBean.Logo = logo[i];
            walletBeanList.add(walletBean);
        }
        showListView();
    }
}
