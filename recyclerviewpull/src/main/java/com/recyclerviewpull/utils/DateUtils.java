package com.recyclerviewpull.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 本类描述:
 */
public class DateUtils {
    private static int mill = 9;

    /**
     * 将unix时间戳转换为date
     *
     * @param timestampString
     * @return
     */
    public static String timeStamp2Date(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(timestamp));
        return date;
    }

    public static String getCurrentDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }
    public static String getCurrentaDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    public static String getDiffDateInfo(String dateUnixTimeStamp) {
        String mEnd = timeStamp2Date(dateUnixTimeStamp);
        String result = getDiffDateInfo(getCurrentDate(), mEnd, mill);
        mill = mill-- <= 0 ? 9 : mill--;
        return result;
    }

    public static String getDiffDateInfo(String startDate, String endDate, int millTime) {
        String result = "";
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long between = 0;
        try {
            Date begin = dfs.parse(startDate);
            Date end = dfs.parse(endDate);
            between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        result = day + "天" + hour + "小时" + min + "分" + s + "秒" + millTime;

        return result;
    }
}
