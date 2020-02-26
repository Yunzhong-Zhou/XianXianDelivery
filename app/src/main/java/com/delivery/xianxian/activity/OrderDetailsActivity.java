package com.delivery.xianxian.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.TruckPath;
import com.amap.api.services.route.TruckRouteRestult;
import com.bumptech.glide.Glide;
import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.cy.dialog.BaseDialog;
import com.delivery.xianxian.R;
import com.delivery.xianxian.base.BaseActivity;
import com.delivery.xianxian.model.OrderCancelModel;
import com.delivery.xianxian.model.OrderDetailsModel;
import com.delivery.xianxian.net.OkHttpClientManager;
import com.delivery.xianxian.net.URLs;
import com.delivery.xianxian.utils.CommonUtil;
import com.delivery.xianxian.utils.MyLogger;
import com.hyphenate.easeui.EaseConstant;
import com.squareup.okhttp.Request;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import overlay.AMapUtil;
import overlay.TruckRouteColorfulOverLay;

import static com.delivery.xianxian.net.OkHttpClientManager.IMGHOST;

/**
 * Created by zyz on 2019-10-23.
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity implements RouteSearch.OnTruckRouteSearchListener, AMap.OnMyLocationChangeListener {
    String id = "";
    OrderDetailsModel model;
    private AMap aMap;
    private MapView mapView;
    private RouteSearch mRouteSearch;
    private TruckRouteRestult truckRouteResult;
    private LatLonPoint mStartPoint = null;//起点
    private LatLonPoint mEndPoint = null;//终点，39.995576,116.481288
    List<LatLonPoint> pointList = new ArrayList<>();//途经点
    private ProgressDialog progDialog = null;// 搜索时进度条

    private RecyclerView recyclerView;
    List<OrderDetailsModel.TindentBean.AddrListBean> list = new ArrayList<>();
    CommonAdapter<OrderDetailsModel.TindentBean.AddrListBean> mAdapter;

    LinearLayout ll_hint1, ll_hint2, ll_hint3;
    ImageView imageView1, tv_shouqi;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12, textView13, textView14, textView15, textView16,
            textView17, textView18, textView19,
            tv_confirm, tv_fujiafei, tv_shishiwendu;

    boolean isOpen = false;

    /*//开始点、结束点、计算距离
    double lat = 0, lng = 0;
    private DPoint mStartDPoint = null;
    private DPoint mEndDPoint = null;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        //初始化地图
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        requestServer();
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
//        aMapTrackClient.stopTrack(new TrackParam(Constants.SERVICE_ID, terminalId), new SimpleOnTrackLifecycleListener());
    }

    @Override
    protected void initView() {
        ll_hint1 = findViewByID_My(R.id.ll_hint1);
        ll_hint2 = findViewByID_My(R.id.ll_hint2);
        ll_hint3 = findViewByID_My(R.id.ll_hint3);
        ll_hint3.setVisibility(View.GONE);
        imageView1 = findViewByID_My(R.id.imageView1);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);
        textView4 = findViewByID_My(R.id.textView4);
        textView5 = findViewByID_My(R.id.textView5);
        textView6 = findViewByID_My(R.id.textView6);
        textView7 = findViewByID_My(R.id.textView7);
        textView8 = findViewByID_My(R.id.textView8);
        textView9 = findViewByID_My(R.id.textView9);
        textView10 = findViewByID_My(R.id.textView10);
        textView11 = findViewByID_My(R.id.textView11);
        textView12 = findViewByID_My(R.id.textView12);
        textView13 = findViewByID_My(R.id.textView13);
        textView14 = findViewByID_My(R.id.textView14);
        textView15 = findViewByID_My(R.id.textView15);
        textView16 = findViewByID_My(R.id.textView16);
        textView17 = findViewByID_My(R.id.textView17);
        textView18 = findViewByID_My(R.id.textView18);
        textView19 = findViewByID_My(R.id.textView19);
        tv_confirm = findViewByID_My(R.id.tv_confirm);

        tv_shouqi = findViewByID_My(R.id.tv_shouqi);
        tv_fujiafei = findViewByID_My(R.id.tv_fujiafei);
        tv_shishiwendu = findViewByID_My(R.id.tv_shishiwendu);

        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);

    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
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
                    showToast(info, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onResponse(OrderDetailsModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单详情" + response);
                model = response;
                for (int i = 0; i < response.getTindent().getAddr_list().size(); i++) {
                    pointList.add(new LatLonPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat())
                            , Double.valueOf(response.getTindent().getAddr_list().get(i).getLng())));//添加标注点
                    if (i == 0) {
                        mStartPoint = new LatLonPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat())
                                , Double.valueOf(response.getTindent().getAddr_list().get(i).getLng()));//起点

                    } else if (i == (model.getTindent().getAddr_list().size() - 1)) {
                        mEndPoint = new LatLonPoint(Double.valueOf(response.getTindent().getAddr_list().get(i).getLat()),
                                Double.valueOf(response.getTindent().getAddr_list().get(i).getLng()));//终点

                    } else {
                        //途经点

                    }
                }
                setfromandtoMarker();//显示标注物

