package com.recyclerviewpull.adapter;

import android.view.View;

/**
 * 创建时间:16/5/25 20:16
 * 本类描述:recyclerview 的点击跟长按监听接口
 */
public interface OnItemClickListener {
    /**
     * 点击监听
     *
     * @param view item
     */
    void onItemClick(View view, int position);

    /**
     * 长按监听
     */
    boolean onItemLongClick(View view, int position);
//    void onItemClick(ViewGroup parent, View view, T t, int position);
//
//    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);


}
