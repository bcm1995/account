package com.bcm.account.self_fragment_page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bcm.account.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.bmobbean.myUser;
import cn.bmob.v3.BmobUser;

public class activity_self_modify extends AppCompatActivity {
    TextView back,name,finish;
    EditText content_nonumber;
    EditText content_number;
    String receive;
    Integer type;
    String details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_person_modify);
        back=(TextView)findViewById(R.id.modify_back);
        name=(TextView)findViewById(R.id.modify_name);
        finish=(TextView)findViewById(R.id.modify_finish);
        content_nonumber=(EditText)findViewById(R.id.modify_details1);
        content_number=(EditText)findViewById(R.id.modify_details2);
        Intent intent = getIntent();
        receive=intent.getStringExtra("title");
        checkstyle();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name.setText(receive);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUser newUser = new myUser();
                if(type==1)
                {
                    details=content_nonumber.getText().toString();
                    newUser.setUsername(details);
                }
                else if(type==2)
                {
                    details=content_nonumber.getText().toString();
                    if(details.equals("")) {
                        finish();
                        Toast.makeText(getApplicationContext(),"未更改信息",Toast.LENGTH_SHORT).show();
                    }
                    newUser.setSignDetails(details);
                }
                else if(type==3)
                {
                    details=content_number.getText().toString();
                    if(details.equals("")) {
                        finish();
                        Toast.makeText(getApplicationContext(),"未更改信息",Toast.LENGTH_SHORT).show();
                    }
                    newUser.setAge(details);
                }
                else if(type==4)
                {
                    details=content_nonumber.getText().toString();
                    if(details.equals("")) {
                        finish();
                        Toast.makeText(getApplicationContext(),"未更改信息",Toast.LENGTH_SHORT).show();
                    }
                    newUser.setLocation(details);
                }
                BmobUser bmobUser = BmobUser.getCurrentUser(myUser.class);
                newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"更新用户信息失败:" + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    public  void checkstyle()
    {
        if(receive.equals("修改昵称"))
        {
           type=1;
           content_number.setVisibility(View.GONE);
        }
        else if(receive.equals("修改签名"))
        {
            type=2;
            content_number.setVisibility(View.GONE);
        }
        else if(receive.equals("修改年龄"))
        {
            type=3;
            content_nonumber.setVisibility(View.GONE);
        }
        else if(receive.equals("修改所在地"))
        {
            type=4;
            content_number.setVisibility(View.GONE);
        }
    }
}
