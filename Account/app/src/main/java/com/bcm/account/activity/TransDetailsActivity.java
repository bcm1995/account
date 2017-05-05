package com.bcm.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.adapter.TransAdapter;
import com.bcm.account.bmobbean.ATrans;
import com.bcm.account.bmobbean.AWallet;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.newsbean.TransBean;
import com.bcm.account.tools.DataCenter;
import com.bcm.account.tools.view.NumberScrollTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Bean on 2017/5/3.
 */

public class TransDetailsActivity extends Activity {
    private LinearLayout HeaderLayout, MainLayout;
    private ImageView Back;
    private String user_id;
    private TextView HeaderTitle;
    private NumberScrollTextView TotalMoney;
    private String typeEng,type;
    private List<TransBean> transBeanList;
    private TransAdapter adapter;
    private ListView mTransList;
    private TextView YearInCount;
    private TextView YearOutCount;
    private TextView TransBtn;
    // 计算钱数到小数点两位
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.debit_layout);
        // 获取用户信息
        myUser user = myUser.getCurrentUser(myUser.class);
        user_id = user.getObjectId();
        bindView();
        final Intent intent = getIntent();
        String color = intent.getStringExtra("color");
        type = intent.getStringExtra("type");
        HeaderLayout.setBackgroundColor(Color.parseColor(color));
        MainLayout.setBackgroundColor(Color.parseColor(color));
        Back.setVisibility(View.VISIBLE);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                setResult(2);
            }
        });
        getMoneyFromBmob(type);
        HeaderTitle.setText(type);
        // 类型
        typeEng = getTypeEng(type);
        // 获取转账数据
        getTransDetails(typeEng);
        TransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),TransActivity.class);
                intent.putExtra("type",typeEng);
                startActivityForResult(intent,1);
            }
        });
        TotalMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),MoneyEditActivity.class);
                intent1.putExtra("type",typeEng);
                startActivityForResult(intent1,2);
            }
        });
    }

    private void bindView() {
        HeaderLayout = (LinearLayout) findViewById(R.id.headerLayout);
        MainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        Back = (ImageView) findViewById(R.id.reButton);
        TotalMoney = (NumberScrollTextView) findViewById(R.id.totalMoney);
        HeaderTitle = (TextView) findViewById(R.id.header_title);
        mTransList = (ListView) findViewById(R.id.mTransList);
        mTransList.setDividerHeight(0);
        YearInCount = (TextView) findViewById(R.id.yearInCount);
        YearOutCount = (TextView) findViewById(R.id.yearOutCount);
        TransBtn = (TextView) findViewById(R.id.transBtn);
    }

    // 设置数字
    private void setNumber(Float num){
        TotalMoney.setFromAndEndNumber(0, num);
        TotalMoney.setDuration(1500);
        TotalMoney.start();
    }
    // 计算类型
    private String getTypeEng(String type) {
        String types = "";
        if (type.equals("储蓄卡")) {
            types = "debit";
        } else if (type.equals("现金")) {
            types = "cash";
        } else if (type.equals("借入")) {
            types = "join";
        } else if (type.equals("借出")) {
            types = "loan";
        } else if (type.equals("信用卡")) {
            types = "credit";
        }
        return types;
    }

    // 显示ListView

    private void showListView(List<TransBean> list) {
        adapter = new TransAdapter(getApplicationContext(), list);
        mTransList.setAdapter(adapter);
    }

    // 计算每个月的钱数

    private void calcuterMonth(List<TransBean> tList) {
        HashMap<String, String> hashInSet = new HashMap<>();
        HashMap<String, String> hashOutSet = new HashMap<>();
        Float yearInCount = 0.0f;
        Float yearOutCount = 0.0f;
        // 获取所有的月份
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < tList.size(); i++) {
            monthList.add(tList.get(i).trans_month);
        }
        // 去重月份
        HashSet<String> hs = new HashSet<String>(monthList);
        // 生成新List
        List<String> resetList = new ArrayList<>(hs);
        // 计算每个月份的钱
        for (int i = 0; i < resetList.size(); i++) {
            String month = resetList.get(i);
            Float inCount = 0.0f;
            Float outCount = 0.0f;
            // 分类计算
            for (int j = 0; j < tList.size(); j++) {
                String tMonth = tList.get(j).trans_month;
                String tWay = tList.get(j).trans_way;
                String tMoney = tList.get(j).trans_money;
                if (tMonth.equals(month)) {
                    if (tWay.equals("in")) {
                        inCount = inCount + Float.parseFloat(tMoney);
                        yearInCount = yearInCount + Float.parseFloat(tMoney);
                    } else if (tWay.equals("out")) {
                        outCount = outCount + Float.parseFloat(tMoney);
                        yearOutCount = yearOutCount + Float.parseFloat(tMoney);
                    }
                }
            }
            hashInSet.put(month, df.format(inCount) + "");
            hashOutSet.put(month, df.format(outCount) + "");
        }
        // 设置当年的收入及支出
        YearOutCount.setText(df.format(yearOutCount)+"");
        YearInCount.setText(df.format(yearInCount)+"");
        // 整理出各个月份的数据
        for (int i = 0; i < tList.size(); i++) {
            String month = tList.get(i).trans_month;
            tList.get(i).trans_month_in = hashInSet.get(month);
            tList.get(i).trans_month_out = hashOutSet.get(month);
        }
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

    // 查看转账记录
    private void getTransDetails(String type) {
        BmobQuery<ATrans> query = new BmobQuery("ATrans");
        query.addWhereEqualTo("user_id", user_id);
        query.addWhereEqualTo("trans_wallet", type);
        query.order("-createdAt");
        transBeanList = new ArrayList<>();
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (e == null) {
                    Log.i("trans", jsonArray.toString());
                    if (jsonArray.length() > 0) {
                        TransBean transBean;
                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                JSONObject trans = jsonArray.getJSONObject(i);
                                transBean = new TransBean();
                                String logo = trans.optString("trans_logo");
                                transBean.trans_logo = trans.optString("trans_logo");
                                transBean.trans_logo_int = DataCenter.getImageUri(logo);
                                transBean.trans_month = trans.optString("trans_month");
                                transBean.trans_name = trans.optString("trans_name");
                                transBean.trans_remark = trans.optString("trans_remark");
                                transBean.trans_wallet = trans.optString("trans_wallet");
                                transBean.trans_way = trans.optString("trans_way");
                                transBean.trans_day = trans.optString("createdAt").substring(5, 11);
                                transBean.trans_money = trans.optString("trans_money");
                                transBeanList.add(transBean);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        // 计算每个月的总支出或收入
                        calcuterMonth(transBeanList);
                        // 显示ListView
                        showListView(transBeanList);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                // 获取转账数据
                getTransDetails(typeEng);
                break;
            case 2:
                getMoneyFromBmob(type);
                // 获取转账数据
                getTransDetails(typeEng);
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
