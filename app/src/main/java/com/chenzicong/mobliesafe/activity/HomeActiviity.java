package com.chenzicong.mobliesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenzicong.mobliesafe.R;

/**
 * Created by 82023 on 2017/8/8.
 */

class HomeActiviity extends AppCompatActivity {

    protected String[] mTitleStrs;
    private int[] mDrawableIds;
    private GridView mGv_home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initData();

    }

    private void initData() {
        mTitleStrs = new String[]{
                "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"
        };
        mDrawableIds = new int[]{R.drawable.home_1, R.drawable.home_2, R.drawable.home_3, R.drawable.home_4, R.drawable.home_5,
                R.drawable.home_6, R.drawable.home_7, R.drawable.home_8, R.drawable.home_9};
        mGv_home.setAdapter(new MyAdapter());
        //注册九宫格点击事件
        mGv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    private void initUI() {
        mGv_home = (GridView) findViewById(R.id.gv_name);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return mTitleStrs.length;
        }

        @Override
        public Object getItem(int i) {
            return mTitleStrs[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
            TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            Log.i("czc", "getView: " + i + mTitleStrs[i] + "....");
            tv_title.setText(mTitleStrs[i]);
            iv_icon.setBackgroundResource(mDrawableIds[i]);
            return view;

        }
    }
}
