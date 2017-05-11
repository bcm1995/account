package com.bcm.account.self_fragment_page;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.AccountMainActivity;
import com.bcm.account.R;
import com.bcm.account.bmobbean.AWallet;
import com.bcm.account.bmobbean.myUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class activity_self_register_sms extends AppCompatActivity {
    EditText sms,username,password;
    TextView back;
    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_register_sms);
        sms = (EditText) findViewById(R.id.register_sms);
        username = (EditText) findViewById(R.id.register_setname);
        password = (EditText) findViewById(R.id.register_setpassword);
        finish = (Button) findViewById(R.id.register_finish);
        back=(TextView)findViewById(R.id.sms_back);
        Intent intent = getIntent();
        final String phone = intent.getStringExtra("phone_num");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser user = new myUser();
                user.setMobilePhoneNumber(phone);//设置手机号码（必填）
                user.setUsername(username.getText().toString());                  //设置用户名，如果没有传用户名，则默认为手机号码
                user.setPassword(password.getText().toString());                  //设置用户密码
                user.signOrLogin(sms.getText().toString(), new SaveListener<myUser>() {
                    @Override
                    public void done(myUser user,BmobException e) {
                        if(e==null){
                            final Intent intent1 = new Intent(getApplicationContext(), activity_self_signup.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            AWallet aWallet = new AWallet();
                            aWallet.setCash_money("0.00");
                            aWallet.setCredit_money("0.00");
                            aWallet.setDebit_money("0.00");
                            aWallet.setJoin_money("0.00");
                            aWallet.setLoan_money("0.00");
                            aWallet.setUser_id(BmobUser.getCurrentUser().getObjectId());
                            aWallet.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(activity_self_register_sms.this, "success", Toast.LENGTH_SHORT).show();
                                        finish();
                                        BmobUser.logOut();
                                        startActivity(intent1);
                                    }else{
                                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"失败:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}
