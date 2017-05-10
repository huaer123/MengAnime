package com.menganime.fragment.mainfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linked.erfli.library.base.BaseFragment;
import com.menganime.R;

/**
 * Created by Administrator on 2017/5/9.
 * 主界面第一个Fragment
 */

public class CollectionHistoryFragment extends BaseFragment {

    private Context context;

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_collectionhistory, null);
        return view;
    }

    @Override
    protected void setDate() {

    }

    @Override
    protected void init(View rootView) {

    }
}
