package com.delivery.xianxian.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delivery.xianxian.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by zyz on 2019-10-19.
 * 广告滚动
 */
public class RollingView extends FrameLayout implements View.OnClickListener {

    // 默认动画执行时间
    private static final int ANIMATION_DURATION = 1000;

    // 延迟滚动时间间隔
    private long mDuration = 3000;
    // 字体颜色
    private int mTextColor = 0xff000000;
    // 点击后字体颜色
    private int mClickColor = 0xff0099ff;
    // 字体大小
    private float mTextSize = 14;
    // 行间距
    private int mTextPadding = 10;
    // 画笔
    private Paint mPaint;
    // 默认每页信息数
    private int mPageSize = 3;
    // 最后一页余数
    private int mUpLimited = mPageSize;
    // 当前显示页码
    private int mCurrentPage = 0;
    // 总分页数
    private int mPageCount;
    // 左图片
    private int mLeftDrawable;
    // 分页数据对象
    private List<LinearLayout> mRollingPages;
    // 默认动画
    private AnimationSet mEnterAnimSet;
    private AnimationSet mExitAnimSet;
    private RollingRunnable mRunnable;
    private Handler mHandler;
    private onItemClickListener mClickListener;
    // 布局参数
    private LayoutParams mFrameParams;
    private LinearLayout.LayoutParams mLinearParams;
    //mEnterDownAnim,mOutUp分别构成向下翻页的进出动画
    private Rotate3dAnimation mEnterDownAnim;
    private Rotate3dAnimation mExitUpAnim;

    //mEnterUpAnim,mOutDown分别构成向下翻页的进出动画
    private Rotate3dAnimation mEnterUpAnim;
    private Rotate3dAnimation mExitDownAnim;

    public RollingView(Context context) {
        this(context, null);
    }

