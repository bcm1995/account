package com.bcm.account.self_fragment_page;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.AccountMainActivity;
import com.bcm.account.bmobbean.myUser;
import com.bcm.account.R;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.BmobUser;

public class activity_self_signup extends AppCompatActivity {
    EditText id,password;
    TextView fpassword,register;
    Button login;
    String username;
    String pass;
    myUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        id = (EditText) findViewById(R.id.sign_id);
        password=(EditText)findViewById(R.id.sign_pass);
        login=(Button)findViewById(R.id.sign_login);
        fpassword=(TextView)findViewById(R.id.signup_forgetpass);
        register=(TextView)findViewById(R.id.signup_register);
        user = myUser.getCurrentUser(myUser.class);
        if(user != null){
            Intent intent = new Intent(getApplicationContext(), AccountMainActivity.class);
            startActivity(intent);
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username= id.getText().toString();
                pass = password.getText().toString();
                BmobUser.loginByAccount(username, pass, new LogInListener<myUser>() {
                    @Override
                    public void done(myUser user, BmobException e) {
                        if(user!=null){
                            Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AccountMainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });
        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),activity_self_forget.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),activity_self_register_phonenum.class);
                startActivity(intent);
            }
        });
    }
}
