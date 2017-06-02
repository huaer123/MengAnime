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
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class CartoonListForClassifyActivity extends BaseActivity implements CartoonClassifyInterface {
    private RelativeLayout rl_back;
    private TextView tv_title;

    private int mh_type_id=1;
    private String mh_type_content;

    private XpulltorefereshiRecyclerView recyclerView;
    private CommonRCAdapter<CartoonInfo> adapter;
    private ArrayList<CartoonInfo> mList = new ArrayList<>();
    private int pageIndex = 1;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_cartoonlist_forclassify);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        mh_type_content = bundle.getString("mh_type_content");
        mh_type_id = bundle.getString("mh_type_id").equals("")?1:Integer.valueOf(bundle.getString("mh_type_id"));

        MyRequest.getCartoonListForClassify(CartoonListForClassifyActivity.this,pageIndex,10,mh_type_id);
    }

    @Override
    protected void init() {
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(mh_type_content);

        recyclerView = (XpulltorefereshiRecyclerView) findViewById(R.id.recyclerview_vertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommonRCAdapter<CartoonInfo>(this,R.layout.item_cartoon_lately,mList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (mList != null && mList.size() > 0) {
                    CartoonInfo cartoonInfo = mList.get(position);
                    holder.loadImageFromNet(R.id.iv_cartoon_picture, cartoonInfo.getColumn_IconURL(),R.mipmap.icon_default);
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
                MyRequest.getCartoonListForClassify(CartoonListForClassifyActivity.this,pageIndex,10,mh_type_id);
            }
        });
        /*recyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, R.drawable.recyclerview_divider));*/
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CartoonListForClassifyActivity.this, OrginalCartoonDetailsActivity.class);
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
    public void getCartoonClassify(String json) {
        if (pageIndex == 1) {// 加载
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
