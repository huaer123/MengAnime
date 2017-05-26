package com.menganime.fragment.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.activity.EditPersonActivity;
import com.menganime.activity.RechargeActivity;
import com.menganime.base.BaseFragment;
import com.menganime.bean.UserInfoAll;
import com.menganime.interfaces.LoginInterface;
import com.menganime.interfaces.UserInfoInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.PhoneInfoUtils;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;

/**
 * Created by Administrator on 2017/5/9.
 * 主界面第三个Fragment
 */

public class MoreFragment extends BaseFragment implements View.OnClickListener, LoginInterface,UserInfoInterface {
    private Context context;
    private TextView tv_title;
    private TextView tv_cancellation;

    private Boolean isLogin = false;

    //登录
    private LinearLayout ll_loginAll;
    private Button bt_login;
    private UserInfoAll.UserInfo userinfo;
    //个人中心
    private LinearLayout ll_personcenterAll;
    private Button bt_edit;//编辑资料
    private Button bt_recharge;//充值
    private ImageView iv_personal;
    private TextView tv_personal_name;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_more, null);
        return view;
    }

    @Override
    protected void setDate() {

    }

    @Override
    protected void init(View rootView) {
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.personal_center));
        tv_cancellation = (TextView) rootView.findViewById(R.id.tv_cancellation);
        tv_cancellation.setOnClickListener(this);

        ll_loginAll = (LinearLayout) rootView.findViewById(R.id.ll_loginAll);
        bt_login = (Button) rootView.findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);


        ll_personcenterAll = (LinearLayout) rootView.findViewById(R.id.ll_personcenterAll);
        iv_personal = (ImageView) rootView.findViewById(R.id.iv_personal);
        tv_personal_name = (TextView) rootView.findViewById(R.id.tv_personal_name);
        bt_edit = (Button) rootView.findViewById(R.id.bt_edit);
        bt_edit.setOnClickListener(this);
        bt_recharge = (Button) rootView.findViewById(R.id.bt_recharge);
        bt_recharge.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * 更新UI
     */
    private void updateUI() {
        isLogin = SharedUtil.getBoolean(context, SharedUtil.IS_LOGINED, false);
        if (isLogin) {
            ll_loginAll.setVisibility(View.GONE);
            ll_personcenterAll.setVisibility(View.VISIBLE);
            String userId = SharedUtil.getString(context,SharedUtil.USERINFO_ID);
            if(userId==null||userId.equals("")){
                ToastUtil.showToast(context,"获取用户Id失败");
                return;
            }
            tv_cancellation.setVisibility(View.VISIBLE);
            MyRequest.getUserInfo(this,userId);
        } else {
            ll_loginAll.setVisibility(View.VISIBLE);
            ll_personcenterAll.setVisibility(View.GONE);
            tv_cancellation.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login://登录
                MyRequest.loginRequestForFragment(this, PhoneInfoUtils.getPhonIMEI(context));
                break;
            case R.id.tv_cancellation://注销
                SharedUtil.setBoolean(context,SharedUtil.IS_LOGINED,false);
                SharedUtil.setString(context,SharedUtil.USERINFO_ID,"");
                updateUI();
                break;
            case R.id.bt_edit://编辑资料
                Intent intent = new Intent(context,EditPersonActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_recharge://充值
                Intent intentReCharge = new Intent(context,RechargeActivity.class);
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
            SharedUtil.setBoolean(context, SharedUtil.IS_LOGINED, true);

            UserInfoAll.UserInfo userinfo = userInfoAll.getUser().get(0);
            if (userinfo != null) {
                SharedUtil.setString(context, SharedUtil.USERINFO_ID, userinfo.getMH_UserInfo_ID());
            }
            updateUI();
        }
    }

    @Override
    public void getUserInfo(String json) {
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
