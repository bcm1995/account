package com.bcm.account.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import com.bcm.account.bmobbean.AInOut;
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
 * Created by Bean on 2017/5/4.
 */

public class InOutEditActivity extends Activity {
    private TextView HeaderTitle;
    private ImageView Back;
    private EditText mRecordName;
    private EditText mRecordMoney;
    private TextView mRecordWallet;
    private TextView mRecordDate;
    private EditText mRecordRemark;
    private Button mRecordSubmit;
    private String user_id;
    private TextView mMoneyTip;
    private TextView mDateTip;
    // 计算钱数到小数点两位
    DecimalFormat df = new DecimalFormat("0.00");
    //
    private String logo, type, wallet_type,way;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.io_edit);
        bindView();
        myUser user = myUser.getCurrentUser(myUser.class);
        user_id = user.getObjectId();
        HeaderTitle.setText("添加记录");
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if (type.equals("join")) {
            mMoneyTip.setText("借入金额");
            mDateTip.setText("借入日期");
            logo = "jieru";
            way = "in";
        } else if (type.equals("loan")) {
            mMoneyTip.setText("借出金额");
            mDateTip.setText("借出日期");
            logo = "jiechu";
            way = "out";
        }
        Back.setVisibility(View.VISIBLE);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecordDate.setText(TimeCenter.getCurrenceDate());
        mRecordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
        mRecordWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] arrayWallet = new String[]{"现金", "储蓄卡", "信用卡"};
                final String[] arrayWalletEng = new String[]{"cash", "debit", "credit"};
                Dialog alertDialog = new AlertDialog.Builder(InOutEditActivity.this)
                        .setItems(arrayWallet, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                wallet_type = arrayWalletEng[which];
                                mRecordWallet.setText(arrayWallet[which]);
                            }
                        }).create();
                alertDialog.show();
            }
        });
    }

    // 绑定数据源
    private void bindView() {
        Back = (ImageView) findViewById(R.id.reButton);
        HeaderTitle = (TextView) findViewById(R.id.header_title);
        mRecordName = (EditText) findViewById(R.id.recordName);
        mRecordMoney = (EditText) findViewById(R.id.recordMoney);
        mRecordWallet = (TextView) findViewById(R.id.recordWallet);
        mRecordDate = (TextView) findViewById(R.id.recordDate);
        mRecordRemark = (EditText) findViewById(R.id.recordRemark);
        mRecordSubmit = (Button) findViewById(R.id.recordSubmit);
        mDateTip = (TextView) findViewById(R.id.dateTip);
        mMoneyTip = (TextView) findViewById(R.id.moneyTip);
    }

    // 检查
    private void check() {
        String name = mRecordName.getText().toString();
        String money = mRecordMoney.getText().toString();
        String wallet = mRecordWallet.getText().toString();
        String date = mRecordDate.getText().toString();
        String remark = mRecordRemark.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "请输入对方姓名", Toast.LENGTH_SHORT).show();
            return;
        } else if (money.equals("")) {
            Toast.makeText(getApplicationContext(), "请输入钱数", Toast.LENGTH_SHORT).show();
            return;
        } else if (wallet.equals("请选择")) {
            Toast.makeText(getApplicationContext(), "请选择账户", Toast.LENGTH_SHORT).show();
            return;
        } else if (date.equals("")) {
            Toast.makeText(getApplicationContext(), "请选择日期", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Float mon = Float.parseFloat(money);
            String moneys = df.format(mon);
            calcutorWallet(name,date,remark,type, wallet_type, moneys);
        }
    }

    // 添加
    private void addRecord(final String name, final String money, final String wallet, final String date, final String remark) {
        AInOut aInOut = new AInOut();
        aInOut.setUser_id(user_id);
        aInOut.setIo_date(date);
        aInOut.setIo_logo(logo);
        aInOut.setIo_type(type);
        aInOut.setIo_remark(remark);
        aInOut.setIo_wallet(wallet);
        aInOut.setIo_name(name);
        aInOut.setIo_money(money);
        aInOut.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    addTransRecord(wallet,way,name,date.substring(5,7),money,logo);
                    Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "获取数据失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // 计算钱包余额

    private void calcutorWallet(String name,String date,String remark,String type, String wallet, String money) {
        Float moneyF = Float.parseFloat(money);
        if (type.equals("join")) {
            Float joinMoney = Float.parseFloat(DataCenter.joinMoney);
            Float joinTotal = joinMoney + moneyF;
            if (wallet.equals("debit")) {
                Float debitMoney = Float.parseFloat(DataCenter.debitMoney);
                Float totalMoney = debitMoney + moneyF;
                updatedBmob("debit", df.format(totalMoney) + "", df.format(joinTotal) + "");
                addRecord(name, money, wallet_type, date, remark);
            } else if (wallet.equals("credit")) {
                Float creditMoney = Float.parseFloat(DataCenter.creditMoney);
                if (moneyF > creditMoney) {
                    Toast.makeText(getApplicationContext(), "仅需￥" + creditMoney + "即可还清信用卡，请重新计算", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Float totalMoney = creditMoney - moneyF;
                    updatedBmob("credit", df.format(totalMoney) + "", df.format(joinTotal) + "");
                    addRecord(name, money, wallet_type, date, remark);
                }

            } else if (wallet.equals("cash")) {
                Float cashMoney = Float.parseFloat(DataCenter.cashMoney);
                Float totalMoney = cashMoney + moneyF;
                updatedBmob("cash", df.format(totalMoney) + "", df.format(joinTotal) + "");
                addRecord(name, money, wallet_type, date, remark);
            }
        } else if (type.equals("loan")) {
            Float loanMoney = Float.parseFloat(DataCenter.loanMoney);
            Float loanTotal = loanMoney + moneyF;
            if (wallet.equals("debit")) {
                Float debitMoney = Float.parseFloat(DataCenter.debitMoney);
                if (moneyF > debitMoney) {
                    Toast.makeText(getApplicationContext(), "账户余额不足", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Float totalMoney = debitMoney - moneyF;
                    updatedBmob("debit", df.format(totalMoney) + "", df.format(loanTotal) + "");
                    addRecord(name, money, wallet_type, date, remark);
                }

            } else if (wallet.equals("credit")) {
                Float creditMoney = Float.parseFloat(DataCenter.creditMoney);
                Float totalMoney = creditMoney + moneyF;
                updatedBmob("credit", df.format(totalMoney) + "", df.format(loanTotal) + "");
                addRecord(name, money, wallet_type, date, remark);
            } else if (wallet.equals("cash")) {
                Float cashMoney = Float.parseFloat(DataCenter.cashMoney);
                if (moneyF > cashMoney) {
                    Toast.makeText(getApplicationContext(), "账户余额不足", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Float totalMoney = cashMoney - moneyF;
                    updatedBmob("cash", df.format(totalMoney) + "", df.format(loanTotal) + "");
                    addRecord(name, money, wallet_type, date, remark);
                }
            }
        }
    }


    // 更新Bmob数据

    private void updatedBmob(final String wallet_t, final String money, final String ioMoney) {
        final AWallet wallet = new AWallet();
        if (wallet_t.equals("cash")) {
            wallet.setCash_money(money);
        } else if (wallet_t.equals("debit")) {
            wallet.setDebit_money(money);
        } else if (wallet_t.equals("credit")) {
            wallet.setCredit_money(money);
        }

        if (type.equals("join")) {
            wallet.setJoin_money(ioMoney);
        } else if (type.equals("loan")) {
            wallet.setLoan_money(ioMoney);
        }

        wallet.update(DataCenter.walletId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    if (wallet_t.equals("cash")) {
                        DataCenter.cashMoney = money;
                    } else if (wallet_t.equals("debit")) {
                        DataCenter.debitMoney = money;
                    } else if (wallet_t.equals("credit")) {
                        DataCenter.creditMoney = money;
                    }

                    if (type.equals("join")) {
                        DataCenter.joinMoney = ioMoney;
                    } else if (type.equals("loan")) {
                        DataCenter.loanMoney = ioMoney;
                    }
                    finish();
                    setResult(1);
//                    Toast.makeText(getApplicationContext(), "更新钱包成功！", Toast.LENGTH_SHORT).show();
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
        if(type.equals("join")){
            trans.setTrans_remark("借入");
        }else if(type.equals("loan")){
            trans.setTrans_remark("借出");
        }
        trans.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
//                    Toast.makeText(getApplicationContext(), "转账记录成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "转账记录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

