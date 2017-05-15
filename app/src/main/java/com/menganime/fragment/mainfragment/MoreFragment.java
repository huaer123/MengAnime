package com.menganime.fragment.mainfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.base.BaseFragment;

/**
 * Created by Administrator on 2017/5/9.
 * 主界面第三个Fragment
 */

public class MoreFragment extends BaseFragment {
    private Context context;
    private TextView tv_title;

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
    }
}
