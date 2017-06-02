package com.menganime.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.bean.WatchCartoonBean;
import com.menganime.weight.PhotoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class WatchCartoonAdapter extends PagerAdapter {
    private ArrayList<View> viewContainter = new ArrayList<View>();
    private List<WatchCartoonBean.Picturelist> list = new ArrayList<>();
    private Context context;

    public WatchCartoonAdapter(Context context, List<WatchCartoonBean.Picturelist> list) {
        this.context = context;
        for (int i = 0; i < list.size(); i++) {
            WatchCartoonBean.Picturelist picture = list.get(i);
            final View view = LayoutInflater.from(context).inflate(R.layout.item_watch_cartoon, null);
            PhotoView iv_watch_cartoon = (PhotoView) view.findViewById(R.id.iv_watch_cartoon);
            Glide.with(context)
                    .load(picture.getChapterURL())
                    //.crossFade()//加载动画
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存
                    .placeholder(R.mipmap.loading_waiting)//默认加载图片
                    .error(R.mipmap.icon_default) //失败图片
                    .into(iv_watch_cartoon);//封面
            /*Bitmap bitmap = null;
            if (picture.getChapterURL() != null && !picture.getChapterURL().equals("")) {
                String imagePath = getImagePath(picture.getChapterURL());
                bitmap = BitmapFactory.decodeFile(imagePath);
            }
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.loading_waiting);
            }
            iv_watch_cartoon.setImageBitmap(bitmap);*/
            viewContainter.add(view);
        }
    }

    /**
     * 获取图片的本地存储路径。
     *
     * @param imageUrl
     *            图片的URL地址。
     * @return 图片的本地存储路径。
     */
    private String getImagePath(String imageUrl) {
        String imageDir = Environment.getExternalStorageDirectory().getPath()
                + "/MengAnime/WatchCartoon/";
        File file = new File(imageDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String imagePath = imageDir +System.currentTimeMillis() + ".jpg";
        return imagePath;
    }

    //viewpager中的组件数量
    @Override
    public int getCount() {
        return viewContainter.size();
    }

    //滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        ((ViewPager) container).removeView(viewContainter.get(position));
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(viewContainter.get(position));
        return viewContainter.get(position);
    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public void addList(List<WatchCartoonBean.Picturelist> list) {
        // TODO Auto-generated method stub
        this.list = list;
    }

    public void clearList() {
        // TODO Auto-generated method stub
        this.list.clear();
    }
}
