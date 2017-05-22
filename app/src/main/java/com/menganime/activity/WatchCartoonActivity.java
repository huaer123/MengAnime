package com.menganime.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.menganime.R;
import com.menganime.adapter.WatchCartoonAdapter;
import com.menganime.base.BaseActivity;
import com.menganime.bean.WatchCartoonBean;
import com.menganime.interfaces.WatchCartoonInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 * 观看漫画Activity
 */

public class WatchCartoonActivity extends BaseActivity implements WatchCartoonInterface{
    String mh_chapter_id = "";

    private ViewPager viewpager;
    private WatchCartoonAdapter cartoonAdapter;
    private int currentViewID,pager_num;
    private int lastValue = -1;
    private boolean left = false;
    private boolean right = false;

    private RelativeLayout rl_title_all;
    private TextView tv_title;
    private RelativeLayout rl_back;

    List<WatchCartoonBean.Picturelist> picturelist = new ArrayList<>();

    @Override
    protected void setView() {
        setContentView(R.layout.activity_watch_cartoon);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        mh_chapter_id = bundle.getString("mh_chapter_id");
        MyRequest.selectChapterContent(this,mh_chapter_id, SharedUtil.getString(this,SharedUtil.USERINFO_ID));
    }

    @Override
    protected void init() {
        rl_title_all = (RelativeLayout) findViewById(R.id.rl_title_all);
        rl_title_all.setBackgroundResource(R.color.transparent);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                currentViewID = arg0;
                tv_title.setText(arg0+1+"/"+picturelist.size());//设置当前漫画数
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

    @Override
    public void getWatchCartoon(String json) {
        WatchCartoonBean watchCartoonBean = JSONObject.parseObject(json,WatchCartoonBean.class);
        if(watchCartoonBean.getStatus().equals("0")){
            picturelist = watchCartoonBean.getPicturelist();
            tv_title.setText("1/"+picturelist.size());
            cartoonAdapter = new WatchCartoonAdapter(this,picturelist);
            viewpager.setAdapter(cartoonAdapter);
        }else{
            ToastUtil.showToast(this,"获取漫画失败");
        }
    }
}
