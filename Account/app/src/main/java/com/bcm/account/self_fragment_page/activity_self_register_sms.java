package com.bcm.account.self_fragment_page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.AccountMainActivity;
import com.bcm.account.R;
import com.bcm.account.bmobbean.myUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class activity_self_register_sms extends AppCompatActivity {
    EditText sms,username,password;
    TextView back;
    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
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
                            Intent intent1 = new Intent(getApplicationContext(), AccountMainActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent1);
                            Toast.makeText(getApplicationContext(),"登陆成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"失败:" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

}
