package com.menganime.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/17.
 * 充值
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rl_back;
    private TextView tv_title;
    private ImageView iv_vip1;
    private ImageView iv_vip2;
    private ImageView iv_vip3;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_recharge);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
    }

    @Override
    protected void init() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.recharge));

        iv_vip1 = (ImageView) findViewById(R.id.iv_vip1);
        iv_vip1.setOnClickListener(this);
        iv_vip2 = (ImageView) findViewById(R.id.iv_vip2);
        iv_vip2.setOnClickListener(this);
        iv_vip3 = (ImageView) findViewById(R.id.iv_vip3);
        iv_vip3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.iv_vip1:
                break;
            case R.id.iv_vip2:
                break;
            case R.id.iv_vip3:
                break;
            default:
                break;
        }
    }
}
