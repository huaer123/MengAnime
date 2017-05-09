package com.menganime.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;

import com.menganime.application.MengAnimeActivityUtil;

import java.util.List;


/**
 * 文件名：BaseFragmentActivity
 * 描    述：FragmentActivity的基类，一些共同的方法写在这
 * 作    者：stt
 * 时    间：2017.4.19
 * 版    本：V1.0.0
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    public MengAnimeActivityUtil BenLiApp;
    private static final String TAG = "BaseFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 透明状态栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 没有标题栏
        BenLiApp = MengAnimeActivityUtil.getInstance();
        BenLiApp.addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setView();
        setData(savedInstanceState);
        init();
    }

    /**
     * 设置布局
     */
    public abstract void setView();

    /**
     * 设置布局数据
     */
    public abstract void setData(Bundle savedInstanceState);

    /**
     * 控件实例化
     */
    public abstract void init();


    /**
     * 返回键监听
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            BenLiApp.finishTop();
            return true;
        }
        return false;
    }


    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }
}
