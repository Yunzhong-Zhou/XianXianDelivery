package com.transport.xianxian.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.TruckPath;
import com.amap.api.services.route.TruckRouteRestult;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.base.BaseActivity;
import com.transport.xianxian.model.OrderDetailsModel;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.MyLogger;

import overlay.AMapUtil;
import overlay.TruckRouteColorfulOverLay;

/**
 * Created by zyz on 2019-10-23.
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity implements RouteSearch.OnTruckRouteSearchListener {
    String id = "";
    OrderDetailsModel model;
    private AMap aMap;
    private MapView mapView;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private TruckRouteRestult truckRouteResult;
    private LatLonPoint mStartPoint = null;
    private LatLonPoint mEndPoint = null;//终点，39.995576,116.481288


    private ProgressDialog progDialog = null;// 搜索时进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        //初始化地图
        mContext = this.getApplicationContext();
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        requestServer();

    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();
        showProgress(true, getString(R.string.app_loading));
        String string = "?token=" + localUserInfo.getToken()
                + "&id=" + id;
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(OrderDetailsActivity.this, URLs.OrderDetails + string, new OkHttpClientManager.ResultCallback<OrderDetailsModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(OrderDetailsModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单详情" + response);
                model = response;
                /*textView1.setText(response.getNickname());//昵称
                textView2.setText("¥ " + response.getMoney());//今日流水
                textView3.setText(response.getMoney());//账户余额
                textView4.setText(response.getOnline_time());//在线时长
                textView5.setText(response.getIndent_count());//今日单量
                textView6.setText(response.getComment_score());//当前评分
                if (!response.getHead().equals(""))
                    Glide.with(getActivity())
                            .load(IMGHOST + response.getHead())
                            .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                            .into(imageView1);//加载图片*/
                mStartPoint = new LatLonPoint(39.902896, 116.42792);//起点
                mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，39.995576,116.481288
                init();
                setfromandtoMarker();//显示标注物
                searchRouteResult(RouteSearch.TRUCK_AVOID_CONGESTION);//默认避免拥堵
            }
        });
    }

    @Override
    protected void updateView() {
        titleView.setTitle("订单详情");
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
    }

    /**
     * 添加覆盖物(起点和终点)
     */
    private void setfromandtoMarker() {
        //批量添加marker到地图上
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addMarker(1,"起点","起点地址描述",mStartPoint);
                addMarker(2,"终点","终点地址描述",mEndPoint);
            }
        });
        //显示多个infowindow
        /*ArrayList<MarkerOptions> optionsList = new ArrayList<>();
        MarkerOptions options1 = new MarkerOptions();
        options1.position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start))
                .title("起点")
                .snippet("起点地址描述")
                .setFlat(true);
        MarkerOptions options2 = new MarkerOptions();
        options2.position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end))
                .title("终点")
                .snippet("终点地址描述")
                .setFlat(true);
        optionsList.add(options1);
        optionsList.add(options2);

        aMap.addMarkers(optionsList,true);*/

        /*//只显示一个infowindow
        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mStartPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.start))
                .title("起点")
                .snippet("起点地址描述")
        ).showInfoWindow();

        aMap.addMarker(new MarkerOptions()
                .position(AMapUtil.convertToLatLng(mEndPoint))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.end))
                .title("终点")
                .snippet("终点地址描述")
        ).showInfoWindow();*/
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

        //设置车辆信息
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


    /**
     * 返回监听
     *
     * @param result
     * @param rCode
     */
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
//                aMap.clear();// 清理地图上的所有覆盖物

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
     * by moos on 2017/11/15
     * func:添加marker到地图上显示
     */
    BitmapDescriptor bitmapDescriptor;

    private void addMarker(int type, String s1, String s2,LatLonPoint point) {
        MarkerOptions options1 = new MarkerOptions();
        options1.position(AMapUtil.convertToLatLng(point));
        customizeMarkerIcon(type, s1, s2, new OnMarkerIconLoadListener() {
            @Override
            public void markerIconLoadingFinished(View view) {
//                bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                options1.icon(bitmapDescriptor);
                aMap.addMarker(options1);
            }
        });
       /* MarkerOptions options2 = new MarkerOptions();
        options1.position(AMapUtil.convertToLatLng(mEndPoint));
        customizeMarkerIcon(2,"终点","终点地址描述","卸货点",new OnMarkerIconLoadListener() {
            @Override
            public void markerIconLoadingFinished(View view) {
//                bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                options2.icon(bitmapDescriptor);
                aMap.addMarker(options2);
            }
        });*/

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

    /**
     * by mos on 2017.11.13
     * func:view转bitmap
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;

    }

}
