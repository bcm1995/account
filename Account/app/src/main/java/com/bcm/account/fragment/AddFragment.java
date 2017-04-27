package com.bcm.account.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.adapter.IconAdapter;
import com.bcm.account.bmobbean.ABill;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.newsbean.IconBean;
import com.bcm.account.tools.DataCenter;
import com.bcm.account.tools.InterfaceCenter;
import com.bcm.account.tools.TimeCenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Bean on 2017/4/10.
 */

public class AddFragment extends Fragment {
    private View view;
    private GridView mGridView;
    private IconAdapter adapter;
    private List<IconBean> iconBeanList ;
    private ImageView iconImg;
    private TextView iconText,inType,outType;
    private TextView Money;
    private TextView Add;
    private String bill_types;
    private String bill_logos;
    private String[] inLogos = {"gongzi","shenghuo","linghua","jianzhi","touzi","jiangjin","baoxiao","xianjin","alipay","caipiao","qita"};
    private String[] outLogos = {"yiban","canyin","jiaotong","yinpin","suiguo","lingshi","maicai","yifu","riyong","huafei","hufu","fangzhu","dianying","taobao","suidian","kge"};
    DecimalFormat df = new DecimalFormat("0.00");
    private TextView walletType;
    private String wallet_type;
    private myUser user;
    private String user_id ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_add_fragment, null);
        bindView();
        initData("in");
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               setChoice(i);
                if(bill_types.equals("in")){
                    bill_logos = inLogos[i];
                }else if(bill_types.equals("out")){
                    bill_logos = outLogos[i];
                }
            }
        });
        // 默认现金账户
        wallet_type = "cash";
        setInOut("in");
        inType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInOut("in");
                initData("in");

            }
        });
        outType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInOut("out");
                initData("out");
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    check();
            }
        });
        walletType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] arrayWallet = new String[] {"现金", "储蓄卡", "信用卡"};
                final String[] arrayWalletEng = new String[]{"cash","debit","credit"};
                Dialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setItems(arrayWallet, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wallet_type = arrayWalletEng[which];
                                walletType.setText(arrayWallet[which]);
                            }
                        }).create();
                alertDialog.show();
            }
        });
        // 获取当前用户
        user = myUser.getCurrentUser(myUser.class);
        user_id = user.getObjectId();
        return view;
    }
    private void bindView(){
        mGridView = (GridView) view.findViewById(R.id.gridview);
        iconImg = (ImageView) view.findViewById(R.id.iconImg);
        iconText = (TextView) view.findViewById(R.id.iconText);
        inType = (TextView) view.findViewById(R.id.inType);
        outType = (TextView) view.findViewById(R.id.outType);
        Money = (TextView) view.findViewById(R.id.money);
        Add = (TextView)view.findViewById(R.id.add);
        walletType = (TextView) view.findViewById(R.id.walletType);
    }


    private void setInOut(String type){
        bill_types = type;
        GradientDrawable inColor = (GradientDrawable)
                inType.getBackground();
        GradientDrawable outColor = (GradientDrawable)
                outType.getBackground();
        if(type.equals("in")){
            // 设置in颜色
            inColor.setColor(Color.parseColor("#ffffff"));
            inType.setTextColor(Color.parseColor("#3CCB8D"));
            outColor.setColor(Color.parseColor("#3CCB8D"));
            outType.setTextColor(Color.parseColor("#ffffff"));
        }else if(type.equals("out")){
            // 设置out颜色
            inColor.setColor(Color.parseColor("#3CCB8D"));
            inType.setTextColor(Color.parseColor("#ffffff"));
            outColor.setColor(Color.parseColor("#ffffff"));
            outType.setTextColor(Color.parseColor("#3CCB8D"));
        }
    }

    private void setChoice(int position){
        iconImg.setImageResource(iconBeanList.get(position).icon);
        iconText.setText(iconBeanList.get(position).name);
    }

    private void initData(String type){
        if(type.equals("in")){
            iconBeanList = new ArrayList<>();
            IconBean iconBean;
            for(int i = 0 ; i < DataCenter.inType.length ; i++){
                iconBean = new IconBean();
                iconBean.icon = DataCenter.inTypeImg[i];
                iconBean.name = DataCenter.inType[i];
                iconBeanList.add(iconBean);
            }
            showGridView(iconBeanList);
            setChoice(0);
        }else if(type.equals("out")){
            iconBeanList = new ArrayList<>();
            IconBean iconBean;
            for(int i = 0 ; i < DataCenter.outType.length ; i++){
                iconBean = new IconBean();
                iconBean.icon = DataCenter.outTypeImg[i];
                iconBean.name = DataCenter.outType[i];
                iconBeanList.add(iconBean);
            }
            showGridView(iconBeanList);
            setChoice(0);
        }

    }

    private void showGridView(List<IconBean> LI){
        adapter = new IconAdapter(getActivity(),LI);
        mGridView.setAdapter(adapter);
    }

    // 检查插入数据

    private void check(){
        String moneyStr = Money.getText().toString();
        if(moneyStr.equals("")){
            Toast.makeText(getActivity(),"请输入金额",Toast.LENGTH_SHORT).show();
            return ;
        }
        Float moneyFlo = Float.parseFloat(moneyStr);
        String money =   df.format(moneyFlo)+"";
        insertMoneyToBmob(wallet_type,money,bill_types,bill_logos, TimeCenter.getCurrenceDate());

    }

    // 插入钱数

    private void insertMoneyToBmob(String wallet_type,String bill_money,String bill_type,String logo,String date){
        ABill aBill = new ABill();
        aBill.setWallet_type(wallet_type);
        aBill.setBill_type(bill_type);
        aBill.setBill_money(bill_money);
        aBill.setBill_logo(logo);
        aBill.setBill_date(date);
        aBill.setUser_id(user_id);
        aBill.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Toast.makeText(getActivity(),"记账成功",Toast.LENGTH_SHORT).show();
                    Money.setText("");
                    InterfaceCenter.irefresh.refreshDetail();
                }
            }
        });
    }


}
