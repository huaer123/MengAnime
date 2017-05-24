package com.menganime.weight;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.menganime.R;
import com.menganime.activity.CartoonListForKeyActivity;
import com.menganime.bean.CartoonNameByKey;
import com.recyclerviewpull.RecycleViewDivider;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.List;

import static com.menganime.utils.StatusBarUtils.mContext;

/**
 * Created by Administrator on 2017/5/24.
 */

public class SearchtResultsPopwindow extends PopupWindow {
    private View contentView;
    private XpulltorefereshiRecyclerView recyclerview_results;
    private CommonRCAdapter<CartoonNameByKey.CartoonName> adapter;


    public SearchtResultsPopwindow(final Context context, LayoutInflater layoutInflater, final List<CartoonNameByKey.CartoonName> mList, int width) {
        super(context);


        contentView = layoutInflater.inflate(R.layout.own_dropdown_list, null);

        recyclerview_results = (XpulltorefereshiRecyclerView) contentView.findViewById(R.id.recyclerview_results);
        recyclerview_results.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonRCAdapter<CartoonNameByKey.CartoonName>(context, R.layout.item_own_dropdown, mList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (mList != null && mList.size() > 0) {
                    CartoonNameByKey.CartoonName cartoonInfo = mList.get(position);
                    holder.setText(R.id.tv_keyName, cartoonInfo.getName());
                }
            }
        };
        recyclerview_results.setAdapter(adapter);
        recyclerview_results.setPullRefreshEnabled(false);
        recyclerview_results.setLoadingMoreEnabled(false);
        recyclerview_results.setLoadMoreGone();
        recyclerview_results.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, R.drawable.recyclerview_divider));
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, CartoonListForKeyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", mList.get(position).getName());
                intent.putExtras(bundle);
                context.startActivity(intent);
                dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });

        this.setContentView(contentView);  //设置悬浮窗体内显示的内容View
        this.setWidth(width-5);   //设置悬浮窗体的宽度
        //this.setWidth(LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);   //设置悬浮窗体的高度
        this.setBackgroundDrawable(new ColorDrawable(0x00000000)); // 设置悬浮窗体背景
        //this.setAnimationStyle(R.style.PopupAnimation);
        this.setAnimationStyle(R.anim.slide_in_from_top);     //设置悬浮窗体出现和退出时的动画
        this.setFocusable(true);    // menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应
        this.setOutsideTouchable(true);   //可以再外部点击隐藏掉PopupWindow
    }
}
