package com.menganime.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.menganime.R;
import com.menganime.bean.UserInfoAll;
import com.menganime.config.UrlConfig;
import com.menganime.interfaces.EditPersonInterface;
import com.menganime.utils.LogUtils;
import com.menganime.utils.MyRequest;
import com.menganime.utils.SharedUtil;
import com.menganime.utils.ToastUtil;
import com.menganime.weight.takephoto.app.TakePhotoActivity;
import com.menganime.weight.takephoto.compress.CompressConfig;
import com.menganime.weight.takephoto.model.CropOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 * 编辑个人资料
 */

public class EditPersonActivity extends TakePhotoActivity implements View.OnClickListener,EditPersonInterface{

    private TextView tv_title;
    private RelativeLayout rl_back;

    private RelativeLayout rl_message_circle;
    private ImageView message_circle;//头像
    private RelativeLayout rl_edit_nickname;
    private TextView tv_nickname;//昵称
    private RelativeLayout rl_edit_sex;
    private TextView tv_sex;//性别
    private RelativeLayout rl_edit_from;
    private TextView tv_from;//来自
    private RelativeLayout rl_edit_birthday;
    private TextView tv_birthday;//生日
    private RelativeLayout rl_edit_sign;
    private TextView tv_sign;//签名

    private String userId = "";
    private String nickName="";
    private String sex = "";
    private String from = "";
    private String birthday = "";
    private String sign = "";

