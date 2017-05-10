package com.menganime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.menganime.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 热门连载Adapter
 */

public class SerialAdapter extends RecyclerView.Adapter<SerialAdapter.MyViewHolder>{

    Context mcontext;
    List<String> mlist;
    //List<Integer> mheight;
    public SerialAdapter(Context context, List<String> list) {
        mcontext=context;
        mlist=list;
        //随机高度集合
       /* mheight=new ArrayList<Integer>();
        for(int i=0;i<mlist.size();i++){
            mheight.add((int)(100+Math.random()*300));
        }*/
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
        holder.tv_cartoon_name.setText(mlist.get(arg1));
    }

    //先执行onCreateViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {

        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mcontext).inflate(R.layout.item_cartoon_recommend, parent,
                false));
        return holder;
    }


    //找到布局中空间位置
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_cartoon_name;
        ImageView iv_cartoon;
        public MyViewHolder(View arg0) {
            super(arg0);
            iv_cartoon = (ImageView) arg0.findViewById(R.id.iv_cartoon);
            tv_cartoon_name=(TextView) arg0.findViewById(R.id.tv_cartoon_name);
        }

    }

    public void add(int pos) {

        mlist.add(pos, "insert");
        //mheight.add((int)(100+Math.random()*300));
        notifyItemInserted(pos);
    }




    public void del(int pos) {

        mlist.remove(pos);
        notifyItemRemoved(pos);
    }
}
