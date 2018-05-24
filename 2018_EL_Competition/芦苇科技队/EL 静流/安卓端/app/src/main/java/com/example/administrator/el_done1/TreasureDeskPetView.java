package com.example.administrator.el_done1;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018-5-14.
 */

public class TreasureDeskPetView extends mImageView {
    public final String type = "TreasureDeskPetButton";
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreasureDeskPetView(Context context) {
        super(context);
    }
    public TreasureDeskPetView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }
    public TreasureDeskPetView(Context context, AttributeSet attributeSet, int defStyle){
        super(context,attributeSet,defStyle);
    }

}
