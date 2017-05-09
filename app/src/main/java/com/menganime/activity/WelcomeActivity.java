package com.menganime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.menganime.R;

/**
 * Created by Administrator on 2017/5/9.
 */

public class WelcomeActivity extends Activity {
    private ImageView iv_welcome;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){

        }
    };
    private AlphaAnimation alphaAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        // 隐藏顶部的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        iv_welcome = (ImageView)findViewById(R.id.iv_welcome);
        showAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toMain();
            }
        },2000);
    }

    private void toMain(){
        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        finish();
    }

    /**
     * 显示动画:
     * 	透明度: 0--1 持续2s,
     * 	缩放: 0-->1 持续2s,
     *  旋转: 0-->360 持续2s
     */
    private void showAnimation() {
        //透明度
        if(alphaAnimation == null) {
            alphaAnimation = new AlphaAnimation(0, 1);
        }
        alphaAnimation.setDuration(2000);
        /*//缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);*/
       /* //旋转: 0-->360 持续2s
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);*/
        //添加到集合中
        //animationSet.addAnimation(rotateAnimation);
//        animationSet.addAnimation(scaleAnimation);

        //启动动画
        iv_welcome.startAnimation(alphaAnimation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alphaAnimation != null&&iv_welcome != null) {
            iv_welcome.clearAnimation();
            alphaAnimation = null;
        }
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
