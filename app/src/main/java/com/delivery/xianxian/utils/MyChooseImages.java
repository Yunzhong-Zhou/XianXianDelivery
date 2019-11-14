package com.delivery.xianxian.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.delivery.xianxian.R;

import java.io.File;

/**
 * Created by ling on 2015/8/24.
 * description:选择图片，通过拍照或者图片文件
 */
public class MyChooseImages {
    public static final int REQUEST_CODE_CAPTURE_CAMEIA = 1;// 拍照
    public static final int REQUEST_CODE_PICK_IMAGE = 2;// 从相册中选择

    public static String imagepath;// 图片存储根路径

    public static void showPhotoDialog(final Activity activity) {
        final AlertDialog dlg = new AlertDialog.Builder(activity).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);

        tv_paizhao.setText(activity.getString(R.string.paizhao));
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                    String out_file_path = FileUtil.getImageDownloadDir(activity);
                    File dir = new File(out_file_path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    // 置入一个不设防的VmPolicy（不设置的话 7.0以上一调用拍照功能就崩溃了）
                    // 还有一种方式：manifest中加入provider然后修改intent代码
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                    }
                    imagepath = FileUtil.getImageDownloadDir(activity) + System.currentTimeMillis() + ".png";
                    getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imagepath)));
                    getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    activity.startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMEIA);

                }
                else {
                    Toast.makeText(activity, activity.getString(R.string.app_card), Toast.LENGTH_LONG).show();
                }
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText(activity.getString(R.string.xiangce));
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                activity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                dlg.cancel();
            }
        });
    }
}
