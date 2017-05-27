package com.menganime.fragment.collectionhistoryfragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.activity.CartoonDetailsActivity;
import com.menganime.base.BaseFragment;
import com.menganime.bean.CollectionHistoryBean;
import com.menganime.utils.SharedUtil;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class CollectionFragment extends BaseFragment {
    private Context context;
    private XpulltorefereshiRecyclerView recyclerView;
    private CommonRCAdapter<CollectionHistoryBean> adapter;
    private List<CollectionHistoryBean> mList;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_lately, null);
        return view;
    }

    @Override
    protected void setDate() {
    }

    @Override
    protected void init(View rootView) {
        recyclerView=(XpulltorefereshiRecyclerView) rootView.findViewById(R.id.recyclerview_vertical);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();

        mList = SharedUtil.getCollectionHistoryListForCollection(context, SharedUtil.SAVECOLLECTIONHISTORYLIST);
        adapter = new CommonRCAdapter<CollectionHistoryBean>(getActivity(),R.layout.item_fragment_collection,mList) {
            @Override
            public void convert(ViewHolder holder,final int position) {
                if (mList != null && mList.size() > 0) {
                    CollectionHistoryBean  cartoonInfo = mList.get(position);
                    holder.loadImageFromNet(R.id.iv_cartoon_picture, cartoonInfo.getCartoonPicture(),R.mipmap.icon_default);
                    holder.setText(R.id.tv_cartoon_name, cartoonInfo.getCartoonName());
                    holder.setText(R.id.tv_watch_chapter, cartoonInfo.getWatchChapterContent()==null||cartoonInfo.getWatchChapterContent().equals("")?"未看":"看到第"+cartoonInfo.getWatchChapterContent()+"话");
                    holder.setText(R.id.tv_update_chapter, "更新到第"+cartoonInfo.getMaxChapter()+"话");
                    //holder.setImageDrawable(R.id.iv_collection_menu,getResources().getDrawable(R.mipmap.collection_menu));
                    holder.setOnClickListener(R.id.iv_collection_menu, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chapterMenu(position);
                        }
                    });
                }
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, CartoonDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name", mList.get(position).getCartoonName());
                bundle.putString("infoId",mList.get(position).getCartoonId());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
    }

    /**
     * 更改菜单
     */
    private void chapterMenu(final int position) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_chapter, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        Button cancel = (Button) view.findViewById(R.id.dialog_close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        TextView tv_chapter_name = (TextView) view.findViewById(R.id.tv_chapter_name);
        tv_chapter_name.setText(mList.get(position).getCartoonName());
        TextView dialog_delete = (TextView) view.findViewById(R.id.dialog_delete);
        //删除
        dialog_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedUtil.deleteCollection(context,SharedUtil.SAVECOLLECTIONHISTORYLIST,mList.get(position).getCartoonId());
                //mList = SharedUtil.getCollectionHistoryListForCollection(context, SharedUtil.SAVECOLLECTIONHISTORYLIST);
                mList.remove(position);
                adapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        TextView dialog_deleteAll = (TextView) view.findViewById(R.id.dialog_deleteAll);
        //全部清空
        dialog_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedUtil.deleteAllCollection(context,SharedUtil.SAVECOLLECTIONHISTORYLIST);
                //mList = SharedUtil.getCollectionHistoryListForCollection(context, SharedUtil.SAVECOLLECTIONHISTORYLIST);
                mList.clear();
                adapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
    }
}