    private Dialog dialog;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_edit_personal);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharedUtil.getString(this,SharedUtil.USERINFO_ID);
        if(userId==null||userId.equals("")){
            ToastUtil.showToast(this,"获取用户Id失败");
            return;
        }
        MyRequest.getUserInfoForEdit(this,userId);
    }

    @Override
    protected void init() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.edit_personal));
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setVisibility(View.VISIBLE);
        rl_back.setOnClickListener(this);

        rl_message_circle = (RelativeLayout) findViewById(R.id.rl_message_circle);
        rl_message_circle.setOnClickListener(this);
        message_circle = (ImageView) findViewById(R.id.message_circle);
        rl_edit_nickname = (RelativeLayout) findViewById(R.id.rl_edit_nickname);
        rl_edit_nickname.setOnClickListener(this);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        rl_edit_sex = (RelativeLayout) findViewById(R.id.rl_edit_sex);
        rl_edit_sex.setOnClickListener(this);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        rl_edit_from = (RelativeLayout) findViewById(R.id.rl_edit_from);
        rl_edit_from.setOnClickListener(this);
        tv_from = (TextView) findViewById(R.id.tv_from);
        rl_edit_birthday = (RelativeLayout) findViewById(R.id.rl_edit_birthday);
        rl_edit_birthday.setOnClickListener(this);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        rl_edit_sign = (RelativeLayout) findViewById(R.id.rl_edit_sign);
        rl_edit_sign.setOnClickListener(this);
        tv_sign = (TextView) findViewById(R.id.tv_sign);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back://返回
                finish();
                break;
            case R.id.rl_message_circle://头像
                updatePicture();
                break;
            case R.id.rl_edit_nickname://昵称
                Intent intent =new Intent(EditPersonActivity.this,EditNickNameActivity.class);
                //用Bundle携带数据
                Bundle bundle=new Bundle();
                bundle.putString("nickName", nickName);
                intent.putExtras(bundle);

                startActivity(intent);
                break;
            case R.id.rl_edit_sex://性别
                updateSex();
                break;
            case R.id.rl_edit_from://来自
                updateFrom();
                break;
            case R.id.rl_edit_birthday://生日
                updateBirthday();
                break;
            case R.id.rl_edit_sign://签名
                Intent intentSign = new Intent(EditPersonActivity.this,EditSignActivity.class);
                Bundle bundleSign=new Bundle();
                bundleSign.putString("sign", sign);
                intentSign.putExtras(bundleSign);

                startActivity(intentSign);
                break;
            default:
                break;
        }
    }

    @Override
    public void getUserInfo(String json) {
        UserInfoAll userInfoAll = JSON.parseObject(json,UserInfoAll.class);
        if(userInfoAll.getStatus().equals("0")) {
            UserInfoAll.UserInfo userinfo = userInfoAll.getUser().get(0);
            if(userinfo!=null){
                Glide.with(this)
                        .load(userinfo.getICONURL())
                        .error(R.mipmap.picture_default) //失败图片
                        .into(message_circle);

                tv_nickname.setText(userinfo.getPetName().equals("")?"酷哥":userinfo.getPetName());
                nickName = tv_nickname.getText().toString();
                tv_sex.setText(userinfo.getSex().equals("")?"男":userinfo.getSex());
                sex = tv_sex.getText().toString();
                tv_from.setText(userinfo.getSource());
                from = tv_from.getText().toString();
                tv_birthday.setText(userinfo.getBirthday());
                birthday = tv_birthday.getText().toString();
                tv_sign.setText(userinfo.getIntroduce());
                sign = tv_sign.getText().toString();
            }
        }
    }

    @Override
    public void updateUserInfo(String json) {
        JSONObject object = JSON.parseObject(json);
        String status = object.getString("Status");
        if(status.equals("0")){
            ToastUtil.showToast(this,"修改成功");
        }else{
            ToastUtil.showToast(this,"修改失败");
        }
    }


    /**
     * 更改头像
     */
    private void updatePicture() {
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_photo, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        Button cancel = (Button) view.findViewById(R.id.dialog_close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        TextView dialog_camera = (TextView) view.findViewById(R.id.dialog_camera);
        dialog_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //摄像头
                tophoto(1);
                dialog.cancel();
            }
        });
        TextView dialog_photos = (TextView) view.findViewById(R.id.dialog_photos);
        dialog_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //本地照片
                tophoto(2);
                dialog.cancel();
            }
        });
    }

    /**
     * 更改性别
     */
    private void updateSex(){
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_sex, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        Button cancel = (Button) view.findViewById(R.id.dialog_close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

       final CheckBox checkbox_man = (CheckBox) view.findViewById(R.id.checkbox_man);
       final CheckBox checkbox_woman = (CheckBox) view.findViewById(R.id.checkbox_woman);
        if(sex.equals("男")){
            checkbox_man.setChecked(true);
            checkbox_woman.setChecked(false);
        }
        if(sex.equals("女")){
            checkbox_woman.setChecked(true);
            checkbox_man.setChecked(false);
        }

        RelativeLayout rl_edit_man = (RelativeLayout) view.findViewById(R.id.rl_edit_man);
        rl_edit_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toupdatesex(1);
                checkbox_man.setChecked(true);
                checkbox_woman.setChecked(false);
                tv_sex.setText("男");
                sex = tv_sex.getText().toString();

                dialog.cancel();
            }
        });
        RelativeLayout rl_edit_woman = (RelativeLayout) view.findViewById(R.id.rl_edit_woman);
        rl_edit_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toupdatesex(2);
                checkbox_woman.setChecked(true);
                checkbox_man.setChecked(false);
                tv_sex.setText("女");
                sex = tv_sex.getText().toString();

                dialog.cancel();
            }
        });
    }

    /**
     * 更改性别
     */
    private void toupdatesex(int type){
        MyRequest.updateUserInfo(this,userId,"",type==1?"男":"女","","","");
    }

    /**
     * 更改来自
     */
    private void updateFrom(){
        final Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_from, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        Button cancel = (Button) view.findViewById(R.id.dialog_close);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        final EditText et_from = (EditText) view.findViewById(R.id.et_from);
        et_from.setText(from);

        Button btn_edit_confirm = (Button) view.findViewById(R.id.btn_edit_confirm);
        btn_edit_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRequest.updateUserInfo(EditPersonActivity.this,userId,"","",et_from.getText().toString(),"","");
                from = et_from.getText().toString();
                tv_from.setText(et_from.getText().toString());
                dialog.cancel();
            }
        });
        Button btn_edit_cancel = (Button) view.findViewById(R.id.btn_edit_cancel);
        btn_edit_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void updateBirthday(){
        final Calendar c = Calendar.getInstance();


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(!birthday.equals("")){
            try {
                year = Integer.valueOf(birthday.split("-")[0]);
                month = Integer.valueOf(birthday.split("-")[1]);
                day = Integer.valueOf(birthday.split("-")[2]);
            }catch (Exception e){
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }
        }

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                tv_birthday.setText(DateFormat.format("yyy-MM-dd", c));
                birthday = DateFormat.format("yyy-MM-dd", c).toString();

                MyRequest.updateUserInfo(EditPersonActivity.this,userId,"","","",birthday,"");
            }
        }, year, month-1, day);
        dialog.show();
    }
    /**
     * 选择头像
     * @param type 1摄像头 2本地照片
     */
    private void tophoto(int type){
        File file = new File(Environment.getExternalStorageDirectory(), "/menganime/" + System.currentTimeMillis() + ".png");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
        if(type==1){
            //从相机拍取照片进行裁剪
            getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCaptureWithCrop(imageUri, cropOptions);
        }
        if(type==2){
            //从相册选择照片进行裁剪
            getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGalleryWithCrop(imageUri, cropOptions);
        }
    }
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(String msg) {
        super.takeFail(msg);
    }

    @Override
    public void takeSuccess(final String imagePath) {
        super.takeSuccess(imagePath);
        LogUtils.e(imagePath);
        showImg(imagePath);
        final String newFileName = System.currentTimeMillis() + ".png";
        final Map<String, String> map = new HashMap<>();
        map.put("MH_UserInfo_ID", userId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    uploadForm(map,"fileupload",new File(imagePath),newFileName, UrlConfig.UPDATEPICTURE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG",e.toString());
                }
            }
        }).start();
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        message_circle.setImageBitmap(bitmap);
    }


    private static final String BOUNDARY = "----WebKitFormBoundaryoJy7P0kXqaD8v8aX";
    /**
     * 上传图片
     *
     * @param params       传递的普通参数
     * @param fileFormName 需要上传文件表单中的名字
     * @param uploadFile   需要上传的文件名
     * @param newFileName  上传的文件名称，不填写将为uploadFile的名称
     * @param urlStr       上传的服务器的路径
     * @throws IOException
     */
    public static void uploadForm(Map<String, String> params, String fileFormName,
                                  File uploadFile, String newFileName, String urlStr)
            throws Exception {
        if (newFileName == null || newFileName.trim().equals("")) {
            newFileName = uploadFile.getName();
        }

        StringBuilder sb = new StringBuilder();
        /**
         * 普通的表单数据
         */
        for (String key : params.keySet()) {
            sb.append("--" + BOUNDARY + "\r\n");
            sb.append("Content-Disposition: form-data; name=\"" + key + "\""
                    + "\r\n");
            sb.append("\r\n");
            sb.append(params.get(key) + "\r\n");
        }
        /**
         * 上传文件的头
         */
        sb.append("--" + BOUNDARY + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"" + fileFormName
                + "\"; filename=\"" + newFileName + "\"" + "\r\n");
        sb.append("Content-Type: image/png" + "\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
        sb.append("\r\n");

        byte[] headerInfo = sb.toString().getBytes("UTF-8");
        byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
        System.out.println(sb.toString());
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + BOUNDARY);
        conn.setRequestProperty("Content-Length", String
                .valueOf(headerInfo.length + uploadFile.length()
                        + endInfo.length));
        conn.setDoOutput(true);

        OutputStream out = conn.getOutputStream();
        InputStream in = new FileInputStream(uploadFile);
        out.write(headerInfo);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) != -1)
            out.write(buf, 0, len);

        out.write(endInfo);
        in.close();
        out.close();
        if (conn.getResponseCode() == 200) {
            Log.e("TAG","上传成功");
        }
    }
}
