package com.chenzicong.mobliesafe.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenzicong.mobliesafe.R;
import com.chenzicong.mobliesafe.Utils.SpUtil;
import com.chenzicong.mobliesafe.Utils.StringUtil;
import com.chenzicong.mobliesafe.Utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {
    /**
     * URL地址出错的状态码
     * IO异常状态码
     * JSON解析出错状态码
     */
    private static final int URL_ERROR = 102;
    private static final int IO_ERROR = 103;
    private static final int JSON_ERROR = 104;
    private TextView mTv_version_name;
    private int mLocaversionCode;
    /**
     * 更新新版本的状态码
     */
    protected static final int UPDATE_VERSION = 100;
    /**
     * 进入主页面的状态码
     */
    protected static final int ENTER_HOME = 101;

    private String mVersionDes;
    private String mDownloadUrl;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_VERSION:
                    //弹出对话框，提示用户更新
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    //进入应用程序主界面，activity跳转
                    enterHome();
                    break;
                case URL_ERROR:
                    ToastUtil.show(SplashActivity.this, "URL异常");
                    enterHome();
                    break;
                case IO_ERROR:
                    ToastUtil.show(SplashActivity.this, "读取异常");
                    enterHome();
                    break;
                case JSON_ERROR:
                    ToastUtil.show(SplashActivity.this, "json解析异常");
                    enterHome();
                    break;

            }
        }
    };
    private RelativeLayout mRl_root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splah);
        //初始化UI
        iniUI();
        //初始化数据
        initData();
        //初始化开场动画
        initAnimation();


    }
    /**
     * 初始化动画
     */
 protected  void initAnimation(){
     AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f,1);
     alphaAnimation.setDuration(3000);
     mRl_root.startAnimation(alphaAnimation);
 }
    /**
     * 获取UI的方法
     */
    private void iniUI() {
        mTv_version_name = (TextView) findViewById(R.id.tv_version_name);
        mRl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    /**
     * 弹出对话框，提示用户更新
     */
    protected void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.update_icon);
        builder.setTitle("  版本更新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("赶紧更新吧", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("czc", "onClick: nihao");
                downloadApk();

            }
        });
        builder.setNegativeButton("别急", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enterHome();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                enterHome();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 下载apk
     */
    protected void downloadApk() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "mobilefase.apk";
        Log.i("czc", "downloadApk: "+mDownloadUrl);
        Log.i("czc", "downloadApk: "+path);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                //下载成功
                Log.i("czc", "onSuccess: 下载成功");
                File file = responseInfo.result;
               installApk(file);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                Log.i("czc", "onFailure: 下载失败");
                //下载失败
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.i("czc", "onStart: 开始下载");
                //开始下载
            }

            //下载过程中的方法(下载apk的总大小当前下载位置，是否在下载
            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                Log.i("czc", "onLoading: 正在下载");
                //下载过程
            }
        });
    }


    /**
     * 进入应用程序主界面
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActiviity.class);
        startActivity(intent);
        //在开启一个新的界面后，将导航界面关闭
        finish();

    }
    /**
     *  file  打开安装界面，让用户安装已下载的应用
     */
    protected void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(this, "com.chenzicong.mobliesafe", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivityForResult(intent,0);
    }
//这里回调方法还有问题，先挖个坑
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0) {
            enterHome();
        }
    }

    /**
     * 获取数据方法
     */
    private void initData() {
        //1.应用版本名称
        mTv_version_name.setText("bate:" + getVersionName());
        //检测（本地版本号和服务器版本号的对比）是否更新，如果有更新，提示用户下载
        //2.获取本地版本号
        mLocaversionCode = getVersionCode();
        //3.获取服务器的版本号
        if(SpUtil.getBoolean(this,ConstentValue.OPEN_UPDATE,false)) {
            checkVersion();
        }else {
            //延时进入home
            mHandler.sendEmptyMessageDelayed(ENTER_HOME,3000);
        }
    }

    /**
     * 检测版本号
     */
    private void checkVersion() {
        new Thread(new Runnable() {

            private Message mMsg;

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                //发送请求数据，参数则为请求json的链接地址
                try {
                    mMsg = Message.obtain();
                    //1.封装一个URL地址
                    URL url = new URL("http://192.168.199.105:80/update.json");
                    //2.开启一个链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //3.设置常见请求参数（请求头）
                    //请求超时
                    connection.setConnectTimeout(2000);
                    //读取超时
                    connection.setReadTimeout(2000);
                    //默认是get请求方式，不用写
                    //connection.setRequestMethod("POST");
                    //获取请求成功的响应码
                    if (connection.getResponseCode() == 200) {
                        //以流的形式，将数据获取下来
                        InputStream is = connection.getInputStream();
                        //将流转换成字符串（工具类封装）
                        String json = StringUtil.StreamToString(is);
                        Log.i("czc", "run: " + json);

                        JSONObject jsonObject = new JSONObject(json);

                        String versionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");
                        Log.i("czc", "run: " + versionName);
                        Log.i("czc", "run: " + mVersionDes);
                        Log.i("czc", "run: " + versionCode);
                        Log.i("czc", "run: " + mDownloadUrl);
                        // 比对版本号（服务器版本号》本地版本号，提示用户更新
                        if (mLocaversionCode < Integer.parseInt(versionCode)) {
                            mMsg.what = UPDATE_VERSION;
                        } else {
                            mMsg.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    mMsg.what = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    mMsg.what = IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    mMsg.what = JSON_ERROR;
                } finally {
                    //指定睡眠时间，请求网络的时长超过4秒不作处理
                    //请求网络事件的时长小于4秒，强制让其睡眠满4秒钟
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime < 3000) {
                        try {
                            Thread.sleep(3000 - (endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(mMsg);
                }
            }
        }).start();
    }

    /**
     * 返回版本号
     *
     * @return
     */
    private int getVersionCode() {
        //1.包管理者对象 PackageManager
        PackageManager pm = getPackageManager();
        //2.从包管理者对象中，获取指定包名的基本信息（版本号，版本名称),传0代表获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            //获取版本名称
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回版本名称
     *
     * @return
     */
    private String getVersionName() {
        //1.包管理者对象 PackageManager
        PackageManager pm = getPackageManager();
        //2.从包管理者对象中，获取指定包名的基本信息（版本号，版本名称),传0代表获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            //获取版本名称
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