//                searchRouteResult(RouteSearch.DRIVING_MULTI_STRATEGY_FASTEST_SHORTEST_AVOID_CONGESTION);//默认避免拥堵、设置车辆信息

                switch (model.getTindent().getStatus()) {
                    case -1://未接单
                        ll_hint1.setVisibility(View.VISIBLE);
                        ll_hint2.setVisibility(View.GONE);
                        titleView.showRightTextview("取消订单", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("确认取消该订单吗？", "确认", "取消",
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                                Map<String, String> params = new HashMap<>();
                                                params.put("token", localUserInfo.getToken());
                                                params.put("id", model.getTindent().getId());
                                                params.put("type", "owner_cancel_get");
                                                RequestQuXiao(params, 1);
                                            }
                                        }, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                            }
                        });
                        //计时器
                        Handler startTimehandler = new Handler() {
                            public void handleMessage(android.os.Message msg) {
                                textView1.setText("已等待" + (String) msg.obj);
                            }
                        };
                        new Timer("等待计时器").scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                long time = (int) ((System.currentTimeMillis() - (response.getTindent().getWait_time() * 1000)) / 1000);
                                String hh = new DecimalFormat("00").format(time / 3600);
                                String mm = new DecimalFormat("00").format(time % 3600 / 60);
                                String ss = new DecimalFormat("00").format(time % 60);
                                String timeFormat = new String(hh + "小时" + mm + "分" + ss + "秒");
                                Message msg = new Message();
                                msg.obj = timeFormat;
                                startTimehandler.sendMessage(msg);
                            }

                        }, 0, 1000L);

                        break;
                    case 7:
                        ll_hint1.setVisibility(View.VISIBLE);
                        ll_hint2.setVisibility(View.GONE);
                        titleView.showRightTextview("评价", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("id", model.getTindent().getId());
                                CommonUtil.gotoActivityWithData(OrderDetailsActivity.this, AppraiseActivity.class, bundle, true);
                            }
                        });
                    default:
                        //已接单
                        ll_hint1.setVisibility(View.GONE);
                        ll_hint2.setVisibility(View.VISIBLE);

                        titleView.hideRightBtn_invisible();


                        if (!response.getTindent().getDriver_info().getHead().equals(""))
                            Glide.with(OrderDetailsActivity.this)
                                    .load(IMGHOST + response.getTindent().getDriver_info().getHead())
                                    .centerCrop()
