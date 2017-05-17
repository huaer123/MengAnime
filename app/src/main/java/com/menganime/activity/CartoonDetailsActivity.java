package com.menganime.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
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
import com.menganime.interfaces.CartoonDetailsInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;
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

    private LinearLayout ll_LZ;
    private LinearLayout ll_DXB;
    private LinearLayout ll_FWP;
    private XpulltorefereshiRecyclerView details_recyclerview_LZ;
    private XpulltorefereshiRecyclerView details_recyclerview_DXB;
    private XpulltorefereshiRecyclerView details_recyclerview_FWP;

    private CartoonChapterBean chapterBean;

    String userId="";
    private String infoId;//漫画Id

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
        if(userId==null||userId.equals("")){
            ToastUtil.showToast(this,"获取用户Id失败");
            return;
        }
        MyRequest.getCartoonDetails(this,infoId,userId);
        MyRequest.getCartoonChapter(this,infoId);
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
        details_updatetime = (TextView) findViewById(R.id.details_updatetime);

        ll_LZ = (LinearLayout) findViewById(R.id.ll_LZ);
        ll_DXB = (LinearLayout) findViewById(R.id.ll_DXB);
        ll_FWP = (LinearLayout) findViewById(R.id.ll_FWP);
        details_recyclerview_LZ = (XpulltorefereshiRecyclerView) findViewById(R.id.details_recyclerview_LZ);
        details_recyclerview_DXB = (XpulltorefereshiRecyclerView) findViewById(R.id.details_recyclerview_DXB);
        details_recyclerview_FWP = (XpulltorefereshiRecyclerView) findViewById(R.id.details_recyclerview_FWP);
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
            default:
                break;
        }
    }

    @Override
    public void selectDetails(String json) {
        CartoonDetailsBean bean = JSON.parseObject(json,CartoonDetailsBean.class);
        if(bean!=null){
            if(bean.getStatus().equals("0")){
                Glide.with(this)
                        .load(bean.getCover_IconURL())
                        .error(R.mipmap.ic_launcher) //失败图片
                        .into(details_cartoonPicture);//封面
                details_name.setText(bean.getName());//漫画名字
                tv_recovery.setText(bean.getAuthor());//作者
                details_type.setText("免费漫画");//漫画类型
                details_fightpower.setText("战斗力："+bean.getFightPower());//战斗力
                details_source.setText("来源："+bean.getSource());//来源
                mContentText.setText(bean.getIntroduce());//简介
            }
        }
    }

    @Override
    public void selectchapter(String json) {
        CartoonChapterBean bean = JSON.parseObject(json,CartoonChapterBean.class);
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

    /**
     * 设置连载章节
     */
    private void setLZChapter(final List<CartoonChapterBean.LZ> lzList) {
        details_recyclerview_LZ.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        details_recyclerview_LZ.setLayoutManager(layoutManager);
        details_recyclerview_LZ.setPullRefreshEnabled(false);
        details_recyclerview_LZ.setLoadingMoreEnabled(false);
        details_recyclerview_LZ.setLoadMoreGone();
        CommonRCAdapter<CartoonChapterBean.LZ> adapter = new CommonRCAdapter<CartoonChapterBean.LZ>(this,R.layout.item_details_chapter,lzList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (lzList != null && lzList.size() > 0) {
                    CartoonChapterBean.LZ lz = lzList.get(position);
                    holder.setText(R.id.tv_chapter_name, lz.getWhichChapter());
                }
            }
        };
        details_recyclerview_LZ.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(CartoonDetailsActivity.this,position+"");
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
        details_recyclerview_DXB.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        details_recyclerview_DXB.setLayoutManager(layoutManager);
        details_recyclerview_DXB.setPullRefreshEnabled(false);
        details_recyclerview_DXB.setLoadingMoreEnabled(false);
        details_recyclerview_DXB.setLoadMoreGone();
        CommonRCAdapter<CartoonChapterBean.DHB> adapter = new CommonRCAdapter<CartoonChapterBean.DHB>(this,R.layout.item_details_chapter,dxbList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (dxbList != null && dxbList.size() > 0) {
                    CartoonChapterBean.DHB dhb = dxbList.get(position);
                    holder.setText(R.id.tv_chapter_name, dhb.getWhichChapter());
                }
            }
        };
        details_recyclerview_DXB.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(CartoonDetailsActivity.this,position+"");
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
        details_recyclerview_FWP.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        details_recyclerview_FWP.setLayoutManager(layoutManager);
        details_recyclerview_FWP.setPullRefreshEnabled(false);
        details_recyclerview_FWP.setLoadingMoreEnabled(false);
        details_recyclerview_FWP.setLoadMoreGone();
        CommonRCAdapter<CartoonChapterBean.FWP> adapter = new CommonRCAdapter<CartoonChapterBean.FWP>(this,R.layout.item_details_chapter,fwpList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (fwpList != null && fwpList.size() > 0) {
                    CartoonChapterBean.FWP fwp = fwpList.get(position);
                    holder.setText(R.id.tv_chapter_name, fwp.getWhichChapter());
                }
            }
        };
        details_recyclerview_FWP.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showToast(CartoonDetailsActivity.this,position+"");
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
    }
}
