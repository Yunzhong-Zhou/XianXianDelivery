package com.transport.xianxian.zxing;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.utils.MyLogger;
import com.transport.xianxian.zxing.camera.CameraManager;
import com.transport.xianxian.zxing.decoding.CaptureActivityHandler;
import com.transport.xianxian.zxing.decoding.InactivityTimer;
import com.transport.xianxian.zxing.decoding.RGBLuminanceSource;
import com.transport.xianxian.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class CaptureActivity extends BaseActivity implements Callback {
    private static final int REQUEST_CODE_SCAN_GALLERY = 100;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private ImageButton btnFlash;
    private boolean isFlashOn = false;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;
    //	private Button cancelScanButton;
    /**
     * Called when the activity is first created.
     */
    private FrameLayout frameLayout;
    private double nLenStart = 0;//监听 WebView所用手势
    private Camera myCamera = null;

    int camera_length = 0;//摄像头焦距  0-10
    public static int camera_length_max = 0;//摄像头最大焦距

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture1);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        btnFlash = (ImageButton) findViewById(R.id.btn_flash);
        btnFlash.setOnClickListener(flashListener);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void initView() {
        frameLayout = findViewByID_My(R.id.frameLayout);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int nCnt = event.getPointerCount();
                int n = event.getAction();
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && 2 == nCnt)//<span style="color:#ff0000;">2表示两个手指</span>
                {
                    for (int i = 0; i < nCnt; i++) {
                        float x = event.getX(i);
                        float y = event.getY(i);
                        Point pt = new Point((int) x, (int) y);
                    }
                    int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
                    int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
                    nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
                } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && 2 == nCnt) {
                    for (int i = 0; i < nCnt; i++) {
                        float x = event.getX(i);
                        float y = event.getY(i);
                        Point pt = new Point((int) x, (int) y);
                    }
                    int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
                    int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));

                    double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);

                    if (nLenEnd > nLenStart)//通过两个手指开始距离和结束距离，来判断放大缩小
                    {
//                        Toast.makeText(getApplicationContext(), "放大", Toast.LENGTH_SHORT).show();
                        camera_length = camera_length + (camera_length_max / 5);
                        MyLogger.i(">>>>>放大或缩小距离：" + (nLenEnd - nLenStart) + ">>>>焦距：" + camera_length);
                        if (camera_length <= camera_length_max) {
                            //摄像头焦距不能大于10
                            CameraManager.get().setEnlargeCamera(camera_length);
                        } else {
                            camera_length = camera_length_max;
                            CameraManager.get().setEnlargeCamera(camera_length);
                        }

                    } else {
//                        Toast.makeText(getApplicationContext(), "缩小", Toast.LENGTH_SHORT).show();
                        camera_length = camera_length - (camera_length_max / 5);
                        MyLogger.i(">>>>>放大或缩小距离：" + (nLenEnd - nLenStart) + ">>>>焦距：" + camera_length);
                        if (camera_length >= 0) {
                            //摄像头焦距不能小于0
                            CameraManager.get().setEnlargeCamera(camera_length);
                        } else {
                            camera_length = 0;
                            CameraManager.get().setEnlargeCamera(camera_length);
                        }

                    }
                }
                return true;
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        if (titleView != null) {
            titleView.setTitle(getString(R.string.zxing_h3));
            titleView.showRightTextview(getString(R.string.zxing_h26), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //打开手机中的相册
                    Intent innerIntent = new Intent(Intent.ACTION_PICK); //"android.intent.action.GET_CONTENT"
                    innerIntent.setType("image/*");
                    Intent wrapperIntent = Intent.createChooser(innerIntent, getString(R.string.zxing_h27));
                    startActivityForResult(wrapperIntent, REQUEST_CODE_SCAN_GALLERY);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.scanner_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.scan_local:
//                //打开手机中的相册
//                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
//                innerIntent.setType("image/*");
//                Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
//                this.startActivityForResult(wrapperIntent, REQUEST_CODE_SCAN_GALLERY);
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:
                    //获取选中图片的路径
                    /*Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                    if (cursor.moveToFirst()) {
                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    }
                    cursor.close();*/

                    Uri uri = data.getData();
                    //处理得到的url
                    ContentResolver cr = this.getContentResolver();
                    Cursor cursor = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        cursor = cr.query(uri, null, null, null, null, null);
                    }
                    if (cursor != null) {
                        cursor.moveToFirst();
                        photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    } else {
                        photo_path = uri.getPath();
                    }
                    MyLogger.i(">>>>相片地址>>" + photo_path);

                    mProgress = new ProgressDialog(CaptureActivity.this);
                    mProgress.setMessage(getString(R.string.zxing_h4));
                    mProgress.setCancelable(true);
                    mProgress.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           /*if Result result = scanningImage(photo_path);

                             (result != null) {
//                                Message m = handler.obtainMessage();
//                                m.what = R.id.decode_succeeded;
//                                m.obj = result.getText();
//                                handler.sendMessage(m);
                                Intent resultIntent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString(Constant.INTENT_EXTRA_KEY_QR_SCAN, result.getText());
//                                Logger.d("saomiao",result.getText());
//                                bundle.putParcelable("bitmap",result.get);
                                resultIntent.putExtras(bundle);
                                CaptureActivity.this.setResult(RESULT_OK, resultIntent);

                            } else {
                                Message m = handler.obtainMessage();
                                m.what = R.id.decode_failed;
                                m.obj = "Scan failed!";
                                handler.sendMessage(m);
                            }*/


                            Result result = scanningImage(photo_path);
                            // String result = decode(photo_path);
                            if (result == null) {
                                mProgress.dismiss();
                                Looper.prepare();
                                myToast(getString(R.string.zxing_h28));
                                Looper.loop();
                            } else {
                                mProgress.dismiss();
                                MyLogger.i(">>>>扫码：>>>>" + result.toString());
                                // Log.i("123result", result.getText());
                                // 数据返回
                                Intent resultIntent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putString(Constant.INTENT_EXTRA_KEY_QR_SCAN, result.toString());
                                // 不能使用Intent传递大于40kb的bitmap，可以使用一个单例对象存储这个bitmap
//            bundle.putParcelable("bitmap", barcode);
//            Logger.d("saomiao",resultString);
                                resultIntent.putExtras(bundle);
                                setResult(RESULT_OK, resultIntent);
                                finish();
                            }

                        }
                    }).start();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanner_view);//显示摄像头图像
        SurfaceHolder surfaceHolder = surfaceView.getHolder();

        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

        //quit the scan view
//		cancelScanButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				CaptureActivity.this.finish();
//			}
//		});

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        if (mProgress != null) {
            mProgress.dismiss();
        }

        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //FIXME
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.INTENT_EXTRA_KEY_QR_SCAN, resultString);
            System.out.println("sssssssssssssssss scan 0 = " + resultString);
            // 不能使用Intent传递大于40kb的bitmap，可以使用一个单例对象存储这个bitmap
//            bundle.putParcelable("bitmap", barcode);
//            Logger.d("saomiao",resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
        }
        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    /**
     * 闪光灯开关按钮
     */
    private View.OnClickListener flashListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                boolean isSuccess = CameraManager.get().setFlashLight(!isFlashOn);
                if (!isSuccess) {
                    showToast(getString(R.string.zxing_h2));
                    return;
                }
                if (isFlashOn) {
                    // 关闭闪光灯
                    btnFlash.setImageResource(R.mipmap.flash_off);
                    isFlashOn = false;
                } else {
                    // 开启闪光灯
                    btnFlash.setImageResource(R.mipmap.flash_on);
                    isFlashOn = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}