package com.bcm.account.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * Created by Bean on 2017/4/29.
 */

public class TransActivity extends Activity {
    private TextView HeaderTitle;
    private ImageView Back;
    //
    private LinearLayout WalletFrom, WalletTo;
    private ImageView WalletFromImg, WalletToImg;
    private TextView WalletFromName, WalletToName;
    private TextView WalletFromLeft, WalletToLeft;
    private LinearLayout WalletFromRight, WalletToRight;
    // 钱数
    private EditText WalletFromMoney, WalletToMoney;
    //
    private final String[] arrayWallet = new String[]{"现金", "储蓄卡", "信用卡"};
    private final String[] arrayWalletEng = new String[]{"cash", "debit", "credit"};
    private final String[] arrayRightColor = new String[]{"#57C0EB", "#72AFE2", "#69C9C1"};
    private final String[] arrayLeftColor = new String[]{"#2FB2E8", "#519DDD", "#48BEB4"};
    private final int[] arrayImg = new int[]{R.mipmap.wallet_cash, R.mipmap.wallet_save_card, R.mipmap.wallet_idencard};
    private String Money;
    private TextView Trans;
    private String WFrom, WTo;
    // 计算钱数到小数点两位
    DecimalFormat df = new DecimalFormat("0.00");
    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_trans);
        bindView();
        WFrom = "cash";
        WTo = "cash";
        HeaderTitle.setText("转账");
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        resetColor();
        initFrom(type);
        user_id = myUser.getCurrentUser(myUser.class).getObjectId();
        //
        WalletFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog alertDialog = new AlertDialog.Builder(TransActivity.this)
                        .setItems(arrayWallet, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WFrom = arrayWalletEng[which];
                                // 设置钱包信息
                                WalletFromName.setText(arrayWallet[which]);
                                WalletFromImg.setImageResource(arrayImg[which]);
//                                // 设置左侧颜色
                                GradientDrawable leftColor = (GradientDrawable)
                                        WalletFromLeft.getBackground();
                                leftColor.setColor(Color.parseColor(arrayLeftColor[which]));
//                                // 设置右侧颜色
                                GradientDrawable rightColor = (GradientDrawable)
                                        WalletFromRight.getBackground();
                                rightColor.setColor(Color.parseColor(arrayRightColor[which]));
                            }
                        }).create();
                alertDialog.show();
            }
        });
        WalletTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog alertDialog = new AlertDialog.Builder(TransActivity.this)
                        .setItems(arrayWallet, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WTo = arrayWalletEng[which];
                                // 设置钱包信息
                                WalletToName.setText(arrayWallet[which]);
                                WalletToImg.setImageResource(arrayImg[which]);
                                // 设置左侧颜色
                                GradientDrawable leftColor = (GradientDrawable)
                                        WalletToLeft.getBackground();
                                leftColor.setColor(Color.parseColor(arrayLeftColor[which]));
                                // 设置右侧颜色
                                GradientDrawable rightColor = (GradientDrawable)
                                        WalletToRight.getBackground();
                                rightColor.setColor(Color.parseColor(arrayRightColor[which]));
                            }
                        }).create();
                alertDialog.show();
            }
        });
        // 为钱数设置
        WalletFromMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                WalletToMoney.setText(charSequence + "");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        WalletToMoney.setEnabled(false);
        Trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
    }

    // 初始化来源

    private void initFrom(String type){
        WFrom = type;
        int position = 0;
        for(int i = 0 ; i < arrayWallet.length ; i++ ){
            if(arrayWalletEng[i].equals(type)){
                position = i;
                break;
            }
        }
        WalletFromName.setText(arrayWallet[position]);
        WalletFromImg.setImageResource(arrayImg[position]);
        // 设置左侧颜色
        GradientDrawable leftColor = (GradientDrawable)
                WalletFromLeft.getBackground();
        leftColor.setColor(Color.parseColor(arrayLeftColor[position]));
        // 设置右侧颜色
        GradientDrawable rightColor = (GradientDrawable)
                WalletFromRight.getBackground();
        rightColor.setColor(Color.parseColor(arrayRightColor[position]));
    }

    // 重置颜色
    private void resetColor(){
        // 设置左侧颜色
        GradientDrawable leftColor = (GradientDrawable)
                WalletToLeft.getBackground();
        leftColor.setColor(Color.parseColor("#2FB2E8"));
        // 设置右侧颜色
        GradientDrawable rightColor = (GradientDrawable)
                WalletToRight.getBackground();
        rightColor.setColor(Color.parseColor("#57C0EB"));

        // 设置左侧颜色
        GradientDrawable leftColor1 = (GradientDrawable)
                WalletFromLeft.getBackground();
        leftColor1.setColor(Color.parseColor("#e3e3e3"));
        // 设置右侧颜色
        GradientDrawable rightColor1 = (GradientDrawable)
                WalletFromRight.getBackground();
        rightColor1.setColor(Color.parseColor("#f7f7f7"));
    }


    //绑定数据源

    private void bindView() {
        HeaderTitle = (TextView) findViewById(R.id.header_title);
        Back = (ImageView) findViewById(R.id.reButton);
        Back.setVisibility(View.VISIBLE);
        WalletFrom = (LinearLayout) findViewById(R.id.walletFromLayout);
        WalletTo = (LinearLayout) findViewById(R.id.walletToLayout);
        WalletFromImg = (ImageView) findViewById(R.id.walletFromLogo);
        WalletToImg = (ImageView) findViewById(R.id.walletToLogo);
        WalletFromName = (TextView) findViewById(R.id.walletFromType);
        WalletToName = (TextView) findViewById(R.id.walletToType);
        WalletFromLeft = (TextView) findViewById(R.id.walletFromLeft);
        WalletToLeft = (TextView) findViewById(R.id.walletToLeft);
        WalletFromRight = (LinearLayout) findViewById(R.id.walletFromRight);
        WalletToRight = (LinearLayout) findViewById(R.id.walletToRight);
        // 钱数
        WalletFromMoney = (EditText) findViewById(R.id.walletFromMoney);
        WalletToMoney = (EditText) findViewById(R.id.walletToMoney);
        Trans = (TextView) findViewById(R.id.trans);
    }

    // 检查
    private void check() {
        // 钱数
        String Money = WalletFromMoney.getText().toString();
        if (Money.equals("")) {
            Toast.makeText(getApplicationContext(), "请输入转账钱数", Toast.LENGTH_SHORT).show();
            return;
        } else if (WFrom.equals(WTo)) {
            Toast.makeText(getApplicationContext(), "相同账户之间不能转账", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Float transMoney = Float.parseFloat(Money);
            Float cash = Float.parseFloat(DataCenter.cashMoney);
            Float debit = Float.parseFloat(DataCenter.debitMoney);
            Float credit = Float.parseFloat(DataCenter.creditMoney);
            String FromMoney = "";
            String ToMoney = "";

            // 现金转到储蓄卡
            if (WFrom.equals("cash") && WTo.equals("debit")) {
                // 校验钱数
                if (transMoney > cash) {
                    Toast.makeText(getApplicationContext(), "账户余额不足，请核实金额！当前余额￥" + cash, Toast.LENGTH_SHORT).show();
                    return;
                }
                FromMoney = df.format(cash - transMoney) + "";
                ToMoney = df.format(debit + transMoney) + "";
            } else if (WFrom.equals("cash") && WTo.equals("credit")) {
                // 判断信用卡时候还清
                if (transMoney > credit) {
                    Toast.makeText(getApplicationContext(), "仅需￥" + credit + "就可还清信用卡,请核实后转账", Toast.LENGTH_SHORT).show();
                    return;
                }
                FromMoney = df.format(cash - transMoney) + "";
                ToMoney = df.format(credit - transMoney) + "";
            } else if (WFrom.equals("debit") && WTo.equals("cash")) {
                // 校验钱数
                if (transMoney > debit) {
                    Toast.makeText(getApplicationContext(), "账户余额不足，请核实金额！当前余额:￥" + debit, Toast.LENGTH_SHORT).show();
                    return;
                }
                FromMoney = df.format(debit - transMoney) + "";
                ToMoney = df.format(cash + transMoney) + "";
            } else if (WFrom.equals("debit") && WTo.equals("credit")) {
                // 判断信用卡时候还清
                if (transMoney > credit) {
                    Toast.makeText(getApplicationContext(), "仅需￥" + credit + "就可还清信用卡,请核实后转账", Toast.LENGTH_SHORT).show();
                    return;
                }
                FromMoney = df.format(debit - transMoney) + "";
                ToMoney = df.format(credit - transMoney) + "";
            } else if (WFrom.equals("credit") && WTo.equals("cash")) {
                FromMoney = df.format(credit + transMoney) + "";
                ToMoney = df.format(cash + transMoney) + "";
            } else if (WFrom.equals("credit") && WTo.equals("debit")) {
                FromMoney = df.format(credit + transMoney) + "";
                ToMoney = df.format(debit + transMoney) + "";
            }
            // 更新Bmob
            updateWallet(WFrom, FromMoney, WTo, ToMoney,Money);
        }
    }

    // 更新用户账本
    private void updateWallet(final String fromType, final String fromMoney, final String toType, String toMoney, final String money) {
        AWallet wallet = new AWallet();
        String from = "";
        String to = "";
        // 现金转到储蓄卡
        if (fromType.equals("cash") && toType.equals("debit")) {
            from = "现金";
            to = "储蓄卡";
            wallet.setCash_money(fromMoney);
            wallet.setDebit_money(toMoney);
            DataCenter.cashMoney = fromMoney;
            DataCenter.debitMoney = toMoney;
        } else if (fromType.equals("cash") && toType.equals("credit")) {
            from = "现金";
            to = "信用卡";
            wallet.setCash_money(fromMoney);
            wallet.setCredit_money(toMoney);
            DataCenter.cashMoney = fromMoney;
            DataCenter.creditMoney = toMoney;
        } else if (fromType.equals("debit") && toType.equals("cash")) {
            from = "储蓄卡";
            to = "现金";
            wallet.setDebit_money(fromMoney);
            wallet.setCash_money(toMoney);
            DataCenter.debitMoney = fromMoney;
            DataCenter.cashMoney = toMoney;
        } else if (fromType.equals("debit") && toType.equals("credit")) {
            from = "储蓄卡";
            to = "信用卡";
            wallet.setDebit_money(fromMoney);
            wallet.setCredit_money(toMoney);
            DataCenter.debitMoney = fromMoney;
            DataCenter.creditMoney = toMoney;
        } else if (fromType.equals("credit") && toType.equals("cash")) {
            from = "信用卡";
            to = "现金";
            wallet.setCredit_money(fromMoney);
            wallet.setCash_money(toMoney);
            DataCenter.creditMoney = fromMoney;
            DataCenter.cashMoney = toMoney;
        } else if (fromType.equals("credit") && toType.equals("debit")) {
            from = "信用卡";
            to = "储蓄卡";
            wallet.setCredit_money(fromMoney);
            wallet.setDebit_money(toMoney);
            DataCenter.creditMoney = fromMoney;
            DataCenter.debitMoney = toMoney;
        }
        final String finalTo = to;
        final String finalFrom = from;
        wallet.update(DataCenter.walletId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    String month = TimeCenter.getCurrenceDate().substring(5,7);
                    addTransRecord(fromType,"out","转出",month,money,"zhuanchu","转出到"+ finalTo);
                    addTransRecord(toType,"in","转入",month,money,"zhuanru","来自"+ finalFrom);
                    Toast.makeText(getApplicationContext(), "转账成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 记账

    // 增加转账记录

    private void addTransRecord(String wallet, String way, String name, String month, String money, String logo,String remark) {
        ATrans trans = new ATrans();
        trans.setUser_id(user_id);
        trans.setTrans_logo(logo);
        trans.setTrans_month(month);
        trans.setTrans_name(name);
        trans.setTrans_wallet(wallet);
        trans.setTrans_way(way);
        trans.setTrans_money(money);
        trans.setTrans_remark(remark);
        trans.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(getApplicationContext(), "转账记录成功", Toast.LENGTH_SHORT).show();
                    finish();
                    setResult(1);
                } else {
                    Toast.makeText(getApplicationContext(), "转账记录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
