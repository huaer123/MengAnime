package com.menganime.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.menganime.bean.CollectionHistoryBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 文件名：SharedUtil
 * 描    述：Shared工具类
 * 作    者：stt
 * 时    间：2016.12.30
 * 版    本：V1.0.0
 */
public class SharedUtil {

    public static SharedPreferences mPreference;

    /**
     * 是否登录
     */
    public static final String IS_LOGINED = "isLogined";

    /**
     * 用户Id
     */
    public static final String USERINFO_ID = "userInfo_id";

    /**
     * 收藏历史
     */
    public static final String SAVECOLLECTIONHISTORYLIST = "saveCollectionHistoryList";


    public static SharedPreferences getPreference(Context context) {
        if (mPreference == null)
            mPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return mPreference;
    }

    public static void setInteger(Context context, String name, int value) {
        getPreference(context).edit().putInt(name, value).commit();
    }

    public static int getInteger(Context context, String name, int default_i) {
        return getPreference(context).getInt(name, default_i);
    }

    public static void setString(Context context, String name, String value) {
        getPreference(context).edit().putString(name, value).commit();
    }

    public static void setString(Context context, String name, Double value) {
        getPreference(context).edit().putString(name, value + "").commit();
    }

    public static String getString(Context context, String name) {
        return getPreference(context).getString(name, "");
    }

    public static String getString(Context context, String name, String defalt) {
        return getPreference(context).getString(name, defalt);
    }

    public static void setStringArr(Context context, String name, String[] value) {
        Set<String> set = new HashSet<String>(Arrays.asList(value));
        getPreference(context).edit().putStringSet(name, set).commit();
    }

    public static String[] getStringArr(Context context, String name) {
        Set<String> siteno = new HashSet<String>();
        siteno = getPreference(context).getStringSet(name, siteno);
        String[] data = siteno.toArray(new String[siteno.size()]);
        return data;
    }

    /**
     * 获取boolean类型的配置
     *
     * @param context
     * @param name
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(Context context, String name, boolean defaultValue) {
        return getPreference(context).getBoolean(name, defaultValue);
    }

    /**
     * 设置boolean类型的配置
     *
     * @param context
     * @param name
     * @param value
     */
    public static void setBoolean(Context context, String name, boolean value) {
        getPreference(context).edit().putBoolean(name, value).commit();
    }

    public static Long getLong(Context context, String name, long defaultValue) {
        return getPreference(context).getLong(name, defaultValue);
    }

    public static void setLong(Context context, String name, long value) {
        getPreference(context).edit().putLong(name, value).commit();
    }

    public static void setMoreService(Context context, String name, String value) {
        getPreference(context).edit().putString(name, value).commit();
    }

    public static String getMoreService(Context context, String name, String defaultValue) {
        return getPreference(context).getString(name, defaultValue);
    }

    // 通过键删除值
    public static void isValue(Context context, String name) {
        getPreference(context).edit().remove(name);
    }

