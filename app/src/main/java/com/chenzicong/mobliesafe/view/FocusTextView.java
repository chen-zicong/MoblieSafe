package com.chenzicong.mobliesafe.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by 82023 on 2017/8/9.
 */

class FocusTextView extends android.support.v7.widget.AppCompatTextView {
 //简单的自定义控件，使得
    public FocusTextView(Context context) {
        super(context);

    }

    public FocusTextView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);

    }

    public FocusTextView(Context context,AttributeSet attributeSet,int defStyle) {
        super(context,attributeSet,defStyle);

    }


    @Override
    public boolean isFocused() {
        return true;
    }
}
