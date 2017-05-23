package com.menganime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.bean.CartoonChapterBean;
import com.menganime.bean.CartoonDetailsBean;
import com.menganime.bean.CollectionHistoryBean;
import com.menganime.interfaces.CartoonDetailsInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 * 漫画详情
 */

public class CartoonDetailsActivity extends BaseActivity implements View.OnClickListener,CartoonDetailsInterface{

    private TextView tv_title;
    private RelativeLayout rl_back;
    private String titleName="";

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 2;// 默认展示最大行数2行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态

    private TextView mContentText;// 展示文本内容
    private RelativeLayout mShowMore;// 展示更多
    private ImageView mImageSpread;// 展开
    private ImageView mImageShrinkUp;// 收起

    private ImageView details_cartoonPicture;//封面
    private TextView details_name;//漫画名字
    private TextView tv_recovery;//作者
    private TextView details_type;//漫画类型
    private TextView details_fightpower;//战斗力
    private TextView details_source;//来源
    private TextView details_updatetime;//最后更新时间

    private Button details_collection;//收藏
    private Button details_continue;//续看

    private LinearLayout ll_LZ;
    private LinearLayout ll_DXB;
    private LinearLayout ll_FWP;
    private XpulltorefereshiRecyclerView details_recyclerview_LZ;
    private XpulltorefereshiRecyclerView details_recyclerview_DXB;
    private XpulltorefereshiRecyclerView details_recyclerview_FWP;

    private CommonRCAdapter<CartoonChapterBean.LZ> adapterLZ;
    private CommonRCAdapter<CartoonChapterBean.DHB> adapterDHB;
    private CommonRCAdapter<CartoonChapterBean.FWP> adapterFWP;

    private CartoonChapterBean bean;
    private CartoonDetailsBean detailsBean;

    private String userId="";
    private String infoId;//漫画Id

    private boolean isCollection = false;
    private boolean isHistory = false;
    private String watchChapterString="";
    private String watchChapterContent="";

    @Override
    protected void setView() {
        setContentView(R.layout.activity_cartoon_details);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

        Bundle bundle = this.getIntent().getExtras();
        titleName = bundle.getString("name");
        infoId = bundle.getString("infoId");

        userId = SharedUtil.getString(this,SharedUtil.USERINFO_ID);
        MyRequest.getCartoonDetails(this,infoId,userId);
    }

    @Override
    protected void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(titleName);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(this);

        mContentText = (TextView) findViewById(R.id.details_description);
        mShowMore = (RelativeLayout) findViewById(R.id.show_more);
        mImageSpread = (ImageView) findViewById(R.id.spread);
        mImageShrinkUp = (ImageView) findViewById(R.id.shrink_up);
        mShowMore.setOnClickListener(this);

        details_cartoonPicture = (ImageView) findViewById(R.id.details_cartoonPicture);//封面
        details_name = (TextView) findViewById(R.id.details_name);//漫画名字
        tv_recovery = (TextView) findViewById(R.id.tv_recovery);//作者
        details_type = (TextView) findViewById(R.id.details_type);//漫画类型
        details_fightpower = (TextView) findViewById(R.id.details_fightpower);//战斗力
        details_source = (TextView) findViewById(R.id.details_source);//来源

        details_collection = (Button) findViewById(R.id.details_collection);
        details_collection.setOnClickListener(this);
        details_continue = (Button) findViewById(R.id.details_continue);
        details_continue.setOnClickListener(this);

        details_updatetime = (TextView) findViewById(R.id.details_updatetime);
        ll_LZ = (LinearLayout) findViewById(R.id.ll_LZ);
        ll_DXB = (LinearLayout) findViewById(R.id.ll_DXB);
        ll_FWP = (LinearLayout) findViewById(R.id.ll_FWP);
        details_recyclerview_LZ = (XpulltorefereshiRecyclerView) findViewById(R.id.details_recyclerview_LZ);
        details_recyclerview_DXB = (XpulltorefereshiRecyclerView) findViewById(R.id.details_recyclerview_DXB);
        details_recyclerview_FWP = (XpulltorefereshiRecyclerView) findViewById(R.id.details_recyclerview_FWP);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyRequest.getCartoonChapter(this,infoId);

