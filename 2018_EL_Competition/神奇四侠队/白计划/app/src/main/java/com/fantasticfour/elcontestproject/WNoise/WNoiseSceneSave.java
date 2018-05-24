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

import com.fantasticfour.elcontestproject.R;

/**
 * Created by 12509 on 2018/4/18.
 */

public class WNoiseSceneSave extends DialogFragment {

    private String m_Name = "null";
    private boolean m_BeSave = false;
    public interface CallbackSave{
        void onClickSceneSave(String sceneName, Boolean isSave);
    }


    private CallbackSave callback;

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, "ViewDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceStates){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.save_wnoise, null);
        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callback != null){
                            EditText et_scene_name = (EditText) view.findViewById(R.id.scene_name);
                            m_Name=et_scene_name.getText().toString();
                            m_BeSave=true;
                        }
                    }
                })
                ;
        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                ;
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof CallbackSave){
            callback = (CallbackSave) context;
        }else{
            throw new RuntimeException(context.toString()+"must implement Callback");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        callback.onClickSceneSave(m_Name, m_BeSave);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        callback = null;
    }


}
