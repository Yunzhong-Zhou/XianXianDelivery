package com.transport.xianxian.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.model.Auth_GuaKaoGongSiModel;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.ChooseImages_zyz;
import com.transport.xianxian.utils.FileUtil;
import com.transport.xianxian.utils.MyLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.zelory.compressor.Compressor;

import static com.transport.xianxian.net.OkHttpClientManager.IMGHOST;
import static com.transport.xianxian.utils.ChooseImages_zyz.REQUEST_CODE_CAPTURE_CAMEIA;
import static com.transport.xianxian.utils.ChooseImages_zyz.REQUEST_CODE_PICK_IMAGE;

/**
 * Created by zyz on 2019-10-01.
 * 挂靠公司告知书
 */
public class Auth_GuaKaoGongSiActivity extends BaseActivity {
    //选择图片及上传
    ArrayList<String> listFileNames = new ArrayList<>();
    ArrayList<File> listFiles = new ArrayList<>();
    String type = "";

    TextView textView1;
    EditText editText1;
    ImageView imageView1;
    LinearLayout linearLayout1;
    String depend_name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_guakaogongsi);
    }

    @Override
    protected void initView() {
        textView1 = findViewByID_My(R.id.textView1);
        editText1 = findViewByID_My(R.id.editText1);
        imageView1 = findViewByID_My(R.id.imageView1);
        linearLayout1 = findViewByID_My(R.id.linearLayout1);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imageView1:
            case R.id.linearLayout1:
                type = "depend_image";
                for (int i = 0; i < listFileNames.size(); i++) {
                    if (listFileNames.get(i).equals(type)) {
                        //删除
                        listFileNames.remove(i);
                        listFiles.remove(i);
                    }
                }
                ChooseImages_zyz.showPhotoDialog(this);
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
                    params.put("type", "post_depend_on");
                    params.put("token", localUserInfo.getToken());
                    params.put("depend_name", depend_name);
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
                        MyLogger.i(">>>>>>>>>上传挂靠公司" + response);
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
                + "&type=" + "get_depend_on";
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(this, URLs.Auth_CheZhu + string,
                new OkHttpClientManager.ResultCallback<Auth_GuaKaoGongSiModel>() {
                    @Override
                    public void onError(Request request, String info, Exception e) {
                        hideProgress();
                        if (!info.equals("")) {
                            myToast(info);
                        }
                    }

                    @Override
                    public void onResponse(final Auth_GuaKaoGongSiModel response) {
                        hideProgress();
                        MyLogger.i(">>>>>>>>>车主认证-挂靠公司" + response);
                        editText1.setText(response.getDepend_name());
                        if (!response.getDepend_image().equals("")) {
                            imageView1.setVisibility(View.VISIBLE);
                            linearLayout1.setVisibility(View.GONE);
                            Glide.with(Auth_GuaKaoGongSiActivity.this)
                                    .load(IMGHOST + response.getDepend_image())
                                    .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                                    .into(imageView1);//加载图片
                        } else {
                            imageView1.setVisibility(View.GONE);
                            linearLayout1.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }

    private boolean match() {
        depend_name = editText1.getText().toString().trim();
        if (TextUtils.isEmpty(depend_name)) {
            myToast("请输入挂靠公司名称");
            return false;
        }
        if (listFiles.size() != 1) {
            myToast("请上传告知书照片");
            return false;
        }
        return true;
    }

    @Override
    protected void updateView() {
        titleView.setTitle("挂靠公司告知书");
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
                    uri = Uri.fromFile(new File(ChooseImages_zyz.imagepath));
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
                    case "depend_image":
                        imageView1.setImageBitmap(bitmap);
                        imageView1.setVisibility(View.VISIBLE);
                        linearLayout1.setVisibility(View.GONE);
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
