package com.chenzicong.mobliesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenzicong.mobliesafe.R;

/**
 * Created by 82023 on 2017/8/9.
 */

public class SettingItemView extends RelativeLayout {

    private CheckBox mCheckBoxb_box;
    private TextView mTv_des;
    private TextView mTv_title;

    public SettingItemView(Context context) {
        this(context,null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.activity_setting_item,this);
        mTv_title = findViewById(R.id.tv_title);
        mTv_des = findViewById(R.id.tv_des);
        mCheckBoxb_box = findViewById(R.id.cb_box);
    }
    public boolean isCheck(){
        return mCheckBoxb_box.isChecked();
    }
    public void setCheck(boolean isCheck){
        mCheckBoxb_box.setChecked(isCheck);
        if(isCheck){
        mTv_des.setText("自动更新已开启");
        }else {
            mTv_des.setText("自动更新已关闭");
        }
    }

}
