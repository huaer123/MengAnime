package com.menganime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.interfaces.LoginInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;


/**
 * 文件名：LoginActivity
 * 描    述：登录类
 * 作    者：stt
 * 时    间：2017.01.04
 * 版    本：V1.0.0
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginInterface {
    private Context context;
    private EditText inputUsername, inputPassword;//用户名，密码
    private String username, password;
    private Button loginBtn;
    private TextView forgetPassword;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        context = this;
    }

    @Override
    protected void init() {
        inputUsername = (EditText) findViewById(R.id.login_username);
        inputPassword = (EditText) findViewById(R.id.login_password);
        inputUsername.setText(SharedUtil.getString(this, "userName"));
        inputPassword.setText(SharedUtil.getString(this, "passWord"));
        loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(this);
        forgetPassword = (TextView) findViewById(R.id.login_forget_password);
        forgetPassword.setOnClickListener(this);

    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_button:
                username = inputUsername.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                if (isEmpt()) {
                    MyRequest.loginRequest(this, username, password);
                }
                break;
            case R.id.login_forget_password:
                break;
        }
    }

    private boolean isEmpt() {
        boolean flag = false;
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showToast(context, "请输入用户名");
            flag = false;
        } else if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(context, "请输入密码");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }


    @Override
    public void login(String name, String password) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
