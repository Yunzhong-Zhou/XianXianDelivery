package com.delivery.xianxian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivery.xianxian.R;
import com.delivery.xianxian.model.TemperatureModel;

import java.util.List;


/**
 * Created by zyz on 2016/7/6.
 * Email：1125213018@qq.com
 * description：优惠券 adapter
 */
public class YouHuiQuanAdapter extends BaseAdapter {
    private Context context;
    private List<TemperatureModel.CouponListBean> list;
    private int selectIndex = 0;

    public YouHuiQuanAdapter(Context context, List<TemperatureModel.CouponListBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_youhuiquan, null);
            holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
//            holder.textView3 = (TextView) convertView.findViewById(R.id.textView3);

            holder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView1.setText(list.get(position).getTitle());
//        holder.textView2.setText(list.get(position).getTemperature());
//        holder.textView3.setText("¥ "+list.get(position).getPrice());

        if (selectIndex == position) {
            holder.imageView1.setImageResource(R.mipmap.ic_gouxuan);
        } else {
            holder.imageView1.setImageResource(R.mipmap.ic_weigouxuan);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView textView1,textView2,textView3;
        ImageView imageView1;
    }
}
