package com.delivery.xianxian.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.Auth_ShenFenZhengModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyChooseImages;
import com.delivery.xianxian.utils.FileUtil;
import com.delivery.xianxian.utils.MyLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;
import static com.delivery.xianxian.utils.MyChooseImages.REQUEST_CODE_CAPTURE_CAMEIA;
import static com.delivery.xianxian.utils.MyChooseImages.REQUEST_CODE_PICK_IMAGE;

/**
 * Created by zyz on 2019-10-01.
 * 身份证认证
 */
public class Auth_ShenFenZhengActivity extends BaseActivity {
    //选择图片及上传
    ArrayList<String> listFileNames = new ArrayList<>();
    ArrayList<File> listFiles = new ArrayList<>();
    String type = "";

    ImageView imageView1, imageView2;
    LinearLayout linearLayout1, linearLayout2;

    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_shenfenzheng);
    }

    @Override
    protected void initView() {
        imageView1 = findViewByID_My(R.id.imageView1);
        imageView2 = findViewByID_My(R.id.imageView2);
        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);

        textView1 = findViewByID_My(R.id.textView1);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imageView1:
            case R.id.linearLayout1:
                type = "identity_front_image";
                for (int i = 0; i < listFileNames.size(); i++) {
                    if (listFileNames.get(i).equals(type)) {
                        //删除
                        listFileNames.remove(i);
                        listFiles.remove(i);
                    }
                }
                MyChooseImages.showPhotoDialog(this);
                break;

            case R.id.imageView2:
            case R.id.linearLayout2:
                type = "identity_reverse_image";
                for (int i = 0; i < listFileNames.size(); i++) {
                    if (listFileNames.get(i).equals(type)) {
                        //删除
                        listFileNames.remove(i);
                        listFiles.remove(i);
                    }
                }
                MyChooseImages.showPhotoDialog(this);
                break;
            case R.id.textView1:
                //提交
                if (match()) {
                    showProgress(false, getString(R.string.app_loading1));
                    String[] filenames = new String[]{};
                    File[] files = new File[]{};
                    for (int i = 0; i < listFiles.size(); i++) {
                        filenames = listFileNames.toArray(new String[i]);
                        files = listFiles.toArray(new File[i]);
                    }
                    HashMap<String, String> params = new HashMap<>();
                    params.put("type", "post_identity");
                    params.put("token", localUserInfo.getToken());
                    RequestUpData(filenames, files, params);//
                }
                break;
            default:
                break;

        }
    }

    private void RequestUpData(String[] fileKeys, File[] files, HashMap<String, String> params) {
        OkHttpClientManager.postAsyn(this, URLs.Auth_CheZhu, fileKeys, files, params,
                new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                MyLogger.i(">>>>>>>>>上传身份认证" + response);
                myToast("上传成功");
                finish();
            }
        }, false);

    }

    @Override
    protected void initData() {
        requestServer();
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));
        String string = "?token=" + localUserInfo.getToken()
                + "&type=" + "get_identity";
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(this, URLs.Auth_CheZhu + string,
                new OkHttpClientManager.ResultCallback<Auth_ShenFenZhengModel>() {
                    @Override
                    public void onError(Request request, String info, Exception e) {
                        hideProgress();
                        if (!info.equals("")) {
                            myToast(info);
                        }
                    }

                    @Override
                    public void onResponse(final Auth_ShenFenZhengModel response) {
                        hideProgress();
                        MyLogger.i(">>>>>>>>>车主认证-身份认证" + response);
                        if (!response.getIdentity_front_image().equals("")){
                            imageView1.setVisibility(View.VISIBLE);
                            linearLayout1.setVisibility(View.GONE);
                            Glide.with(Auth_ShenFenZhengActivity.this)
                                    .load(IMGHOST + response.getIdentity_front_image())
                                    .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                                    .into(imageView1);//加载图片
                        }else {
                            imageView1.setVisibility(View.GONE);
                            linearLayout1.setVisibility(View.VISIBLE);
                        }
                        if (!response.getIdentity_reverse_image().equals("")){
                            imageView2.setVisibility(View.VISIBLE);
                            linearLayout2.setVisibility(View.GONE);
                            Glide.with(Auth_ShenFenZhengActivity.this)
                                    .load(IMGHOST + response.getIdentity_reverse_image())
                                    .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                                    .into(imageView2);//加载图片
                        }else {
                            imageView2.setVisibility(View.GONE);
                            linearLayout2.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }

    private boolean match() {
        if (listFiles.size() != 2) {
            myToast("请上传身份证正反面图片");
            return false;
        }
        return true;
    }

    @Override
    protected void updateView() {
        titleView.setTitle("身份证认证");
    }

    /**
     * *****************************************选择图片********************************************
     */
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
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
                options.inSampleSize = 2;//按照比例（1 / inSampleSize）缩小bitmap的宽和高、降低分辨率
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

                switch (type) {
                    case "identity_front_image":
                        imageView1.setImageBitmap(bitmap);
                        imageView1.setVisibility(View.VISIBLE);
                        linearLayout1.setVisibility(View.GONE);
                        break;
                    case "identity_reverse_image":
                        imageView2.setImageBitmap(bitmap);
                        imageView2.setVisibility(View.VISIBLE);
                        linearLayout2.setVisibility(View.GONE);
                        break;
                }

                //图片压缩及保存
                Uri uri1 = Uri.parse("");
                uri1 = Uri.fromFile(new File(imagePath));
                File file1 = new File(FileUtil.getPath(this, uri1));
//                listFiles.add(file1);
                listFileNames.add(type);
                File newFile = null;
                try {
                    newFile = new Compressor(this).compressToFile(file1);
                    listFiles.add(newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    myToast(getString(R.string.app_imgerr));
                }
            }
        }

    }
}
