package com.delivery.xianxian.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.FeeModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by zyz on 2019-11-19.
 * 费用标准
 */
public class FeeModelActivity extends BaseActivity {
    String city = "", car_type_id = "", use_type = "";

    TextView tv_title;
    FeeModel model;
    //车型
    int item = 0;
    ViewPager viewPager;
    private ArrayList<View> pageViews = new ArrayList<>();
    GuidePageAdapter mPageAdapter;
    //包裹小圆点的LinearLayout
    private ViewGroup viewPoints;
    //将小圆点的图片用数组表示
    private ImageView[] imageViews;
    ImageView imageView;

    //基本路费
    private RecyclerView recyclerView1;
    List<FeeModel.BaseBean> list1 = new ArrayList<>();
    CommonAdapter<FeeModel.BaseBean> mAdapter1;
    //额外需求
    private RecyclerView recyclerView2;
    List<FeeModel.OtherBean> list2 = new ArrayList<>();
    CommonAdapter<FeeModel.OtherBean> mAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feemodel);
    }

    @Override
    protected void initView() {
        tv_title = findViewByID_My(R.id.tv_title);

        viewPager = findViewByID_My(R.id.viewPager);
        viewPoints = findViewByID_My(R.id.viewGroup);

        recyclerView1 = findViewByID_My(R.id.recyclerView1);
        LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(FeeModelActivity.this);
        recyclerView1.setLayoutManager(mLinearLayoutManager1);

        recyclerView2 = findViewByID_My(R.id.recyclerView2);
        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(FeeModelActivity.this);
        recyclerView2.setLayoutManager(mLinearLayoutManager2);
    }

    @Override
    protected void initData() {
        city = getIntent().getStringExtra("city");
        car_type_id = getIntent().getStringExtra("car_type_id");
        use_type = getIntent().getStringExtra("use_type");

        tv_title.setText("收费标准-" + city);
        //获取费用信息
        showProgress(true, getString(R.string.app_loading));
        Request("?token=" + localUserInfo.getToken()
                + "&city=" + city
                + "&car_type_id=" + car_type_id
                + "&use_type=" + use_type);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(FeeModelActivity.this, URLs.FeeModel + string, new OkHttpClientManager.ResultCallback<FeeModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    showToast(info);
                }
            }

            @Override
            public void onResponse(FeeModel response) {
                MyLogger.i(">>>>>>>>>费用标准" + response);
                hideProgress();
                model = response;
                //车型
                pageViews = new ArrayList<View>();
                for (int i = 0; i < response.getCar_type().size(); i++) {
                    /*if (response.getCar_type().equals(carTypeList.get(i).getId())) {
                        item = i;
                    }*/
                    pageViews.add(inflater.inflate(R.layout.item_feemodel_car_type_page, null));
                }
                //创建imageviews数组，大小是要显示的图片的数量
                imageViews = new ImageView[pageViews.size()];
                for (int i = 0; i < pageViews.size(); i++) {
                    ImageView iv = (ImageView) pageViews.get(i).findViewById(R.id.iv);
                    TextView tv1 = pageViews.get(i).findViewById(R.id.tv1);
                    TextView tv2 = pageViews.get(i).findViewById(R.id.tv2);
                    TextView tv3 = pageViews.get(i).findViewById(R.id.tv3);
                    tv1.setText("" + response.getCar_type().get(i).getWeight()+ "吨");
                    tv2.setText("" + response.getCar_type().get(i).getSize());
                    tv3.setText("" + response.getCar_type().get(i).getBulk()+ "方");
                    if (!response.getCar_type().get(i).getImage().equals(""))
                        Glide.with(FeeModelActivity.this)
                                .load(IMGHOST + response.getCar_type().get(i).getImage())
                                .centerCrop()
//                                    .placeholder(R.mipmap.headimg)//加载站位图
//                                    .error(R.mipmap.headimg)//加载失败
                                .into(iv);//加载图片

                    //添加小圆点的图片
                    imageView = new ImageView(FeeModelActivity.this);
                    //设置小圆点imageview的参数
                    imageView.setScaleType(ImageView.ScaleType.CENTER);
                    imageView.setLayoutParams(new ViewGroup.LayoutParams(60, 60));//创建一个宽高均为20 的布局
                    imageView.setPadding(10, 10, 10, 10);
                    //将小圆点layout添加到数组中
                    imageViews[i] = imageView;
                    //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
                    if (i == 0) {
                        imageViews[i].setImageResource(R.drawable.yuanxing_lanse);
                    } else {
                        imageViews[i].setImageResource(R.drawable.yuanxing_huise);
                    }

                    //将imageviews添加到小圆点视图组
                    viewPoints.addView(imageViews[i]);
                }
                mPageAdapter = new GuidePageAdapter();
                viewPager.setAdapter(mPageAdapter);
                viewPager.setOnPageChangeListener(new GuidePageChangeListener());

                //基本路费
                list1 = response.getBase();
                if (list1 != null && list1.size() > 0) {
                    mAdapter1 = new CommonAdapter<FeeModel.BaseBean>
                            (FeeModelActivity.this, R.layout.item_feemodel, list1) {
                        @Override
                        protected void convert(ViewHolder holder, FeeModel.BaseBean model, int position) {
                            holder.setText(R.id.textView1, model.getTitle());//标题
                            holder.setText(R.id.textView2, model.getPrice());//价格
                        }
                    };
                    recyclerView1.setAdapter(mAdapter1);
                }

                //基本路费
                list2 = response.getOther();
                if (list2 != null && list2.size() > 0) {
                    mAdapter2 = new CommonAdapter<FeeModel.OtherBean>
                            (FeeModelActivity.this, R.layout.item_feemodel, list2) {
                        @Override
                        protected void convert(ViewHolder holder, FeeModel.OtherBean model, int position) {
                            holder.setText(R.id.textView1, model.getTitle());//标题
                            holder.setText(R.id.textView2, model.getPrice());//价格
                        }
                    };
                    recyclerView2.setAdapter(mAdapter2);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.iv_left:
                //车型选择-左
                if (item > 0) {
                    item--;
                    //设置viewPager切换
                    viewPager.setCurrentItem(item);
                    mPageAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.iv_right:
                //车型选择-右
                if (item < model.getCar_type().size() - 1) {
                    item++;
                    //设置viewPager切换
                    viewPager.setCurrentItem(item);
                    mPageAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.tv_title:
                /*CityPicker.from(this) //activity或者fragment
                        .enableAnimation(true)    //启用动画效果，默认无
//                        .setAnimationStyle(anim)	//自定义动画
                        .setLocatedCity(new LocatedCity(province, city, cityCode))  //APP自身已定位的城市，传null会自动定位（默认）
                        .setHotCities(hotCities)    //指定热门城市
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                //选择的城市
                                tv_city.setText(data.getName() + "");
                            }

                            @Override
                            public void onCancel() {
                                //取消
                                Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                //定位接口，需要APP自身实现，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //定位完成之后更新数据到城市选择器
                                        CityPicker.from(SelectAddressActivity.this).locateComplete(new LocatedCity(province,
                                                city, cityCode), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();//必须指定android:theme="@style/DefaultCityPickerTheme" 否则会报错*/
                break;
        }
    }

    @Override
    protected void updateView() {
//        titleView.setTitle("费用标准-"+city);
        titleView.setVisibility(View.GONE);
    }


    /**
     * *****************************************GuidePageAdapter********************************************
     */
    class GuidePageAdapter extends PagerAdapter {

        //销毁position位置的界面
        @Override
        public void destroyItem(View v, int position, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) v).removeView(pageViews.get(position));

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        //获取当前窗体界面数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return pageViews.size();
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(View v, int position) {
            // TODO Auto-generated method stub
            ((ViewPager) v).addView(pageViews.get(position));
            return pageViews.get(position);
        }

        // 判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View v, Object arg1) {
            // TODO Auto-generated method stub
            return v == arg1;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        private int maxPos = pageViews.size() - 1;//最后一页
        private int currentPageScrollStatus;

        @Override
        public void onPageScrollStateChanged(int state) {
            // TODO Auto-generated method stub
            //记录page滑动状态，如果滑动了state就是1
            currentPageScrollStatus = state;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // TODO Auto-generated method stub

            /*//recyclerView切换
            recyclerView.scrollToPosition(item);
            mStringAdapter.notifyDataSetChanged();*/

            if (item == 0) {
                //如果offsetPixels是0页面也被滑动了，代表在第一页还要往左划
                if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {

                }
            } else if (item == maxPos) {
                //已经在最后一页还想往右划
                if (positionOffsetPixels == 0 && currentPageScrollStatus == 1) {


                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            setCurrentPos(position);
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setImageResource(R.drawable.yuanxing_lanse);
                //不是当前选中的page，其小圆点设置为未选中的状态
                if (position != i) {
                    imageViews[i].setImageResource(R.drawable.yuanxing_huise);
                }
            }
        }

        public void setMaxPage(int position) {
            //设置最后一页的position值
            maxPos = position;
        }

        public void setCurrentPos(int position) {
            //设置当前页的position值
            item = position;
        }
    }
}
