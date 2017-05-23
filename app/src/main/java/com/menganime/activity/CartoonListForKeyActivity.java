package com.menganime.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/23.
 * 通过关键字搜索的漫画列表
 */

public class CartoonListForKeyActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_back;
    private TextView tv_title;

    private String key = "";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_cartoonlist_forkey);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        key = bundle.getString("key");
    }

    @Override
    protected void init() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("搜索:"+key);

        //MyRequest.

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            default:
                break;
        }
    }
}
