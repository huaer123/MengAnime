package com.menganime.fragment.cartoonfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.menganime.R;
import com.menganime.RecommendAdapter;
import com.menganime.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 精彩推荐
 */

public class RecommendFragment extends BaseFragment {
    private Context context;
    RecyclerView recyclerView;
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
        recyclerView=(RecyclerView) rootView.findViewById(R.id.recyclerview_vertical);
        adapter=new RecommendAdapter(context,mlist);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置布局
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);
    }
}
