package com.recyclerviewpull.utils;//package makingfriends.ssn.com.recyclerviewpull.utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by lenovo on 2016/3/26.
// * 操作sp存储的工具类
// */
//public class SPUtils {
//    private static SPUtils spUtils = new SPUtils();
//    private static SharedPreferences sp;
//
//    public static SPUtils getinstants(Context context) {
//        if (sp == null) {
//
//            sp = context.getSharedPreferences("namingrecyclerview.db", Context.MODE_PRIVATE);
//        }
//        return spUtils;
//    }
//
//    //添加
//    public void insert(String key, Object value) {
//        if (value instanceof String) {
//            sp.edit().putString(key, (String) value).commit();
//        }
//        if (value instanceof Boolean) {
//            sp.edit().putBoolean(key, (Boolean) value).commit();
//        }
//        if (value instanceof Integer) {
//            sp.edit().putInt(key, (Integer) value).commit();
//        }
//        if (value instanceof Long) {
//            sp.edit().putLong(key, (Long) value).commit();
//        }
//
//    }
//
//    //获取
//    public <T> T get(String key, T defvalue) {
//        T t = null;
//        if (defvalue == null || defvalue instanceof String) {
//            t = (T) sp.getString(key, (String) defvalue);
//        }
//        if (defvalue instanceof Boolean) {
//            Boolean b = sp.getBoolean(key, (Boolean) defvalue);
//            t = (T) b;
//        }
//        if (defvalue instanceof Integer) {
//            Integer i = sp.getInt(key, (Integer) defvalue);
//            t = (T) i;
//        }
//        if (defvalue instanceof Long) {
//            Long i = sp.getLong(key, (Long) defvalue);
//            t = (T) i;
//        }
//        return t;
//
//    }
//
//
//    /**
//     * 存储List<String>
//     *
//     * @param context
//     * @param key     List<String>对应的key
//     * @param strList 对应需要存储的List<String>
//     */
//    public void putStrListValue(Context context, String key,
//                                List<String> strList) {
//        if (null == strList) {
//            return;
//        }
//        // 保存之前先清理已经存在的数据，保证数据的唯一性
//        removeStrList(context, key);
//        int size = strList.size();
//        insert(key + "size", size);
//        for (int i = 0; i < size; i++) {
//            insert(key + i, strList.get(i));
//        }
//    }
//
//    /**
//     * 清空List<String>所有数据
//     *
//     * @param context
//     * @param key     List<String>对应的key
//     */
//    public void removeStrList(Context context, String key) {
//        int size = get(key + "size", 0);
//        if (0 == size) {
//            return;
//        }
//        remove(context, key + "size");
//        for (int i = 0; i < size; i++) {
//            remove(context, key + i);
//        }
//    }
//
//    /**
//     * 取出List<String>
//     *
//     * @param context
//     * @param key     List<String> 对应的key
//     * @return List<String>
//     */
//    public List<String> getStrListValue(Context context, String key) {
//        List<String> strList = new ArrayList<String>();
//        int size = get(key + "size", 0);
//        //Log.d("sp", "" + size);
//        for (int i = 0; i < size; i++) {
//            strList.add(get(key + i, ""));
//        }
//        return strList;
//    }
//
//    /**
//     * 清空对应key数据
//     *
//     * @param context
//     * @param key
//     */
//    public static void remove(Context context, String key) {
//        sp.edit().remove(key).commit();
//    }
//
//    /**
//     * @param context
//     * @param key     List<String>对应的key
//     * @param str     List<String>中的元素String
//     * @Description TODO 清空List<String>单条数据
//     */
//    public void removeStrListItem(Context context, String key, String str) {
//        int size = get(key + "size", 0);
//        if (0 == size) {
//            return;
//        }
//        List<String> strList = getStrListValue(context, key);
//        // 待删除的List<String>数据暂存
//        List<String> removeList = new ArrayList<String>();
//        for (int i = 0; i < size; i++) {
//            if (str.equals(strList.get(i))) {
//                if (i >= 0 && i < size) {
//                    removeList.add(strList.get(i));
//                    remove(context, key + i);
//                    insert(key + "size", size - 1);
//                }
//            }
//        }
//        strList.removeAll(removeList);
//        // 删除元素重新建立索引写入数据
//        putStrListValue(context, key, strList);
//    }
//
//
//    /**
//     * 清空所有数据
//     *
//     * @param context
//     */
//    public void clear(Context context) {
//        sp.edit().clear().commit();
//    }
//
//}
