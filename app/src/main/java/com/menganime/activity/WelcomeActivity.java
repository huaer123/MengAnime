package com.menganime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.utils.StatusBarUtils;

/**
 * Created by Administrator on 2017/5/9.
 */

public class WelcomeActivity extends BaseActivity {
    private ImageView splash;//欢迎图片,这里固定设置,可动态设置
    private Context mContext;
    //private boolean isLogin;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_welcome);
        mContext = this;
        StatusBarUtils.ff(mContext, R.color.transparent);
    }

    /**
     * 接收上级，填充数据 第二步
     */
    @Override
    protected void setDate(Bundle savedInstanceState) {
        //isLogin = SharedUtil.getBoolean(this, "isLogin", false);

    }

    /**
     * 实例化控件 第三步
     */
    @Override
    protected void init() {
        splash = (ImageView) findViewById(R.id.splash);
        //欢迎图片动态设置操作
        initView();
    }

    /**
     * 数据操作
     */
    private void initView() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(2000);
        splash.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
