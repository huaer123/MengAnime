package com.menganime.utils;

import android.app.Activity;
import android.app.Dialog;

import com.menganime.base.BaseActivity;
import com.menganime.base.BaseFragment;
import com.menganime.config.UrlConfig;
import com.menganime.interfaces.CartoonClassifyInterface;
import com.menganime.interfaces.CartoonDetailsInterface;
import com.menganime.interfaces.EditNickNameInterface;
import com.menganime.interfaces.EditPersonInterface;
import com.menganime.interfaces.LoginInterface;
import com.menganime.interfaces.RechargeInterface;
import com.menganime.interfaces.RecommendInterface;
import com.menganime.interfaces.WatchCartoonInterface;
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
    public static void loginRequest(final Activity activity, final String imei) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final LoginInterface login = (LoginInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("imei", imei);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e(UrlConfig.USERREGIST + "imei=" + imei);
        OkHttpUtils.post().url(UrlConfig.USERREGIST).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                login.login(response);//把结果回调给接口，谁用谁去实现接口就行了
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                //login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * @param fragment
     * @param page     第几页
     * @param count    每页数量
     * @param column   精彩推荐标识
     */
    public static void getRecommendList(final BaseFragment fragment, int page, int count, int column) {
        final Dialog progDialog = DialogUtils.showWaitDialog(fragment.getActivity());
        final RecommendInterface recommend = (RecommendInterface) fragment;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("page", page);
            params.put("count", count);
            params.put("column", column);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.SELECTRECOMMENDLIST).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                recommend.getRecommendList(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(fragment.getActivity(), "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getUserInfo
     * 功    能：获取用户信息
     * 参    数：Activity activity final String mh_info_id
     * 返回值：无
     */
    public static void getUserInfo(final Activity activity, final String mh_info_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final LoginInterface login = (LoginInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.USERINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                //login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getUserInfoForEdit
     * 功    能：在编辑用户资料获取用户信息】
     * 参    数：Activity activity final String mh_info_id
     * 返回值：无
     */
    public static void getUserInfoForEdit(final Activity activity, final String mh_info_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final EditPersonInterface info = (EditPersonInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.USERINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.getUserInfo(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getUserInfoForEdit
     * 功    能：更改用户信息ForNickName
     * 参    数：Activity activity final String mh_info_id
     * 返回值：无
     */
    public static void updateUserInfoForNickName(final Activity activity, final String mh_info_id, final String petname) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final EditNickNameInterface info = (EditNickNameInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
            params.put("petname", petname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e(UrlConfig.UPDATEUSERINFO + "mh_userinfo_id=" + mh_info_id + "&petname=" + petname);
        OkHttpUtils.post().url(UrlConfig.UPDATEUSERINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.updateUserInfo(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getUserInfoForEdit
     * 功    能：更改用户信息ForNickName
     * 参    数：Activity activity final String mh_info_id
     * 返回值：无
     */
    public static void updateUserInfoForSign(final Activity activity, final String mh_info_id, final String introduce) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final EditNickNameInterface info = (EditNickNameInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
            params.put("introduce", introduce);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.UPDATEUSERINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.updateUserInfo(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getUserInfoForEdit
     * 功    能：更改用户信息
     * 参    数：Activity activity final String mh_info_id
     * 返回值：无
     */
    public static void updateUserInfo(final Activity activity, String mh_info_id, String petname, String sex, String source, String birthday, String introduce) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final EditPersonInterface info = (EditPersonInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
            params.put("petname", petname);
            params.put("sex", sex);
            params.put("source", source);
            params.put("birthday", birthday);
            params.put("introduce", introduce);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.UPDATEUSERINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.updateUserInfo(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取漫画详情
     *
     * @param activity
     * @param mh_info_id
     * @param mh_userinfo_id
     */
    public static void getCartoonDetails(final Activity activity, String mh_info_id, String mh_userinfo_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final CartoonDetailsInterface info = (CartoonDetailsInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
            params.put("mh_info_id", mh_info_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(UrlConfig.SELECTDETAILS + "mh_userinfo_id=" + mh_userinfo_id + "&mh_info_id=" + mh_info_id);
        OkHttpUtils.post().url(UrlConfig.SELECTDETAILS).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.selectDetails(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取漫画章节
     *
     * @param activity
     * @param mh_info_id
     */
    public static void getCartoonChapter(final Activity activity, String mh_info_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final CartoonDetailsInterface info = (CartoonDetailsInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
            params.put("mh_info_id", mh_info_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(UrlConfig.SELECTCHAPTER + "&mh_info_id=" + mh_info_id);
        OkHttpUtils.post().url(UrlConfig.SELECTCHAPTER).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.selectchapter(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 用户开通VIP
     *
     * @param activity
     * @param price
     */
    public static void openVip(final Activity activity, String mh_userinfo_id, String price, String viplevel) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final RechargeInterface info = (RechargeInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_userinfo_id);
            params.put("price", price);
            params.put("viplevel", viplevel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(UrlConfig.USERVIP + "&mh_userinfo_id=" + mh_userinfo_id + "&price=" + price + "&viplevel=" + viplevel);
        OkHttpUtils.post().url(UrlConfig.USERVIP).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.openVip(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 查询用户看过哪些原创漫画
     *
     * @param activity
     * @param mh_userinfo_id
     */
    public static void selectUserChapter(final Activity activity, String mh_userinfo_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final CartoonDetailsInterface info = (CartoonDetailsInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_userinfo_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(UrlConfig.SELECTUSERCHAPTER + "&mh_userinfo_id=" + mh_userinfo_id);
        OkHttpUtils.post().url(UrlConfig.SELECTUSERCHAPTER).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.selectUserChapter(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 查询用户看过哪些原创漫画
     *
     * @param activity
     * @param mh_userinfo_id
     */
    public static void selectChapterContent(final Activity activity, String mh_chapter_id, String mh_userinfo_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final WatchCartoonInterface info = (WatchCartoonInterface) activity;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_chapter_id", mh_chapter_id);
            params.put("mh_userinfo_id", mh_userinfo_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.d(UrlConfig.SELECTCHAPTERCONTENT + "&mh_chapter_id=" + mh_chapter_id + "&mh_userinfo_id=" + mh_userinfo_id);
        OkHttpUtils.post().url(UrlConfig.SELECTCHAPTERCONTENT).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                info.getWatchCartoon(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getUserInfo
     * 功    能：获取用户信息
     * 参    数：Activity activity final String mh_info_id
     * 返回值：无
     */
    public static void getUserInfo(final BaseFragment fragment, final String mh_info_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(fragment.getActivity());
        final LoginInterface login = (LoginInterface) fragment;
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("mh_userinfo_id", mh_info_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.USERINFO).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(fragment.getActivity(), "服务器有错误，请稍候再试");
                //失败之后的处理
                //login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getCartoonClassify
     * 功    能：获取漫画分类
     * 返回值：无
     */
    public static void getCartoonClassify(final BaseFragment fragment) {
        final Dialog progDialog = DialogUtils.showWaitDialog(fragment.getActivity());
        final CartoonClassifyInterface login = (CartoonClassifyInterface) fragment;
        Map<String, Object> params = new HashMap<>();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.SELECTCARTOONCLASSIFY).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                login.getCartoonClassify(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(fragment.getActivity(), "服务器有错误，请稍候再试");
                //失败之后的处理
                //login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getCartoonForKey
     * 功    能：获取漫画名字通过关键字
     * 返回值：无
     */
    public static void getCartoonNameForKey(final BaseFragment fragment, String key) {
        //final Dialog progDialog = DialogUtils.showWaitDialog(fragment.getActivity());
        final CartoonClassifyInterface login = (CartoonClassifyInterface) fragment;
        Map<String, Object> params = new HashMap<>();
        params.put("key", key);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.SELECTCARTOONBYKEY).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                login.getCartoonNameForKey(response);
               /* if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }*/
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(fragment.getActivity(), "服务器有错误，请稍候再试");
                //失败之后的处理
                //login.login(response);
               /* if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }*/
            }
        });
    }

    /**
     * 方法名：getCartoonForKey
     * 功    能：获取漫画列表通过漫画类型
     * 返回值：无
     */
    public static void getCartoonListForClassify(final BaseActivity activity, int page, int count, int mh_type_id) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final CartoonClassifyInterface login = (CartoonClassifyInterface) activity;
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("count", count);
        params.put("mh_type_id", mh_type_id);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.SELECTCARTOONLISTBYCLASSIFY).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                login.getCartoonClassify(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                //login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }

    /**
     * 方法名：getCartoonForKey
     * 功    能：获取漫画列表通过漫画类型
     * 返回值：无
     */
    public static void getCartoonListForKey(final BaseActivity activity, int page, int count, String key) {
        final Dialog progDialog = DialogUtils.showWaitDialog(activity);
        final CartoonClassifyInterface login = (CartoonClassifyInterface) activity;
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("count", count);
        params.put("key", key);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.SELECTCARTLISTBYKEY).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                LogUtils.d(response);
                //成功之后的处理
                login.getCartoonClassify(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showToast(activity, "服务器有错误，请稍候再试");
                //失败之后的处理
                //login.login(response);
                if (progDialog.isShowing()) {
                    progDialog.dismiss();
                }
            }
        });
    }
}
