package Managers;

import android.app.Activity;

import io.github.yuweiguocn.lib.squareloading.SquareLoading;
import scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView;

public class LoadingManager {
    private LoadingManager() {

    }

    public static LoadingManager getLoadingManager() {
        return new LoadingManager();
    }

    public Kawaii_LoadingView getkawaii_loadingView(Activity activity, int R_id) {
        return (Kawaii_LoadingView) activity.findViewById(R_id);
    }

    public Kawaii_LoadingView kawaii_loading_start(Kawaii_LoadingView kawaii_loadingView) {
        kawaii_loadingView.setVisibility(Kawaii_LoadingView.VISIBLE);
        kawaii_loadingView.startMoving();
        kawaii_loadingView.setTop(1);
        return kawaii_loadingView;
    }

    public Kawaii_LoadingView kawaii_loading_stop(Kawaii_LoadingView kawaii_loadingView) {
        kawaii_loadingView.setVisibility(Kawaii_LoadingView.INVISIBLE);
        kawaii_loadingView.stopMoving();
        kawaii_loadingView.setTop(0);
        return kawaii_loadingView;
    }

    public SquareLoading getSquareLoading(Activity activity, int R_id) {
        return (SquareLoading) activity.findViewById(R_id);
    }

    public SquareLoading square_loading_start(SquareLoading squareLoading) {
        squareLoading.setVisibility(SquareLoading.VISIBLE);
        squareLoading.setTop(1);
        squareLoading.startLayoutAnimation();
        squareLoading.setClipToPadding(true);
        return squareLoading;
    }

    public SquareLoading square_loading_stop(SquareLoading squareLoading) {
        squareLoading.setVisibility(SquareLoading.INVISIBLE);
        squareLoading.setTop(0);
        squareLoading.setClipToPadding(true);
        return squareLoading;
    }
}


//1.
/*github:https://github.com/Carson-Ho/Kawaii_LoadingView
<scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView

            android:id="@+id/Kawaii_LoadingView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"

            android:background="#FFCCFF"
            app:blockColor="#ffffff"

            app:lineNumber="3"
            app:fixBlock_Angle="5"
            app:moveBlock_Angle="20"

            app:blockInterval="8dp"
            app:half_BlockWidth="15dp"
            app:initPosition="0"

            app:isClock_Wise="false"
            app:moveSpeed="500"
            app:move_Interpolator="@android:anim/bounce_interpolator"

            />
*
*
*
*
* */


//2.
/*
*<io.github.yuweiguocn.lib.squareloading.SquareLoading
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#E91E63"
        app:squareColor="@android:color/white"
        app:squareSize="12dp"
        app:squareCorner="2dp"
        app:dividerSize="2dp"
        app:xCount="4"
        app:yCount="3"
        />
*
* */
