package com.bcm.account.self_fragment_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class activity_self_forget extends AppCompatActivity {
    EditText phone,sms,pass,againpass;
    TextView find,back;
    Button finish;
    String passnum,againpassnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_forget);
        phone=(EditText)findViewById(R.id.forget_setphone);
        sms=(EditText)findViewById(R.id.forget_sms);
        pass=(EditText)findViewById(R.id.forget_setnewpass);
        againpass=(EditText)findViewById(R.id.forget_setpassagain);
        find = (TextView)findViewById(R.id.forget_find);
        finish=(Button)findViewById(R.id.forget_finish);
        back = (TextView)findViewById(R.id.forget_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.requestSMSCode(phone.getText().toString(),"毕业记账验证码", new QueryListener<Integer>() {

                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        if(ex==null){//验证码发送成功
                            find.setText("已发送");
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"发送失败: "+ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passnum=pass.getText().toString();
                againpassnum=againpass.getText().toString();
                if(passnum.equals(againpassnum))
                {
                    BmobUser.resetPasswordBySMSCode(sms.getText().toString(),pass.getText().toString(), new UpdateListener() {
                        @Override
                        public void done(BmobException ex) {
                            if(ex==null){
                                Toast.makeText(getApplicationContext(),"密码重置成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"重置失败："+ex.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
