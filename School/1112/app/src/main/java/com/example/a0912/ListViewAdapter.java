package com.example.a0912;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private List RateLists;

    public ListViewAdapter(Context mContext, List RateList) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.RateLists = RateList;
    }

    @Override
    public int getCount() {
        return RateLists.size();
    }

    @Override
    public Object getItem(int i) {
        return RateLists.get(i);
    }

    private static class ViewHolder {
        TextView CurrencyText;
        TextView CashBuyText;
        TextView CashSoldText;
        TextView SpotBuyText;
        TextView SpotSoldText;

        public ViewHolder(TextView CurrencyText, TextView CashBuyText, TextView CashSoldText, TextView SpotBuyText, TextView SpotSoldText) {
            this.CurrencyText = CurrencyText;
            this.CashBuyText = CashBuyText;
            this.CashSoldText = CashSoldText;
            this.SpotBuyText = SpotBuyText;
            this.SpotSoldText = SpotSoldText;
        }
    }

    @Override
    public long getItemId(int i) {
        return RateLists.indexOf(i);
    }

    @Override
    public View getView(int position, View ConvertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (ConvertView == null) {
            ConvertView = inflater.inflate(R.layout.listview_custom, viewGroup, false);
            holder = new ViewHolder(
                    (TextView) ConvertView.findViewById(R.id.CurrencyTextView),
                    (TextView) ConvertView.findViewById(R.id.CashBuyTextView),
                    (TextView) ConvertView.findViewById(R.id.CashSoldTextView),
                    (TextView) ConvertView.findViewById(R.id.SpotBuyTextView),
                    (TextView) ConvertView.findViewById(R.id.SpotSoldTextView)
            );
            //保存狀態避免一直重新建立還要find id
            ConvertView.setTag(holder);

        } else {
            //取出狀態
            holder = (ViewHolder) ConvertView.getTag();
        }
        RateItem mRateItem = (RateItem) getItem(position);
        holder.CurrencyText.setText(mRateItem.getCurrency());
        holder.CashBuyText.setText(mRateItem.getCashBuyRate());
        holder.CashSoldText.setText(mRateItem.getCashSoldRate());
        holder.SpotBuyText.setText(mRateItem.getSpotBuyRate());
        holder.SpotSoldText.setText(mRateItem.getSpotSoldRate());
        return ConvertView;
    }
}




