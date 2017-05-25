package com.menganime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.bean.CartoonInfo;
import com.menganime.bean.RecommendInfo;
import com.menganime.interfaces.CartoonClassifyInterface;
import com.menganime.utils.MyRequest;
import com.recyclerviewpull.RecycleViewDivider;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.menganime.utils.StatusBarUtils.mContext;

/**
 * Created by Administrator on 2017/5/23.
 * 通过关键字搜索的漫画列表
 */

public class CartoonListForKeyActivity extends BaseActivity implements View.OnClickListener,CartoonClassifyInterface {
    private RelativeLayout rl_back;
    private TextView tv_title;

    private TextView tv_sorry;
    private XpulltorefereshiRecyclerView recyclerView;
    private CommonRCAdapter<CartoonInfo> adapter;
    private ArrayList<CartoonInfo> mList = new ArrayList<>();

    private int pageIndex = 0;
    private String key = "";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_cartoonlist_forkey);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        key = bundle.getString("key");
    }

    @Override
    protected void init() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("搜索:"+key);

        MyRequest.getCartoonListForKey(this,pageIndex,10,key);

        tv_sorry = (TextView) findViewById(R.id.tv_sorry);
        recyclerView = (XpulltorefereshiRecyclerView) findViewById(R.id.recyclerview_vertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonRCAdapter<CartoonInfo>(this,R.layout.item_cartoon_lately,mList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (mList != null && mList.size() > 0) {
                    CartoonInfo cartoonInfo = mList.get(position);
                    holder.loadImageFromNet(R.id.iv_cartoon_picture, cartoonInfo.getColumn_IconURL(),R.mipmap.ic_launcher);
                    holder.setText(R.id.tv_cartoon_name, cartoonInfo.getName());
                    holder.setText(R.id.tv_cartoon_author, cartoonInfo.getAuthor());
                    holder.setText(R.id.tv_cartoon_Describe, cartoonInfo.getSubtitle());
                    holder.setRating(R.id.ratingbar_id,Integer.valueOf(cartoonInfo.getStar()),5);
                }
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(new XpulltorefereshiRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {

                recyclerView.setLoadingMoreEnabled(false);
                pageIndex++;
                MyRequest.getCartoonListForKey(CartoonListForKeyActivity.this,pageIndex,10,key);
            }
        });
        /*recyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, R.drawable.recyclerview_divider));*/
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CartoonListForKeyActivity.this, CartoonDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name", mList.get(position).getName());
                bundle.putString("infoId",mList.get(position).getMH_Info_ID());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void getCartoonClassify(String json) {
        if (pageIndex == 0) {// 加载
            mList.removeAll(mList);
        }
        recyclerView.loadMoreComplete();
        recyclerView.setLoadingMoreEnabled(true);

        RecommendInfo recommendInfo = JSON.parseObject(json,RecommendInfo.class);
        if(recommendInfo!=null){
            int status = Integer.valueOf(recommendInfo.getStatus());
            if(status==0){
                List<CartoonInfo> list = recommendInfo.getList();
                if (list == null || list.size() == 0) {
                    if(pageIndex==0){
                        recyclerView.setVisibility(View.GONE);
                        tv_sorry.setVisibility(View.VISIBLE);
                    }
                    return;
                }
                mList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void getCartoonNameForKey(String json) {

    }
}
