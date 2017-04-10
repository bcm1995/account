package com.bcm.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class AccountMainActivity extends AppCompatActivity {
    private TextView Tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_main);
        bindView();
        getDataFromBmob();
    }
    private void bindView(){
        Tip = (TextView) findViewById(R.id.tip);
    }
    // 从Bmob 获取数据
    private void getDataFromBmob(){
        BmobQuery<Test> query = new BmobQuery("Test");
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if(e == null){
                    Log.i("array:",jsonArray.toString());
                    try {
                        JSONObject tips = jsonArray.getJSONObject(0);
                        Tip.setText("成功连接Bmob："+tips.optString("test"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
