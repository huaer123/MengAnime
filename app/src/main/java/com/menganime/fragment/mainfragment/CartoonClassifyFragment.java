package com.menganime.fragment.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.menganime.R;
import com.menganime.activity.CartoonListForClassifyActivity;
import com.menganime.activity.CartoonListForKeyActivity;
import com.menganime.base.BaseFragment;
import com.menganime.bean.CartoonClassifyBean;
import com.menganime.bean.CartoonNameByKey;
import com.menganime.interfaces.CartoonClassifyInterface;
import com.menganime.utils.LogUtils;
import com.menganime.utils.MyRequest;
import com.menganime.utils.ToastUtil;
import com.menganime.weight.SearchtResultsPopwindow;
import com.recyclerviewpull.XpulltorefereshiRecyclerView;
import com.recyclerviewpull.adapter.CommonRCAdapter;
import com.recyclerviewpull.adapter.OnItemClickListener;
import com.recyclerviewpull.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 * 主界面第三个Fragment漫画分类
 */

public class CartoonClassifyFragment extends BaseFragment implements CartoonClassifyInterface {
    private Context context;
    XpulltorefereshiRecyclerView recyclerView;
    private CommonRCAdapter<CartoonClassifyBean.CartoonClassfiy> adapter;

    private EditText edit_search;

    private List<CartoonClassifyBean.CartoonClassfiy> mList = new ArrayList<CartoonClassifyBean.CartoonClassfiy>();

    @Override
    protected View setView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_cartoonclassify, null);
        return view;
    }

    @Override
    protected void setDate() {
        MyRequest.getCartoonClassify(this);
    }

    @Override
    protected void init(View rootView) {
        edit_search = (EditText) rootView.findViewById(R.id.edit_search);
        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    // 先隐藏键盘
                    ((InputMethodManager) edit_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    // 查找公司
                    if (edit_search.getText().toString().equals("")) {
                        ToastUtil.showToast(context, "请输入漫画名或作者");
                    } else {
                        Intent intent = new Intent(context, CartoonListForKeyActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key", edit_search.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    return true;
                }
                return false;
            }
        });

        recyclerView = (XpulltorefereshiRecyclerView) rootView.findViewById(R.id.recyclerview_vertical);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommonRCAdapter<CartoonClassifyBean.CartoonClassfiy>(getActivity(), R.layout.item_cartoonclassify, mList) {
            @Override
            public void convert(ViewHolder holder, int position) {
                if (mList != null && mList.size() > 0) {
                    CartoonClassifyBean.CartoonClassfiy cartoonInfo = mList.get(position);
                    holder.loadImageFromNet(R.id.tv_picture, cartoonInfo.getMH_Type_ICONURL(), R.mipmap.ic_launcher);
                    holder.setText(R.id.tv_classify, cartoonInfo.getMH_Type_Name());
                }
            }
        };
        recyclerView.setAdapter(adapter);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, CartoonListForClassifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("mh_type_id", mList.get(position).getMH_Type_ID());
                bundle.putString("mh_type_content", mList.get(position).getMH_Type_Name());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                try {
                    String key = edit_search.getText().toString();
                    if(key.length()>0) {
                        MyRequest.getCartoonNameForKey(CartoonClassifyFragment.this, key);
                        LogUtils.d("popwindow-----EditText");
                    }
                } catch (Exception e) {
                    if (e != null && e.getMessage() != null) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int
                    arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int
                    arg3) {

            }
        });
    }

    @Override
    public void getCartoonClassify(String json) {
        CartoonClassifyBean bean = JSONObject.parseObject(json, CartoonClassifyBean.class);
        if (bean.getStatus().equals("0")) {
            List<CartoonClassifyBean.CartoonClassfiy> cartoonClassfiyList = bean.getMH_TypeList();
            mList.clear();
            mList.addAll(cartoonClassfiyList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getCartoonNameForKey(String json) {
        CartoonNameByKey bean = JSONObject.parseObject(json, CartoonNameByKey.class);
        if (bean.getStatus().equals("0")) {
            List<CartoonNameByKey.CartoonName> cartoonClassfiyList = bean.getMH_InfoList();
            if(cartoonClassfiyList!=null&&cartoonClassfiyList.size()>0) {
                //实例化自定义的PopupWindow
                SearchtResultsPopwindow view = new SearchtResultsPopwindow(context, getActivity().getLayoutInflater(), cartoonClassfiyList, edit_search.getWidth());
                //制定自定义PopupWindow显示的位置
                view.showAsDropDown(edit_search, 0, -5);
                LogUtils.d("popwindow-----view.showAsDropDown(edit_search, 0, -5);");
            }
        }
    }
}
