package com.example.administrator.el_done1;
        import android.app.Activity;
        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.os.Environment;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.io.File;

public class jbmusic extends Activity {
    private MediaPlayer mediaPlayer;//MediaPlayer对象
    private boolean isPause=false;//是否暂停
    private File file;//要播放的文件
    private TextView hint;//声明提示信息的文本框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jbmusic);
        //获取各功能按钮
        final Button button=(Button)findViewById(R.id.button);//播放

        hint=(TextView)findViewById(R.id.textView);
        if(!isFileExist()){
            button.setEnabled(false);
        }
        //对MediaPlayer对象添加事件监听，当播放完成时重新开始音乐播放
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play();
            }
        });
        //对播放按钮进行事件监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                if(isPause){
                    button.setText("暂停");
                    isPause=false;
                }
                if(mediaPlayer.isPlaying()&&!isPause){
                    mediaPlayer.pause();
                    isPause=true;
                    ((Button)v).setText("继续");
                    hint.setText("暂停播放音频....");
                    button.setEnabled(true);
                }else{
                    mediaPlayer.start();
                    ((Button)v).setText("暂停");
                    hint.setText("继续播放音频....");
                    button.setEnabled(false);
                }
                button.setEnabled(true);
            }
        });
    }
    @Override
    protected void onDestroy() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        super.onDestroy();
    }
    //判断文件是否存在
    private boolean  isFileExist(){
        file=new File(Environment.getExternalStorageDirectory()+File.separator+"myMP32.mp3");
        if(file.exists()){
            //  mediaPlayer=new MediaPlayer();
            mediaPlayer=MediaPlayer.create(this,R.raw.my);
            try {
//                mediaPlayer.setDataSource(file.getAbsolutePath());
//                mediaPlayer.prepare();//预加载音频
//                mediaPlayer.start();//播放音乐
            }catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(this,"file exist",Toast.LENGTH_LONG).show();
            return true;
        }else{
            Toast.makeText(this,"file don't exist",Toast.LENGTH_LONG).show();
        }
        return false;
    }
    //播放音乐的方法
    private void play(){
        try{
            mediaPlayer.reset();//从新设置要播放的音乐
            //           mediaPlayer.setDataSource(file.getAbsolutePath());
//            mediaPlayer.prepare();//预加载音频
            mediaPlayer=MediaPlayer.create(this,R.raw.my);
            mediaPlayer.start();//播放音乐
            hint.setText("开始播放");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("err",e.getMessage());
        }
        return ;
    }
}