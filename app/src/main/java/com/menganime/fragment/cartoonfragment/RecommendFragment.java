package com.menganime.fragment.cartoonfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.menganime.R;
import com.menganime.adapter.RecommendAdapter;
import com.menganime.base.BaseFragment;
import com.menganime.utils.ToastUtil;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 精彩推荐
 */

public class RecommendFragment extends BaseFragment implements OnItemClickListener {
    private Context context;
    XpulltorefereshiRecyclerView recyclerView;
    List<String> mlist;
    RecommendAdapter adapter;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_recommend, null);
        return view;
    }

    @Override
    protected void setDate() {
        mlist=new ArrayList<String>();
        for(int i=0;i<50;i++){
            mlist.add("number"+i);
        }
    }

    @Override
    protected void init(View rootView) {
        recyclerView=(XpulltorefereshiRecyclerView) rootView.findViewById(R.id.recyclerview_vertical);
        adapter=new RecommendAdapter(context,mlist);
        adapter.setOnItemClickListener(this);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        StaggeredGridLayoutManager mLinearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
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
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ToastUtil.showToast(context,position+"");
    }

    @Override
    public boolean onItemLongClick(View view, int position) {
        return false;
    }
}
