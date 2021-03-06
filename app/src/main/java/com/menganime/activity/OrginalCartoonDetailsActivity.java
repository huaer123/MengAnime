package com.menganime.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.base.BaseActivity;
import com.menganime.bean.CartoonChapterBean;
import com.menganime.bean.CartoonDetailsBean;
import com.menganime.bean.CollectionHistoryBean;
import com.menganime.bean.UserInfoAll;
import com.menganime.interfaces.CartoonDetailsInterface;
import com.menganime.interfaces.LoginInterface;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.weight.MyLayoutManager;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 * 原创漫画-漫画详情
 */

public class OrginalCartoonDetailsActivity extends BaseActivity implements View.OnClickListener, CartoonDetailsInterface, LoginInterface {

    private static final String WATCHCHAPTERVIP1 = "1";//vip1->1本书
    private static final String WATCHCHAPTERVIP2 = "10";//vip2->10本书
    private static final String WATCHCHAPTERVIP3 = "all";//vip3->全部
    public String userWatchNum = "0";

    private TextView tv_title;
    private RelativeLayout rl_back;
    private String titleName = "";

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

    private String userId = "";
    private String infoId;//漫画Id

    private boolean isCollection = false;
    private boolean isHistory = false;
    private String watchChapterString = "";
    private String watchChapterContent = "";
    /**
     * 用户已经观看的漫画
     */
    private List<Integer> userChapter = new ArrayList<>();
    /**
     * 用户开通VIP的信息
     */
    private UserInfoAll.VIP vip;


    @Override
    protected void setView() {
        setContentView(R.layout.activity_cartoon_details);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

        Bundle bundle = this.getIntent().getExtras();
        titleName = bundle.getString("name");
        infoId = bundle.getString("infoId");

        userId = SharedUtil.getString(this, SharedUtil.USERINFO_ID);
        MyRequest.getCartoonDetails(this, infoId, userId);
        MyRequest.selectUserChapter(this, userId);
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
        MyRequest.getCartoonChapter(this, infoId);

        userId = SharedUtil.getString(this, SharedUtil.USERINFO_ID);
        isCollection = SharedUtil.isCollection(this, SharedUtil.SAVECOLLECTIONHISTORYLIST, infoId);
        showCollection(isCollection);
        isHistory = SharedUtil.isHistory(this, SharedUtil.SAVECOLLECTIONHISTORYLIST, infoId);
        if (isHistory) {
            watchChapterString = SharedUtil.selectWatchChapterByCartoonId(this, SharedUtil.SAVECOLLECTIONHISTORYLIST, infoId);
            watchChapterContent = SharedUtil.selectWatchChapterContentByCartoonId(this, SharedUtil.SAVECOLLECTIONHISTORYLIST, infoId);
        } else {
            watchChapterString = "";
            watchChapterContent = "";
        }
        showWatchChapter();
    }

    /**
     * 关于收藏的显示
     *
     * @param isCollection
     */
    private void showCollection(boolean isCollection) {
        if (isCollection) {//已经收藏
            details_collection.setText(getString(R.string.details_already_collection));
            details_collection.setBackgroundDrawable(getResources().getDrawable(R.drawable.more_rl_background));
        } else {//未收藏
            details_collection.setText(getString(R.string.details_add_collection));
            details_collection.setBackgroundDrawable(getResources().getDrawable(R.drawable.personal_button));
        }
    }

    /**
     * 关于漫画的显示
     */
    private void showWatchChapter() {
        if (!watchChapterString.equals("")) {//已经阅读
            details_continue.setText("续看 第" + watchChapterContent + "话");
        } else {//未阅读
            details_continue.setText(getString(R.string.details_begin_watch));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                if (isCollection) {//已经收藏了
                    SharedUtil.deleteCollection(this, SharedUtil.SAVECOLLECTIONHISTORYLIST, infoId);
                    isCollection = false;
                } else {
                    CollectionHistoryBean bean = new CollectionHistoryBean();
                    bean.setType("1");
                    bean.setCartoonId(infoId);
                    bean.setCartoonPicture(detailsBean.getCover_IconURL() == null ? "" : detailsBean.getCover_IconURL());
                    bean.setCartoonName(detailsBean.getName());
                    bean.setMaxChapter(detailsBean.getMaxChapter());
                    SharedUtil.addCollection(this, SharedUtil.SAVECOLLECTIONHISTORYLIST, bean);
                    isCollection = true;
                }
                showCollection(isCollection);
                break;
            case R.id.details_continue:
                manageUserWatchChapter();
                break;
            default:
                break;
        }
    }

