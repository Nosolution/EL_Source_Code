package com.example.administrator.el_done1;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018-5-14.
 */


public class TreasureBGView extends mImageView {
    public final String type = "TreasureBGButton";
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TreasureBGView(Context context) {
        super(context);
    }
    public TreasureBGView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public TreasureBGView(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
    }
/*    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        Context context = getContext();
        Toast.makeText((Activity)getContext(),"hint",Toast.LENGTH_SHORT).show();
    }
        无法运行*/
}
