package com.bcm.account.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcm.account.R;
import com.bcm.account.adapter.IconAdapter;
import com.bcm.account.newsbean.IconBean;
import com.bcm.account.tools.DataCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bean on 2017/4/10.
 */

public class AddFragment extends Fragment {
    private View view;
    private GridView mGridView;
    private IconAdapter adapter;
    private List<IconBean> iconBeanList ;
    private ImageView iconImg;
    private TextView iconText,inType,outType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_add_fragment, null);
        bindView();
        initData("in");

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               setChoice(i);
            }
        });
        setInOut("in");
        inType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInOut("in");
                initData("in");
            }
        });
        outType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInOut("out");
                initData("out");
            }
        });
        return view;
    }
    private void bindView(){
        mGridView = (GridView) view.findViewById(R.id.gridview);
        iconImg = (ImageView) view.findViewById(R.id.iconImg);
        iconText = (TextView) view.findViewById(R.id.iconText);
        inType = (TextView) view.findViewById(R.id.inType);
        outType = (TextView) view.findViewById(R.id.outType);
    }

    private void setInOut(String type){
        GradientDrawable inColor = (GradientDrawable)
                inType.getBackground();
        GradientDrawable outColor = (GradientDrawable)
                outType.getBackground();
        if(type.equals("in")){
            // 设置in颜色
            inColor.setColor(Color.parseColor("#ffffff"));
            inType.setTextColor(Color.parseColor("#3CCB8D"));
            outColor.setColor(Color.parseColor("#3CCB8D"));
            outType.setTextColor(Color.parseColor("#ffffff"));
        }else if(type.equals("out")){
            // 设置out颜色
            inColor.setColor(Color.parseColor("#3CCB8D"));
            inType.setTextColor(Color.parseColor("#ffffff"));
            outColor.setColor(Color.parseColor("#ffffff"));
            outType.setTextColor(Color.parseColor("#3CCB8D"));
        }
    }

    private void setChoice(int position){
        iconImg.setImageResource(iconBeanList.get(position).icon);
        iconText.setText(iconBeanList.get(position).name);
    }

    private void initData(String type){
        if(type.equals("in")){
            iconBeanList = new ArrayList<>();
            IconBean iconBean;
            for(int i = 0 ; i < DataCenter.inType.length ; i++){
                iconBean = new IconBean();
                iconBean.icon = DataCenter.inTypeImg[i];
                iconBean.name = DataCenter.inType[i];
                iconBeanList.add(iconBean);
            }
            showGridView(iconBeanList);
            setChoice(0);
        }else if(type.equals("out")){
            iconBeanList = new ArrayList<>();
            IconBean iconBean;
            for(int i = 0 ; i < DataCenter.outType.length ; i++){
                iconBean = new IconBean();
                iconBean.icon = DataCenter.outTypeImg[i];
                iconBean.name = DataCenter.outType[i];
                iconBeanList.add(iconBean);
            }
            showGridView(iconBeanList);
            setChoice(0);
        }

    }

    private void showGridView(List<IconBean> LI){
        adapter = new IconAdapter(getActivity(),LI);
        mGridView.setAdapter(adapter);
    }

}
