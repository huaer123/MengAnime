package com.menganime.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.bean.UserInfoAll;
import com.menganime.interfaces.LoginInterface;
import com.menganime.interfaces.RechargeInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;

/**
 * Created by Administrator on 2017/5/17.
 * 充值界面
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener, RechargeInterface, LoginInterface {
    private static final String PRICEVIP1 = "0.1";//vip1->价格
    private static final String PRICEVIP2 = "10";//vip2->价格
    private static final String PRICEVIP3 = "20";//vip3->价格

    private RelativeLayout rl_back;
    private TextView tv_title;
    private ImageView iv_vip1;
    private ImageView iv_vip2;
    private ImageView iv_vip3;

    private ImageView iv_personal;
    private TextView tv_personal_name;

    /* private String name;
     private String picture;*/
    private UserInfoAll.UserInfo userinfo;
    private UserInfoAll.VIP vip;
    private String vipType;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_recharge);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        /*Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("name");
        picture = bundle.getString("picture");*/
    }

    @Override
    protected void init() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.recharge));

        iv_personal = (ImageView) findViewById(R.id.iv_personal);
        tv_personal_name = (TextView) findViewById(R.id.tv_personal_name);

        iv_vip1 = (ImageView) findViewById(R.id.iv_vip1);
        iv_vip1.setOnClickListener(this);
        iv_vip2 = (ImageView) findViewById(R.id.iv_vip2);
        iv_vip2.setOnClickListener(this);
        iv_vip3 = (ImageView) findViewById(R.id.iv_vip3);
        iv_vip3.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userId = SharedUtil.getString(this, SharedUtil.USERINFO_ID);
        if (userId == null || userId.equals("")) {
            ToastUtil.showToast(this, "获取用户Id失败");
            return;
        }
        MyRequest.getUserInfo(this, userId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.iv_vip1:
                vipType = "1";
                MyRequest.openVip(this, SharedUtil.getString(this, SharedUtil.USERINFO_ID), PRICEVIP1, "di");
                break;
            case R.id.iv_vip2:
                vipType = "";
                MyRequest.openVip(this, SharedUtil.getString(this, SharedUtil.USERINFO_ID), PRICEVIP2, "zhong");
                break;
            case R.id.iv_vip3:
                vipType = "";
                MyRequest.openVip(this, SharedUtil.getString(this, SharedUtil.USERINFO_ID), PRICEVIP3, "gao");
                break;
            default:
                break;
        }
    }

    @Override
    public void openVip(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        String status = jsonObject.getString("Status");
        if (status.equals("0")) {
            ToastUtil.showToast(this, "开通成功");
            if (vipType.equals("1")) {
                vip.setVIP_di("di");
            }
            if (vipType.equals("2")) {
                vip.setVIP_zhong("zhong");
            }
            if (vipType.equals("3")) {
                vip.setVIP_gao("gao");
            }
            updateVip();
        } else if (status.equals("2")) {
            ToastUtil.showToast(this, "开通失败,本月您已开通过此VIP");
        }
    }

    @Override
    public void login(String json) {
        UserInfoAll userInfoAll = JSON.parseObject(json, UserInfoAll.class);
        if (userInfoAll.getStatus().equals("0")) {
            userinfo = userInfoAll.getUser().get(0);
            if (userinfo != null) {
                Glide.with(this)
                        .load(userinfo.getICONURL())
                        .error(R.mipmap.ic_launcher) //失败图片
                        .into(iv_personal);
                tv_personal_name.setText(userinfo.getPetName().equals("") ? "酷哥" : userinfo.getPetName());
            }
            vip = userInfoAll.getVIP().get(0);
            updateVip();
        }
    }

    private void updateVip() {
        iv_vip1.setBackgroundDrawable(vip.getVIP_di().equals("di") ? getResources().getDrawable(R.mipmap.vip1_selected) : getResources().getDrawable(R.mipmap.vip1_unselected));
        iv_vip2.setBackgroundDrawable(vip.getVIP_zhong().equals("zhong") ? getResources().getDrawable(R.mipmap.vip1_selected) : getResources().getDrawable(R.mipmap.vip1_unselected));
        iv_vip3.setBackgroundDrawable(vip.getVIP_gao().equals("gao") ? getResources().getDrawable(R.mipmap.vip1_selected) : getResources().getDrawable(R.mipmap.vip1_unselected));
    }
}
