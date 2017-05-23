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

    public final static String VERSION_ID = "1.0";
    public final static String META_DATA = "pan";

    /**
     * 获取精彩推荐列表
     */
    public static final String SELECTRECOMMENDLIST = BaseUrl+"mh/select/SelectMHList?";

    /**
     * 用户注册接口
     */
    public static final String USERREGIST = BaseUrl+"mh/user/UserRegist?cp_id="+META_DATA+"&app_version="+VERSION_ID+"&param=regist&";

    /**
     * 获取用户信息
     */
    public static final String USERINFO = BaseUrl+"mh/user/UserRegist?param=selectuserinfo&";

    /**
     * 更改用户资料
     */
    public static final String UPDATEUSERINFO = BaseUrl+"mh/user/UpdateUserInfo?";

    /**
     * 更改头像
     */
    public static final String UPDATEPICTURE= "http://mh.cyngame.cn:8083/mh/upload/UploadPicture";

    /**
     * 漫画详情
     */
    public static final String SELECTDETAILS = BaseUrl+"mh/select/SelectMHDetail?";

    /**
     * 获取漫画章节
     */
    public static final String SELECTCHAPTER = BaseUrl+"mh/select/SelectMHChapter?";

    /**
     * 开通vip
     */
    public static final String USERVIP = BaseUrl+"mh/vip/UserVip?";

    /**
     * 获取用户看过哪些原创曼胡
     */
    public static final String SELECTUSERCHAPTER = BaseUrl+"mh/select/SelectUserChapter?";

    /**
     * 获取具体漫画
     */
    public static final String SELECTCHAPTERCONTENT = BaseUrl+"mh/select/SelectMHChapterContent?";

    /**
     * 获取分类列表
     */
    public static final String SELECTCARTOONCLASSIFY = BaseUrl+"mh/select/SelectMH_Type";

    /**
     * 通过关键字获取漫画名字
     */
    public static final String SELECTCARTOONBYKEY = BaseUrl+"mh/select/SelectByKey?";

    /**
     * 通过漫画分类获取漫画列表
     */
    public static final String selectCartoonListByClassify = BaseUrl+"mh/select/SelectMH_TypeDetailList?";
}
