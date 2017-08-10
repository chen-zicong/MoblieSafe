package com.chenzicong.mobliesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenzicong.mobliesafe.R;
import com.chenzicong.mobliesafe.Utils.SpUtil;
import com.chenzicong.mobliesafe.Utils.ToastUtil;

/**
 * Created by 82023 on 2017/8/8.
 */

class HomeActiviity extends AppCompatActivity {

    protected String[] mTitleStrs;
    private int[] mDrawableIds;
    private GridView mGv_home;
    private AlertDialog mDialog;

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
                        showDialog();
                        break;
                    case 8:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showDialog() {
        //0.判断那本事是否存储密码
        String psd = SpUtil.getString(this, ConstentValue.MOBILE_SAFE_PSD, "");
        if (TextUtils.isEmpty(psd)) {
            //1.初始设置密码的对话框
            showSetPsdDialog();
        } else {
            //2.确认密码对话框
            showConfirmPsdDialog();
        }

    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog() {
        //因为需要自己去定义对话框的显示样式,所以需要调用dialog.setView
        //view是由自己编写的xml转换成的view对象xml--->view;
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.mydialog);
        mDialog = builder.create();
        final View view = View.inflate(this,R.layout.dialog_confirm_psd,null);
        mDialog.setView(view);
        mDialog.show();
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button)view.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
                String psd = et_set_psd.getText().toString();
                if(!TextUtils.isEmpty(psd)){
                    String confirm_psd = SpUtil.getString(getApplicationContext(),ConstentValue.MOBILE_SAFE_PSD,"");
                    if(psd.equals(confirm_psd)){
                        Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                        mDialog.dismiss();
                        SpUtil.putString(getApplicationContext(),ConstentValue.MOBILE_SAFE_PSD,psd);
                    }else {
                        ToastUtil.show(getApplicationContext(),"密码错误");
                    }
                }else {
                    ToastUtil.show(getApplicationContext(),"密码不能为空");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
    }

    /**
     * 设置密码对话框
     */
    private void showSetPsdDialog() {
        //因为需要自己去定义对话框的显示样式,所以需要调用dialog.setView
        //view是由自己编写的xml转换成的view对象xml--->view;
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.mydialog);
        mDialog = builder.create();
        final View view = View.inflate(this,R.layout.dialog_set_psd,null);
        mDialog.setView(view);
        mDialog.show();
        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
                EditText et_confirm_pad = (EditText) view.findViewById(R.id.et_confirm_pad);
                String psd = et_set_psd.getText().toString();
                String confirmPsd = et_confirm_pad.getText().toString();
                if(!TextUtils.isEmpty(psd)&&!TextUtils.isEmpty(confirmPsd)){
                    if(psd.equals(confirmPsd)){
                        Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                        startActivity(intent);
                        mDialog.dismiss();
                        SpUtil.putString(getApplicationContext(),ConstentValue.MOBILE_SAFE_PSD,psd);
                    }else {
                        ToastUtil.show(getApplicationContext(),"密码不一致");
                    }
                }else {
                    ToastUtil.show(getApplicationContext(),"密码不能为空");
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mDialog.dismiss();
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
