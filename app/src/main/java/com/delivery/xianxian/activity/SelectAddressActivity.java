package com.delivery.xianxian.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.delivery.xianxian.R;
import com.delivery.xianxian.adapter.Pop_ListAdapter;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.SelectAddressModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.MyLogger;
import com.delivery.xianxian.view.FixedPopupWindow;
import com.squareup.okhttp.Request;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import overlay.AMapUtil;

import static com.delivery.xianxian.activity.OrderDetailsActivity.convertViewToBitmap;

/**
 * Created by zyz on 2019-11-14.
 */
public class SelectAddressActivity extends BaseActivity {
    int type = 0;//10001起点、10002终点、10003途经点
    private AMap aMap;//地图的控制器类，用来操作地图
    private MapView mMapView;//地图控件
    private GeocodeSearch geocoderSearch;//查询控制器
    LatLonPoint point = null;

//    double latitude = 0, longititude = 0;

    List<HotCity> hotCities = new ArrayList<>();//热门城市
    String province = "", city = "", cityCode = "", district = "";

    String addr = "", addr_detail = "", lat = "", lng = "", name = "", mobile = "";

    //顶部 中部
    EditText et_addr;
    TextView tv_city, tv_lishi, tv_changyong, tv_ditu;
    LinearLayout ll_center;
    RecyclerView recyclerView;
    List<SelectAddressModel.HistoryBean> list1 = new ArrayList<>();
    CommonAdapter<SelectAddressModel.HistoryBean> mAdapter1;
    List<SelectAddressModel.OftenUsedBean> list2 = new ArrayList<>();
    CommonAdapter<SelectAddressModel.OftenUsedBean> mAdapter2;
    //底部
    EditText editText1, editText2, editText3, editText4;
    TextView textView2;
    ImageView imageView1;


    Handler handler = new Handler();
    Runnable runnable;