    /**
     * 处理用户观看漫画的逻辑
     */
    private void manageUserWatchChapter() {
        if (userId.equals("")) {
            promptLogin();
        } else {
            MyRequest.getUserInfo(this, userId);
        }
    }

    /**
     * 是否观看过这本书
     */
    private boolean IsWatched() {
        boolean isWatched = false;
        for (int i = 0; i < userChapter.size(); i++) {
            String eachinfoId = Integer.toString(userChapter.get(i));
            if (eachinfoId.equals(infoId)) {
                isWatched = true;
                break;
            }
        }
        return isWatched;
    }

    /**
     * 设置用户可以观看多少原创漫画
     */
    private void setWatchChapter() {
        if (vip != null) {
            String di = vip.getVIP_di();
            String zhong = vip.getVIP_zhong();
            String gao = vip.getVIP_gao();
            if (gao.equals("gao")) {
                userWatchNum = WATCHCHAPTERVIP3;
            } else if (di.equals("di")) {
                userWatchNum = WATCHCHAPTERVIP1;
                if (zhong.equals("zhong")) {
                    userWatchNum = Integer.valueOf(WATCHCHAPTERVIP1) + Integer.valueOf(WATCHCHAPTERVIP2) + "";
                }
            } else if (zhong.equals("zhong")) {
                userWatchNum = WATCHCHAPTERVIP2;
            } else {
                userWatchNum = "0";
                promptRecharge();
                return;
            }
            boolean isWatched = IsWatched();
            if (!isWatched) {
                if (!userWatchNum.equals("all")) {
                    if (userChapter.size() >= Integer.valueOf(userWatchNum)) {
                        promptWatchMoreThan();
                    } else {
                        toIntentWatchCartoon();
                    }
                } else {
                    toIntentWatchCartoon();
                }
            } else {
                toIntentWatchCartoon();
            }
        } else {
            promptRecharge();
            return;
        }
    }

