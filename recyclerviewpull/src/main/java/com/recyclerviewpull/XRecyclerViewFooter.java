package com.recyclerviewpull;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by expect_xh on 2016/3/23.
 */
public class XRecyclerViewFooter extends LinearLayout {
    private LinearLayout mContainer;//布局指向
    private Context mContext;//上下文引用

    public final static int STATE_LOADING = 0; //正在加载
    public final static int STATE_COMPLETE = 1;  //加载完成

    private ImageView mProgressBar;    // 正在刷新的图标

    // 均匀旋转动画
//    private RotateAnimation refreshingAnimation;
    private TextView mTextview;

    public XRecyclerViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XRecyclerViewFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public XRecyclerViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    //初始化
    private void initView(Context context) {
        mContext = context;
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listview_footer, null);
        mTextview = (TextView) mContainer.findViewById(R.id.listview_foot_more);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);

        mProgressBar = (ImageView) findViewById(R.id.listview_foot_progress);

        ///添加匀速转动动画
//        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
//                context, R.anim.rotating);
//        LinearInterpolator lir = new LinearInterpolator();
//        refreshingAnimation.setInterpolator(lir);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void removeView(){
        removeView(mContainer);
    }



    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                this.setVisibility(VISIBLE);

//                WindowManager wm = (WindowManager) getContext()
//                        .getSystemService(Context.WINDOW_SERVICE);
//                RecyclerView.LayoutParams lp;
//                lp= (RecyclerView.LayoutParams) this.getLayoutParams();
//                lp.width=wm.getDefaultDisplay().getWidth();
//                lp.height=dip2px(getContext(),50);
//                this.setLayoutParams(lp);
                mTextview.setText("加载中...");
                mProgressBar.setVisibility(VISIBLE);
//                mProgressBar.setAnimation(refreshingAnimation);
                startPropertyAnim(mProgressBar);
                break;
            case STATE_COMPLETE:
//                RecyclerView.LayoutParams lpa;
//                lpa= (RecyclerView.LayoutParams) this.getLayoutParams();
//                lpa.width=0;
//                lpa.height=0;
//                this.setLayoutParams(lpa);
                mTextview.setText("没有更多数据...");
                //this.setVisibility(GONE);
                mProgressBar.clearAnimation();
                if(mAnim != null && mAnim.isRunning()) {
                    mAnim.clone();
                }
                mProgressBar.setVisibility(GONE);
                break;
            default:
                break;
        }
    }
    private ObjectAnimator mAnim;
    // 动画实际执行
    private void startPropertyAnim(View veie) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        mAnim = ObjectAnimator.ofFloat(veie, "rotation", 0f, 360f);
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.setInterpolator(new LinearInterpolator());
        // 动画的持续时间，执行多久？
        mAnim.setDuration(500);

        // 回调监听
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

        // 正式开始启动执行动画
        mAnim.start();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
