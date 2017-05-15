package com.menganime.fragment.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.activity.LoginActivity;
import com.menganime.activity.PersonCenterActivity;
import com.menganime.base.BaseFragment;
import com.menganime.utils.SharedUtil;

/**
 * Created by Administrator on 2017/5/9.
 * 主界面第三个Fragment
 */

public class MoreFragment extends BaseFragment implements View.OnClickListener {
    private Context context;
    private TextView tv_title;
    private RelativeLayout rl_more_personal;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_more, null);
        return view;
    }

    @Override
    protected void setDate() {

    }

    @Override
    protected void init(View rootView) {
        tv_title = (TextView) rootView.findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.more_more));
        rl_more_personal = (RelativeLayout) rootView.findViewById(R.id.rl_more_personal);
        rl_more_personal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_more_personal:
                Boolean isLogin = SharedUtil.getBoolean(context,SharedUtil.IS_LOGINED,false);
                if(isLogin){
                    Intent intent = new Intent(context, PersonCenterActivity.class);
                    startActivity(intent);
                }else{
                   Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }
}