        isCollection = SharedUtil.isCollection(this,SharedUtil.SAVECOLLECTIONHISTORYLIST,infoId);
        showCollection(isCollection);
        isHistory = SharedUtil.isHistory(this,SharedUtil.SAVECOLLECTIONHISTORYLIST,infoId);
        if(isHistory){
            watchChapterString = SharedUtil.selectWatchChapterByCartoonId(this,SharedUtil.SAVECOLLECTIONHISTORYLIST,infoId);
            watchChapterContent =SharedUtil.selectWatchChapterContentByCartoonId(this,SharedUtil.SAVECOLLECTIONHISTORYLIST,infoId);
        }else{
            watchChapterString = "";
            watchChapterContent = "";
        }
        showWatchChapter();
    }

    private void showCollection(boolean isCollection) {
        if(isCollection){//已经收藏
            details_collection.setText(getString(R.string.details_already_collection));
            details_collection.setBackgroundDrawable(getResources().getDrawable(R.drawable.more_rl_background));
        }else{//未收藏
            details_collection.setText(getString(R.string.details_add_collection));
            details_collection.setBackgroundDrawable(getResources().getDrawable(R.drawable.personal_button));
        }
    }

    private void showWatchChapter() {
        if(!watchChapterString.equals("")){//已经阅读
            details_continue.setText("续看 第"+watchChapterContent+"话");
        }else{//未阅读
            details_continue.setText(getString(R.string.details_begin_watch));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.show_more:
                if (mState == SPREAD_STATE) {
                    mContentText.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.GONE);
                    mImageSpread.setVisibility(View.VISIBLE);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    mContentText.setMaxLines(Integer.MAX_VALUE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.VISIBLE);
                    mImageSpread.setVisibility(View.GONE);
                    mState = SPREAD_STATE;
                }
                break;
            case R.id.details_collection:
                if(isCollection){//已经收藏了
                    SharedUtil.deleteCollection(this,SharedUtil.SAVECOLLECTIONHISTORYLIST,infoId);
                    isCollection = false;
                }else{
                    CollectionHistoryBean bean = new CollectionHistoryBean();
                    bean.setType("1");
                    bean.setCartoonId(infoId);
                    bean.setCartoonName(detailsBean.getName());
                    bean.setMaxChapter(detailsBean.getMaxChapter());
                    SharedUtil.addCollection(this,SharedUtil.SAVECOLLECTIONHISTORYLIST,bean);
                    isCollection = true;
                }
                showCollection(isCollection);
                break;
            case R.id.details_continue:
                if (watchChapterString.equals("")) {
                    if (bean != null) {
                        if (bean.getStatus().equals("0")) {
                            if (bean.getLZ() != null && bean.getLZ().size() > 0) {
                                watchChapterString = bean.getLZ().get(0).getMH_Chapter_ID();
                                watchChapterContent = bean.getLZ().get(0).getWhichChapter();
                            } else if (bean.getDHB() != null && bean.getDHB().size() > 0) {
                                watchChapterString = bean.getDHB().get(0).getMH_Chapter_ID();
                                watchChapterContent = bean.getDHB().get(0).getWhichChapter();
                            } else if (bean.getFWP() != null && bean.getFWP().size() > 0) {
                                watchChapterString = bean.getFWP().get(0).getMH_Chapter_ID();
                                watchChapterContent = bean.getFWP().get(0).getWhichChapter();
                            }

                            isHistory = true;
                            CollectionHistoryBean bean = new CollectionHistoryBean();
                            bean.setType("0");
                            bean.setCartoonId(infoId);
                            bean.setCartoonName(detailsBean.getName());
                            bean.setWatchChapter(watchChapterString);
                            bean.setWatchChapterContent(watchChapterContent);
                            SharedUtil.updateHistory(CartoonDetailsActivity.this, SharedUtil.SAVECOLLECTIONHISTORYLIST, bean);
                        }
                    }
                }

                Intent intent = new Intent(CartoonDetailsActivity.this, WatchCartoonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mh_chapter_id", watchChapterString);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void selectDetails(String json) {
        detailsBean = JSON.parseObject(json,CartoonDetailsBean.class);
        if(detailsBean!=null){
            if(detailsBean.getStatus().equals("0")){
                Glide.with(this)
                        .load(detailsBean.getCover_IconURL())
                        .error(R.mipmap.ic_launcher) //失败图片
                        .into(details_cartoonPicture);//封面
                details_name.setText(detailsBean.getName());//漫画名字
                tv_recovery.setText(detailsBean.getAuthor());//作者
                details_type.setText("免费漫画");//漫画类型
                details_fightpower.setText("战斗力："+detailsBean.getFightPower());//战斗力
                details_source.setText("来源："+detailsBean.getSource());//来源
                mContentText.setText(detailsBean.getIntroduce());//简介
            }
        }
    }

    @Override
    public void selectchapter(String json) {
        bean = JSON.parseObject(json,CartoonChapterBean.class);
        if(bean!=null){
            if(bean.getStatus().equals("0")){
                details_updatetime.setText("最后更新："+bean.getUpdateTime());
                if(bean.getLZ()!=null&&bean.getLZ().size()>0){
                    ll_LZ.setVisibility(View.VISIBLE);
                    setLZChapter(bean.getLZ());
                }else{
                    ll_LZ.setVisibility(View.GONE);
                }
                if(bean.getDHB()!=null&&bean.getDHB().size()>0){
                    ll_DXB.setVisibility(View.VISIBLE);
                    setDXBChapter(bean.getDHB());
                }else{
                    ll_DXB.setVisibility(View.GONE);
                }
                if(bean.getFWP()!=null&&bean.getFWP().size()>0){
                    ll_FWP.setVisibility(View.VISIBLE);
                    setFWPChapter(bean.getFWP());
                }else{
                    ll_FWP.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void selectUserChapter(String json) {

    }

    /**
     * 设置连载章节
     */
    private void setLZChapter(final List<CartoonChapterBean.LZ> lzList) {
        //details_recyclerview_LZ.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        details_recyclerview_LZ.setLayoutManager(layoutManager);
        details_recyclerview_LZ.setPullRefreshEnabled(false);
        details_recyclerview_LZ.setLoadingMoreEnabled(false);
        details_recyclerview_LZ.setLoadMoreGone();
        adapterLZ = new CommonRCAdapter<CartoonChapterBean.LZ>(this,R.layout.item_details_chapter,lzList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (lzList != null && lzList.size() > 0) {
                    CartoonChapterBean.LZ lz = lzList.get(position);
                    holder.setText(R.id.tv_chapter_name, lz.getWhichChapter());
                    if (isHistory){
                        if(lz.getMH_Chapter_ID().equals(watchChapterString)){
                            holder.setBackgroundRes(R.id.tv_chapter_name,R.drawable.shape_orange);
                        }else{
                            holder.setBackgroundRes(R.id.tv_chapter_name,R.drawable.more_rl_background);
                        }
                    }
                }
            }
        };
        details_recyclerview_LZ.setAdapter(adapterLZ);
        adapterLZ.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                isHistory=true;
                CollectionHistoryBean bean = new CollectionHistoryBean();
                bean.setType("0");
                bean.setCartoonId(infoId);
                bean.setCartoonName(detailsBean.getName());
                watchChapterString = lzList.get(position).getMH_Chapter_ID();
                bean.setWatchChapter(lzList.get(position).getMH_Chapter_ID());
                bean.setWatchChapterContent(lzList.get(position).getWhichChapter());
                bean.setMaxChapter(detailsBean.getMaxChapter());
                SharedUtil.updateHistory(CartoonDetailsActivity.this,SharedUtil.SAVECOLLECTIONHISTORYLIST,bean);
                adapterLZ.notifyDataSetChanged();
                Intent intent = new Intent(CartoonDetailsActivity.this,WatchCartoonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mh_chapter_id",lzList.get(position).getMH_Chapter_ID());
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
     * 设置单行本章节
     */
    private void setDXBChapter(final List<CartoonChapterBean.DHB> dxbList) {
        //details_recyclerview_DXB.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        details_recyclerview_DXB.setLayoutManager(layoutManager);
        details_recyclerview_DXB.setPullRefreshEnabled(false);
        details_recyclerview_DXB.setLoadingMoreEnabled(false);
        details_recyclerview_DXB.setLoadMoreGone();
        adapterDHB = new CommonRCAdapter<CartoonChapterBean.DHB>(this,R.layout.item_details_chapter,dxbList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (dxbList != null && dxbList.size() > 0) {
                    CartoonChapterBean.DHB dhb = dxbList.get(position);
                    holder.setText(R.id.tv_chapter_name, dhb.getWhichChapter());
                    if (isHistory){
                        if(dhb.getMH_Chapter_ID().equals(watchChapterString)){
                            holder.setBackgroundRes(R.id.tv_chapter_name,R.drawable.shape_orange);
                        }else{
                            holder.setBackgroundRes(R.id.tv_chapter_name,R.drawable.more_rl_background);
                        }
                    }
                }
            }
        };
        details_recyclerview_DXB.setAdapter(adapterDHB);
        adapterDHB.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                isHistory=true;
                CollectionHistoryBean bean = new CollectionHistoryBean();
                bean.setType("0");
                bean.setCartoonId(infoId);
                bean.setCartoonName(detailsBean.getName());
                watchChapterString = dxbList.get(position).getMH_Chapter_ID();
                bean.setWatchChapter(dxbList.get(position).getMH_Chapter_ID());
                bean.setWatchChapterContent(dxbList.get(position).getWhichChapter());
                bean.setMaxChapter(detailsBean.getMaxChapter());
                SharedUtil.updateHistory(CartoonDetailsActivity.this,SharedUtil.SAVECOLLECTIONHISTORYLIST,bean);
                adapterDHB.notifyDataSetChanged();
                Intent intent = new Intent(CartoonDetailsActivity.this,WatchCartoonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mh_chapter_id",dxbList.get(position).getMH_Chapter_ID());
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
     * 设置番外篇章节
     */
    private void setFWPChapter(final List<CartoonChapterBean.FWP> fwpList) {
        //details_recyclerview_FWP.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        details_recyclerview_FWP.setLayoutManager(layoutManager);
        details_recyclerview_FWP.setPullRefreshEnabled(false);
        details_recyclerview_FWP.setLoadingMoreEnabled(false);
        details_recyclerview_FWP.setLoadMoreGone();
        adapterFWP = new CommonRCAdapter<CartoonChapterBean.FWP>(this,R.layout.item_details_chapter,fwpList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (fwpList != null && fwpList.size() > 0) {
                    CartoonChapterBean.FWP fwp = fwpList.get(position);
                    holder.setText(R.id.tv_chapter_name, fwp.getWhichChapter());
                    if (isHistory){
                        if(fwp.getMH_Chapter_ID().equals(watchChapterString)){
                            holder.setBackgroundRes(R.id.tv_chapter_name,R.drawable.shape_orange);
                        }else{
                            holder.setBackgroundRes(R.id.tv_chapter_name,R.drawable.more_rl_background);
                        }
                    }
                }
            }
        };
        details_recyclerview_FWP.setAdapter(adapterFWP);
        adapterFWP.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                isHistory=true;
                CollectionHistoryBean bean = new CollectionHistoryBean();
                bean.setType("0");
                bean.setCartoonId(infoId);
                bean.setCartoonName(detailsBean.getName());
                watchChapterString = fwpList.get(position).getMH_Chapter_ID();
                bean.setWatchChapter(fwpList.get(position).getMH_Chapter_ID());
                bean.setWatchChapterContent(fwpList.get(position).getWhichChapter());
                bean.setMaxChapter(detailsBean.getMaxChapter());
                SharedUtil.updateHistory(CartoonDetailsActivity.this,SharedUtil.SAVECOLLECTIONHISTORYLIST,bean);
                adapterFWP.notifyDataSetChanged();
                Intent intent = new Intent(CartoonDetailsActivity.this,WatchCartoonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mh_chapter_id",fwpList.get(position).getMH_Chapter_ID());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
    }
}
