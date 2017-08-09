package com.chenzicong.mobliesafe.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chenzicong.mobliesafe.R;
import com.chenzicong.mobliesafe.Utils.SpUtil;
import com.chenzicong.mobliesafe.view.SettingItemView;

/**
 * Created by 82023 on 2017/8/9.
 */

class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //
        initUpdate();


    }


    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_unpdate);
        //获取已有的开关状态
        boolean open_update = SpUtil.getBoolean(this, ConstentValue.OPEN_UPDATE, false);
        //是否选中,根据上一次存储的结果做决定
        siv_update.setCheck(open_update);


        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果之前是选中，点击后变成未选中
                //如果之前是选中，点击后变成选中
                boolean check = siv_update.isCheck();
                siv_update.setCheck(!check);
                SpUtil.putBoolean(getApplicationContext(),ConstentValue.OPEN_UPDATE,!check);
            }
        });
    }
}