    RecyclerView recyclerView_addr;
    CommonAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectaddress);
        //初始化地图
        mMapView = (MapView) findViewById(R.id.route_map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        init();//初始化AMap对象,设置点击位置监听
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void initView() {
        et_addr = findViewByID_My(R.id.et_addr);
        tv_city = findViewByID_My(R.id.tv_city);

        recyclerView_addr = findViewByID_My(R.id.recyclerView_addr);
        LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(this);
        recyclerView_addr.setLayoutManager(mLinearLayoutManager1);
        recyclerView_addr.setVisibility(View.GONE);
        /*recyclerView_addr.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                recyclerView_addr.setVisibility(View.GONE);
                return false;
            }
        });*/

        tv_lishi = findViewByID_My(R.id.tv_lishi);
        tv_changyong = findViewByID_My(R.id.tv_changyong);
        tv_ditu = findViewByID_My(R.id.tv_ditu);
        ll_center = findViewByID_My(R.id.ll_center);
        ll_center.setVisibility(View.GONE);
        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        editText1 = findViewByID_My(R.id.editText1);
        editText2 = findViewByID_My(R.id.editText2);
        editText3 = findViewByID_My(R.id.editText3);
        editText4 = findViewByID_My(R.id.editText4);
        textView2 = findViewByID_My(R.id.textView2);
        imageView1 = findViewByID_My(R.id.imageView1);

        et_addr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    et_addr.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(final Editable s) {
                            if (runnable != null) {
                                handler.removeCallbacks(runnable);
                                Log.v("tag", "---" + s.toString());
                            }
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    MyLogger.i("tag", "输入>>>>>>" + s.toString());
                                    if (!et_addr.getText().toString().trim().equals("")) {
                                        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                                        InputtipsQuery inputquery = new InputtipsQuery(et_addr.getText().toString().trim(), city);
                                        inputquery.setCityLimit(false);//限制在当前城市
                                        Inputtips inputTips = new Inputtips(SelectAddressActivity.this, inputquery);
                                        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                                            @Override
                                            public void onGetInputtips(List<Tip> list, int i) {
                                                MyLogger.i(">>>>>" + list.size());
                                                if (list.size() > 0) {
                                                    //显示弹窗
//                                        showPopupWindow1(et_addr, list);
                                                    showMapAddr(list);
                                                }
                                            }
                                        });
                                        inputTips.requestInputtipsAsyn();
                                    } else {
                                        recyclerView_addr.setVisibility(View.GONE);
                                    }
                                }
                            };
                            Log.v("tag", "(((((" + s.toString());
                            handler.postDelayed(runnable, 800);
                        }
                    });
                } else {
                    // 此处为失去焦点时的处理内容
                    recyclerView_addr.setVisibility(View.GONE);
                }
            }
        });

        et_addr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    hideInput();
                    //do something
                    //doSearch();
                    MyLogger.i(">>>>>>>>输入后：" + et_addr.getText().toString().trim());
                    if (!et_addr.getText().toString().trim().equals("")) {
                        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                        InputtipsQuery inputquery = new InputtipsQuery(et_addr.getText().toString().trim(), city);
                        inputquery.setCityLimit(false);//限制在当前城市
                        Inputtips inputTips = new Inputtips(SelectAddressActivity.this, inputquery);
                        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                            @Override
                            public void onGetInputtips(List<Tip> list, int i) {
                                if (list.size() > 0) {
                                    //显示弹窗
//                                    showPopupWindow1(et_addr, list);
                                    showMapAddr(list);
                                }
                            }
                        });
                        inputTips.requestInputtipsAsyn();
                    } else {
                        recyclerView_addr.setVisibility(View.GONE);
                    }

                    return true;
                }
                return false;
            }
        });
        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    editText1.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(final Editable s) {
                            if (runnable != null) {
                                handler.removeCallbacks(runnable);
                                Log.v("tag", "---" + s.toString());
                            }
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    MyLogger.i("tag", "输入>>>>>>" + s.toString());
                                    if (!editText1.getText().toString().trim().equals("")) {
                                        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                                        InputtipsQuery inputquery = new InputtipsQuery(editText1.getText().toString().trim(), city);
                                        inputquery.setCityLimit(false);//限制在当前城市
                                        Inputtips inputTips = new Inputtips(SelectAddressActivity.this, inputquery);
                                        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                                            @Override
                                            public void onGetInputtips(List<Tip> list, int i) {
                                                MyLogger.i(">>>>>" + list.size());
                                                if (list.size() > 0) {
                                                    //显示弹窗
//                                        showPopupWindow1(et_addr, list);
                                                    showMapAddr(list);
                                                }
                                            }
                                        });
                                        inputTips.requestInputtipsAsyn();
                                    } else {
                                        recyclerView_addr.setVisibility(View.GONE);
                                    }
                                }
                            };
                            Log.v("tag", "(((((" + s.toString());
                            handler.postDelayed(runnable, 800);
                        }
                    });
                } else {
                    // 此处为失去焦点时的处理内容
                    recyclerView_addr.setVisibility(View.GONE);
                }
            }
        });

        editText1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    hideInput();
                    //do something
                    //doSearch();
                    MyLogger.i(">>>>>>>>输入后：" + editText1.getText().toString().trim());
                    if (!editText1.getText().toString().trim().equals("")) {
                        //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                        InputtipsQuery inputquery = new InputtipsQuery(editText1.getText().toString().trim(), city);
                        inputquery.setCityLimit(false);//限制在当前城市
                        Inputtips inputTips = new Inputtips(SelectAddressActivity.this, inputquery);
                        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
                            @Override
                            public void onGetInputtips(List<Tip> list, int i) {
                                if (list.size() > 0) {
                                    //显示弹窗
//                                    showPopupWindow1(et_addr, list);
                                    showMapAddr(list);
                                }
                            }
                        });
                        inputTips.requestInputtipsAsyn();
                    } else {
                        recyclerView_addr.setVisibility(View.GONE);
                    }

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        //热门城市
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

        switch (type) {
            case 10001:
                //起点
                imageView1.setImageResource(R.mipmap.ic_quan_blue);
                editText1.setHint("发货地信息");
                textView2.setText("确认发货地");
                break;
            case 10002:
                //终点
                imageView1.setImageResource(R.mipmap.ic_quan_red);
                editText1.setHint("收货地信息");
                textView2.setText("确认收货地");
                break;
            case 10003:
                //途经点
                imageView1.setImageResource(R.mipmap.ic_quan_blue);
                editText1.setHint("途经地信息");
                textView2.setText("确认途经地");
                break;
        }

        requestServer();
    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));
        String string = "?page=" + 1//当前页号
                + "&count=" + "50"//页面行数
                + "&token=" + localUserInfo.getToken();
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(this, URLs.SelectAddress + string, new OkHttpClientManager.ResultCallback<SelectAddressModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(SelectAddressModel response) {
                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>常用地址" + response);
                list1 = response.getHistory();
                mAdapter1 = new CommonAdapter<SelectAddressModel.HistoryBean>
                        (SelectAddressActivity.this, R.layout.item_selectaddress, list1) {
                    @Override
                    protected void convert(ViewHolder holder, SelectAddressModel.HistoryBean model, int position) {
                        holder.setText(R.id.tv1, model.getAddr());
                        holder.setText(R.id.tv2, model.getAddr_detail());
                        holder.setText(R.id.tv3, model.getName());
                        holder.setText(R.id.tv4, model.getMobile());
                        //删除地址
                        TextView tv_delete = holder.getView(R.id.tv_delete);
                        tv_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, String> params = new HashMap<>();
                                params.put("token", localUserInfo.getToken());
                                params.put("id", list1.get(position).getId() + "");
                                RequestDelete(params, 1, position);
                            }
                        });
                    }
                };
                mAdapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", list1.get(i).getName());
                        bundle.putString("mobile", list1.get(i).getMobile());
                        bundle.putString("addr", list1.get(i).getAddr());
                        bundle.putString("city", list1.get(i).getCity());
                        bundle.putString("addr_id", list1.get(i).getId());
                        resultIntent.putExtras(bundle);
                        SelectAddressActivity.this.setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
                recyclerView.setAdapter(mAdapter1);

                list2 = response.getOften_used();
                mAdapter2 = new CommonAdapter<SelectAddressModel.OftenUsedBean>
                        (SelectAddressActivity.this, R.layout.item_selectaddress, list2) {
                    @Override
                    protected void convert(ViewHolder holder, SelectAddressModel.OftenUsedBean model, int position) {
                        holder.setText(R.id.tv1, model.getAddr());
                        holder.setText(R.id.tv2, model.getAddr_detail());
                        holder.setText(R.id.tv3, model.getName());
                        holder.setText(R.id.tv4, model.getMobile());
                        //删除地址
                        TextView tv_delete = holder.getView(R.id.tv_delete);
                        tv_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, String> params = new HashMap<>();
                                params.put("token", localUserInfo.getToken());
                                params.put("id", list2.get(position).getId() + "");
                                RequestDelete(params, 2, position);
                            }
                        });
                    }
                };
                mAdapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        Intent resultIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", list2.get(i).getName());
                        bundle.putString("mobile", list2.get(i).getMobile());
                        bundle.putString("addr", list2.get(i).getAddr());
                        bundle.putString("city", list2.get(i).getCity());
                        bundle.putString("addr_id", list2.get(i).getId());
                        resultIntent.putExtras(bundle);
                        SelectAddressActivity.this.setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                        return false;
                    }
                });
            }
        });
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(30 * 1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。

        myLocationStyle.showMyLocation(true);//显示自己的位置

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setScaleControlsEnabled(true);//控制比例尺控件是否显示

        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
//                latitude = location.getLatitude();
//                longititude = location.getLongitude();

                //根据首页传入的地址进行展示地图
                MyLogger.i(">>>>>>" + getIntent().getStringExtra("city"));
                if (!getIntent().getStringExtra("city").equals("")) {
                    getLatlon(getIntent().getStringExtra("city"));
                }else {
                    //获取位置信息
                    point = new LatLonPoint(location.getLatitude(), location.getLongitude());
                    // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                    RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
                    geocoderSearch.getFromLocationAsyn(query);
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());//构造一个位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));//设置地图放大级别
                }

            }
        });
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                latitude = latLng.latitude;
//                longititude = latLng.longitude;
                //获取位置信息
                point = new LatLonPoint(latLng.latitude, latLng.longitude);
                // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);
            }
        });
        //坐标转地址
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                //解析result获取地址描述信息
                if (rCode == 1000) {
                    //成功
                    if (result != null && result.getRegeocodeAddress() != null
                            && result.getRegeocodeAddress().getFormatAddress() != null) {
                        addr = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
                        province = result.getRegeocodeAddress().getProvince();//省
                        city = result.getRegeocodeAddress().getCity();//市
                        district = result.getRegeocodeAddress().getDistrict();//区

                        tv_city.setText(city);
                        cityCode = result.getRegeocodeAddress().getCityCode();//市代码

                        MyLogger.i(">>选取地址>>>>" + addr + result.getRegeocodeQuery().getPoint().getLatitude());
                        et_addr.setText(addr);
                        editText1.setText(addr);


                        lat = result.getRegeocodeQuery().getPoint().getLatitude() + "";
                        lng = result.getRegeocodeQuery().getPoint().getLongitude() + "";

                        //移动到指定点
                        LatLng marker1 = new LatLng(result.getRegeocodeQuery().getPoint().getLatitude(),
                                result.getRegeocodeQuery().getPoint().getLongitude());
                        //设置中心点
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));

                        //绘制Marker
                        setfromandtoMarker(city + district,//市+区
                                addr,
                                point);

                    }

                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_btn:
                //返回
                finish();
                break;
            case R.id.iv_close:
                //删除地址
                et_addr.setText("");
                editText1.setText("");
                if (marker != null) {
                    marker.remove();
                }
                recyclerView_addr.setVisibility(View.GONE);
                break;
            case R.id.tv_lishi:
                //历史地址
                ll_center.setVisibility(View.VISIBLE);
                tv_lishi.setTextColor(getResources().getColor(R.color.black1));
                tv_changyong.setTextColor(getResources().getColor(R.color.black3));
                recyclerView.setAdapter(mAdapter1);
                break;
            case R.id.tv_changyong:
                //常用地址
                ll_center.setVisibility(View.VISIBLE);
                tv_lishi.setTextColor(getResources().getColor(R.color.black3));
                tv_changyong.setTextColor(getResources().getColor(R.color.black1));
                recyclerView.setAdapter(mAdapter2);
                break;
            case R.id.tv_ditu:
                //地图上选地址
                ll_center.setVisibility(View.GONE);
                break;
            case R.id.tv_city:
                //选择城市
                CityPicker.from(this) //activity或者fragment
                        .enableAnimation(true)    //启用动画效果，默认无
