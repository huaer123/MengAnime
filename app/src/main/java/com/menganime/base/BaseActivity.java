package com.menganime.base;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.menganime.okhttps.OkHttpUtils;
import com.menganime.service.NetBroadcastReceiver;
import com.menganime.utils.NetWorkUtils;
import com.menganime.utils.PGActivityUtil;


/**
 * 文件名：BaseActivity
 * 描    述：activity的基类，一些共同的方法写在这
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */

public abstract class BaseActivity extends Activity implements NetBroadcastReceiver.NetEvevt {
    public PGActivityUtil PGApp;
    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;
    /**
     * Notification管理
     */
    public NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PGApp = PGActivityUtil.getInstance();
        PGApp.addActivity(this);
        evevt = this;
        inspectNet();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        setOnCreate(savedInstanceState);
//        StatusBarUtils.ff(this);
//        StatusBarUtils.ff(mContext, R.color.transparent);
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetWorkUtils.getNetWorkState(BaseActivity.this);
        return isNetConnect();
    }

    /**
     * 方法名：getNetStart
     * 功    能：网络判断方法
     * 参    数：
     * 返回值：无
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetWorkUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_NONE) {
            return false;

        }
        return false;
    }

    private void setOnCreate(Bundle savedInstanceState) {
        setView();
        setDate(savedInstanceState);
        init();
    }

    /**
     * 设置布局 第一步
     */
    protected abstract void setView();

    /**
     * 接收上级，填充数据 第二步
     */
    protected abstract void setDate(Bundle savedInstanceState);

    /**
     * 实例化控件 第三步
     */
    protected abstract void init();

    /**
     * 返回键监听
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            PGApp.finishTop();
            return true;
        }
        return false;
    }


    /**
     * 清除当前创建的通知栏
     */
    public void clearNotify(int notifyId) {
        mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
//		mNotification.cancel(getResources().getString(R.string.app_name));
    }

    /**
     * 清除所有通知栏
     */
    public void clearAllNotify() {
        mNotificationManager.cancelAll();// 删除你发的所有通知
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        evevt = this;
        inspectNet();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