//                    .placeholder(R.mipmap.headimg)//加载站位图
//                    .error(R.mipmap.headimg)//加载失败
                                    .into(imageView1);//加载图片

                        textView4.setText(response.getTindent().getDriver_info().getNickname());//昵称
                        textView5.setText("评分" + response.getTindent().getDriver_info().getComment_score());//评分
                        textView6.setText("" + response.getTindent().getDriver_info().getCard_number());//车牌
                        textView7.setText(response.getTindent().getUse_type());//类型

                        break;
                }

                textView10.setText("发布时间：" + response.getTindent().getPlan_time());//发布时间
                textView11.setText("订单号：" + response.getTindent().getSn());//订单号：
                textView12.setText(response.getTindent().getStatus_text());// 状态
                textView13.setText(response.getTindent().getCar_type());// 订单车型
                textView14.setText(response.getTindent().getContacts_mobile());//联系电话
                textView15.setText(response.getTindent().getTemperature());//温层
                textView16.setText(response.getTindent().getRemark());//备注
                textView17.setText(response.getTindent().getPay_status());//支付状态
                textView18.setText("¥ " + response.getTindent().getTotal_price());//金额

                //地址列表
                list = response.getTindent().getAddr_list();
                mAdapter = new CommonAdapter<OrderDetailsModel.TindentBean.AddrListBean>
                        (OrderDetailsActivity.this, R.layout.item_orderdetail, list) {
                    @Override
                    protected void convert(ViewHolder holder, OrderDetailsModel.TindentBean.AddrListBean model, int position) {
                        //车型
                        TextView textView1 = holder.getView(R.id.tv1);
                        if (position == 0) {
                            textView1.setText("发");
                            textView1.setBackgroundResource(R.drawable.yuanxing_lanse);
                            holder.setText(R.id.tv3, "发货人：" + model.getName());//发货人
                        } else if (position == (response.getTindent().getAddr_list().size() - 1)) {
                            textView1.setText("收");
                            textView1.setBackgroundResource(R.drawable.yuanxing_juse);
                            holder.setText(R.id.tv3, "收货人：" + model.getName());//发货人
                        } else {
                            //途经点
                            textView1.setText("途");
                            textView1.setBackgroundResource(R.drawable.yuanxing_huise);
                            holder.setText(R.id.tv3, "收货人：" + model.getName());//发货人
                        }
                        holder.setText(R.id.tv2, model.getAddr());//地址

                        holder.setText(R.id.tv4, "手机号：" + model.getMobile());//手机号
                    }
                };
                recyclerView.setAdapter(mAdapter);

                //标签
                FlowLayoutAdapter<String> flowLayoutAdapter;
               /* List<String> stringList = new ArrayList<>();
                for (int i = 0; i < response.getTindent().getTag().size(); i++) {
                    stringList.add(response.getTindent().getTag().get(i).getVal());
                }*/
                flowLayoutAdapter = new FlowLayoutAdapter<String>(response.getTindent().getOther_tag()) {
                    @Override
                    public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, String bean) {
//                                holder.setText(R.id.tv,bean);
                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(bean);
                        tv.setTextColor(getResources().getColor(R.color.black1));
                        tv.setBackgroundResource(R.drawable.yuanjiao_3_huise);
                    }

                    @Override
                    public void onItemClick(int position, String bean) {
//                        showToast("点击" + position);
                    }

                    @Override
                    public int getItemLayoutID(int position, String bean) {
                        return R.layout.item_flowlayout;
                    }
                };
                ((FlowLayout) findViewByID_My(R.id.flowLayout)).setAdapter(flowLayoutAdapter);

                //确认按钮
                if (response.getTindent().getConfirm_text() != null) {
                    tv_confirm.setVisibility(View.VISIBLE);
                    tv_confirm.setText(response.getTindent().getConfirm_text().getName());
                } else {
                    tv_confirm.setVisibility(View.GONE);
                }

                //附加费
                if (response.getTindent().getConfirm_attach_data() != null) {
                    tv_fujiafei.setVisibility(View.VISIBLE);
                } else {
                    tv_fujiafei.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textView2:
                //添加附加费
                dialog = new BaseDialog(OrderDetailsActivity.this);
                dialog.contentView(R.layout.dialog_jiajifei)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView1 = dialog.findViewById(R.id.textView1);
                textView1.setText("添加附加费用");
                final EditText editText1 = dialog.findViewById(R.id.editText1);
                if (!model.getTindent().getOwner_fee().equals("") && !model.getTindent().getOwner_fee().equals("0.00")) {
                    editText1.setText(model.getTindent().getOwner_fee());
                } else {
                    editText1.setHint("请输入附加费用");
                }
                /*TextView textView3 = dialog.findViewById(R.id.textView3);
                textView3.setText("确认");*/
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!editText1.getText().toString().trim().equals("")) {
                            //增加
                            CommonUtil.hideSoftKeyboard_fragment(v, OrderDetailsActivity.this);
                            dialog.dismiss();
                            Map<String, String> params = new HashMap<>();
                            params.put("token", localUserInfo.getToken());
                            params.put("owner_fee", editText1.getText().toString().trim());
                            params.put("id", model.getTindent().getId());
                            params.put("type", "owner_add_attach");
                            RequestQuXiao(params, 2);

                        } else {
                            myToast("请输入与加急费用");
                        }
                    }
                });
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.textView3:
                //立即发布给附近所有司机
                showToast("立即发布给附近所有司机？", "确认", "取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Map<String, String> params = new HashMap<>();
                                params.put("token", localUserInfo.getToken());
//                                params.put("t_indent_confirm_id", model.getTindent().getConfirm_text().getId());
                                params.put("t_indent_id", model.getTindent().getId());
                                RequestPush(params);

                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                break;
            case R.id.textView8:
                //聊天
                Bundle bundle1 = new Bundle();
                bundle1.putString(EaseConstant.EXTRA_USER_ID, model.getTindent().getDriver_info().getHx_username());
                CommonUtil.gotoActivityWithData(this, ChatActivity.class, bundle1, false);
                break;
            case R.id.textView9:
                //打电话
                showToast("确认拨打 " + model.getTindent().getDriver_info().getMobile() + " 吗？", "确认", "取消",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                //创建打电话的意图
                                Intent intent = new Intent();
                                //设置拨打电话的动作
                                intent.setAction(Intent.ACTION_CALL);//直接拨出电话
//                                                                        intent.setAction(Intent.ACTION_DIAL);//只调用拨号界面，不拨出电话
                                //设置拨打电话的号码
                                intent.setData(Uri.parse("tel:" + model.getTindent().getDriver_info().getMobile()));
                                //开启打电话的意图
                                startActivity(intent);
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                break;
            case R.id.tv_shouqi:
                //收起
                isOpen = !isOpen;
                if (isOpen) {
                    tv_shouqi.setImageResource(R.mipmap.ic_down_black);
                    ll_hint3.setVisibility(View.VISIBLE);
                } else {
                    tv_shouqi.setImageResource(R.mipmap.ic_up_black);
                    ll_hint3.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_confirm:
                //确认按钮
                switch (model.getTindent().getConfirm_text().getOption()) {
                    case "confirm_shipment":
                        //确认装货
                        showToast("确认司机已装货吗？", "确认", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Map<String, String> params = new HashMap<>();
                                        params.put("token", localUserInfo.getToken());
                                        params.put("t_indent_confirm_id", model.getTindent().getConfirm_text().getId());
                                        params.put("t_indent_id", model.getTindent().getId());
                                        params.put("type", "1");//操作的类型1确认装货2提醒司机装货3确认卸货4附加费添加5附加费确认6转单确认7确认订单配送完毕
                                        RequestConfirm(params, 1);
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                        break;
                    case "confirm_disburden":
                        //确认卸货
                        showToast("确认司机已卸货吗？", "确认", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Map<String, String> params = new HashMap<>();
                                        params.put("token", localUserInfo.getToken());
                                        params.put("t_indent_confirm_id", model.getTindent().getConfirm_text().getId());
                                        params.put("t_indent_id", model.getTindent().getId());
                                        params.put("type", "3");//操作的类型1确认装货2提醒司机装货3确认卸货4附加费添加5附加费确认6转单确认7确认订单配送完毕
                                        RequestConfirm(params, 3);
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                        break;
                    case "confirm_attach":
                        //确认附加费
                        showToast("确认司机添加的附加费吗？", "确认", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Map<String, String> params = new HashMap<>();
                                        params.put("token", localUserInfo.getToken());
                                        params.put("t_indent_confirm_id", model.getTindent().getConfirm_text().getId());
                                        params.put("t_indent_id", model.getTindent().getId());
                                        params.put("type", "5");//操作的类型1确认装货2提醒司机装货3确认卸货4附加费添加5附加费确认6转单确认7确认订单配送完毕
                                        RequestConfirm(params, 5);
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                        break;
                    case "confirm_finish":
                        //订单完成
                        showToast("确认订单完成吗？", "确认", "取消",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        Map<String, String> params = new HashMap<>();
                                        params.put("token", localUserInfo.getToken());
                                        params.put("t_indent_confirm_id", model.getTindent().getConfirm_text().getId());
                                        params.put("t_indent_id", model.getTindent().getId());
                                        params.put("type", "7");//操作的类型1确认装货2提醒司机装货3确认卸货4附加费添加5附加费确认6转单确认7确认订单配送完毕
                                        RequestConfirm(params, 7);
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                        break;

                }
                break;
            case R.id.tv_fujiafei:
                //附加费
                dialog = new BaseDialog(OrderDetailsActivity.this);
                dialog.contentView(R.layout.dialog_fujiafei)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView textView2 = dialog.findViewById(R.id.textView2);
                textView2.setText("¥" + model.getTindent().getConfirm_attach_data().getMoney());
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(OrderDetailsActivity.this);
                recyclerView.setLayoutManager(mLinearLayoutManager);
                CommonAdapter<OrderDetailsModel.TindentBean.ConfirmAttachDataBean.DetailBean> mAdapter1 =
                        new CommonAdapter<OrderDetailsModel.TindentBean.ConfirmAttachDataBean.DetailBean>
                                (OrderDetailsActivity.this, R.layout.item_feemodel,
                                        model.getTindent().getConfirm_attach_data().getDetail()) {
                            @Override
                            protected void convert(ViewHolder holder, OrderDetailsModel.TindentBean.ConfirmAttachDataBean.DetailBean model, int position) {
                                holder.setText(R.id.textView1, model.getName());
                                holder.setText(R.id.textView2, "¥" + model.getMoney());
                            }
                        };
                recyclerView.setAdapter(mAdapter1);

                TextView textView3 = dialog.findViewById(R.id.textView3);
                TextView textView4 = dialog.findViewById(R.id.textView4);
                if (model.getTindent().getConfirm_attach_data().getNeed_pay() == 1) {
                    //需要支付
                    textView3.setText("稍后付款");
                    textView4.setText("现在付款");
                } else {
                    //不需要支付
                    textView3.setText("关闭");
                    textView4.setText("已支付");
                }
                dialog.findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getTindent().getConfirm_attach_data().getNeed_pay() == 1) {
                            //可支付
                            dialog.dismiss();
                            showToast("是否现在支付（余额支付）", "是", "否", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    showProgress(true, "正在支付，请稍后...");
                                    Map<String, String> params = new HashMap<>();
                                    params.put("token", localUserInfo.getToken());
                                    params.put("t_indent_confirm_id", model.getTindent().getConfirm_attach_data().getIdX());
                                    params.put("type", "5");//操作的类型1确认装货2提醒司机装货3确认卸货4附加费添加5附加费确认6转单确认7确认订单配送完毕
                                    RequestConfirm(params, 5);
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                dialog.findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.textView19:
                //费用明细
                dialog = new BaseDialog(OrderDetailsActivity.this);
                dialog.contentView(R.layout.dialog_list)
                        .layoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .animType(BaseDialog.AnimInType.CENTER)
                        .canceledOnTouchOutside(true)
                        .dimAmount(0.8f)
                        .show();
                TextView tv1 = dialog.findViewById(R.id.textView1);
                tv1.setText("费用明细");
                RecyclerView rv1 = dialog.findViewById(R.id.recyclerView);
                LinearLayoutManager mLinearLayoutManager1 = new LinearLayoutManager(OrderDetailsActivity.this);
                rv1.setLayoutManager(mLinearLayoutManager1);
                CommonAdapter<OrderDetailsModel.TindentBean.PriceDetailBean> ap1 =
                        new CommonAdapter<OrderDetailsModel.TindentBean.PriceDetailBean>
                                (OrderDetailsActivity.this, R.layout.item_feemodel,
                                        model.getTindent().getPrice_detail()) {
                            @Override
                            protected void convert(ViewHolder holder, OrderDetailsModel.TindentBean.PriceDetailBean model, int position) {
                                holder.setText(R.id.textView1, model.getTitle());
                                holder.setText(R.id.textView2, "¥" + model.getPrice());
                            }
                        };
                rv1.setAdapter(ap1);
                dialog.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;

            case R.id.tv_shishiwendu:
                CommonUtil.gotoActivity(OrderDetailsActivity.this, TemperatureActivity.class);
                break;
        }
    }

    //发布给附近司机
    private void RequestPush(Map<String, String> params) {
        OkHttpClientManager.postAsyn(OrderDetailsActivity.this, URLs.OrderDetails_Push, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(String response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>发布给附近司机" + response);
                showToast("已发布信息给附近司机");
            }
        }, false);
    }

    @Override
    protected void updateView() {
        titleView.setTitle("订单详情");
    }

    private void RequestConfirm(Map<String, String> params, int type) {
        OkHttpClientManager.postAsyn(OrderDetailsActivity.this, URLs.OrderDetails_Confirm, params, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    if (info.contains("余额不足")) {
                        showToast(info,
                                "取消", "去充值",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        CommonUtil.gotoActivity(OrderDetailsActivity.this, RechargeActivity.class, false);
                                    }
                                });
                    } else if (info.contains("认证")) {
                        showToast(info,
                                "取消", "去认证",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        CommonUtil.gotoActivity(OrderDetailsActivity.this, Auth_ShenFenZhengActivity.class, false);
                                    }
                                });
                    } else {
                        showToast(info);
                    }
                }
            }

            @Override
            public void onResponse(String response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>操作的类型1确认装货2提醒司机装货3确认卸货4附加费添加5附加费确认6转单确认7确认订单配送完毕" + response);
//                myToast("确认成功");
//                finish();
                JSONObject jObj;
                try {
                    jObj = new JSONObject(response);
                    /*JSONArray jsonArray = jObj.getJSONArray("data");
                    list = JSON.parseArray(jsonArray.toString(), Fragment1ListModel.class);
                    MyLogger.i(">>>>>>>" + list.size());*/
                    myToast(jObj.getString("msg"));
                    if (type == 7) {
                        //去评价
                        Bundle bundle = new Bundle();
                        bundle.putString("id", model.getTindent().getId());
                        CommonUtil.gotoActivityWithData(OrderDetailsActivity.this, AppraiseActivity.class, bundle, true);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                requestServer();
            }
        }, false);
    }

    private void RequestQuXiao(Map<String, String> params, int type) {
        OkHttpClientManager.postAsyn(OrderDetailsActivity.this, URLs.OrderDetails_QuXiao, params, new OkHttpClientManager.ResultCallback<OrderCancelModel>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(OrderCancelModel response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>取消" + response);
                if (type == 1) {
                    //取消订单
                    Bundle bundle = new Bundle();
                    bundle.putString("id", model.getTindent().getId());
                    bundle.putSerializable("model", response);
                    CommonUtil.gotoActivityWithData(OrderDetailsActivity.this, OrderCancelActivity.class, bundle, true);
                } else {
                    //添加附加费
                    myToast("添加附加费成功");
                    requestServer();
                }

            }
        }, false);
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(30 * 1000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.showMyLocation(true);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(this);

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
                for (int i = 0; i < model.getTindent().getAddr_list().size(); i++) {
                    if (i == 0) {
                        addMarker(1, model.getTindent().getAddr_list().get(i).getAddr_detail(),
                                model.getTindent().getAddr_list().get(i).getAddr(), pointList.get(i));//起点
                    } else if (i == (model.getTindent().getAddr_list().size() - 1)) {
                        addMarker(2, model.getTindent().getAddr_list().get(i).getAddr_detail(),
                                model.getTindent().getAddr_list().get(i).getAddr(), pointList.get(i));//终点
                    } else {
                        //途经点
                        addMarker(3, model.getTindent().getAddr_list().get(i).getAddr_detail(),
                                model.getTindent().getAddr_list().get(i).getAddr(), pointList.get(i));
                    }

                }
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
//        fromAndTo.setPlateNumber(model.getTindent().getCar_type_info().getCar_number());//车牌号，如A6BN05
//        fromAndTo.setPlateProvince("京");

        // 第一个参数表示路径规划的起点和终点，第二个参数表示计算路径的模式，第三个参数表示途经点，第四个参数货车大小 必填
        RouteSearch.TruckRouteQuery query = new RouteSearch.TruckRouteQuery(fromAndTo, mode, pointList, RouteSearch.TRUCK_SIZE_HEAVY);

        /*query.setTruckAxis(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_axis()));//轴数
        query.setTruckHeight(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_height()));//高 单位：米
        query.setTruckWidth(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_weight()));//宽度，单位：米
        query.setTruckLoad(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_load()));//最大载重，单位：吨
        query.setTruckWeight(Float.valueOf(model.getTindent().getCar_type_info().getVehicle_weight()));//载重*/

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
        } else if (rCode == 1901) {
            myToast("参数无效");

        } else if (rCode == 1904) {
            myToast("搜索失败,请检查网络连接！");
        } else if (rCode == 1002) {
            myToast("key验证无效！");
        } else {
            MyLogger.i("结果：" + rCode);
        }
    }

    /**
     * by moos on 2017/11/15
     * func:添加marker到地图上显示
     */
    BitmapDescriptor bitmapDescriptor;

    private void addMarker(int type, String s1, String s2, LatLonPoint point) {
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
                //途经点
                tv3.setText("途经点");
                tv3.setTextColor(getResources().getColor(R.color.black1));
                iv.setImageResource(R.mipmap.end);
                break;
        }
        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
        listener.markerIconLoadingFinished(markerView);

    }

    @Override
    public void onMyLocationChange(Location location) {
        MyLogger.i(">>>>>>>>我的位置：" + location.getLatitude());
        requestServer();//30秒刷新一次数据
//        lat = location.getLatitude();
//        lng = location.getLongitude();
//        requestServer();

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
