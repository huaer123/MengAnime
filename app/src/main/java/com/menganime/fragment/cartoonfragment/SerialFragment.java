package com.menganime.fragment.cartoonfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.menganime.R;
import com.menganime.activity.CartoonDetailsActivity;
import com.menganime.adapter.SerialAdapter;
import com.menganime.base.BaseFragment;
import com.menganime.bean.CartoonInfo;
import com.menganime.bean.RecommendInfo;
import com.menganime.interfaces.RecommendInterface;
import com.menganime.utils.MyRequest;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 热门连载
 */

public class SerialFragment extends BaseFragment implements OnItemClickListener,RecommendInterface {
    private Context context;
    XpulltorefereshiRecyclerView recyclerView;
    List<CartoonInfo> mlist = new ArrayList<>();
    SerialAdapter adapter;
    private int pageIndex = 0;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_serical, null);
        return view;
    }

    @Override
    protected void setDate() {
        MyRequest.getRecommendList(this,pageIndex,10,2);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void init(View rootView) {
        recyclerView=(XpulltorefereshiRecyclerView) rootView.findViewById(R.id.recyclerview_verticalSerical);
        adapter=new SerialAdapter(context,mlist);
        adapter.setOnItemClickListener(this);
        //设置动画
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingListener(new XpulltorefereshiRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                recyclerView.setLoadingMoreEnabled(false);
            }

            @Override
            public void onLoadMore() {
                recyclerView.setLoadingMoreEnabled(false);
                recyclerView.setLoadingMoreEnabledAnimoto(true);
                pageIndex++;
                MyRequest.getRecommendList(SerialFragment.this,pageIndex,10,2);
            }
        });
    }

    @Override
    public void getRecommendList(String json) {
        if (pageIndex == 0) {// 加载
            mlist.clear();
            adapter.clearList();
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
                mlist.addAll(list);
                adapter.addList(mlist);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(context, CartoonDetailsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("name", mlist.get(position).getName());
        bundle.putString("infoId",mlist.get(position).getMH_Info_ID());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        return false;
    }
}
