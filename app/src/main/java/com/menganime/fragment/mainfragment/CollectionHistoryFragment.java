package com.menganime.fragment.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.activity.LoginActivity;
import com.menganime.activity.PersonCenterActivity;
import com.menganime.base.BaseFragment;
import com.menganime.bean.UserInfoAll;
import com.menganime.fragment.collectionhistoryfragment.CollectionFragment;
import com.menganime.fragment.collectionhistoryfragment.HistoryFragment;
import com.menganime.interfaces.UserInfoInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9.
 * 主界面第一个Fragment
 */

public class CollectionHistoryFragment extends BaseFragment implements UserInfoInterface {
    private Context context;

    private ViewPager viewPager;// 页卡内容
    private ImageView imageView;// 动画图片
    private ArrayList<BaseFragment> fragments;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private int selectedColor, unSelectedColor;// 显示和不显示时的颜色变化
    /**
     * 页卡总数
     **/
    private static final int pageSize = 2;
    private int tag = 0;

    // 定义四个fragment
    public CollectionFragment aFragmnet;// 历史
    public HistoryFragment iFragmnet;// 收藏

    // 定义四个控件
    private TextView tv_collection_all;
    private TextView tv_history_all;

    private FragmentPagerAdapter fragmentPagerAdapter;


    private LinearLayout ll_userinfo;
    private ImageView iv_picture;
    private TextView tv_username;

    private String picture;
    private String username;


    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_collectionhistory, null);
        return view;
    }

    @Override
    protected void setDate() {

    }

    @Override
    protected void init(View rootView) {
        viewPager = (ViewPager) rootView.findViewById(R.id.vPager);
        imageView = (ImageView) rootView.findViewById(R.id.cursor);
        tv_collection_all = (TextView) rootView.findViewById(R.id.tv_collection_all);
        tv_history_all = (TextView) rootView.findViewById(R.id.tv_history_all);

        ll_userinfo = (LinearLayout) rootView.findViewById(R.id.ll_userinfo);
        iv_picture = (ImageView) rootView.findViewById(R.id.iv_picture);
        tv_username = (TextView) rootView.findViewById(R.id.tv_username);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        setOperation();
    }

    public void setOperation() {
        selectedColor = getResources().getColor(R.color.black);// 点击时变成白色
        unSelectedColor = getResources().getColor(R.color.white);// 不点击时是灰色
        initImageView(); // 初始化页卡动画
        initTextView(); // 初始化头标
        initViewPager(); // 初始化Viewpager页

        ll_userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = SharedUtil.getString(context, SharedUtil.USERINFO_ID);
                if (userId.equals("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), PersonCenterActivity.class));
                }
            }
        });

    }

    private void setDataForLogin() {
        String userId = SharedUtil.getString(context, SharedUtil.USERINFO_ID);
        if (userId.equals("")) {
            iv_picture.setImageDrawable(getResources().getDrawable(R.mipmap.collectionhistory_login));
            tv_username.setText("登录");
        } else {
            Glide.with(context)
                    .load(picture)
                    .error(R.mipmap.collectionhistory_login) //失败图片
                    .into(iv_picture);
            tv_username.setText(username);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        String userId = SharedUtil.getString(context, SharedUtil.USERINFO_ID);
        if (userId.equals("")) {
            iv_picture.setImageDrawable(getResources().getDrawable(R.mipmap.collectionhistory_login));
            tv_username.setText("登录");
        } else {
            MyRequest.getUserInfo(this, userId);
        }
    }

    @Override
    public void getUserInfo(String json) {
        UserInfoAll userInfoAll = JSON.parseObject(json, UserInfoAll.class);
        if (userInfoAll.getStatus().equals("0")) {
            UserInfoAll.UserInfo userinfo = userInfoAll.getUser().get(0);
            if (userinfo != null) {
                Glide.with(this)
                        .load(userinfo.getICONURL())
                        .error(R.mipmap.picture_default) //失败图片
                        .into(iv_picture);
                tv_username.setText(userinfo.getPetName().equals("") ? "酷哥" : userinfo.getPetName());
            }
        }
    }

    /**
     * 初始化Viewpager页
     */
    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new CollectionFragment());
        fragments.add(new HistoryFragment());

        aFragmnet = (CollectionFragment) fragments.get(0);
        iFragmnet = (HistoryFragment) fragments.get(1);

        fragmentPagerAdapter = new FragmentPagerAdapter(
                getChildFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                // TODO Auto-generated method stub
                return fragments.get(position);
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     * 初始化头标
     */
    private void initTextView() {
        initTabSelected();
        tv_collection_all.setTextColor(selectedColor);

      /*  tv_collection_all.setText(getString(R.string.recruitment_all));
        tv_history_all
                .setText(getString(R.string.recruitment_internship));
        tv_recruitment_parttime
                .setText(getString(R.string.recruitment_parttime));
        tv_recruitment_schoolrecruit
                .setText(getString(R.string.recruitment_schoolrecruit));*/

        tv_collection_all.setOnClickListener(new MyOnClickListener(0));
        tv_history_all.setOnClickListener(new MyOnClickListener(1));
    }

    /**
     * 初始化选中值
     */
    public void initTabSelected() {
        tv_collection_all.setTextColor(unSelectedColor);
        tv_history_all.setTextColor(unSelectedColor);
    }


    /**
     * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     */

    private void initImageView() {
        bmpW = BitmapFactory.decodeResource(getResources(),
                R.mipmap.line_bottom).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dip2px(context, 150);//dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / pageSize - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
        // = 偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 为选项卡绑定监听器，不同页面显示不同数据
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        public void onPageScrollStateChanged(int index) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int index) {
            Animation animation = new TranslateAnimation(one * currIndex, one
                    * index, 0, 0);// 显然这个比较简洁，只有一行代码。
            currIndex = index;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            imageView.startAnimation(animation);
            tag = index;

            switch (index) {
                case 0:
                    tv_collection_all.setTextColor(selectedColor);
                    tv_history_all.setTextColor(unSelectedColor);
                    break;
                case 1:
                    tv_collection_all.setTextColor(unSelectedColor);
                    tv_history_all.setTextColor(selectedColor);
                    break;
                case 2:
                    tv_collection_all.setTextColor(unSelectedColor);
                    tv_history_all.setTextColor(unSelectedColor);
                    break;
                case 3:
                    tv_collection_all.setTextColor(unSelectedColor);
                    tv_history_all.setTextColor(unSelectedColor);
                    break;
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 头标点击监听，点击和不点击时的颜色变化LoginActivity
     */
    private class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        public void onClick(View v) {
            initTabSelected();
            switch (index) {
                case 0:
                    tv_collection_all.setTextColor(selectedColor);
                    break;
                case 1:
                    tv_history_all.setTextColor(selectedColor);
                    break;
            }
            viewPager.setCurrentItem(index);
        }
    }
}
