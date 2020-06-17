package com.example.a0912;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Irr extends AppCompatActivity {

    EditText year;
    EditText dollar;
    EditText takeyear;
    EditText takedollar;
    EditText Irr;
    EditText money;
    Button button, add;
    double period, times, premium, expired;
    String type;
    String number;
    String curreny;
    int itemIndex = 0;
    List<String> buy_cash;
    List<String> buy_cash2;
    private static final String url2 = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
    private String UpdateTime;
    RateItem mRateItem;
    TextView textView32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irr);

        year = findViewById(R.id.year);
        dollar = findViewById(R.id.dollar);
        takeyear = findViewById(R.id.takeyear);
        takedollar = findViewById(R.id.takedollar);
        Irr = findViewById(R.id.Irr);
        money = findViewById(R.id.money);
        button = findViewById(R.id.button);
        add = findViewById(R.id.add);
        textView32 = findViewById(R.id.textView32);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        buy_cash = new ArrayList<String>();
        buy_cash2 = new ArrayList<String>();
        new Thread(runnable).start();

        final String[] inter = {"請選擇", "美金 (USD)", "港幣 (HKD)", "英鎊 (GBP)", "澳幣 (AUD)", "加拿大幣 (CAD)", "新加坡幣 (SGD)",
                "瑞士法郎 (CHF)", "日圓 (JPY)", "南非幣 (ZAR)", "瑞典幣 (SEK)", "紐元 (NZD)", "泰幣 (THB)", "菲國比索 (PHP)",
                "印尼幣 (IDR)", "歐元 (EUR)", "韓元 (KRW)", "越南盾 (VND)", "馬來幣 (MYR)", "人民幣 (CNY)", "新台幣 (TWD)"};

        ArrayAdapter<String> itemlist = new ArrayAdapter<>(this, R.layout.myspinner,
                inter);
        spinner.setAdapter(itemlist);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemIndex = position;
                if (buy_cash.size() == 0) {
                    Toast.makeText(Irr.this, "載入資料完成", Toast.LENGTH_SHORT).show();
                } else if (position == 20) {
                    itemIndex = position;
                    curreny = inter[itemIndex];
                    textView32.setText(inter[itemIndex]);
                    Toast.makeText(Irr.this, inter[itemIndex], Toast.LENGTH_SHORT).show();
                    number = "1";
                } else {
                    itemIndex = position;
                    curreny = inter[itemIndex];
                    textView32.setText(inter[itemIndex]);
                    Toast.makeText(Irr.this, inter[itemIndex], Toast.LENGTH_SHORT).show();
                    number = buy_cash2.get(itemIndex);
                    new Thread(runnable).start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        DecimalFormat df = new DecimalFormat("#.##");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat df = new DecimalFormat("0.00");

                String a = year.getText().toString();
                String b = dollar.getText().toString();
                String c = takeyear.getText().toString();
                String p = takedollar.getText().toString();

                if ("".equals(year.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(dollar.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(takeyear.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(takedollar.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                period = Integer.parseInt(a);//年份
                premium = Integer.parseInt(b);//金額
                times = Integer.parseInt(c);//領回年度
                expired = Integer.parseInt(p);//領回錢
                double remuneration = expired - (period * premium); // 淨報酬
                double irr = 0.0; // IRR起始
                double result = 0.0; //
                double fraction = (expired / premium);
                money.setText(String.valueOf(Math.round(remuneration)));

                if (period == 1 && remuneration >= 0) { // 躉繳判斷
                    while (fraction > result) {

                        result = 0.0; // 歸0避免壘加
                        result += Math.pow(1 + irr, times);
                        irr += 0.00001;
                    }
                    Irr.setText(String.valueOf(df.format(irr * 100)));
                    type = "躉繳";
                } else if (period == 1 && remuneration < 0) { // 躉繳負值
                    while (fraction > result) {
                        result = 0.0;
                        result += Math.pow(irr, times);
                        irr += 0.00001;
                    }
                    Irr.setText(String.valueOf(df.format((irr - 1) * 100)));
                    type = "躉繳";
                } else if (times > 1 && times >= period && remuneration >= 0) { // 正常情況下的IRR(期滿領回或期滿過後才領回)
                    while (fraction > result) {
                        result = 0.0;
                        for (double i = times; i > (times - period); i--) {
                            result += Math.pow(1 + irr, i);
                        }
                        irr += 0.00001;
                    }
                    Irr.setText(String.valueOf(df.format(irr * 100)));
                    type = "年繳";
                } else if (times > 1 && times >= period && remuneration < 0) { // 沒有提早+淨報酬為負
                    while (fraction > result) {
                        result = 0.0;
                        for (double i = times; i > (times - period); i--) {
                            result += Math.pow(irr, i);
                        }
                        irr += 0.00001;
                    }
                    type = "年繳";
                    Irr.setText(String.valueOf(df.format((irr - 1) * 100)));
                } else if (times > 1 && times < period && (times * premium) - expired <= 0) { // 提早領回，淨報酬為正
                    while (fraction > result) {
                        result = 0.0;
                        for (double i = times; i > 0; i--) {
                            result += Math.pow(1 + irr, i);
                        }
                        irr += 0.00001;
                    }
                    type = "年繳";
                    Irr.setText(String.valueOf(df.format(irr * 100)));
                } else if (times > 1 && times < period && (times * premium) - expired > 0) { // 提早領回，淨報酬為負
                    while (fraction > result) {
                        result = 0.0;
                        for (double i = times; i > 0; i--) {
                            result += Math.pow(irr, i);
                        }
                        irr += 0.00001;
                    }
                    type = "年繳";
                    Irr.setText(String.valueOf(df.format((irr - 1) * 100)));
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(year.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(dollar.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(takeyear.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(takedollar.getText().toString())) {
                    Toast.makeText(Irr.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                AlertDialog.Builder ad = new AlertDialog.Builder(Irr.this);
                ad.setTitle("提醒");
                ad.setMessage("儲蓄險IRR一定要高於定存利率呦~否則是會虧本的");
                ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
                    public void onClick(DialogInterface dialog, int i) {
                        Bundle bundle = new Bundle();
                        String a = year.getText().toString();
                        String b = dollar.getText().toString();
                        String c = takeyear.getText().toString();
                        String p = takedollar.getText().toString();
                        String irr = Irr.getText().toString();
                        String expired = takedollar.getText().toString();
                        period = Integer.parseInt(a);//年份
                        premium = Integer.parseInt(b);//金額
                        double total = period * premium;

                        String t = String.valueOf(total);
                        String thetype = type;
                        String number2 = number;
                        String curreny2 = curreny;
                        Intent it = new Intent(Irr.this, money3_edit.class)
                                .putExtra("t", t)
                                .putExtra("b", b)
                                .putExtra("c", c)
                                .putExtra("p", p)
                                .putExtra("irr", irr)
                                .putExtra("thetype", thetype)
                                .putExtra("curreny2", curreny2)
                                .putExtra("number2", number2)
                                .putExtra("expired", expired);
                        startActivity(it);
                    }
                });
                ad.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        //不退出不用執行任何操作
                    }
                });
                ad.show();//顯示對話框
            }
        });
    }

    Runnable runnable = new Runnable() { //專案管理裡的工作包，說穿了就是要做的事情啦
        @Override
        public void run() {
            try {
                Document doc = Jsoup.connect(url2).get();
                int i = 0;
                buy_cash.add("請選擇");
                buy_cash2.add("請選擇");
                for (Element title : doc.select("div[class=\"visible-phone print_hide\"]")) {
                    mRateItem = new RateItem();
                    //取得幣別並存入
                    mRateItem.setCurrency(title.text());
                    buy_cash.add(title.text());
                    //匯率為一次四筆所以一次抓出並存入
                    if (i < doc.select("td[class=\"rate-content-cash text-right print_hide\"]").size()) {
                        //利用eq()可以指定為第幾筆資料
                        mRateItem.setCashBuyRate("55");
                        mRateItem.setCashBuyRate(doc.select("td[class=\"rate-content-cash text-right print_hide\"]").eq(i).text());
                        buy_cash2.add(doc.select("td[class=\"rate-content-cash text-right print_hide\"]").eq(i).text());
                        i = i + 2;
                    }
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
    @SuppressLint("HandlerLeak")  //一個服務的窗口
            Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setListViewAdapter();

        }
    };
    private TextView UpdateTimeText;

    public void setListViewAdapter() {
        LayoutInflater inflater = LayoutInflater.from(this);
        //include的TextView直接find就能找到了不需要透過inflater建立View
        UpdateTimeText = (TextView) findViewById(R.id.UpdateTimeHeader);
        UpdateTimeText.setText("更新時間：" + UpdateTime);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            ConfirmExit();//按返回鍵，則執行退出確認
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {//退出確認
        AlertDialog.Builder ad = new AlertDialog.Builder(Irr.this);
        ad.setTitle("返回");
        ad.setMessage("確定要離開此試算嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                Irr.this.finish();//關閉activity
            }
        });
        ad.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//顯示對話框
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(Irr.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.gsg) {
            Intent intent = new Intent();
            intent.setClass(Irr.this, GSG.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
