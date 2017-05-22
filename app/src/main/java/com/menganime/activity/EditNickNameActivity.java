package com.menganime.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.interfaces.EditNickNameInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;

/**
 * Created by Administrator on 2017/5/16.
 * 编辑用户资料-更改昵称
 */

public class EditNickNameActivity extends BaseActivity implements View.OnClickListener,EditNickNameInterface{

    private TextView tv_title;
    private RelativeLayout rl_back;
    private TextView tv_finish;
    private EditText et_nickname;

    private String nickName = "";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_edit_nickname);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        nickName = bundle.getString("nickName");
    }

    @Override
    protected void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("昵称");
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(this);
        tv_finish = (TextView) findViewById(R.id.tv_finish);
        tv_finish.setVisibility(View.VISIBLE);
        tv_finish.setOnClickListener(this);

        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_nickname.setText(nickName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_finish:
                String userId = SharedUtil.getString(this,SharedUtil.USERINFO_ID);
                if(userId==null||userId.equals("")){
                    ToastUtil.showToast(this,"获取用户Id失败");
                    return;
                }
                MyRequest.updateUserInfoForNickName(EditNickNameActivity.this,userId,et_nickname.getText().toString());
                break;
        }
    }

    @Override
    public void updateUserInfo(String json) {
        JSONObject object = JSON.parseObject(json);
        String status = object.getString("Status");
        if(status.equals("0")){
            ToastUtil.showToast(this,"修改昵称成功");
        }else{
            ToastUtil.showToast(this,"修改昵称失败");
        }
        finish();
    }
}
