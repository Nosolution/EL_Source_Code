package com.fantasticfour.elcontestproject.WNoise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoisePreset;


/**
 * Created by 12509 on 2018/4/20.
 */

public class WNoiseSceneLoad2 extends DialogFragment {

    private String title;

    //private String[] items;

    private List<String> itemsList = new ArrayList<>();

    private List<WNoisePreset> mWNoistPresetList;

    private DialogInterface.OnClickListener onClickListener;

    private DialogInterface.OnClickListener positiveCallback;

    public interface CallBackLoad{
        void onClickSceneLoad(WNoisePreset wNoisePreset, Boolean isLoad);
    }

    public void show(String title1, List<WNoisePreset> wNoisePresetList, DialogInterface.OnClickListener onClickListener,
                     DialogInterface.OnClickListener positiveCallback, FragmentManager fragmentManager) {

        this.title = title1;
        this.mWNoistPresetList = wNoisePresetList;
        this.onClickListener = onClickListener;
        this.positiveCallback = positiveCallback;
        show(fragmentManager, "SingleChoiceDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        for(WNoisePreset wNoisePreset : mWNoistPresetList){
            itemsList.add(wNoisePreset.m_Name);
        }
        String[] items = itemsList.toArray(new String[itemsList.size()]);
        builder.setTitle(title).setSingleChoiceItems(items, 0, onClickListener)
                .setPositiveButton("确定", positiveCallback);
        return builder.create();
    }

}
