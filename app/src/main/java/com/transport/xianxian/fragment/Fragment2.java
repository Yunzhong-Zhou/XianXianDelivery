package com.transport.xianxian.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cy.cyflowlayoutlibrary.FlowLayout;
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter;
import com.hyphenate.easeui.EaseConstant;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.transport.xianxian.R;
import com.transport.xianxian.activity.ChatActivity;
import com.transport.xianxian.activity.MainActivity;
import com.transport.xianxian.base.BaseFragment;
import com.transport.xianxian.model.Fragment2Model;
import com.transport.xianxian.net.OkHttpClientManager;
import com.transport.xianxian.net.URLs;
import com.transport.xianxian.utils.CommonUtil;
import com.transport.xianxian.utils.MyLogger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by fafukeji01 on 2016/1/6.
 * 订单
 */
public class Fragment2 extends BaseFragment {
    int page = 1;
    private RecyclerView recyclerView;
    List<Fragment2Model> list1 = new ArrayList<>();
    CommonAdapter<Fragment2Model> mAdapter1;

    List<Fragment2Model> list2 = new ArrayList<>();
    CommonAdapter<Fragment2Model> mAdapter2;

    List<Fragment2Model> list3 = new ArrayList<>();
    CommonAdapter<Fragment2Model> mAdapter3;

    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private TextView textView1, textView2, textView3;
    private View view1, view2, view3;
    int type = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        StatusBarUtil.setTransparent(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        /*if (MainActivity.item == 1) {
            requestServer();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.item == 1) {
            requestServer();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (MainActivity.item == 1) {
            requestServer();
        }
    }

    @Override
    protected void initView(View view) {
        //刷新
        setSpringViewMore(true);//不需要加载更多
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                String string = "?page=" + page//当前页号
                        + "&count=" + "10"//页面行数
                        + "&token=" + localUserInfo.getToken();
                Request(string);
            }

            @Override
            public void onLoadmore() {
                page++;
                String string = "?page=" + page//当前页号
                        + "&count=" + "10"//页面行数
                        + "&token=" + localUserInfo.getToken();
                Request(string);
            }
        });

        recyclerView = findViewByID_My(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);

        linearLayout1 = findViewByID_My(R.id.linearLayout1);
        linearLayout2 = findViewByID_My(R.id.linearLayout2);
        linearLayout3 = findViewByID_My(R.id.linearLayout3);

        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);

        textView1 = findViewByID_My(R.id.textView1);
        textView2 = findViewByID_My(R.id.textView2);
        textView3 = findViewByID_My(R.id.textView3);

        view1 = findViewByID_My(R.id.view1);
        view2 = findViewByID_My(R.id.view2);
        view3 = findViewByID_My(R.id.view3);


        for (int i = 0; i < 10; i++) {
            list1.add(new Fragment2Model());
        }
        mAdapter1 = new CommonAdapter<Fragment2Model>
                (getActivity(), R.layout.item_fragment2, list1) {
            @Override
            protected void convert(ViewHolder holder, Fragment2Model model, int position) {
                        /*holder.setText(R.id.textView1, model.getMember_nickname());
                        holder.setText(R.id.textView2, model.getMoney() + getString(R.string.app_ge));
                        holder.setText(R.id.textView3, model.getShow_created_at());
                        ImageView imageView1 = holder.getView(R.id.imageView1);
                        if (!model.getMember_head().equals(""))
                            Glide.with(getActivity())
                                    .load(IMGHOST + model.getMember_head())
                                    .centerCrop()
//                                    .placeholder(R.mipmap.headimg)//加载站位图
//                                    .error(R.mipmap.headimg)//加载失败
                                    .into(imageView1);//加载图片
                        else
                            imageView1.setImageResource(R.mipmap.headimg);*/

                //标签
                FlowLayoutAdapter<String> flowLayoutAdapter;
                List<String> list = new ArrayList<>();
                list.add("专车");
                list.add("6吨");
                list.add("15-20℃恒温");
                flowLayoutAdapter = new FlowLayoutAdapter<String>(list) {
                    @Override
                    public void bindDataToView(FlowLayoutAdapter.ViewHolder holder, int position, String bean) {
//                                holder.setText(R.id.tv,bean);
                        TextView tv = holder.getView(R.id.tv);
                        tv.setText(bean);
                                /*if (position == 0){
                                    tv.setBackgroundResource(R.drawable.yuanjiao_3_lanse);
                                }else {
                                    tv.setBackgroundResource(R.drawable.yuanjiao_3_huise);
                                }*/
                    }

                    @Override
                    public void onItemClick(int position, String bean) {

//                                showToast("点击" + position);
                    }

                    @Override
                    public int getItemLayoutID(int position, String bean) {
                        return R.layout.item_flowlayout;
                    }
                };
                ((FlowLayout) holder.getView(R.id.flowLayout1)).setAdapter(flowLayoutAdapter);
                ((FlowLayout) holder.getView(R.id.flowLayout2)).setAdapter(flowLayoutAdapter);

                //去聊天
                holder.getView(R.id.iv_xinxi).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(EaseConstant.EXTRA_USER_ID, "18306043086");
                        CommonUtil.gotoActivityWithData(getActivity(), ChatActivity.class, bundle, false);
                    }
                });
            }
        };

        recyclerView.setAdapter(mAdapter1);

    }

    @Override
    protected void initData() {
//        requestServer();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearLayout1:
                type = 1;
                changeUI();
                break;
            case R.id.linearLayout2:
                type = 2;
                changeUI();
                break;
            case R.id.linearLayout3:
                type = 3;
                changeUI();
                break;
        }
    }

    private void changeUI() {
        switch (type) {
            case 1:
                textView1.setTextColor(getResources().getColor(R.color.blue));
                textView2.setTextColor(getResources().getColor(R.color.black2));
                textView3.setTextColor(getResources().getColor(R.color.black2));
                view1.setVisibility(View.VISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);

                /*if (list1.size() > 0) {
                    showContentPage();
                    recyclerView.setAdapter(adapter1);
                    adapter1.notifyDataSetChanged();
                } else {
                    showEmptyPage();//空数据
                }*/

                break;
            case 2:
                textView1.setTextColor(getResources().getColor(R.color.black2));
                textView2.setTextColor(getResources().getColor(R.color.blue));
                textView3.setTextColor(getResources().getColor(R.color.black2));

                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.VISIBLE);
                view3.setVisibility(View.INVISIBLE);

                /*if (list2.size() > 0) {
                    showContentPage();
                    recyclerView.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                } else {
                    showEmptyPage();//空数据
                }*/
                break;
            case 3:
                textView1.setTextColor(getResources().getColor(R.color.black2));
                textView2.setTextColor(getResources().getColor(R.color.black2));
                textView3.setTextColor(getResources().getColor(R.color.blue));
                view1.setVisibility(View.INVISIBLE);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.VISIBLE);
                /*if (list3.size() > 0) {
                    showContentPage();
                    recyclerView.setAdapter(adapter3);
                    adapter3.notifyDataSetChanged();
                } else {
                    showEmptyPage();//空数据
                }*/
                break;
            default:
                break;
        }

    }

    @Override
    protected void updateView() {

    }

    @Override
    public void requestServer() {
        super.requestServer();
//        this.showLoadingPage();

        showProgress(true, getString(R.string.app_loading));
        String string = "?page=" + page//当前页号
                + "&count=" + "10"//页面行数
                + "&token=" + localUserInfo.getToken();
        Request(string);
    }

    private void Request(String string) {
        OkHttpClientManager.getAsyn(getActivity(), URLs.Fragment2 + string, new OkHttpClientManager.ResultCallback<Fragment2Model>() {
            @Override
            public void onError(Request request, String info, Exception e) {
//                showErrorPage();
                hideProgress();
                if (!info.equals("")) {
                    myToast(info);
                }
            }

            @Override
            public void onResponse(final Fragment2Model response) {
//                showContentPage();
                hideProgress();
                MyLogger.i(">>>>>>>>>订单" + response);

            }
        });
    }

}
