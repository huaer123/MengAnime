package com.menganime.config;

/**
 * 文件名：UrlConfig
 * 描    述：接口的Url地址
 * 作    者：stt
 * 时    间：2017.1.18
 * 版    本：V1.0.0
 */

public class UrlConfig {
    // public static final String BaseUrl = "http://123.56.96.237:8001";// 正式
    //public static final String BaseUrl = "http://192.168.0.90:8001";// 测试
    public static final String BaseUrl = "http://mh.cyngame.cn:8083/";// 正式域名

    public final static String VERSION_ID = "1.3.8";
    public final static String META_DATA = "pan";

    /**
     * 获取精彩推荐列表
     */
    public static final String SELECTRECOMMENDLIST = BaseUrl+"mh/select/SelectMHList?";

    /**
     * 用户注册接口
     */
    public static final String USERREGIST = BaseUrl+"mh/user/UserRegist?cp_id"+META_DATA+"&app_version="+VERSION_ID+"&param=regist&";

    public static final String USERINFO = BaseUrl+"mh/user/UserRegist?param=selectuserinfo&";
}
