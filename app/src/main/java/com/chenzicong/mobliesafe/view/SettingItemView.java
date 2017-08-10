package com.chenzicong.mobliesafe.view;

import android.content.Context;
import android.content.res.TypedArray;
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
    private String mDestitle;
    private String mDesoff;
    private String mDesno;


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
        //获取自定义已经原生属性的操作
        initAttrs(context,attrs);
        mTv_title.setText(mDestitle);
    }

    /**
     * 返回属性集合中自定义属性的属性值
     * @param attrs  构造方法中维护好的属性集合
     */
    private void initAttrs(Context context,AttributeSet attrs) {
        TypedArray SettingItemView = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        mDestitle = SettingItemView.getString(R.styleable.SettingItemView_destitle);
        mDesno = SettingItemView.getString(R.styleable.SettingItemView_desno);
        mDesoff = SettingItemView.getString(R.styleable.SettingItemView_desoff);


    }

    public boolean isCheck(){
        return mCheckBoxb_box.isChecked();
    }
    public void setCheck(boolean isCheck){
        mCheckBoxb_box.setChecked(isCheck);
        if(isCheck){
        mTv_des.setText(mDesno);
        }else {
            mTv_des.setText(mDesoff);
        }
    }

}
