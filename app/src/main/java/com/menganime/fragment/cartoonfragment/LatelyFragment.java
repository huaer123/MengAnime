package com.menganime.fragment.cartoonfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.menganime.R;
import com.menganime.base.BaseFragment;
import com.menganime.bean.CartoonLatelyInfo;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9.
 * 最近更新
 */

public class LatelyFragment extends BaseFragment {
    private Context context;

    private XpulltorefereshiRecyclerView recyclerView;
    private CommonRCAdapter<CartoonLatelyInfo> adapter;
    private ArrayList<CartoonLatelyInfo> mList = null;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_cartoonclassify, null);
        return view;
    }

    @Override
    protected void setDate() {
        mList = new ArrayList<>();
        for(int i=0;i<50;i++){
            CartoonLatelyInfo info = new CartoonLatelyInfo();
            info.setCartoonName("啦啦啦"+i);
            info.setCartoonAuthor("py"+i);
            info.setCartoonDescribe("ajlkdfaj;"+i);
            mList.add(info);
        }
    }

    @Override
    protected void init(View rootView) {
        recyclerView = (XpulltorefereshiRecyclerView) rootView.findViewById(R.id.recyclerview_vertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonRCAdapter<CartoonLatelyInfo>(getActivity(),R.layout.item_cartoon_lately,mList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (mList != null && mList.size() > 0) {
                    CartoonLatelyInfo cartoonInfo = mList.get(position);
                    holder.loadImageFromNet(R.id.iv_cartoon_picture, cartoonInfo.getCartoonPicture(),R.mipmap.ic_launcher);
                    holder.setText(R.id.tv_cartoon_name, cartoonInfo.getCartoonName());
                    holder.setText(R.id.tv_cartoon_author, cartoonInfo.getCartoonAuthor());
                    holder.setText(R.id.tv_cartoon_Describe, cartoonInfo.getCartoonDescribe());
                    holder.setRating(R.id.ratingbar_id,3,5);
                }
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
    }
}
