package com.transport.xianxian.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.transport.xianxian.R;


/**
 * @author zyz
 *	自定义标题栏
 */
public class TitleView extends FrameLayout {
	private Activity mActivity; // 当前activity
	private ImageView btn_left, btn_right;
	private TextView tv_title,left_text,right_text;
	private EditText et_search;
	private RelativeLayout rl_title;

	public ImageView getBtn_right() {
		return btn_right;
	}

//	public TitleView(Context context) {
//		this(context, null);
//	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        mActivity= (Activity) context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_title, this, true);
		rl_title = (RelativeLayout) findViewById(R.id.rl_title);

		btn_left = (ImageView) findViewById(R.id.left_btn);
		btn_right = (ImageView) findViewById(R.id.right_btn);
		tv_title = (TextView) findViewById(R.id.title_text);
		et_search= (EditText) findViewById(R.id.et_search);
		left_text= (TextView) findViewById(R.id.left_text);
		right_text= (TextView) findViewById(R.id.right_text);
		setBack(null);
	}

	/**
	 * 设置返回的一系列事件
	 */
	public void setBack(OnClickListener listener){
		btn_left.setImageResource(R.mipmap.ic_return1);
        if(listener!=null){
            btn_left.setOnClickListener(listener);
        }else {
            btn_left.setOnClickListener(mListener);
        }
	}
	/**
	 * 设置左按钮
	 */
	public void setLeftBtn(int resid,OnClickListener listener){
		btn_left.setImageResource(resid);
		btn_left.setOnClickListener(listener);
	}

	/**
	 * 显示左按钮
	 */
	public void showLeftBtn(){
		btn_left.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏左按钮
	 */
	public void hideLeftBtn(){
		btn_left.setVisibility(View.INVISIBLE);
	}
	/**
	 * 显示左边的textview，并设置值
	 */
	public void showLeftTextview(String str, OnClickListener listener){
		left_text.setVisibility(View.VISIBLE);
		btn_left.setVisibility(View.GONE);
		left_text.setText(str);
		left_text.setOnClickListener(listener);
	}

	/**
	 * 右按钮点击事件
	 * @param listener
	 */
	public void setRightBtn(int resid,OnClickListener listener){
		//btn_right.setTag(resid);
        btn_right.setVisibility(View.VISIBLE);
		btn_right.setImageResource(resid);
		btn_right.setOnClickListener(listener);
	}

	/**
	 * 显示右按钮
	 */
	public void showRightBtn(){
		btn_right.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏右按钮
	 */
	public void hideRightBtn_invisible(){
		btn_right.setVisibility(View.INVISIBLE);
		btn_right.setOnClickListener(null);
	}

	/**
	 * 显示右边的textview，并设置值
	 */
	public void showRightTextview(String str, OnClickListener listener){
		right_text.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.GONE);
		right_text.setText(str);
		right_text.setOnClickListener(listener);
	}
//	public void setActivity(Activity curActivity){
//		this.mActivity = curActivity;
//	}

	public void setTitle(int title){
		tv_title.setText(getContext().getText(title));
		tv_title.setVisibility(View.VISIBLE);
	}
    public void setTitle(String title){
		tv_title.setText(title);
		tv_title.setVisibility(View.VISIBLE);
	}
	public void setTitleColor(int color){
		tv_title.setTextColor(mActivity.getResources().getColor(color));
	}
	/**
	 * 设置背景颜色
	 */
	public void setBackground(int background){
		rl_title.setBackgroundResource(background);
	}
	/**
	 * 设置title右边图片
	 */
	public void setTitleRightDrawable(Drawable drawable, OnClickListener listener){
		tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
		tv_title.setOnClickListener(listener);
	}
	/**
	 * 显示edittext，替换title
	 */
	public void showEditText(){
		et_search.setVisibility(View.VISIBLE);
		tv_title.setVisibility(View.GONE);
	}
	/**
	 * 获得edittext的内容
	 */
	public String getEditTextContent(){
		return et_search.getText().toString().trim();
	}

	public void showTitle(){
		tv_title.setVisibility(View.VISIBLE);
	}


	public void hideTitle(){
		tv_title.setVisibility(View.INVISIBLE);
	}

	public void goneTitleView(){
		rl_title.setVisibility(View.GONE);
	}
	/**
	 * 返回按钮固定点击事件
	 */
	private OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mActivity != null){
				mActivity.finish();
				//mActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			}
		}
	};

	public EditText getEt_search() {
		return et_search;
	}
}
