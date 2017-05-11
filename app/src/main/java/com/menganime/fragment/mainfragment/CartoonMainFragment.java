package com.menganime.fragment.mainfragment;

import android.content.Context;
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
import android.widget.TextView;

import com.menganime.R;
import com.menganime.base.BaseFragment;
import com.menganime.fragment.cartoonfragment.LatelyFragment;
import com.menganime.fragment.cartoonfragment.OriginalFragment;
import com.menganime.fragment.cartoonfragment.RecommendFragment;
import com.menganime.fragment.cartoonfragment.SerialFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9.
 * 主界面第二个Fragment
 */

public class CartoonMainFragment extends BaseFragment {
    private Context context;

    private ViewPager viewPager;// 页卡内容
    private ImageView imageView;// 动画图片
    private ArrayList<BaseFragment> fragments;// Tab页面列表
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private int selectedColor, unSelectedColor;// 显示和不显示时的颜色变化
    /** 页卡总数 **/
    private static final int pageSize = 4;
    private int tag = 0;

    // 定义四个fragment
    public RecommendFragment aFragmnet;// 精彩推荐
    public SerialFragment iFragmnet;// 热门连载
    public OriginalFragment pFragmnet;// 原创漫画
    public LatelyFragment sFragmnet;// 最近更新

    // 定义四个控件
    private TextView tv_recruitment_all;
    private TextView tv_recruitment_internship;
    private TextView tv_recruitment_parttime;
    private TextView tv_recruitment_schoolrecruit;

    private FragmentPagerAdapter fragmentPagerAdapter;


    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_cartoonmain, null);
        return view;
    }

    @Override
    protected void setDate() {

    }

    @Override
    protected void init(View rootView) {
        viewPager = (ViewPager) rootView.findViewById(R.id.vPager);
        imageView = (ImageView) rootView.findViewById(R.id.cursor);
        tv_recruitment_all = (TextView) rootView.findViewById(R.id.tv_recruitment_all);
        tv_recruitment_internship = (TextView) rootView.findViewById(R.id.tv_recruitment_internship);
        tv_recruitment_parttime = (TextView) rootView.findViewById(R.id.tv_recruitment_parttime);
        tv_recruitment_schoolrecruit = (TextView) rootView.findViewById(R.id.tv_recruitment_schoolrecruit);

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
    public void setOperation(){
        selectedColor = getResources().getColor(R.color.black);// 点击时变成白色
        unSelectedColor = getResources().getColor(R.color.white);// 不点击时是灰色
        initImageView(); // 初始化页卡动画
        initTextView(); // 初始化头标
        initViewPager(); // 初始化Viewpager页
    }
    /**
     * 初始化Viewpager页
     */
    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new SerialFragment());
        fragments.add(new OriginalFragment());
        fragments.add(new LatelyFragment());

        aFragmnet = (RecommendFragment) fragments.get(0);
        iFragmnet = (SerialFragment) fragments.get(1);
        pFragmnet = (OriginalFragment) fragments.get(2);
        sFragmnet = (LatelyFragment) fragments.get(3);

        fragmentPagerAdapter = new FragmentPagerAdapter(
                getChildFragmentManager ()) {

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
     *
     */
    private void initTextView() {
        initTabSelected();
        tv_recruitment_all.setTextColor(selectedColor);

      /*  tv_recruitment_all.setText(getString(R.string.recruitment_all));
        tv_recruitment_internship
                .setText(getString(R.string.recruitment_internship));
        tv_recruitment_parttime
                .setText(getString(R.string.recruitment_parttime));
        tv_recruitment_schoolrecruit
                .setText(getString(R.string.recruitment_schoolrecruit));*/

        tv_recruitment_all.setOnClickListener(new MyOnClickListener(0));
        tv_recruitment_internship.setOnClickListener(new MyOnClickListener(1));
        tv_recruitment_parttime.setOnClickListener(new MyOnClickListener(2));
        tv_recruitment_schoolrecruit
                .setOnClickListener(new MyOnClickListener(3));
    }
    /**
     * 初始化选中值
     */
    public void initTabSelected() {
        tv_recruitment_all.setTextColor(unSelectedColor);
        tv_recruitment_parttime.setTextColor(unSelectedColor);
        tv_recruitment_internship.setTextColor(unSelectedColor);
        tv_recruitment_schoolrecruit.setTextColor(unSelectedColor);
    }


    /**
     * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     */

    private void initImageView() {
        bmpW = BitmapFactory.decodeResource(getResources(),
                R.mipmap.line_bottom).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
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
                    tv_recruitment_all.setTextColor(selectedColor);
                    tv_recruitment_internship.setTextColor(unSelectedColor);
                    tv_recruitment_parttime.setTextColor(unSelectedColor);
                    tv_recruitment_schoolrecruit.setTextColor(unSelectedColor);
                    break;
                case 1:
                    tv_recruitment_all.setTextColor(unSelectedColor);
                    tv_recruitment_internship.setTextColor(selectedColor);
                    tv_recruitment_parttime.setTextColor(unSelectedColor);
                    tv_recruitment_schoolrecruit.setTextColor(unSelectedColor);
                    break;
                case 2:
                    tv_recruitment_all.setTextColor(unSelectedColor);
                    tv_recruitment_internship.setTextColor(unSelectedColor);
                    tv_recruitment_parttime.setTextColor(selectedColor);
                    tv_recruitment_schoolrecruit.setTextColor(unSelectedColor);
                    break;
                case 3:
                    tv_recruitment_all.setTextColor(unSelectedColor);
                    tv_recruitment_internship.setTextColor(unSelectedColor);
                    tv_recruitment_parttime.setTextColor(unSelectedColor);
                    tv_recruitment_schoolrecruit.setTextColor(selectedColor);
                    break;
            }
        }
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
                    tv_recruitment_all.setTextColor(selectedColor);
                    break;
                case 1:
                    tv_recruitment_internship.setTextColor(selectedColor);
                    break;
                case 2:
                    tv_recruitment_parttime.setTextColor(selectedColor);
                    break;
                case 3:
                    tv_recruitment_schoolrecruit.setTextColor(selectedColor);
                    break;
            }
            viewPager.setCurrentItem(index);
        }
    }
}
