package com.menganime.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.bean.UserInfoAll;
import com.menganime.interfaces.LoginInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.PhoneInfoUtils;
import com.menganime.utils.SharedUtil;


/**
 * 描    述：注册类
 * 作    者：stt
 * 时    间：2017.01.04
 * 版    本：V1.0.0
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginInterface {
    private Context context;

    private TextView tv_title;
    private Button bt_login;

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
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.login));
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                MyRequest.loginRequest(this, PhoneInfoUtils.getPhonIMEI(this));
                break;
        }
    }


    @Override
    public void login(String json) {
        UserInfoAll userInfoAll = JSON.parseObject(json,UserInfoAll.class);
        if(userInfoAll.getStatus().equals("0")) {
            SharedUtil.setBoolean(this,SharedUtil.IS_LOGINED,true);

            UserInfoAll.UserInfo userinfo = userInfoAll.getUser().get(0);
            if(userinfo!=null){
                SharedUtil.setString(this,SharedUtil.USERINFO_ID,userinfo.getMH_UserInfo_ID());
            }

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("登录成功");
            dialog.setMessage("保留当前收藏的漫画");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //将本地收藏的漫画提交到服务器
                    finish();
                }
            });
            dialog.setNeutralButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            finish();
                        }
                    });
            dialog.show();
        }
    }
}
