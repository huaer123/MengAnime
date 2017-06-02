package com.menganime.fragment.cartoonfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.menganime.R;
import com.menganime.activity.CartoonDetailsActivity;
import com.menganime.activity.OrginalCartoonDetailsActivity;
import com.menganime.base.BaseFragment;
import com.menganime.bean.CartoonInfo;
import com.menganime.bean.RecommendInfo;
import com.menganime.interfaces.RecommendInterface;
import com.menganime.utils.MyRequest;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 最近更新
 */

public class LatelyFragment extends BaseFragment implements RecommendInterface {
    private Context context;

    private XpulltorefereshiRecyclerView recyclerView;
    private CommonRCAdapter<CartoonInfo> adapter;
    private ArrayList<CartoonInfo> mList = new ArrayList<>();
    private int pageIndex = 1;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_lately, null);
        return view;
    }

    @Override
    protected void setDate() {
        MyRequest.getRecommendList(this,pageIndex,10,4);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void init(View rootView) {
        recyclerView = (XpulltorefereshiRecyclerView) rootView.findViewById(R.id.recyclerview_vertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonRCAdapter<CartoonInfo>(getActivity(),R.layout.item_cartoon_lately,mList) {
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
                MyRequest.getRecommendList(LatelyFragment.this,pageIndex,10,4);
            }
        });
       /* recyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, R.drawable.recyclerview_divider));*/
            adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, OrginalCartoonDetailsActivity.class);
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
    public void getRecommendList(String json) {
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
}
