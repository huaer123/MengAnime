package com.menganime.fragment.cartoonfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.menganime.R;
import com.menganime.adapter.SerialAdapter;
import com.menganime.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 热门连载
 */

public class SerialFragment extends BaseFragment {
    private Context context;
    RecyclerView recyclerView;
    List<String> mlist;
    SerialAdapter adapter;

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
        adapter=new SerialAdapter(context,mlist);
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
    }
}
