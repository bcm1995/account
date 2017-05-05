package com.bcm.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.activity.TransDetailsActivity;
import com.bcm.account.activity.TransActivity;
import com.bcm.account.activity.TransInOutActivity;
import com.bcm.account.adapter.WalletAdapter;
import com.bcm.account.bmobbean.AWallet;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.newsbean.WalletBean;
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

public class WalletFragment extends Fragment {
    private View view;
    private ListView mListView;
    private List<WalletBean> walletBeanList;
    private WalletAdapter adapter;
    private String[] typeArray = {"储蓄卡","借出","借入","信用卡","现金"};
    private String[] introArray = {"储蓄卡余额","别人欠我的钱","我欠别人的钱","未设置信用额度","现金余额"};
    private String[] leftColorArray = {"#519DDD","#F3955D","#EB7EBB","#48BEB4","#2FB2E8"};
    private String[] rightColorArray = {"#72AFE2","#F4A97C","#EE9DCA","#69C9C1","#57C0EB"};
    private String[] moneyArray = {"--","--","--","--","--"};
    private int[] logo = {R.mipmap.wallet_save_card,R.mipmap.wallet_out,R.mipmap.wallet_get,R.mipmap.wallet_idencard,R.mipmap.wallet_cash};
    //用户信息
    private myUser user;
    private String user_id;
    private TextView total_left;
    // 计算钱数到小数点两位
    DecimalFormat df = new DecimalFormat("0.00");
    // 转账
    private TextView Trans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_wallet_fragment, null);
        bindView();
        initData();
        user = myUser.getCurrentUser(myUser.class);
        user_id = user.getObjectId();
        getDataFromBmob();
        // 转账监听
        Trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TransActivity.class);
                intent.putExtra("type","cash");
                startActivityForResult(intent,1);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0||i==3||i==4){
                    Intent intent = new Intent(getActivity(), TransDetailsActivity.class);
                    intent.putExtra("type",typeArray[i]);
                    intent.putExtra("color",rightColorArray[i]);
                    startActivityForResult(intent,2);
                }else {
                    Intent intent = new Intent(getActivity(), TransInOutActivity.class);
                    intent.putExtra("type",typeArray[i]);
                    intent.putExtra("color",rightColorArray[i]);
                    startActivityForResult(intent,2);
                }

            }
        });
        return view;
    }
    // 绑定数据源
    private void bindView(){
        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setDividerHeight(0);
        total_left = (TextView) view.findViewById(R.id.total_left);
        Trans = (TextView) view.findViewById(R.id.confirm);
    }
    // showListView
    private void showListView(){
        if(adapter == null){
            adapter = new WalletAdapter(getActivity(),walletBeanList);
            mListView.setAdapter(adapter);
        }else {
            adapter.changeDate(walletBeanList);
        }

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
            walletBean.money = moneyArray[i];
            walletBeanList.add(walletBean);
        }
        showListView();
    }

    // 计算余额

    private void calcuterLeft(String debit,String loan,String join,String credit,String cash){
        Float debitf = Float.parseFloat(debit);
        Float loanf = Float.parseFloat(loan);
        Float joinf = Float.parseFloat(join);
        Float creditf = Float.parseFloat(credit);
        Float cashf = Float.parseFloat(cash);
        Float total = debitf+joinf + cashf - loanf - creditf;
        total_left.setText(df.format(total)+"");
    }

    // 从钱包表中获取数据
    public void getDataFromBmob(){
        BmobQuery<AWallet> query = new BmobQuery("AWallet");
        query.addWhereEqualTo("user_id",user_id);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if(e == null){
                    if(jsonArray.length()>0){
                        try {
                            JSONObject wallet = jsonArray.getJSONObject(0);
                            String debit = wallet.optString("debit_money");
                            String cash = wallet.optString("cash_money");
                            String join = wallet.optString("join_money");
                            String credit = wallet.optString("credit_money");
                            String loan = wallet.optString("loan_money");
                            walletBeanList.get(0).money = debit;
                            walletBeanList.get(1).money = loan;
                            walletBeanList.get(2).money = join;
                            walletBeanList.get(3).money = credit;
                            walletBeanList.get(4).money = cash;
                            calcuterLeft(debit,loan,join,credit,cash);
                            DataCenter.walletId = wallet.optString("objectId");
                            DataCenter.cashMoney = cash;
                            DataCenter.debitMoney = debit;
                            DataCenter.creditMoney = credit;
                            DataCenter.joinMoney = join;
                            DataCenter.loanMoney = loan;
                            showListView();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        // 装载钱数

                    }
                }else{
                    Log.i("message",e.getMessage());
                    Toast.makeText(getActivity(),"失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 回调获取数据


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                getDataFromBmob();
                break;
            case 2:
                getDataFromBmob();
                break;
        }
    }
}

