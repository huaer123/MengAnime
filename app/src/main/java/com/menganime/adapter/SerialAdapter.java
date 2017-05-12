package com.menganime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 热门连载Adapter
 */

public class SerialAdapter extends RecyclerView.Adapter<SerialAdapter.MyViewHolder>{

    Context mcontext;
    List<CartoonInfo> mlist;
    public SerialAdapter(Context context, List<CartoonInfo> list) {
        mcontext=context;
        mlist=list;
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }


    @Override
    public int getItemCount() {

        return mlist.size();
    }

    //绑定，渲染数据到view中
    @Override
    public void onBindViewHolder(MyViewHolder holder, int arg1) {

        /*ViewGroup.LayoutParams lp=holder.iv_cartoon.getLayoutParams();
        lp.height=mheight.get(arg1);
        holder.iv_cartoon.setLayoutParams(lp);*/
        holder.tv_cartoon_name.setText(mlist.get(arg1).getName());
    }

    //先执行onCreateViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int position) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mcontext).inflate(R.layout.item_cartoon_recommend, parent,
                false));
        holder.tv_cartoon_name.setText(mlist.get(position).getName());
        holder.tv_cartoon_hua.setText(mlist.get(position).getChapter_Count()+"话");
        holder.tv_content.setText(mlist.get(position).getSubtitle());
        Glide.with(mcontext)
                .load(mlist.get(position).getColumn_IconURL())
                .error(R.mipmap.line_bottom) //失败图片
                .into(holder.iv_cartoon);

        holder.largeLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(view, position);
            }
        });
        return holder;
    }


    //找到布局中空间位置
    class MyViewHolder extends RecyclerView.ViewHolder{
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
    }

    public void clearList() {
        // TODO Auto-generated method stub
        this.mlist.clear();
    }
}
