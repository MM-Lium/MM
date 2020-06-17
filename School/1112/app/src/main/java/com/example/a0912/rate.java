package com.example.a0912;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class rate extends Fragment {

    Button button;

    private ListView mListView;
    private TextView UpdateTimeText;
    List<String> buy_cash;
    List<String> buy_cash2;

    private static final String url2 = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
    private String UpdateTime;
    private List RateList;
    RateItem mRateItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_rate, container, false);
        Toast.makeText(getActivity(), "載入資料中", Toast.LENGTH_SHORT).show();

        buy_cash = new ArrayList<String>();
        buy_cash2 = new ArrayList<String>();
        mListView = (ListView) rootView.findViewById(R.id.listView);
        UpdateTimeText = (TextView) rootView.findViewById(R.id.UpdateTimeHeader);

        new Thread(runnable).start();
        return rootView;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect(url2).get();
                RateList = new ArrayList();
                int i = 0;
                for (Element title : doc.select("div[class=\"visible-phone print_hide\"]")) {
                    mRateItem = new RateItem();
                    //取得幣別並存入
                    mRateItem.setCurrency(title.text());
                    buy_cash.add(title.text());

                    //匯率為一次四筆所以一次抓出並存入
                    if (i < doc.select("td[class=\"text-right display_none_print_show print_width\"]").size()) {
                        //利用eq()可以指定為第幾筆資料
                        mRateItem.setCashBuyRate(doc.select("td[class=\"text-right display_none_print_show print_width\"]").eq(i).text());
                        mRateItem.setCashSoldRate(doc.select("td[class=\"text-right display_none_print_show print_width\"]").eq(i + 1).text());
                        mRateItem.setSpotBuyRate(doc.select("td[class=\"text-right display_none_print_show print_width\"]").eq(i + 2).text());
                        mRateItem.setSpotSoldRate(doc.select("td[class=\"text-right display_none_print_show print_width\"]").eq(i + 3).text());
                        buy_cash2.add(doc.select("td[class=\"text-right display_none_print_show print_width\"]").eq(i).text());
                        i += 4;
                    }

                    RateList.add(mRateItem);
                }
                //更新時間的抓取
                String Temp = doc.select("span[class=\"time\"]").text();
                //去處裡字串
                UpdateTime = Temp.substring(11);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //利用handler去更新View
            handler.sendEmptyMessage(0);
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setListViewAdapter();
        }
    };

    public void setListViewAdapter() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        UpdateTimeText.setText("更新時間：" + UpdateTime);
        mListView.setAdapter(new ListViewAdapter(getActivity(), RateList));
    }

}
