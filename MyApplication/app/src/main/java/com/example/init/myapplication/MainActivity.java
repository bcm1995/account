package com.example.init.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this,"hello world",Toast.LENGTH_LONG).show();
        Toast.makeText(this,"hello world1",Toast.LENGTH_LONG).show();
        Toast.makeText(this,"hello world2",Toast.LENGTH_LONG).show();
        Toast.makeText(this,"hello world3",Toast.LENGTH_LONG).show();
        Toast.makeText(this,"hello world4",Toast.LENGTH_LONG).show();
        Toast.makeText(this,"hello world5",Toast.LENGTH_LONG).show();
    }
}
