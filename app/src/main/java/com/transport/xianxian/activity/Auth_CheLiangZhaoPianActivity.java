package com.transport.xianxian.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by zyz on 2019-10-01.
 * 车辆照片
 */
public class Auth_CheLiangZhaoPianActivity extends BaseActivity {
    int item = 0;
    RecyclerView recyclerView;
    List<String> stringList = new ArrayList<>();
    CommonAdapter<String> mStringAdapter;

    ViewPager viewPager;
    private ArrayList<View> pageViews = new ArrayList<>();
    GuidePageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_cheliangzhaopian);
    }

    @Override
    protected void initView() {
        recyclerView = findViewByID_My(R.id.recyclerView);
        viewPager = findViewByID_My(R.id.viewPager);
        stringList.add("小型面包车");
        stringList.add("大型面包车");
        stringList.add("小货车");
        stringList.add("中型货车");
        stringList.add("大型货车");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        mStringAdapter = new CommonAdapter<String>
                (Auth_CheLiangZhaoPianActivity.this, R.layout.item_auth_cheliangzhaopian_tv, stringList) {
            @Override
            protected void convert(ViewHolder holder, String model, int position) {
                TextView tv1 = holder.getView(R.id.tv1);
                TextView tv2 = holder.getView(R.id.tv2);
                LinearLayout ll = holder.getView(R.id.ll);
                tv1.setText(model);
                tv2.setText(model);
                if (item == position) {
                    ll.setVisibility(View.VISIBLE);
                    tv1.setVisibility(View.GONE);
                } else {
                    ll.setVisibility(View.GONE);
                    tv1.setVisibility(View.VISIBLE);
                }

            }
        };
        mStringAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                item = i;
                ChangeUI();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        recyclerView.setAdapter(mStringAdapter);

        pageViews = new ArrayList<View>();
        for (int i = 0; i < stringList.size(); i++) {
            pageViews.add(inflater.inflate(R.layout.item_auth_clzp_vp_page, null));
        }
        for (int i = 0; i < pageViews.size(); i++) {
            /*ImageView imageView = (ImageView) pageViews.get(i).findViewById(R.id.imageView);
            imageView.setImageResource(R.color.red);*/
            TextView tv1 = pageViews.get(i).findViewById(R.id.tv1);
            tv1.setText(stringList.get(i));
        }
        mPageAdapter = new GuidePageAdapter();
        viewPager.setAdapter(mPageAdapter);
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_left:
                if (item > 0) {
                    item--;
                    ChangeUI();
                }
                break;
            case R.id.iv_right:
                if (item < stringList.size()-1) {
                    item++;
                    ChangeUI();
                }
                break;
        }
    }
    private void ChangeUI(){
        //设置viewPager切换
        viewPager.setCurrentItem(item);
        mPageAdapter.notifyDataSetChanged();
        //recyclerView切换
        recyclerView.scrollToPosition(item);
        mStringAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("车辆照片");
    }


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

            //recyclerView切换
            recyclerView.scrollToPosition(item);
            mStringAdapter.notifyDataSetChanged();

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