    /**
     * 跳转到观看漫画界面
     */
    private void toIntentWatchCartoon() {
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
                    bean.setCartoonPicture(detailsBean.getCover_IconURL() == null ? "" : detailsBean.getCover_IconURL());
                    bean.setCartoonName(detailsBean.getName());
                    bean.setWatchChapter(watchChapterString);
                    bean.setWatchChapterContent(watchChapterContent);
                    SharedUtil.updateHistory(OrginalCartoonDetailsActivity.this, SharedUtil.SAVECOLLECTIONHISTORYLIST, bean);
                }
            }
        }

        Intent intent = new Intent(OrginalCartoonDetailsActivity.this, WatchCartoonActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("mh_chapter_id", watchChapterString);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 提示用户充值
     */

    private void promptRecharge() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("温馨提示");
        dialog.setMessage("您还不是会员,是否需要充值");
        dialog.setPositiveButton("充值", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //调转到登录界面
                startActivity(new Intent(OrginalCartoonDetailsActivity.this, RechargeActivity.class));
                return;
            }
        });
        dialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        return;
                    }
                });
        dialog.show();
    }

    /**
     * 提示用户充值
     */
    private void promptWatchMoreThan() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("温馨提示");
        dialog.setMessage("您本月观看的原创漫画已达上限,可以充值高级会员,观看全部原创漫画.");
        dialog.setPositiveButton("充值", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //调转到登录界面
                startActivity(new Intent(OrginalCartoonDetailsActivity.this, RechargeActivity.class));
                return;
            }
        });
        dialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        return;
                    }
                });
        dialog.show();
    }

    /**
     * 提示用户登录
     */
    private void promptLogin() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setMessage("想阅读这本漫画吗,请先登录!");
        dialog.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //调转到登录界面
                startActivity(new Intent(OrginalCartoonDetailsActivity.this, LoginActivity.class));
                return;
            }
        });
        dialog.setNeutralButton("取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        return;
                    }
                });
        dialog.show();
    }

    @Override
    public void selectDetails(String json) {
        detailsBean = JSON.parseObject(json, CartoonDetailsBean.class);
        if (detailsBean != null) {
            if (detailsBean.getStatus().equals("0")) {
                Glide.with(this)
                        .load(detailsBean.getCover_IconURL())
                        .error(R.mipmap.icon_default) //失败图片
                        .into(details_cartoonPicture);//封面
                details_name.setText(detailsBean.getName());//漫画名字
                tv_recovery.setText(detailsBean.getAuthor());//作者
                details_type.setText("免费漫画");//漫画类型
                details_fightpower.setText("战斗力：" + detailsBean.getFightPower());//战斗力
                details_source.setText("来源：" + detailsBean.getSource());//来源
                mContentText.setText(detailsBean.getIntroduce());//简介
            }
        }
    }

    @Override
    public void selectchapter(String json) {
        bean = JSON.parseObject(json, CartoonChapterBean.class);
        if (bean != null) {
            if (bean.getStatus().equals("0")) {
                details_updatetime.setText("最后更新：" + bean.getUpdateTime());
                if (bean.getLZ() != null && bean.getLZ().size() > 0) {
                    ll_LZ.setVisibility(View.VISIBLE);
                    Collections.reverse(bean.getLZ());
                    setLZChapter(bean.getLZ());
                } else {
                    ll_LZ.setVisibility(View.GONE);
                }
                if (bean.getDHB() != null && bean.getDHB().size() > 0) {
                    ll_DXB.setVisibility(View.VISIBLE);
                    Collections.reverse(bean.getDHB());
                    setDXBChapter(bean.getDHB());
                } else {
                    ll_DXB.setVisibility(View.GONE);
                }
                if (bean.getFWP() != null && bean.getFWP().size() > 0) {
                    ll_FWP.setVisibility(View.VISIBLE);
                    Collections.reverse(bean.getFWP());
                    setFWPChapter(bean.getFWP());
                } else {
                    ll_FWP.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void selectUserChapter(String json) {
        JSONObject object = JSON.parseObject(json);
        String status = object.getString("Status");
        if (status.equals("0")) {
            JSONArray array = object.getJSONArray("MH_InfoList");
            for (int i = 0; i < array.size(); i++) {
                userChapter.add(array.getInteger(i));
            }
        }
    }

    /**
     * 设置连载章节
     */
    private void setLZChapter(final List<CartoonChapterBean.LZ> lzList) {
        //details_recyclerview_LZ.setItemAnimator(new DefaultItemAnimator());
        //设置布局
        MyLayoutManager layoutManager = new MyLayoutManager(this, 4);
        layoutManager.setScrollEnabled(false);
        details_recyclerview_LZ.setLayoutManager(layoutManager);
        details_recyclerview_LZ.setPullRefreshEnabled(false);
        details_recyclerview_LZ.setLoadingMoreEnabled(false);
        details_recyclerview_LZ.setLoadMoreGone();
        adapterLZ = new CommonRCAdapter<CartoonChapterBean.LZ>(this, R.layout.item_details_chapter, lzList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (lzList != null && lzList.size() > 0) {
                    CartoonChapterBean.LZ lz = lzList.get(position);
                    holder.setText(R.id.tv_chapter_name, lz.getWhichChapter());
                    if (isHistory) {
                        if (lz.getMH_Chapter_ID().equals(watchChapterString)) {
                            holder.setBackgroundRes(R.id.tv_chapter_name, R.drawable.shape_orange);
                        } else {
                            holder.setBackgroundRes(R.id.tv_chapter_name, R.drawable.more_rl_background);
                        }
                    }
                }
            }
        };
        details_recyclerview_LZ.setAdapter(adapterLZ);
        adapterLZ.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                manageUserWatchChapter();
                //去阅读
                isHistory = true;
                CollectionHistoryBean bean = new CollectionHistoryBean();
                bean.setType("0");
                bean.setCartoonId(infoId);
                bean.setCartoonName(detailsBean.getName());
                bean.setCartoonPicture(detailsBean.getCover_IconURL() == null ? "" : detailsBean.getCover_IconURL());
                watchChapterString = lzList.get(position).getMH_Chapter_ID();
                bean.setWatchChapter(lzList.get(position).getMH_Chapter_ID());
                bean.setWatchChapterContent(lzList.get(position).getWhichChapter());
                SharedUtil.updateHistory(OrginalCartoonDetailsActivity.this, SharedUtil.SAVECOLLECTIONHISTORYLIST, bean);
                adapterLZ.notifyDataSetChanged();
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
        MyLayoutManager layoutManager = new MyLayoutManager(this, 4);
        layoutManager.setScrollEnabled(false);
        details_recyclerview_DXB.setLayoutManager(layoutManager);
        details_recyclerview_DXB.setPullRefreshEnabled(false);
        details_recyclerview_DXB.setLoadingMoreEnabled(false);
        details_recyclerview_DXB.setLoadMoreGone();
        adapterDHB = new CommonRCAdapter<CartoonChapterBean.DHB>(this, R.layout.item_details_chapter, dxbList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (dxbList != null && dxbList.size() > 0) {
                    CartoonChapterBean.DHB dhb = dxbList.get(position);
                    holder.setText(R.id.tv_chapter_name, dhb.getWhichChapter());
                    if (isHistory) {
                        if (dhb.getMH_Chapter_ID().equals(watchChapterString)) {
                            holder.setBackgroundRes(R.id.tv_chapter_name, R.drawable.shape_orange);
                        } else {
                            holder.setBackgroundRes(R.id.tv_chapter_name, R.drawable.more_rl_background);
                        }
                    }
                }
            }
        };
        details_recyclerview_DXB.setAdapter(adapterDHB);
        adapterDHB.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                manageUserWatchChapter();
                isHistory = true;
                CollectionHistoryBean bean = new CollectionHistoryBean();
                bean.setType("0");
                bean.setCartoonId(infoId);
                bean.setCartoonName(detailsBean.getName());
                bean.setCartoonPicture(detailsBean.getCover_IconURL() == null ? "" : detailsBean.getCover_IconURL());
                watchChapterString = dxbList.get(position).getMH_Chapter_ID();
                bean.setWatchChapter(dxbList.get(position).getMH_Chapter_ID());
                bean.setWatchChapterContent(dxbList.get(position).getWhichChapter());
                SharedUtil.updateHistory(OrginalCartoonDetailsActivity.this, SharedUtil.SAVECOLLECTIONHISTORYLIST, bean);
                adapterLZ.notifyDataSetChanged();
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
        MyLayoutManager layoutManager = new MyLayoutManager(this, 4);
        layoutManager.setScrollEnabled(false);
        details_recyclerview_FWP.setLayoutManager(layoutManager);
        details_recyclerview_FWP.setPullRefreshEnabled(false);
        details_recyclerview_FWP.setLoadingMoreEnabled(false);
        details_recyclerview_FWP.setLoadMoreGone();
        adapterFWP = new CommonRCAdapter<CartoonChapterBean.FWP>(this, R.layout.item_details_chapter, fwpList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (fwpList != null && fwpList.size() > 0) {
                    CartoonChapterBean.FWP fwp = fwpList.get(position);
                    holder.setText(R.id.tv_chapter_name, fwp.getWhichChapter());
                    if (isHistory) {
                        if (fwp.getMH_Chapter_ID().equals(watchChapterString)) {
                            holder.setBackgroundRes(R.id.tv_chapter_name, R.drawable.shape_orange);
                        } else {
                            holder.setBackgroundRes(R.id.tv_chapter_name, R.drawable.more_rl_background);
                        }
                    }
                }
            }
        };
        details_recyclerview_FWP.setAdapter(adapterFWP);
        adapterFWP.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                manageUserWatchChapter();
                isHistory = true;
                CollectionHistoryBean bean = new CollectionHistoryBean();
                bean.setType("0");
                bean.setCartoonId(infoId);
                bean.setCartoonName(detailsBean.getName());
                bean.setCartoonPicture(detailsBean.getCover_IconURL() == null ? "" : detailsBean.getCover_IconURL());
                watchChapterString = fwpList.get(position).getMH_Chapter_ID();
                bean.setWatchChapter(fwpList.get(position).getMH_Chapter_ID());
                bean.setWatchChapterContent(fwpList.get(position).getWhichChapter());
                SharedUtil.updateHistory(OrginalCartoonDetailsActivity.this, SharedUtil.SAVECOLLECTIONHISTORYLIST, bean);
                adapterLZ.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
    }

    @Override
    public void login(String json) {
        UserInfoAll userInfoAll = JSON.parseObject(json, UserInfoAll.class);
        if (userInfoAll.getStatus().equals("0")) {
            vip = userInfoAll.getVIP().get(0);
        }
        //去阅读
        setWatchChapter();
    }
}
