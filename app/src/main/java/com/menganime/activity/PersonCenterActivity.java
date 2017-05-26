package com.menganime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.bean.UserInfoAll;
import com.menganime.interfaces.LoginInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;


/**
 * Created by Administrator on 2017/5/15.
 * 个人中心界面
 */

public class PersonCenterActivity extends BaseActivity implements View.OnClickListener,LoginInterface{
    private RelativeLayout rl_back;
    private TextView tv_title;
    private TextView tv_cancellation;

    private Button bt_edit;//编辑资料
    private Button bt_recharge;//充值
    private ImageView iv_personal;
    private TextView tv_personal_name;

    private UserInfoAll.UserInfo userinfo;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_personcenter);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userId = SharedUtil.getString(this,SharedUtil.USERINFO_ID);
        if(userId==null||userId.equals("")){
            ToastUtil.showToast(this,"获取用户Id失败");
            return;
        }
        MyRequest.getUserInfo(this,userId);
    }

    @Override
    protected void init() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.personal_center));
        tv_cancellation = (TextView) findViewById(R.id.tv_cancellation);
        tv_cancellation.setVisibility(View.VISIBLE);
        tv_cancellation.setOnClickListener(this);

        iv_personal = (ImageView) findViewById(R.id.iv_personal);
        tv_personal_name = (TextView) findViewById(R.id.tv_personal_name);

        bt_edit = (Button) findViewById(R.id.bt_edit);
        bt_edit.setOnClickListener(this);
        bt_recharge = (Button) findViewById(R.id.bt_recharge);
        bt_recharge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancellation://注销
                SharedUtil.setBoolean(this,SharedUtil.IS_LOGINED,false);
                SharedUtil.setString(this,SharedUtil.USERINFO_ID,"");
                //startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.bt_edit://编辑资料
                Intent intent = new Intent(PersonCenterActivity.this,EditPersonActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_recharge://充值
                Intent intentReCharge = new Intent(PersonCenterActivity.this,RechargeActivity.class);
                /*Bundle bundle=new Bundle();
                bundle.putString("name",userinfo.getPetName());
                bundle.putString("picture",userinfo.getICONURL());
                intentReCharge.putExtras(bundle);*/
                startActivity(intentReCharge);
                break;
            default:
                break;
        }
    }

    @Override
    public void login(String json) {
        UserInfoAll userInfoAll = JSON.parseObject(json,UserInfoAll.class);
        if(userInfoAll.getStatus().equals("0")) {
            userinfo = userInfoAll.getUser().get(0);
            if(userinfo!=null){
                Glide.with(this)
                        .load(userinfo.getICONURL())
                        .error(R.mipmap.picture_default) //失败图片
                        .into(iv_personal);
                tv_personal_name.setText(userinfo.getPetName().equals("")?"酷哥":userinfo.getPetName());
            }
        }
    }
}
