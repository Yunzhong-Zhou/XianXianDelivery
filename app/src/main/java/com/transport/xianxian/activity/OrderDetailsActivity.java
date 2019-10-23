package com.transport.xianxian.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.TruckPath;
import com.amap.api.services.route.TruckRouteRestult;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;

import overlay.TruckRouteColorfulOverLay;

/**
 * Created by zyz on 2019-10-23.
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity implements RouteSearch.OnTruckRouteSearchListener {
    private AMap aMap;
    private MapView mapView;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private TruckRouteRestult truckRouteResult;
    private LatLonPoint mStartPoint = new LatLonPoint(39.902896, 116.42792);
    private LatLonPoint mEndPoint = new LatLonPoint(39.894914, 116.322062);//终点，39.995576,116.481288


    private ProgressDialog progDialog = null;// 搜索时进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);

        mContext = this.getApplicationContext();
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        setfromandtoMarker();
        searchRouteResult(RouteSearch.TRUCK_AVOID_CONGESTION);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void updateView() {
        titleView.setTitle("订单详情");
    }

    private void setfromandtoMarker() {
        //添加覆盖物
        aMap.addMarker(new MarkerOptions()
                .position(convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
        aMap.addMarker(new MarkerOptions()
                .position(convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setOnTruckRouteSearchListener(this);


        //隐藏顶部控件
        findViewById(R.id.routemap_header).setVisibility(View.GONE);
    }


    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int mode) {
        if (mStartPoint == null) {
            myToast("定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
            myToast("终点未设置");
        }
        showProgressDialog();
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);


        fromAndTo.setPlateNumber("A6BN05");
        fromAndTo.setPlateProvince("京");

        // 第一个参数表示路径规划的起点和终点，第二个参数表示计算路径的模式，第三个参数表示途经点，第四个参数货车大小 必填
        RouteSearch.TruckRouteQuery query = new RouteSearch.TruckRouteQuery(fromAndTo, mode, null, RouteSearch.TRUCK_SIZE_HEAVY);

        query.setTruckAxis(6);
        query.setTruckHeight(3.9f);
        query.setTruckWidth(3);
        query.setTruckLoad(45);
        query.setTruckWeight(50);

        //异步查询
        mRouteSearch.calculateTruckRouteAsyn(query);
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null) {
            progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setIndeterminate(false);
            progDialog.setCancelable(true);
            progDialog.setMessage("正在搜索...");
            progDialog.show();
        }
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onTruckRouteSearched(TruckRouteRestult result, int rCode) {
        dissmissProgressDialog();

        //建议通过TruckPath中getRestriction() 判断路线上是否存在限行
        /**
         * 限行结果
         * 0，未知（未输入完整/正确车牌号信息时候显示）
         * 1，已规避限行
         * 2，起点限行
         * 3，途径点在限行区域内（设置途径点才出现此报错）
         * 4，途径限行区域
         * 5，终点限行
         */

        if (rCode == 1000) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                truckRouteResult = result;
                TruckPath path = truckRouteResult.getPaths().get(0);
                if (path == null) {
                    return;
                }
                aMap.clear();// 清理地图上的所有覆盖物

                TruckRouteColorfulOverLay drivingRouteOverlay = new TruckRouteColorfulOverLay(
                        this, aMap, path, truckRouteResult.getStartPos(),
                        truckRouteResult.getTargetPos(), null);

                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.setIsColorfulline(true);
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            } else {
                myToast("对不起，没有搜索到相关数据！");
            }
        } else if (rCode == 1904) {
            myToast("搜索失败,请检查网络连接！");
        } else if (rCode == 1002) {
            myToast("key验证无效！");
        } else {
            myToast("结果：" + rCode);
        }
    }

    /**
     * 把LatLonPoint对象转化为LatLon对象
     */
    public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
        return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
    }
}
