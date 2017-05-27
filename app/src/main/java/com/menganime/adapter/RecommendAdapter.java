package com.menganime.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.bean.CartoonInfo;
import com.recyclerviewpull.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 精彩推荐Adapter
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {

    Context mcontext;
    List<CartoonInfo> mlist;
    List<Integer> mheight;

    public final int VIEW_TYPE_HEADER = 1024;
    public final int VIEW_TYPE_NORMAL = 1025;

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public RecommendAdapter(Context context, List<CartoonInfo> list) {
        mcontext = context;
        mlist = list;
        //随机高度集合
        mheight = new ArrayList<Integer>();
        for (int i = 0; i < mlist.size(); i++) {
            mheight.add((int) (100 + Math.random() * 300));
        }
    }


    @Override
    public int getItemCount() {

        return mlist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mlist != null && position == 0) {
            return VIEW_TYPE_HEADER;
        }
        return VIEW_TYPE_NORMAL;
    }


    //绑定，渲染数据到view中
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
            ViewGroup.LayoutParams lp = holder.iv_cartoon.getLayoutParams();
            if (getItemViewType(position) == VIEW_TYPE_HEADER) {
                StaggeredGridLayoutManager.LayoutParams clp = (StaggeredGridLayoutManager.LayoutParams) holder.largeLabel.getLayoutParams();
                // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
                clp.setFullSpan(true);
                //holder.iv_cartoon.setLayoutParams(lp);
                holder.tv_cartoon_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                holder.tv_cartoon_name.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            if (getItemViewType(position) == VIEW_TYPE_NORMAL) {
                lp.height = mheight.get(position);
                holder.iv_cartoon.setLayoutParams(lp);
            }
            holder.tv_cartoon_name.setText(mlist.get(position).getName());
            holder.tv_cartoon_hua.setText(mlist.get(position).getChapter_Count()+"话");
            holder.tv_content.setText(mlist.get(position).getSubtitle());
            Glide.with(mcontext)
                .load(mlist.get(position).getColumn_IconURL())
                .error(R.mipmap.icon_default) //失败图片
                .into(holder.iv_cartoon);

            holder.largeLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(view, position);
                }
            });
    }

    //先执行onCreateViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(
                    mcontext).inflate(R.layout.item_cartoon_recommend, parent,
                    false));
    }

    //找到布局中空间位置
    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout largeLabel;
        TextView tv_cartoon_name;
        TextView tv_content;
        TextView tv_cartoon_hua;
        ImageView iv_cartoon;

        public MyViewHolder(View arg0) {
            super(arg0);
            largeLabel = (RelativeLayout) arg0.findViewById(R.id.largeLabel);
            iv_cartoon = (ImageView) arg0.findViewById(R.id.iv_cartoon);
            tv_cartoon_name = (TextView) arg0.findViewById(R.id.tv_cartoon_name);
            tv_content = (TextView) arg0.findViewById(R.id.tv_content);
            tv_cartoon_hua = (TextView) arg0.findViewById(R.id.tv_cartoon_hua);
        }

    }

    public void addList(List<CartoonInfo> list) {
        // TODO Auto-generated method stub
        this.mlist = list;
        for (int i = 0; i < mlist.size(); i++) {
            mheight.add((int) (100 + Math.random() * 300));
        }
    }

    public void clearList() {
        // TODO Auto-generated method stub
        this.mlist.clear();
        mheight.clear();
    }

    /*public void add(int pos) {

        mlist.add(pos, "insert");
        mheight.add((int) (100 + Math.random() * 300));
        notifyItemInserted(pos);
    }


    public void del(int pos) {

        mlist.remove(pos);
        notifyItemRemoved(pos);
    }*/
}
