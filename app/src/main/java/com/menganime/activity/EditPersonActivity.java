package com.menganime.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.menganime.R;
import com.menganime.weight.takephoto.app.TakePhotoActivity;

/**
 * Created by Administrator on 2017/5/15.
 * 编辑个人资料
 */

public class EditPersonActivity extends TakePhotoActivity implements View.OnClickListener{

    private TextView tv_title;

    private ImageView message_circle;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_edit_personal);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {

    }

    @Override
    protected void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.edit_personal));

        message_circle = (ImageView) findViewById(R.id.message_circle);
        message_circle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_circle:
                break;
        }
    }
}
