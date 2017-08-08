package com.chenzicong.mobliesafe.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 82023 on 2017/8/8.
 */

public class StringUtil {
    public static String StreamToString(InputStream is) {
        //1.在读取的过程中，将读取的内容存储在缓存中， 然后再一次性转换成字符串返回
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // 独流操作 ，循环
        byte[] buffer = new byte[1024];
        //记录读取数据的临时变量
        int temp ;
        try {
            while ((temp = is.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
                //返回读取的数据
                return bos.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
