package com.fantasticfour.elcontestproject.WNoise;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fantasticfour.elcontestproject.Instance.Ins_WNoise.WNoisePreset;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import java.util.List;

/**
 * Created by 12509 on 2018/4/19.
 */


public class WNoiseSceneLoad extends DialogFragment {
    private List<WNoisePreset> PresetList = Instance.s_WNoiseController.GetPresetList();

    public interface CallbackLoad{
        void onClickLoad(String sceneName, Boolean isSave);
    }

    private CallbackLoad callback;

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "ViewDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle saveIntenceStates){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.save_wnoise, null);
        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callback != null){
                            EditText et_scene_name = (EditText) view.findViewById(R.id.scene_name);
                            callback.onClickLoad(et_scene_name.getText().toString(), Boolean.TRUE);
                        }
                    }
                })
        ;
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_scene_name = (EditText) view.findViewById(R.id.scene_name);
                        callback.onClickLoad("haha", false);
                    }
                })
        ;
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof CallbackLoad){
            callback = (CallbackLoad) context;
        }else{
            throw new RuntimeException(context.toString()+"must implement Callback");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }


}
