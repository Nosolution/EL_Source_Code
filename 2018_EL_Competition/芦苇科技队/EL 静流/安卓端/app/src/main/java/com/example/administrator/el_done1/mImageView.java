package com.example.administrator.el_done1;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018-5-15.
 */

public class mImageView extends android.support.v7.widget.AppCompatImageView {
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public mImageView(Context context) {
        super(context);
    }

    public mImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
