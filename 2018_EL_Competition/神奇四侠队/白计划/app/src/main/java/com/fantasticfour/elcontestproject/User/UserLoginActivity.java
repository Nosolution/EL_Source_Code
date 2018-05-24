package com.fantasticfour.elcontestproject.User;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fantasticfour.elcontestproject.Instance.BackendInteraction.BackendDAO;
import com.fantasticfour.elcontestproject.Instance.BackendInteraction.MyBmobUser;
import com.fantasticfour.elcontestproject.Instance.Ins_Preferences.User;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import cn.bmob.v3.exception.BmobException;

public class UserLoginActivity extends AppCompatActivity implements BackendDAO.LoginCallback{
    public static final int s_LoginSuccessResultCode = 101;
    public static final int s_LoginFailResultCode = 100;
    private User m_User;
    private int m_ResultCode;
    private Button m_btn_Confirm;
    private TextView m_tv_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_login);

        m_ResultCode = s_LoginFailResultCode;
        ModifyDisplay();
        DealWithTitle();
        DealWithView();
    }
    private void ModifyDisplay(){
        Display display = getWindowManager().getDefaultDisplay();
        WindowManager.LayoutParams params = getWindow().getAttributes();
        Point size = new Point();display.getSize(size);
        params.width = (int) (size.x*0.9);
        getWindow().setAttributes(params);
    }
    private void DealWithTitle(){
        //左侧按钮禁用
        Button btn_Left = findViewById(R.id.title_leftbutton);
        btn_Left.setClickable(false);

        //右侧按钮禁用
        Button btn_Right = findViewById(R.id.title_rightbutton);
        btn_Right.setClickable(false);

        //中间文本
        TextView tv_Middle = findViewById(R.id.title_middletext);
        tv_Middle.setText("用户登陆");
    }
    private void DealWithView(){
        //账号框
        final EditText et_Account = findViewById(R.id.user_et_activity_login_account);

        //密码框
        final EditText et_Password = findViewById(R.id.user_et_activity_login_password);

        //确认登陆按钮
        m_btn_Confirm = findViewById(R.id.user_btn_activity_login_confirm);

        //下部状态栏
        m_tv_Status = findViewById(R.id.user_tv_activity_login_status);

        m_btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.m_UserName = et_Account.getText().toString();
                user.m_Password = et_Password.getText().toString();
                user.m_ProfileState = User.ProfileState.Normal;
                m_btn_Confirm.setEnabled(false);
                m_tv_Status.setText("正在登陆中，请稍后");
                Instance.s_BackendDAO.Login(user, UserLoginActivity.this);
            }
        });
    }

    @Override
    public void LoginSuccess(MyBmobUser user){
        m_tv_Status.setText("登陆成功");
        m_ResultCode = s_LoginSuccessResultCode;
        m_User = new User(user.getUsername(), user.getEmail(), "", User.ProfileState.values()[user.getProfileState()], user.getAvatar());

        Intent intent = getIntent();
        intent.putExtra("user",m_User);
        setResult(m_ResultCode, intent);
        finish();
    }

    @Override
    public void LoginError(BmobException e){
        m_btn_Confirm.setEnabled(true);
        m_tv_Status.setText(e.toString());
    }
}
