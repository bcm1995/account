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

import com.bcm.account.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class activity_self_register_phonenum extends AppCompatActivity {
    Button next;
    EditText phone;
    TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phonenum);
        next = (Button)findViewById(R.id.register_next);
        phone = (EditText)findViewById(R.id.register_phonenumber);
        back =(TextView)findViewById(R.id.phonenum_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String num = phone.getText().toString();
                BmobSMS.requestSMSCode(num,"毕业记账验证码", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        if(ex==null){//验证码发送成功
                            Intent intent = new Intent(getApplicationContext(),activity_self_register_sms.class);
                            intent.putExtra("phone_num",num);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"失败"+ex.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}
