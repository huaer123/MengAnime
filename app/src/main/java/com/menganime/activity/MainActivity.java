package com.menganime.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linked.erfli.library.base.BaseFragmentActivity;
import com.menganime.R;
import com.menganime.fragment.mainfragment.CartoonClassifyFragment;
import com.menganime.fragment.mainfragment.CartoonMainFragment;
import com.menganime.fragment.mainfragment.CollectionHistoryFragment;
import com.menganime.fragment.mainfragment.MoreFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    private Context context;
    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private static List<Fragment> fragmentList = new ArrayList<Fragment>();

    private ImageView collectionhistoryImage, cartoonmainImage, cartoonclassifyImage, moreImage;
    private LinearLayout layout_collectionhistory, layout_cartoonmain, layout_cartoonclassify, layout_more;


    private int fragmentIndex = 0;

    @Override
    public void setView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setData(Bundle savedInstanceState) {
        fragmentIndex = getIntent().getIntExtra("index", 1);
    }

    @Override
    public void init() {
        context = this;
        viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        initView();
        fragmentPagerAdapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return fragmentList.size();
            }

            @Override
            public Fragment getItem(int position) {
                // TODO Auto-generated method stub
                return fragmentList.get(position);
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);


        //初始化第一个
//        taskImage.setImageResource(R.mipmap.main_task_s);
//        taskText.setTextColor(ContextCompat.getColor(context, R.color.blue));
        changeBottom(fragmentIndex);
        viewPager.setCurrentItem(fragmentIndex);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                changeBottom(position);

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
    }

    private void changeBottom(int position) {
        /**
         * 图片
         */
        collectionhistoryImage.setImageResource(R.mipmap.main_collectionhistory);
        cartoonmainImage.setImageResource(R.mipmap.main_cartoonmain);
        cartoonclassifyImage.setImageResource(R.mipmap.main_cartoonclassify);
        moreImage.setImageResource(R.mipmap.main_more);

        switch (position) {
            case 0:
                collectionhistoryImage.setImageResource(R.mipmap.main_collectionhistory_s);
                break;
            case 1:
                cartoonmainImage.setImageResource(R.mipmap.main_cartoonmain_s);
                break;
            case 2:
                cartoonclassifyImage.setImageResource(R.mipmap.main_cartoonclassify_s);
                break;
            case 3:
                moreImage.setImageResource(R.mipmap.main_more_s);
                break;
        }
    }

    private void initView() {
        /**
         * 小图标
         */
        collectionhistoryImage = (ImageView) findViewById(R.id.main_collectionhistory_task);
        cartoonmainImage = (ImageView) findViewById(R.id.main_image_cartoonmain);
        cartoonclassifyImage = (ImageView) findViewById(R.id.main_image_cartoonclassify);
        moreImage = (ImageView) findViewById(R.id.main_image_more);

        /**
         * 点击布局控件
         */
        layout_collectionhistory = (LinearLayout) findViewById(R.id.main_layout_collectionhistory);
        layout_cartoonmain = (LinearLayout) findViewById(R.id.main_layout_cartoonmain);
        layout_cartoonclassify = (LinearLayout) findViewById(R.id.main_layout_cartoonclassify);
        layout_more = (LinearLayout) findViewById(R.id.main_layout_more);

        fragmentList.add(new CollectionHistoryFragment());
        fragmentList.add(new CartoonMainFragment());
        fragmentList.add(new CartoonClassifyFragment());
        fragmentList.add(new MoreFragment());

        layout_collectionhistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(0);
            }
        });

        layout_cartoonmain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(1);
            }
        });

        layout_cartoonclassify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(2);
            }
        });

        layout_more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(3);
            }
        });
    }
}