//                        .setAnimationStyle(anim)	//自定义动画
                        .setLocatedCity(new LocatedCity(province, city, cityCode))  //APP自身已定位的城市，传null会自动定位（默认）
                        .setHotCities(hotCities)    //指定热门城市
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                //选择的城市
                                tv_city.setText(data.getName() + "");
                                getLatlon(data.getName());
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
                        .show();//必须指定android:theme="@style/DefaultCityPickerTheme" 否则会报错
                break;
            case R.id.textView1:
                //通讯录
                selectContact();
                break;
            case R.id.textView2:
                //确认发货地
                if (match()) {
                    textView2.setClickable(false);
                    this.showProgress(true, "正在添加位置...");
                    params.put("token", localUserInfo.getToken());
                    params.put("addr", addr);
                    params.put("addr_detail", addr_detail);
                    params.put("lat", lat);
                    params.put("lng", lng);
                    params.put("name", name);
                    params.put("mobile", mobile);
                    params.put("city", city);//根据这个值判断是都为同一个城市，是否为长途
                    RequestAdd(params);
                }
                break;
        }
    }

    private void RequestAdd(Map<String, String> params) {
        OkHttpClientManager.postAsyn(SelectAddressActivity.this, URLs.AddAddress, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(final Request request, String info, Exception e) {
                hideProgress();
                textView2.setClickable(true);
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                MyLogger.i(">>>>>>>>>添加地址" + response);
                hideProgress();
                textView2.setClickable(true);
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
//                    MyLogger.i(">>>>>>>>"+jObj.getString("data"));
                    Intent resultIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("mobile", mobile);
                    bundle.putString("addr", addr);
                    bundle.putString("city", city);
                    bundle.putString("addr_id", jObj.getString("data"));
                    resultIntent.putExtras(bundle);
                    SelectAddressActivity.this.setResult(RESULT_OK, resultIntent);
                    finish();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, false);

    }

    private void RequestDelete(Map<String, String> params, int type, int position) {
        OkHttpClientManager.postAsyn(SelectAddressActivity.this, URLs.DeleteAddress, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(final Request request, String info, Exception e) {
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
                MyLogger.i(">>>>>>>>>删除地址" + response);
                if (type == 1) {
                    list1.remove(position);
                    mAdapter1.notifyDataSetChanged();
                } else {
                    list2.remove(position);
                    mAdapter2.notifyDataSetChanged();
                }
            }
        }, false);

    }

    private boolean match() {
        /* = editText1.getText().toString().trim();
        if (TextUtils.isEmpty()) {
            myToast("请输入备注信息");
            return false;
        }*/
        addr = et_addr.getText().toString().trim();
        if (TextUtils.isEmpty(addr) && lat.equals("") && lng.equals("")) {
            myToast("请选择地址");
            return false;
        }
        addr_detail = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(addr_detail)) {
            myToast("请输入楼层及门牌号码");
            return false;
        }
        name = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            myToast("请输入联系人");
            return false;
        }
        mobile = editText4.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            myToast("请输入联系电话");
            return false;
        }
        /*if (mobile.length() != 11) {
            myToast("请输入11位手机号");
            return false;
        }*/

        return true;
    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }

    private void showMapAddr(List<Tip> tips) {
        List<String> list = new ArrayList<String>();
        List<LatLonPoint> pointList = new ArrayList<>();//途经点
        for (int i = 0; i < tips.size(); i++) {
            if (!tips.get(i).getAddress().equals("")) {
                list.add(tips.get(i).getAddress());
                pointList.add(tips.get(i).getPoint());
            }
        }
        mAdapter = new CommonAdapter<String>
                (SelectAddressActivity.this, R.layout.item_mapaddr_list, list) {
            @Override
            protected void convert(ViewHolder holder, String model, int position) {
                holder.setText(R.id.textView1, model);

            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                et_addr.setText(list.get(i));
                //获取位置信息
                point = pointList.get(i);
                // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);

                recyclerView_addr.setVisibility(View.GONE);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        recyclerView_addr.setAdapter(mAdapter);
        recyclerView_addr.setVisibility(View.VISIBLE);
/*// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = recyclerView_addr.getTop();
                int height1 = recyclerView_addr.getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        recyclerView_addr.setVisibility(View.GONE);
                    }
                    if (y > height1) {
                        recyclerView_addr.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });*/


    }

    private void showPopupWindow1(View v, List<Tip> tips) {
        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(SelectAddressActivity.this).inflate(
                R.layout.pop_list_1, null);
        final FixedPopupWindow popupWindow = new FixedPopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        contentView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = contentView.findViewById(R.id.pop_listView).getTop();
                int height1 = contentView.findViewById(R.id.pop_listView).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popupWindow.dismiss();
                    }
                    if (y > height1) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
        // 设置按钮的点击事件
        ListView pop_listView = (ListView) contentView.findViewById(R.id.pop_listView);
        List<String> list = new ArrayList<String>();
        List<LatLonPoint> pointList = new ArrayList<>();//途经点
        for (int i = 0; i < tips.size(); i++) {
            if (!tips.get(i).getAddress().equals("")) {
                list.add(tips.get(i).getAddress());
                pointList.add(tips.get(i).getPoint());
            }
        }
        final Pop_ListAdapter adapter = new Pop_ListAdapter(SelectAddressActivity.this, list);
//        adapter.setSelectItem(i1);
        pop_listView.setAdapter(adapter);
        pop_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                i1 = i;
                adapter.setSelectItem(i);
                adapter.notifyDataSetChanged();

                et_addr.setText(list.get(i));
                //获取位置信息
                point = pointList.get(i);
                // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
                geocoderSearch.getFromLocationAsyn(query);

                popupWindow.dismiss();

            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        ColorDrawable dw = new ColorDrawable(this.getResources().getColor(R.color.transparent));
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(v);
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    //切换城市
    private void getLatlon(String cityName) {
        //构造 GeocodeSearch 对象，并设置监听。
        GeocodeSearch geocodeSearch = new GeocodeSearch(SelectAddressActivity.this);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {

            //------------------------坐标转地址/坐标转地址的监听回调-----------------------
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null &&
                            geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress geocodeAddress = geocodeResult.getGeocodeAddressList().get(0);
                        double latitude = geocodeAddress.getLatLonPoint().getLatitude();//纬度
                        double longititude = geocodeAddress.getLatLonPoint().getLongitude();//经度
                        String adcode = geocodeAddress.getAdcode();//区域编码

                       /* LogUtils.e("地理编码", geocodeAddress.getAdcode() + "");
                        LogUtils.e("纬度latitude", latitude + "");
                        LogUtils.e("经度longititude", longititude + "");*/

                       /* LatLng lng = new LatLng(latitude, longititude);
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(lng));*/

                        addr = geocodeAddress.getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
                        province = geocodeAddress.getProvince();//省
                        city = geocodeAddress.getCity();//市
                        district = geocodeAddress.getDistrict();//区

                        tv_city.setText(city);
                        et_addr.setText(addr);
                        cityCode = geocodeAddress.getAdcode();//区域编码


                        lat = latitude + "";
                        lng = longititude + "";

                        //移动到指定点
                        LatLng marker1 = new LatLng(latitude, longititude);
                        //设置中心点
                        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));

                        //绘制Marker
                        setfromandtoMarker(city + district,//市+区
                                addr,
                                point);

                    } else {
                        myToast("地址名出错");
                    }
                }
            }
        });

        GeocodeQuery geocodeQuery = new GeocodeQuery(cityName.trim(), "30000");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }

    /**
     * **************************************打开通讯录，选择联系人***************************************
     */
    public static final int REQUEST_SELECT_PHONE_NUMBER = 1;

    public void selectContact() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_PHONE_NUMBER);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {

            Uri contactUri = data.getData();
            String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
            Cursor cursor = getContentResolver().query(contactUri, projection,
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                name = cursor.getString(nameIndex);      //联系人姓名
                mobile = cursor.getString(numberIndex).trim().replaceAll(" ","");  //联系人号码-去掉空格
                MyLogger.i(">>>>>>>>"+mobile);
                editText3.setText(name);
                editText4.setText(mobile);

                cursor.close();
            }
        }
    }


    /**
     * **************************************绘制Marker覆盖物***************************************
     */
    /**
     * 添加覆盖物(起点和终点)
     */
    private void setfromandtoMarker(String s1, String s2, LatLonPoint point) {
        //批量添加marker到地图上
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case 10001:
                        //起点
                        addMarker(1, s1, s2, point);
                        break;
                    case 10002:
                        //终点
                        addMarker(2, s1, s2, point);
                        break;
                    case 10003:
                        //途经点
                        addMarker(3, s1, s2, point);
                        break;
                }


            }
        });
    }

    /**
     * by moos on 2017/11/15
     * func:添加marker到地图上显示
     */
    BitmapDescriptor bitmapDescriptor;
    MarkerOptions options = null;
    Marker marker = null;

    private void addMarker(int type, String s1, String s2, LatLonPoint point) {
        if (options == null) {
            options = new MarkerOptions();
        }
        options.position(AMapUtil.convertToLatLng(point));
        customizeMarkerIcon(type, s1, s2, new OnMarkerIconLoadListener() {
            @Override
            public void markerIconLoadingFinished(View view) {
//                bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                options.icon(bitmapDescriptor);
                if (marker != null) {
//                    aMap.clear();//移除所有marker
                    marker.remove();
                }
                marker = aMap.addMarker(options);//添加marker
            }
        });

    }

    /**
     * by moos on 2017/11/13
     * func:定制化marker的图标
     *
     * @return
     */
    private void customizeMarkerIcon(int type, String s1, String s2, OnMarkerIconLoadListener listener) {
        final View markerView = LayoutInflater.from(this).inflate(R.layout.marker_bg, null);
        TextView tv1 = markerView.findViewById(R.id.tv1);
        TextView tv2 = markerView.findViewById(R.id.tv2);
        TextView tv3 = markerView.findViewById(R.id.tv3);
        ImageView iv = markerView.findViewById(R.id.iv);

        tv1.setText(s1);
        tv2.setText(s2);
        switch (type) {
            case 1:
                //装货地
                tv3.setText("装货地");
                tv3.setTextColor(getResources().getColor(R.color.blue));
                iv.setImageResource(R.mipmap.start);
                break;
            case 2:
                //卸货地
                tv3.setText("卸货地");
                tv3.setTextColor(getResources().getColor(R.color.red));
                iv.setImageResource(R.mipmap.end);
                break;
            default:
                //途经点
                tv3.setText("途经点");
                tv3.setTextColor(getResources().getColor(R.color.black1));
                iv.setImageResource(R.mipmap.end);
                break;
        }
        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
        listener.markerIconLoadingFinished(markerView);

    }

    /**
     * by moos on 2017/11/15
     * func:自定义监听接口,用来marker的icon加载完毕后回调添加marker属性
     */
    public interface OnMarkerIconLoadListener {
        void markerIconLoadingFinished(View view);
    }
}
