package com.menganime.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import com.menganime.config.UrlConfig;
import com.menganime.interfaces.LoginInterface;
import com.menganime.okhttps.OkHttpUtils;
import com.menganime.okhttps.callback.GenericsCallback;
import com.menganime.okhttps.utils.JsonGenericsSerializator;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 文件名：MyRequest
 * 描    述：请求工具类
 * 作    者：stt
 * 时    间：2017.01.11
 * 版    本：V1.0.0
 */

public class MyRequest {
    /**
     * 方法名：loginRequest
     * 功    能：登陆
     * 参    数：Activity activity final String username, final String password
     * 返回值：无
     */
    public static void loginRequest(final Activity activity, final String username, final String password) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final LoginInterface login = (LoginInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("Name", username);
            params.put("PassWord", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.BaseUrl).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                Log.i("loginInfo", response);
                //成功之后的处理
                login.login(username, password);//把结果回调给接口，谁用谁去实现接口就行了
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                login.login(username, password);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }


}
