package com.fantasticfour.elcontestproject.User;

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
import com.fantasticfour.elcontestproject.Instance.Ins_Preferences.User;
import com.fantasticfour.elcontestproject.Instance.Instance;
import com.fantasticfour.elcontestproject.R;

import cn.bmob.v3.exception.BmobException;

public class UserRegisterActivity extends AppCompatActivity implements  BackendDAO.RegisterCallback {
    TextView m_tv_Status;
    Button m_btn_Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_register);

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
        tv_Middle.setText("用户注册");
    }
    private void DealWithView(){
        //邮箱框
        final EditText et_Email = findViewById(R.id.user_et_activity_register_email);

        //用户名框
        final EditText et_Username = findViewById(R.id.user_et_activity_register_user_name);

        //密码框
        final EditText et_Password = findViewById(R.id.user_et_activity_register_password);

        //确认注册按钮
        m_btn_Confirm = findViewById(R.id.user_btn_activity_register_confirm);

        //下部状态栏
        m_tv_Status = findViewById(R.id.user_tv_activity_register_status);

        m_btn_Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User(et_Username.getText().toString(), et_Email.getText().toString(), et_Password.getText().toString(), User.ProfileState.NoProfile, "");
                m_btn_Confirm.setEnabled(false);
                m_tv_Status.setText("正在注册中，请稍后");
                Instance.s_BackendDAO.RegisterNewAccount(newUser, UserRegisterActivity.this);
            }
        });
    }

    @Override
    public void RegisterSuccess(){
        m_tv_Status.setText("注册成功，请查收验证邮件");
    }
    @Override
    public void RegisterError(BmobException e){
        m_tv_Status.setText(e.toString());
        m_btn_Confirm.setEnabled(true);
    }
}
