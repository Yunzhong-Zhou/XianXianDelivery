package com.delivery.xianxian.popupwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.delivery.xianxian.R;
import com.delivery.xianxian.utils.MyLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.content.FileProvider;


/**
 * Created by fafukeji01 on 2017/4/1.
 * 放大显示图片
 */

public class ZoomIMGPopupWindow extends PopupWindow {
    private Context mContext;
    private Class<?> targetActivity;
    private View view;
    String url;
    Handler handler = new Handler();
    Uri uri = null;
    RelativeLayout relativeLayout;

    public ZoomIMGPopupWindow(Context mContext, String url) {
        this.view = LayoutInflater.from(mContext).inflate(R.layout.pop_zoomimg, null);
        this.mContext = mContext;
        this.url = url;
        initView(view);
        initData();

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();
                int height1 = view.findViewById(R.id.pop_layout).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                    if (y > height1) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_pop_anim);
    }

    private void initView(View view) {
        MyLogger.i(">>>>>>>图片url："+url);
        relativeLayout = view.findViewById(R.id.pop_layout);
        PhotoView photoView = (PhotoView) view.findViewById(R.id.img);
        Glide.with(mContext).load(url).into(photoView);//加载图片
        // 启用图片缩放功能
        photoView.enable();
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //保存图片
                MyLogger.i(">>>>>>>长按保存图片"+url);
               /* // 网络图片转 bitmap
                Bitmap bitmap = FileUtil.returnBitMap(url);
                if (FileUtil.saveImageToGallery(mContext,bitmap) == true){
                    Toast.makeText(mContext,mContext.getString(R.string.zxing_h23),Toast.LENGTH_LONG).show();
                }*/
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        uri = printScreen(relativeLayout, mContext.getString(R.string.app_name) + System.currentTimeMillis());
                    }
                });
                Toast.makeText(mContext,mContext.getString(R.string.app_file), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    private void initData() {
    }

    /**
     * 截取图片存到本地
     */
    public Uri printScreen(View view, String picName) {
        //图片地址
        String imgPath = Environment.getExternalStorageDirectory() + "/" + picName + ".png";
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null) {
            try {
                File f = new File(imgPath);
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                FileOutputStream out = new FileOutputStream(f);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

                Uri uri = FileProvider.getUriForFile(mContext, mContext.getPackageName()+".fileprovider", f);

//                Uri uri = Uri.fromFile(f);

                return uri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
