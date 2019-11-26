package com.delivery.xianxian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.model.RechargeModel;

import java.util.List;

/**
 * Created by zyz on 2016/7/6.
 * Email：1125213018@qq.com
 * description：pop adapter
 */
public class Recharge_GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<RechargeModel.MoneyListBean> list;
    private int selectIndex = 0;

    public Recharge_GridViewAdapter(Context context, List<RechargeModel.MoneyListBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectItem(int selectItem) {
        this.selectIndex = selectItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recharge_gridview, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
            holder.linearLayout1 = (LinearLayout) convertView.findViewById(R.id.linearLayout1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView1.setText("充值¥" + list.get(position).getPrice());
        holder.textView2.setText("送¥" + list.get(position).getAward());

        if (selectIndex == position) {
            holder.textView1.setTextColor(context.getResources().getColor(R.color.blue));
            holder.textView2.setTextColor(context.getResources().getColor(R.color.blue));
            holder.linearLayout1.setBackgroundResource(R.drawable.yuanjiaobiankuang_5_lanse);
        } else {
            holder.textView1.setTextColor(context.getResources().getColor(R.color.black1));
            holder.textView2.setTextColor(context.getResources().getColor(R.color.black1));
            holder.linearLayout1.setBackgroundResource(R.drawable.yuanjiaobiankuang_5_huise);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textView1, textView2;
        LinearLayout linearLayout1;
    }
}
