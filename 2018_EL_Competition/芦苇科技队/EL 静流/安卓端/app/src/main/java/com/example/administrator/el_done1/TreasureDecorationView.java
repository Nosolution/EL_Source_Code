package com.example.administrator.el_done1;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018-5-14.
 */


public class TreasureDecorationView extends mImageView {
    public final String type = "TreasureDecorationButton";
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreasureDecorationView(Context context) {
        super(context);
    }
    public TreasureDecorationView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public TreasureDecorationView(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
    }

}
