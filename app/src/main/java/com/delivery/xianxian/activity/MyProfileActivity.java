package com.delivery.xianxian.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cy.dialog.BaseDialog;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.MyProfileModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.FileUtil;
import com.delivery.xianxian.utils.MyChooseImages;
import com.delivery.xianxian.utils.MyLogger;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.zelory.compressor.Compressor;

import static com.delivery.xianxian.utils.MyChooseImages.REQUEST_CODE_CAPTURE_CAMEIA;
import static com.delivery.xianxian.utils.MyChooseImages.REQUEST_CODE_PICK_IMAGE;


/**
 * Created by fafukeji01 on 2017/5/8.
 * 我的资料
 */

public class MyProfileActivity extends BaseActivity {
    //选择图片及上传
    ArrayList<String> listFileNames = new ArrayList<>();
    ArrayList<File> listFiles = new ArrayList<>();
    ImageView imageView1;
    TextView textView, textView1, textView2, textView3, textView4;
    private TimeCount time;

    String phonenum = "", code = "",industry = "";

    MyProfileModel model;
    int i1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取个人信息
        showProgress(true, getString(R.string.app_loading));
        requestInfo("?token=" + localUserInfo.getToken());
    }

    @Override
    protected void initView() {
        imageView1 = findViewByID_My(R.id.imageView1);
        textView = findViewByID_My(R.id.textView);
        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
    }

    @Override
    protected void initData() {
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
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
                model = response;
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

                //昵称
                textView1.setText(response.getNickname());
                //手机号
                textView2.setText(response.getMobile());
                //行业
                textView3.setText(response.getIndustry());
                //实名认证//1已认证2未认证
                if (response.getIs_certification() == 1){
                    textView4.setText("已认证");
                }else {
                    textView4.setText("未认证");
                }

                localUserInfo.setPhoneNumber(response.getMobile());
                localUserInfo.setNickname(response.getNickname());
//                localUserInfo.setEmail(response.getEmail());
                localUserInfo.setUserImage(response.getHead());

                hideProgress();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView:
                //上传头像
                if (match()) {
                    String[] filenames = new String[]{};
                    File[] files = new File[]{};
                    if (listFiles.size() > 0) {
                        for (int i = 0; i < listFiles.size(); i++) {
                            filenames = listFileNames.toArray(new String[i]);
                            files = listFiles.toArray(new File[i]);
                        }
                    }
                    textView.setClickable(false);
                    showProgress(true, "正在修改，请稍候...");
                    params.put("token", localUserInfo.getToken());
                    params.put("nickname", textView1.getText().toString().trim());
                    params.put("industry", industry);
                    RequestChangeProfile(filenames, files, params);//修改
                }
                break;
            case R.id.linearLayout1:
                //头像
                MyChooseImages.showPhotoDialog(this);
                break;
            case R.id.linearLayout2:
                //昵称
                dialog = new BaseDialog(MyProfileActivity.this);
                dialog.contentView(R.layout.dialog_changename)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
//                        TextView textView1 = dialog.findViewById(R.id.textView1);
//                        textView1.setText(e.getMessage());
                final EditText editText1 = dialog.findViewById(R.id.editText1);
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1.getText().toString().trim().equals("")) {
                            CommonUtil.hideSoftKeyboard_fragment(v, MyProfileActivity.this);
                            dialog.dismiss();
                            textView1.setText(editText1.getText().toString().trim());
                        } else {
                            myToast("请输入昵称");
                        }
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.linearLayout3:
                //手机号

                break;
            case R.id.linearLayout4:
                //行业
                BaseDialog dialog1 = new BaseDialog(MyProfileActivity.this);
                dialog1.contentView(R.layout.dialog_list)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView title = dialog1.findViewById(R.id.textView1);
                title.setText("选择行业");
                RecyclerView rv = dialog1.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MyProfileActivity.this);
                rv.setLayoutManager(mLinearLayoutManager);

                List<String> mStringList = new ArrayList<>();
                if (model != null) {
                    for (int i = 0; i < model.getIndustry_list().size(); i++) {
                        mStringList.add(model.getIndustry_list().get(i).getVal());
                    }
                }

                CommonAdapter<String> adapter = new CommonAdapter<String>
                        (MyProfileActivity.this, R.layout.item_dialog_list, mStringList) {
                    @Override
                    protected void convert(ViewHolder holder, String model, int position) {
                        TextView tv = holder.getView(R.id.textView);
                        ImageView iv = holder.getView(R.id.imageView);
                        tv.setText(model);
                        if (position == i1) {
                            tv.setTextColor(getResources().getColor(R.color.blue));
                            iv.setImageResource(R.mipmap.ic_xuanzhong);
                        } else {
                            tv.setTextColor(getResources().getColor(R.color.black1));
                            iv.setImageResource(R.mipmap.ic_weixuan);
                        }
                    }
                };
                adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        i1 = i;
                        industry = model.getIndustry_list().get(i).getKey() + "";
                        adapter.notifyDataSetChanged();
                        textView3.setText(mStringList.get(i));
                        dialog1.dismiss();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                rv.setAdapter(adapter);
                dialog1.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });
                break;
            case R.id.linearLayout5:
                //实名认证
                CommonUtil.gotoActivity(MyProfileActivity.this,Auth_ShenFenZhengActivity.class,false);
                break;
        }
    }

    private void RequestCode(Map<String, String> params) {
        OkHttpClientManager.postAsyn(this, URLs.Code, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                textView2.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                textView2.setClickable(true);
                MyLogger.i(">>>>>>>>>验证码" + response);
                time.start();//开始计时
                myToast(getString(R.string.app_sendcode_hint));

            }
        }, false);

    }

    //修改信息
    private void RequestChangeProfile(String[] fileKeys, File[] files, HashMap<String, String> params) {
        OkHttpClientManager.postAsyn(MyProfileActivity.this, URLs.ChangeProfile, fileKeys, files, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                textView1.setClickable(true);
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                textView1.setClickable(true);
                MyLogger.i(">>>>>>>>>修改信息" + response);
                myToast("修改成功");
                requestInfo("?token=" + localUserInfo.getToken());
            }
        });
    }

    private void RequestChangePhone(Map<String, String> params) {
        OkHttpClientManager.postAsyn(this, URLs.ChangePhone, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                textView3.setClickable(true);
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                hideProgress();
                textView3.setClickable(true);
                MyLogger.i(">>>>>>>>>修改手机" + response);
                time.start();//开始计时
                myToast("修改成功");
                //退出程序
                localUserInfo.setUserId("");
                localUserInfo.setUserName("");
                localUserInfo.setToken("");
                localUserInfo.setPhoneNumber("");
                localUserInfo.setNickname("");
                localUserInfo.setWalletaddr("");
                localUserInfo.setEmail("");
                localUserInfo.setUserImage("");
                //环信退出登录
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        CommonUtil.gotoActivityWithFinishOtherAll(MyProfileActivity.this, LoginActivity.class, true);
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String error) {

                    }
                });

            }
        }, false);

    }

    private boolean match() {
//        phonenum = textView2.getText().toString().trim();
        /*if (TextUtils.isEmpty(phonenum)) {
            myToast("请输入新手机号码");
            return false;
        }*/
        return true;
    }

    @Override
    protected void updateView() {
        titleView.setTitle("个人信息");
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
                options.inSampleSize = 2;
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

                } catch (IOException e) {
                    e.printStackTrace();
                    myToast(getString(R.string.app_imgerr));
                }
            }
        }

    }

    //获取验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            textView2.setText(getString(R.string.app_reacquirecode));
            textView2.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            textView2.setClickable(false);
            textView2.setText(millisUntilFinished / 1000 + getString(R.string.app_codethen));
        }
    }
}
