package com.transport.xianxian.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.model.ChangeProfileModel;
import com.transport.xianxian.model.MyProfileModel;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.FileUtil;
import com.transport.xianxian.utils.MyChooseImages;
import com.transport.xianxian.utils.MyLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

import static com.transport.xianxian.utils.MyChooseImages.REQUEST_CODE_CAPTURE_CAMEIA;
import static com.transport.xianxian.utils.MyChooseImages.REQUEST_CODE_PICK_IMAGE;


/**
 * Created by fafukeji01 on 2017/5/8.
 * 我的资料
 */

public class MyProfileActivity extends BaseActivity {
    //选择图片及上传
    ArrayList<String> listFileNames;
    ArrayList<File> listFiles;
    ImageView imageView1;
    TextView textView1, textView2, textView3, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

    }

    @Override
    protected void onResume() {
        super.onResume();
        textView1.setText(localUserInfo.getPhonenumber());
        textView2.setText(localUserInfo.getNickname());
        textView3.setText(localUserInfo.getInvuteCode());
//        textView4.setText(localUserInfo.getEmail());

        if (!localUserInfo.getUserImage().equals(""))
            Glide.with(MyProfileActivity.this)
                    .load(OkHttpClientManager.IMGHOST + localUserInfo.getUserImage())
                    .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                    .into(imageView1);//加载图片
        else
            imageView1.setImageResource(R.mipmap.headimg);

    }

    @Override
    protected void initView() {
        imageView1 = findViewByID_My(R.id.imageView1);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);

    }

    @Override
    protected void initData() {
        //获取个人信息
        showProgress(true, getString(R.string.app_loading));
        requestInfo("?token=" + localUserInfo.getToken());
    }

    private void requestInfo(String string) {
        OkHttpClientManager.getAsyn(MyProfileActivity.this, URLs.Info + string, new OkHttpClientManager.ResultCallback<MyProfileModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(MyProfileModel response) {
                MyLogger.i(">>>>>>>>>个人信息" + response);
                //头像
                if (!response.getHead().equals(""))
                    Glide.with(MyProfileActivity.this)
                            .load(OkHttpClientManager.IMGHOST + response.getHead())
                            .centerCrop()
//                            .placeholder(R.mipmap.headimg)//加载站位图
//                            .error(R.mipmap.headimg)//加载失败
                            .into(imageView1);//加载图片
                else
                    imageView1.setImageResource(R.mipmap.headimg);

                //手机号
                textView1.setText(response.getMobile());
                //昵称
                textView2.setText(response.getNickname());
                //邀请码
                textView3.setText(response.getInvite_code());
                //等级
                textView4.setText(response.getGrade_title());

                localUserInfo.setPhoneNumber(response.getMobile());
                localUserInfo.setNickname(response.getNickname());
                localUserInfo.setInvuteCode(response.getInvite_code());
//                localUserInfo.setEmail(response.getEmail());
                localUserInfo.setUserImage(response.getHead());

                hideProgress();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.linearLayout1:
                //头像
                MyChooseImages.showPhotoDialog(MyProfileActivity.this);
                break;

            case R.id.linearLayout5:
                //邮箱
//                CommonUtil.gotoActivity(this, EmailActivity.class, false);
                break;

            case R.id.linearLayout6:
                //ETH地址管理
                CommonUtil.gotoActivity(this, SelectAddressActivity.class, false);
                break;
            case R.id.linearLayout10:
                //绑定银行卡
                CommonUtil.gotoActivity(this, BankCardSettingActivity.class, false);
                break;
            case R.id.linearLayout7:
                //交易密码
                CommonUtil.gotoActivity(this, SetTransactionPasswordActivity.class, false);
                break;
            case R.id.linearLayout8:
                //登录密码
                CommonUtil.gotoActivity(this, ChangePasswordActivity.class, false);
                break;*/
        }
    }

    //修改信息
    private void RequestChangeProfile(String[] fileKeys, File[] files, HashMap<String, String> params) {
        OkHttpClientManager.postAsyn(MyProfileActivity.this, URLs.ChangeProfile, fileKeys, files, params, new OkHttpClientManager.ResultCallback<ChangeProfileModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(ChangeProfileModel response) {
                MyLogger.i(">>>>>>>>>修改信息" + response);
                myToast("修改成功");
                localUserInfo.setUserImage(response.getHead());
                //头像
                if (!response.getHead().equals(""))
                    Glide.with(MyProfileActivity.this)
                            .load(OkHttpClientManager.IMGHOST + response.getHead())
                            .centerCrop()
//                            .placeholder(R.mipmap.headimg)//加载站位图
//                            .error(R.mipmap.headimg)//加载失败
                            .into(imageView1);//加载图片
                else
                    imageView1.setImageResource(R.mipmap.headimg);
                //邮箱
//                textView3.setText(response.getEmail());
                hideProgress();
            }
        });
    }
    @Override
    protected void updateView() {
        titleView.setTitle("修改头像/电话");
    }

    /**
     * *****************************************选择图片********************************************
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = null;
            String imagePath = null;
            switch (requestCode) {
                case REQUEST_CODE_CAPTURE_CAMEIA:
                    //相机
                    uri = Uri.parse("");
                    uri = Uri.fromFile(new File(MyChooseImages.imagepath));
                    imagePath = uri.getPath();
                    break;
                case REQUEST_CODE_PICK_IMAGE:
                    //相册
                    uri = data.getData();
                    //处理得到的url
                    ContentResolver cr = this.getContentResolver();
                    Cursor cursor = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        cursor = cr.query(uri, null, null, null, null, null);
                    }
                    if (cursor != null) {
                        cursor.moveToFirst();
                        imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    } else {
                        imagePath = uri.getPath();
                    }
                    break;
            }
            if (uri != null) {
                MyLogger.i(">>>>>>>>>>获取到的图片路径1：" + imagePath);
                //图片过大解决方法
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 32;
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

                imageView1.setImageBitmap(bitmap);
                imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
                listFileNames = new ArrayList<>();
                listFileNames.add("head");

                Uri uri1 = Uri.parse("");
                uri1 = Uri.fromFile(new File(imagePath));
                File file1 = new File(FileUtil.getPath(this, uri1));
                listFiles = new ArrayList<>();
                File newFile = null;
                try {
                    newFile = new Compressor(this).compressToFile(file1);
                    listFiles.add(newFile);
                    MyLogger.i(">>>>>选择图片结果>>>>>>>>>" + listFileNames.toString() + ">>>>>>" + listFiles.toString());
                    String[] filenames = new String[]{};
                    File[] files = new File[]{};
                    for (int i = 0; i < listFiles.size(); i++) {
                        filenames = listFileNames.toArray(new String[i]);
                        files = listFiles.toArray(new File[i]);
                    }
                    this.showProgress(true, getString(R.string.app_loading1));
                    params.put("token", localUserInfo.getToken());
//                    params.put("email", "");
                    RequestChangeProfile(filenames, files, params);//修改
                } catch (IOException e) {
                    e.printStackTrace();
                    myToast(getString(R.string.app_imgerr));
                }

            }
        }

    }
}
