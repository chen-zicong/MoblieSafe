package com.chenzicong.mobliesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.chenzicong.mobliesafe.R;

/**
 * Created by 82023 on 2017/8/10.
 */

public class TitleView extends RelativeLayout {
    public TitleView(Context context) {
        this(context,null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.activity_main_titlebar,this);



    }
}