    /**
     * 判断是否收藏了该漫画
     *
     * @param context
     * @param name
     * @param cartoonId
     * @return
     */
    public static boolean isCollection(Context context, String name, String cartoonId) {
        boolean isCollection = false;
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (cartoonId.equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("1") || collectionHistoryBean.getType().equals("2")) {
                        //如果==1|2，说明已收藏该漫画
                        return true;
                    }
                }
            }
        }
        return isCollection;
    }

    /**
     * 添加收藏
     *
     * @param context
     * @param name
     * @param bean
     * @return
     */
    public static void addCollection(Context context, String name, CollectionHistoryBean bean) {
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = collectionHistoryBeanList;
        Boolean isExist = false;
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (bean.getCartoonId().equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("0")){
                        //如果是0,将0->2
                        collectionHistoryBeanListNew.get(i).setType("2");
                        collectionHistoryBeanListNew.get(i).setMaxChapter(bean.getMaxChapter());
                    }
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                //如果列表里面没有，直接添加进去
                collectionHistoryBeanListNew.add(bean);
            }
        } else {
            //如果列表里面没有，直接添加进去
            collectionHistoryBeanListNew.add(bean);
        }
        String json = JSONArray.toJSONString(collectionHistoryBeanListNew);
        getPreference(context).edit().putString(name, json).commit();
        LogUtils.d("List<CartoonChapterBean>-----" + json);
    }

    /**
     * 取消收藏
     *
     * @param context
     * @param name
     * @param cartoonId
     * @return
     */
    public static void deleteCollection(Context context, String name, String cartoonId) {
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = collectionHistoryBeanList;
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (cartoonId.equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("1")) {//如果是收藏，将其删掉
                        collectionHistoryBeanListNew.remove(i);
                    }
                    if (collectionHistoryBean.getType().equals("2")) {//如果是2,2->0
                        collectionHistoryBeanListNew.get(i).setType("0");
                    }
                    break;
                }
            }
            String json = JSONArray.toJSONString(collectionHistoryBeanListNew);
            getPreference(context).edit().putString(name, json).commit();
            LogUtils.d("List<CartoonChapterBean>-----" + json);
        }
    }

    /**
     * 取消所有的收藏
     *
     * @param context
     * @param name
     * @return
     */
    public static void deleteAllCollection(Context context, String name) {
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = new ArrayList<>();
        collectionHistoryBeanListNew= collectionHistoryBeanList;
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (collectionHistoryBean.getType().equals("1")) {//如果是收藏，将其删掉
                    collectionHistoryBeanListNew.remove(i);
                }
                if (collectionHistoryBean.getType().equals("2")) {//如果是2,2->0
                    collectionHistoryBeanListNew.get(i).setType("0");
                }
            }
            String json = JSONArray.toJSONString(collectionHistoryBeanListNew);
            getPreference(context).edit().putString(name, json).commit();
            LogUtils.d("List<CartoonChapterBean>-----" + json);
        }
    }

    /**
     * 判断是否观看了该漫画
     *
     * @param context
     * @param name
     * @param cartoonId
     * @return
     */
    public static boolean isHistory(Context context, String name, String cartoonId) {
        boolean isCollection = false;
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (cartoonId.equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("0") || collectionHistoryBean.getType().equals("2")) {
                        //如果==0|2，说明已观看该漫画
                        return true;
                    }
                }
            }
        }
        return isCollection;
    }

    /**
     * 查询该漫画
     *
     * @param context
     * @param name
     * @param cartoonId
     * @return
     */
    public static String selectWatchChapterByCartoonId(Context context, String name, String cartoonId) {
        String watchChapter = "";
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (cartoonId.equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("0") || collectionHistoryBean.getType().equals("2")) {
                        //如果==0|2，说明已观看该漫画
                        return collectionHistoryBeanList.get(i).getWatchChapter();
                    }
                }
            }
        }
        return watchChapter;
    }

    /**
     * 查询该漫画章节内容
     *
     * @param context
     * @param name
     * @param cartoonId
     * @return
     */
    public static String selectWatchChapterContentByCartoonId(Context context, String name, String cartoonId) {
        String watchChapterContent = "";
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (cartoonId.equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("0") || collectionHistoryBean.getType().equals("2")) {
                        //如果==0|2，说明已观看该漫画
                        return collectionHistoryBeanList.get(i).getWatchChapterContent();
                    }
                }
            }
        }
        return watchChapterContent;
    }


    /**
     * 添加历史
     *
     * @param context
     * @param name
     * @param bean
     * @return
     */
    public static void updateHistory(Context context, String name, CollectionHistoryBean bean) {
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = collectionHistoryBeanList;
        Boolean isExist = false;
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (bean.getCartoonId().equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("1")) {//如果是1,1->2
                        collectionHistoryBeanListNew.get(i).setType("2");
                    }
                    collectionHistoryBeanListNew.get(i).setWatchChapter(bean.getWatchChapter());
                    collectionHistoryBeanListNew.get(i).setWatchChapterContent(bean.getWatchChapterContent());
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                //如果列表里面没有，直接添加进去
                collectionHistoryBeanListNew.add(bean);
            }
        } else {
            //如果列表里面没有，直接添加进去
            collectionHistoryBeanListNew.add(bean);
        }
        String json = JSONArray.toJSONString(collectionHistoryBeanListNew);
        getPreference(context).edit().putString(name, json).commit();
        LogUtils.d("List<CartoonChapterBean>-----" + json);
    }

    /**
     * 删除历史
     *
     * @param context
     * @param name
     * @param cartoonId
     * @return
     */
    public static void deleteHistory(Context context, String name, String cartoonId) {
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = collectionHistoryBeanList;
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                if (cartoonId.equals(collectionHistoryBean.getCartoonId())) {
                    if (collectionHistoryBean.getType().equals("0")) {//如果是观看，将其删掉
                        collectionHistoryBeanListNew.remove(i);
                    }
                    if (collectionHistoryBean.getType().equals("2")) {//如果是2,2->1
                        collectionHistoryBeanListNew.get(i).setType("1");
                        collectionHistoryBeanListNew.get(i).setWatchChapter("");
                        collectionHistoryBeanListNew.get(i).setWatchChapterContent("");
                    }
                    break;
                }
            }
            String json = JSONArray.toJSONString(collectionHistoryBeanListNew);
            getPreference(context).edit().putString(name, json).commit();
            LogUtils.d("List<CartoonChapterBean>-----" + json);
        }
    }

    /**
     * 删除全部历史
     *
     * @param context
     * @param name
     * @return
     */
    public static void deleteAllHistory(Context context, String name) {
        List<CollectionHistoryBean> collectionHistoryBeanList = getCollectionHistoryList(context, name);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = new ArrayList<>();
        collectionHistoryBeanListNew= collectionHistoryBeanList;
        if (collectionHistoryBeanList != null && collectionHistoryBeanList.size() > 0) {
            for (int i = 0; i < collectionHistoryBeanList.size(); i++) {
                CollectionHistoryBean collectionHistoryBean = collectionHistoryBeanList.get(i);
                    if (collectionHistoryBean.getType().equals("0")) {//如果是观看，将其删掉
                        collectionHistoryBeanListNew.remove(i);
                    }
                    if (collectionHistoryBean.getType().equals("2")) {//如果是2,2->1
                        collectionHistoryBeanListNew.get(i).setType("1");
                        collectionHistoryBeanListNew.get(i).setWatchChapter("");
                        collectionHistoryBeanListNew.get(i).setWatchChapterContent("");
                    }
            }
            String json = JSONArray.toJSONString(collectionHistoryBeanListNew);
            getPreference(context).edit().putString(name, json).commit();
            LogUtils.d("List<CartoonChapterBean>-----" + json);
        }
    }

    /**
     * 获取List<CartoonChapterBean>
     *
     * @param context
     * @param name
     * @return
     */
    public static List<CollectionHistoryBean> getCollectionHistoryList(Context context, String name) {
        String listJson = getPreference(context).getString(name, "");
        if (listJson == null || listJson.equals("")) {
            return new ArrayList<CollectionHistoryBean>();
        }
        List<CollectionHistoryBean> list = JSON.parseArray(listJson, CollectionHistoryBean.class);
        LogUtils.d("List<CartoonChapterBean>-----" + listJson);
        return list;
    }

    /**
     * 获取收藏列表List<CartoonChapterBean>
     *
     * @param context
     * @param name
     * @return
     */
    public static List<CollectionHistoryBean> getCollectionHistoryListForCollection(Context context, String name) {
        String listJson = getPreference(context).getString(name, "");
        if (listJson == null || listJson.equals("")) {
            return new ArrayList<CollectionHistoryBean>();
        }
        List<CollectionHistoryBean> list = JSON.parseArray(listJson, CollectionHistoryBean.class);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = new ArrayList<CollectionHistoryBean>();
        for (int i = 0; i < list.size(); i++) {
            CollectionHistoryBean bean = list.get(i);
            if (bean.getType().equals("1") || bean.getType().equals("2")) {
                collectionHistoryBeanListNew.add(bean);
            }
        }
        LogUtils.d("List<CartoonChapterBean>-----" + listJson);
        return collectionHistoryBeanListNew;
    }

    /**
     * 获取观看历史列表List<CartoonChapterBean>
     *
     * @param context
     * @param name
     * @return
     */
    public static List<CollectionHistoryBean> getCollectionHistoryListForHistory(Context context, String name) {
        String listJson = getPreference(context).getString(name, "");
        if (listJson == null || listJson.equals("")) {
            return new ArrayList<CollectionHistoryBean>();
        }
        List<CollectionHistoryBean> list = JSON.parseArray(listJson, CollectionHistoryBean.class);
        List<CollectionHistoryBean> collectionHistoryBeanListNew = new ArrayList<CollectionHistoryBean>();
        for (int i = 0; i < list.size(); i++) {
            CollectionHistoryBean bean = list.get(i);
            if (bean.getType().equals("0") || bean.getType().equals("2")) {
                collectionHistoryBeanListNew.add(bean);
            }
        }
        LogUtils.d("List<CartoonChapterBean>-----" + listJson);
        return collectionHistoryBeanListNew;
    }
}
