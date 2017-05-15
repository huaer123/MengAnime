package com.menganime.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2017/5/15.
 * 获取手机信息工具类
 */

public class PhoneInfoUtils {
    //获取手机的国际移动设备识别码
    public static String getPhonIMEI(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService
                (Context.TELEPHONY_SERVICE);
        String IMEI = null;
        IMEI = telephonyManager.getDeviceId();
        String model = Build.MODEL;
        return IMEI;
    }
}
