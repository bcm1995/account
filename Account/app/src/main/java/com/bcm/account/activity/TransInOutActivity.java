package com.bcm.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.adapter.IoAdapter;
import com.bcm.account.bmobbean.AInOut;
import com.bcm.account.bmobbean.AWallet;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.newsbean.IoBean;
import com.bcm.account.tools.view.NumberScrollTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Bean on 2017/5/4.
 */

public class TransInOutActivity extends Activity {
    public String user_id;
    private TextView HeaderTitle;
    private ImageView Back;
    private LinearLayout MainLayout,HeaderLayout;
    private String type;
    private TextView TransTip;
    private NumberScrollTextView TransMoney;
    private ListView TransListView;
    private IoAdapter adapter;
    private List<IoBean> IoBeanList;
    private TextView AddRecord;
    private String typeEng="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.trans_inout_layout);
        // 获取用户信息
        myUser user = myUser.getCurrentUser(myUser.class);
        user_id = user.getObjectId();
        bindView();
        final Intent intent = getIntent();
        String color = intent.getStringExtra("color");
        type = intent.getStringExtra("type");
        HeaderLayout.setBackgroundColor(Color.parseColor(color));
        MainLayout.setBackgroundColor(Color.parseColor(color));
        HeaderTitle.setText(type);
        Back.setVisibility(View.VISIBLE);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                setResult(2);
            }
        });
        TransTip.setText(type+"总额");
        getMoneyFromBmob(type);
        if(type.equals("借入")){
            typeEng = "join";
            getIoRecord("join");
        }else if (type.equals("借出")){
            typeEng = "loan";
            getIoRecord("loan");
        }
    //    添加记录
        AddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),InOutEditActivity.class);
                intent1.putExtra("type",typeEng);
                startActivityForResult(intent1,1);
            }
        });
    }

    // 绑定数据源

    private void bindView() {
        HeaderLayout = (LinearLayout) findViewById(R.id.headerLayout);
        MainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        Back = (ImageView) findViewById(R.id.reButton);
        HeaderTitle = (TextView) findViewById(R.id.header_title);
        TransTip = (TextView) findViewById(R.id.transTip);
        TransMoney = (NumberScrollTextView) findViewById(R.id.transMoney);
        TransListView = (ListView) findViewById(R.id.transListView);
        AddRecord = (TextView) findViewById(R.id.addRecord);
    }

    //

    private void showListView(List<IoBean> iList){
        adapter = new IoAdapter(getApplicationContext(),iList);
        TransListView.setAdapter(adapter);
        TransListView.setDividerHeight(0);
    }

    // 设置数字
    private void setNumber(Float num){
        TransMoney.setFromAndEndNumber(0, num);
        TransMoney.setDuration(1500);
        TransMoney.start();
    }

    // 查看钱数
    private void getMoneyFromBmob(final String type) {
        BmobQuery<AWallet> query = new BmobQuery("AWallet");
        query.addWhereEqualTo("user_id", user_id);
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {

                    if (jsonArray.length() > 0) {
                        try {
                            JSONObject wallet = jsonArray.getJSONObject(0);
                            String debitMoney = wallet.optString("debit_money");
                            String cashMoney = wallet.optString("cash_money");
                            String joinMoney = wallet.optString("join_money");
                            String creditMoney = wallet.optString("credit_money");
                            String loanMoney = wallet.optString("loan_money");
                            if (type.equals("储蓄卡")) {
                                setNumber(Float.parseFloat(debitMoney));
                            } else if (type.equals("现金")) {
                                setNumber(Float.parseFloat(cashMoney));
                            } else if (type.equals("借入")) {
                                setNumber(Float.parseFloat(joinMoney));
                            } else if (type.equals("借出")) {
                                setNumber(Float.parseFloat(loanMoney));
                            } else if (type.equals("信用卡")) {
                                setNumber(Float.parseFloat(creditMoney));
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 获取记录

    private void getIoRecord(String type){
        BmobQuery<AInOut> query = new BmobQuery("AInOut");
        query.addWhereEqualTo("user_id",user_id);
        query.addWhereEqualTo("io_type",type);
        query.order("-createdAt");
        IoBeanList = new ArrayList<>();
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if(e == null){
                    if (jsonArray.length()>0){
                        IoBean ioBean;
                        for(int i =0;i<jsonArray.length();i++){
                            ioBean = new IoBean();
                            JSONObject iBean = jsonArray.optJSONObject(i);
                            ioBean.io_date = iBean.optString("io_date");
                            ioBean.io_name = iBean.optString("io_name");
                            ioBean.io_type = iBean.optString("io_type");
                            ioBean.io_money = iBean.optString("io_money");
                            ioBean.io_logo = iBean.optString("io_logo");
                            ioBean.io_wallet = iBean.optString("io_wallet");
                            ioBean.user_id = iBean.optString("user_id");
                            ioBean.io_remark = iBean.optString("io_remark");
                            IoBeanList.add(ioBean);
                        }
                        showListView(IoBeanList);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                getMoneyFromBmob(type);
                getIoRecord(typeEng);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            setResult(2);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
