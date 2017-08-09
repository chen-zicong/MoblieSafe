package com.chenzicong.mobliesafe.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 82023 on 2017/8/10.
 */

public class SpUtil {
    private static SharedPreferences sp;

    public static void putBoolean(Context context, String key, boolean value) {
        if (sp == null) {
            sp =context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();

    }

    /**
     * 写入boolean变量到sp中
     * @param context    上下文环境
     * @param key       储存接点名称
     * @param defValue  没有此节点的默认值
     * @return  默认值或者此节点读取到的结果
     */
    public static boolean getBoolean(Context context,String key ,boolean defValue){
        //（储存接待呢文件名称，读写方式）
        if(sp==null){
            sp =context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);
    }
}