    public RollingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 从xml中获取属性
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RollingView);
        mTextSize = array.getDimension(R.styleable.RollingView_rv_textSize, mTextSize);
        mTextColor = array.getColor(R.styleable.RollingView_rv_textColor, mTextColor);
        array.recycle();
        // 创建默认显示隐藏动画
        createEnterAnimation();
        createExitAnimation();
        // 初始化画笔
        mPaint = new TextPaint();
        // 初始化Handler对象
        mHandler = new Handler(Looper.getMainLooper());

        mEnterDownAnim = createAnim(-90, 0, true, true);
        mExitUpAnim = createAnim(0, 90, false, true);
        mEnterUpAnim = createAnim(90, 0, true, false);
        mExitDownAnim = createAnim(0, -90, false, false);
    }

    private Rotate3dAnimation createAnim(float start, float end, boolean turnIn, boolean turnUp) {
        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, turnIn, turnUp);
        rotation.setDuration(300);
        rotation.setFillAfter(false);
        rotation.setInterpolator(new AccelerateInterpolator());
        return rotation;
    }

    /**
     * 设置分页大小
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.mPageSize = this.mUpLimited = pageSize;
    }

    /**
     * 设置延迟时间
     *
     * @param millionSeconds
     */
    public void setDelayedDuration(long millionSeconds) {
        this.mDuration = millionSeconds;
    }

    /**
     * 设置显示动画
     *
     * @param animation
     */
    public void setEnterAnimation(AnimationSet animation) {
        mEnterAnimSet = animation;
    }

    /**
     * 设置隐藏动画
     *
     * @param animation
     */
    public void setExitAnimation(AnimationSet animation) {
        mExitAnimSet = animation;
    }

    /**
     * 设置行距
     *
     * @param padding
     */
    public void setTextPadding(int padding) {
        this.mTextPadding = padding;
    }

    /**
     * 设置点击后字体颜色
     *
     * @param color
     */
    public void setClickColor(int color) {
        this.mClickColor = color;
    }

    /**
     * 设置左图片
     *
     * @param drawable
     */
    public void setLeftDrawable(int drawable) {
        this.mLeftDrawable = drawable;
    }

    /**
     * 设置点击事件
     *
     * @param clickListener
     */
    public void setOnItemClickListener(onItemClickListener clickListener) {
        if (null == clickListener) return;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 如果是未指定大小，那么设置宽为300px
        int exceptWidth = 300;
        int exceptHeight = 0;
        // 计算高度，如果将高度设置为textSize会很丑，因为文字有默认的上下边距。
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            if (mTextSize > 0) {
                mPaint.setTextSize(mTextSize);
                Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
                exceptHeight = (int) (fontMetrics.bottom - fontMetrics.top);
            }
        }
        int width = resolveSize(exceptWidth, widthMeasureSpec);
        int height = resolveSize(exceptHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void setRollingText(List<String> array) {
        if (null == array || array.isEmpty()) return;
        this.removeAllViews();
        if (mRollingPages == null) {
            mRollingPages = new ArrayList<>();
        }
        mRollingPages.clear();
        // 计算商数
        int quotient = array.size() / mPageSize;
        // 计算余数
        int remainder = array.size() % mPageSize;
        // 计算需要创建多少页
        mPageCount = remainder == 0 ? quotient : quotient + 1;
        for (int i = 0; i < mPageCount; i++) {
            // 创建一个布局
            LinearLayout container = createContainer();
            if (i == mPageCount - 1) {
                mUpLimited = remainder == 0 ? mPageSize : remainder;
            }
            for (int n = 0; n < mUpLimited; n++) {
                TextView textView = createTextView(array.get(mPageSize * i + n));
                container.addView(textView);
            }
            // 添加到分页中
            mRollingPages.add(container);
            this.addView(container);
        }
        // 初始化显示第一页
        mCurrentPage = 0;
        mRollingPages.get(mCurrentPage).setVisibility(VISIBLE);
        this.setVisibility(mRollingPages.get(mCurrentPage));
    }

    /**
     * 创建页对象
     *
     * @return
     */
    private LinearLayout createContainer() {
        if (mFrameParams == null) {
            mFrameParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            mFrameParams.gravity = Gravity.CENTER_VERTICAL;
        }
        LinearLayout container = new LinearLayout(getContext());
        container.setLayoutParams(mFrameParams);
        container.setOrientation(LinearLayout.VERTICAL);
        return container;
    }

    private void setVisibility(LinearLayout container) {
        int count = container.getChildCount();
        for (int i = 0; i < count; i++) {
            container.getChildAt(i).setVisibility(VISIBLE);
        }
    }

    /**
     * 创建页内容对象
     *
     * @param text
     * @return
     */
    private TextView createTextView(String text) {
        if (mLinearParams == null) {
            mLinearParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            mLinearParams.gravity = Gravity.CENTER_VERTICAL;
        }
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(mLinearParams);
        textView.setSingleLine();
        textView.setPadding(mTextPadding, mTextPadding, mTextPadding, mTextPadding);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(mTextColor);
        textView.setVisibility(INVISIBLE);
        textView.setText(text);
        if (mLeftDrawable > 0) {
            Drawable drawable = getContext().getResources().getDrawable(mLeftDrawable);
            // Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), mLeftDrawable);
            // Drawable drawable = new BitmapDrawable(getContext().getResources(), bitmap);
            drawable.setBounds(0, 0, 10, 10);
            textView.setCompoundDrawablePadding(10);
            textView.setCompoundDrawables(drawable, null, null, null);
        }
        textView.setOnClickListener(this);
        // 设置字体大小
        if (mTextSize > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        }
        return textView;
    }

    private void createEnterAnimation() {
        mEnterAnimSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0, TranslateAnimation.RELATIVE_TO_PARENT, 1f, TranslateAnimation.RELATIVE_TO_SELF, 0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        mEnterAnimSet.addAnimation(translateAnimation);
        mEnterAnimSet.addAnimation(alphaAnimation);
        mEnterAnimSet.setDuration(ANIMATION_DURATION);
    }

    private void createExitAnimation() {
        mExitAnimSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f, TranslateAnimation.RELATIVE_TO_PARENT, -1f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        mExitAnimSet.addAnimation(translateAnimation);
        mExitAnimSet.addAnimation(alphaAnimation);
        mExitAnimSet.setDuration(ANIMATION_DURATION);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pause();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                resume();
                break;
        }
        return true;
    }

    public void resume() {
        // 只有一页时不进行切换
        if (mPageCount < 1) return;
        if (mRunnable == null) {
            mRunnable = new RollingRunnable();
        } else {
            mHandler.removeCallbacks(mRunnable);
        }
        mHandler.postDelayed(mRunnable, mDuration);
    }

    public void pause() {
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void onClick(View v) {
        if (null == mClickListener) return;
        TextView textView = (TextView) v;
        mClickListener.onItemClick(textView);
        textView.setTextColor(mClickColor);
    }

    /**
     * 隐藏当前页,显示下一页任务
     */
    public class RollingRunnable implements Runnable {

        @Override
        public void run() {
            // 隐藏当前页
            LinearLayout currentView = mRollingPages.get(mCurrentPage);
            currentView.setVisibility(INVISIBLE);
            if (mExitAnimSet != null) {
                currentView.startAnimation(mExitAnimSet);// mExitUpAnim);
            }
            mCurrentPage++;
            if (mCurrentPage >= mPageCount) {
                mCurrentPage = 0;
            }
            // 显示下一页
            LinearLayout nextView = mRollingPages.get(mCurrentPage);
            nextView.setVisibility(VISIBLE);
            setVisibility(nextView);
            if (mEnterAnimSet != null) {
                nextView.startAnimation(mEnterAnimSet);// mEnterDownAnim);
            }
            mHandler.postDelayed(this, mDuration);
        }
    }

    public class Rotate3dAnimation extends Animation {
        private final float mFromDegrees;
        private final float mToDegrees;
        private final boolean mTurnIn;
        private final boolean mTurnUp;
        private float mCenterX;
        private float mCenterY;
        private Camera mCamera;

        public Rotate3dAnimation(float fromDegrees, float toDegrees, boolean turnIn, boolean turnUp) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mTurnIn = turnIn;
            mTurnUp = turnUp;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
            mCenterY = getHeight() / 2;
            mCenterX = getWidth() / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;
            final int derection = mTurnUp ? 1 : -1;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mTurnIn) {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, derection * mCenterY * (interpolatedTime), 0.0f);
            }
            camera.rotateX(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

    public interface onItemClickListener {
        void onItemClick(TextView v);
    }
}