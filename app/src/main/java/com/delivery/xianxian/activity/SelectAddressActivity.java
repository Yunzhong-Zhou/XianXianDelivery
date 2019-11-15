package com.delivery.xianxian.activity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.utils.MyLogger;

/**
 * Created by zyz on 2019-11-14.
 */
public class SelectAddressActivity extends BaseActivity {
    private AMap aMap;//地图的控制器类，用来操作地图
    private MapView mMapView;//地图控件
    private GeocodeSearch geocoderSearch;//查询控制器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectaddress);
        //初始化地图
        mMapView = (MapView) findViewById(R.id.route_map);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        init();//初始化AMap对象,设置点击位置监听
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
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setScaleControlsEnabled(true);//控制比例尺控件是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //我的位置
                MyLogger.i(">>>>>>>>>我的位置:" + location.getLatitude());
            }
        });
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MyLogger.i(">>>>>>>选取点：" + latLng.latitude);
                //获取位置信息
                LatLonPoint point = new LatLonPoint(latLng.latitude, latLng.longitude);
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
//                        addressName = result.getRegeocodeAddress().getFormatAddress(); // 逆转地里编码不是每次都可以得到对应地图上的opi
                        MyLogger.i(">>>>>>" + result.getRegeocodeAddress().getFormatAddress());
                        /*MyLogger.i(">>>>>>" + result.getRegeocodeAddress().getProvince()//省
                                + result.getRegeocodeAddress().getCity()//市
                                + result.getRegeocodeAddress().getDistrict()//区
                        );*/

                    }

                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {

            }
        });

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

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setVisibility(View.GONE);
    }
}
