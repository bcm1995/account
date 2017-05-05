package com.bcm.account.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;
import com.bcm.account.bmobbean.ATrans;
import com.bcm.account.bmobbean.AWallet;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.tools.DataCenter;
import com.bcm.account.tools.TimeCenter;

import java.text.DecimalFormat;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Bean on 2017/5/5.
 */

public class MoneyEditActivity extends Activity {
    private TextView HeaderTitle;
    private ImageView Back;
    private EditText MoneyTotal;
    private Button Sure;
    private String all;
    private String user_id, type;
    // 计算钱数到小数点两位
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.money_edit);
        bindView();
        user_id = myUser.getCurrentUser(myUser.class).getObjectId();
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("debit")) {
            MoneyTotal.setText(DataCenter.debitMoney);
            all = DataCenter.debitMoney;
        } else if (type.equals("cash")) {
            MoneyTotal.setText(DataCenter.cashMoney);
            all = DataCenter.cashMoney;
        } else if (type.equals("credit")) {
            MoneyTotal.setText(DataCenter.creditMoney);
            all = DataCenter.creditMoney;
        }
        HeaderTitle.setText("余额修改");
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    private void bindView() {
        MoneyTotal = (EditText) findViewById(R.id.moneyTotal);
        Sure = (Button) findViewById(R.id.sure);
        HeaderTitle = (TextView) findViewById(R.id.header_title);
        Back = (ImageView) findViewById(R.id.reButton);
        Back.setVisibility(View.VISIBLE);
    }

    private void check() {
        String moneyNow = MoneyTotal.getText().toString();
        Float nowF = Float.parseFloat(moneyNow);
        Float allF = Float.parseFloat(all);
        Float mergeF = nowF - allF;
        if (mergeF > 0) {
//            Toast.makeText(getApplicationContext(),"余额增加了"+Math.abs(mergeF),Toast.LENGTH_SHORT).show();
            updatedBmob(type, df.format(nowF) + "");
            addTransRecord(type, "in", "余额平账增加", TimeCenter.getCurrenceDate().substring(5, 7), df.format(Math.abs(mergeF)) + "", "pingzhang");
        } else if (mergeF == 0) {
            Toast.makeText(getApplicationContext(), "余额没有变化,请核实后进行修改" + Math.abs(mergeF), Toast.LENGTH_SHORT).show();
            return;
        } else {
//            Toast.makeText(getApplicationContext(),"余额减少了"+Math.abs(mergeF),Toast.LENGTH_SHORT).show();
            updatedBmob(type, df.format(nowF) + "");
            addTransRecord(type, "out", "余额平账减少", TimeCenter.getCurrenceDate().substring(5, 7), df.format(Math.abs(mergeF)) + "", "pingzhang");
        }
    }

    // 更新Bmob数据

    private void updatedBmob(String type, String money) {
        AWallet wallet = new AWallet();
        if (type.equals("cash")) {
            wallet.setCash_money(money);
        } else if (type.equals("debit")) {
            wallet.setDebit_money(money);
        } else if (type.equals("credit")) {
            wallet.setCredit_money(money);
        }
        wallet.update(DataCenter.walletId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "更新钱包成功！", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "更新钱包失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // 增加转账记录

    private void addTransRecord(String wallet, String way, String name, String month, String money, String logo) {
        ATrans trans = new ATrans();
        trans.setUser_id(user_id);
        trans.setTrans_logo(logo);
        trans.setTrans_month(month);
        trans.setTrans_name(name);
        trans.setTrans_wallet(wallet);
        trans.setTrans_way(way);
        trans.setTrans_money(money);
        trans.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    finish();
                    setResult(2);
//                    Toast.makeText(getApplicationContext(), "转账记录成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "转账记录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
