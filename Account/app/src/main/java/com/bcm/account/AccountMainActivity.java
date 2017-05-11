package com.bcm.account;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bcm.account.fragment.AddFragment;
import com.bcm.account.fragment.DetailsFragment;
import com.bcm.account.fragment.ReportFragment;
import com.bcm.account.fragment.SelfFragment;
import com.bcm.account.fragment.WalletFragment;
import com.bcm.account.tools.InterfaceCenter;
import com.bcm.account.tools.NoScrollViewPager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.yinglan.alphatabs.AlphaTabView;
import com.yinglan.alphatabs.AlphaTabsIndicator;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class AccountMainActivity extends FragmentActivity implements InterfaceCenter.refreshListener{
    private TextView Tip;
    private AlphaTabView tabView1, tabView2,tabView3,tabView4, tabView5;
    protected int mWidth;
    private DetailsFragment detailsFragment = new DetailsFragment();
    private ReportFragment reportFragment = new ReportFragment();
    private WalletFragment walletFragment = new WalletFragment();
    private SelfFragment selfFragment = new SelfFragment();
    private AddFragment addFragment = new AddFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Fresco.initialize(this);
        setContentView(R.layout.activity_account_main);
        bindView();
        getDataFromBmob();
        initData();
        InterfaceCenter.setInterface(this);
    }

    private void bindView(){
//        Tip = (TextView) findViewById(R.id.tip);
    }

    // 初始化数据

    private void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        NoScrollViewPager viewPager = (NoScrollViewPager) findViewById(R.id.noscrollpager);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        AlphaTabsIndicator alphaIndicator = (AlphaTabsIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);
        tabView1 = (AlphaTabView) findViewById(R.id.AlphaView1);
        tabView2 = (AlphaTabView) findViewById(R.id.AlphaView2);
        tabView3 = (AlphaTabView) findViewById(R.id.AlphaView3);
        tabView4 = (AlphaTabView) findViewById(R.id.AlphaView4);
        tabView5 = (AlphaTabView) findViewById(R.id.AlphaView5);

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
//                        Tip.setText("成功连接Bmob："+tips.optString("test"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error:"+e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    public void refreshDetail() {
//        Toast.makeText(getApplicationContext(),"activity",Toast.LENGTH_SHORT).show();
        detailsFragment.getDateFromBmob();
    }

    @Override
    public void refreshWallet() {
        walletFragment.getDataFromBmob();
    }

    @Override
    public void refreshReport() {
        reportFragment.getDataFromBmob();
    }

    private class MainAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(detailsFragment);
            fragments.add(walletFragment);
            fragments.add(addFragment);
            fragments.add(reportFragment);
            fragments.add(selfFragment);

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
