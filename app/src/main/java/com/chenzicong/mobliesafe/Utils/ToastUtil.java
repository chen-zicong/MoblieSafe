package com.chenzicong.mobliesafe.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 82023 on 2017/8/8.
 */

public class ToastUtil {
    public static void show(Context context , String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
