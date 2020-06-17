package com.example.a0912;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class GSG extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    RadioGroup two;
    EditText pa, money, stamonth, dollar, dollar2, number, endmonth;
    Button button, add;
    TextView text7, text6, text9, text10, text4, text8;
    RadioButton radio3, radio4, radio5;
    int itemIndex = 0;
    List<String> buy_cash;
    List<String> buy_cash2;
    private static final String url2 = "https://rate.bot.com.tw/xrt?Lang=zh-TW";
    private String UpdateTime;
    RateItem mRateItem;
    String TYPE;
    long month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsg);

        two = findViewById(R.id.two);
        pa = findViewById(R.id.pa);
        money = findViewById(R.id.money);
        stamonth = findViewById(R.id.stamonth);
        endmonth = findViewById(R.id.endmonth);
        dollar = findViewById(R.id.dollar);
        dollar2 = findViewById(R.id.dollar2);
        button = findViewById(R.id.button);
        add = findViewById(R.id.add);
        two.setOnCheckedChangeListener(this);
        pa = findViewById(R.id.pa);
        number = findViewById(R.id.number);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        radio5 = (RadioButton) findViewById(R.id.radio5);
        text7 = (TextView) findViewById(R.id.text7);
        text6 = (TextView) findViewById(R.id.text6);
        text9 = (TextView) findViewById(R.id.text9);
        text10 = (TextView) findViewById(R.id.text10);
        text4 = (TextView) findViewById(R.id.text4);
        text8 = (TextView) findViewById(R.id.text8);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        buy_cash = new ArrayList<String>();
        buy_cash2 = new ArrayList<String>();

        stamonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        stamonth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });

        endmonth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg2();
                    return true;
                }
                return false;
            }
        });

        endmonth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg2();
                }
            }
        });

        //建立Thread
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
                    Toast.makeText(GSG.this, "載入資料完成", Toast.LENGTH_SHORT).show();
                } else if (position == 20) {
                    itemIndex = position;
                    Toast.makeText(GSG.this, inter[itemIndex], Toast.LENGTH_SHORT).show();
                    text10.setText(inter[itemIndex]);
                    text4.setText(inter[itemIndex]);
                    text8.setText(inter[itemIndex]);
                    number.setText("1");
                } else {
                    itemIndex = position;
                    Toast.makeText(GSG.this, inter[itemIndex], Toast.LENGTH_SHORT).show();
                    text10.setText(inter[itemIndex]);
                    text4.setText(inter[itemIndex]);
                    text8.setText(inter[itemIndex]);
                    number.setText(buy_cash2.get(itemIndex));
                    new Thread(runnable).start();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pa2 = pa.getText().toString();
                String number1 = money.getText().toString();
                String mouth1 = stamonth.getText().toString();
                String mouth2 = endmonth.getText().toString();

                if ("".equals(money.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(pa.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(stamonth.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(endmonth.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    Date d1 = sdf.parse(mouth1);
                    Date d2 = sdf.parse(mouth2);
                    long diff = d2.getTime() - d1.getTime();
                    month = diff / (1000 * 60 * 60 * 24) / 30;
                    double p = Double.valueOf(pa2).doubleValue() / 1200; //%數
                    int m = Integer.parseInt(String.valueOf(month));//月份
                    int z = Integer.parseInt(number1); //金額
                    double last; //最後金額
                    double rate_t = 1;

                    if (two.getCheckedRadioButtonId() == R.id.radio3) {
                        TYPE = "零存整付";
                        double i;
                        for (i = 0; i < m; i++) {
                            rate_t *= (1 + p);
                        }
                        last = z * (rate_t - 1) / p * (1 + p);
                        String last2 = String.valueOf(Math.round(last));
                        String last3 = String.valueOf(Math.round(last) - z * m);
                        GSG.this.dollar2.setText(last2);
                        GSG.this.dollar.setText(last3);
                    }
                    if (two.getCheckedRadioButtonId() == R.id.radio4) {
                        TYPE = "整存整付";
                        double interest = Math.pow(1 + p, m);
                        double whole = Math.round(z * interest);
                        String last2 = String.valueOf(whole);
                        String last3 = String.valueOf(whole - z);
                        GSG.this.dollar.setText(last3);
                        GSG.this.dollar2.setText(last2);
                    } else if (two.getCheckedRadioButtonId() == R.id.radio5) {
                        TYPE = "存本取息";
                        double interest = z * p;
                        double end = Math.round(interest) * m;
                        String last2 = String.valueOf(end);
                        String last3 = String.valueOf(end + z);
                        GSG.this.dollar.setText(last2);
                        GSG.this.dollar2.setText(last3);
                    }
                } catch (Exception e) {
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(money.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(pa.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(stamonth.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(endmonth.getText().toString())) {
                    Toast.makeText(GSG.this, "數值不能為空", Toast.LENGTH_LONG).show();
                    return;
                }

                if (itemIndex == 20) {
                    if (two.getCheckedRadioButtonId() == R.id.radio3) {
                        Bundle bundle = new Bundle();
                        String papa = pa.getText().toString();
                        String s = money.getText().toString();
                        long i = Integer.valueOf(s).intValue() * month;
                        String beforem = String.valueOf(i);
                        String mmonth = stamonth.getText().toString();
                        String mmonth2 = endmonth.getText().toString();
                        String afterm = dollar2.getText().toString();
                        String type = TYPE;
                        Intent it = new Intent(GSG.this, money_edit.class)
                                .putExtra("beforem", beforem)
                                .putExtra("papa", papa)
                                .putExtra("afterm", afterm)
                                .putExtra("mmonth", mmonth)
                                .putExtra("type", type)
                                .putExtra("mmonth2", mmonth2);
                        startActivity(it);
                    } else {
                        Bundle bundle = new Bundle();
                        String beforem = money.getText().toString();
                        String papa = pa.getText().toString();
                        String afterm = dollar2.getText().toString();
                        String mmonth = stamonth.getText().toString();
                        String mmonth2 = endmonth.getText().toString();
                        String type = TYPE;
                        Intent it = new Intent(GSG.this, money_edit.class)
                                .putExtra("beforem", beforem)
                                .putExtra("papa", papa)
                                .putExtra("afterm", afterm)
                                .putExtra("mmonth", mmonth)
                                .putExtra("type", type)
                                .putExtra("mmonth2", mmonth2);
                        startActivity(it);
                    }
                } else {
                    if (two.getCheckedRadioButtonId() == R.id.radio3) {
                        Bundle bundle = new Bundle();
                        String name = inter[itemIndex];
                        String number2 = number.getText().toString();
                        String papa = pa.getText().toString();
                        String type = TYPE;
                        String s = money.getText().toString();
                        int i = Integer.valueOf(s).intValue() * 12;
                        String beforem = String.valueOf(i);
                        String mmonth = stamonth.getText().toString();
                        String mmonth2 = endmonth.getText().toString();
                        String afterm = dollar2.getText().toString();
                        Intent it = new Intent(GSG.this, money2_edit.class)
                                .putExtra("beforem", beforem)
                                .putExtra("papa", papa)
                                .putExtra("afterm", afterm)
                                .putExtra("mmonth", mmonth)
                                .putExtra("mmonth2", mmonth2)
                                .putExtra("number2", number2)
                                .putExtra("type", type)
                                .putExtra("name", name);
                        startActivity(it);
                    } else {
                        Bundle bundle = new Bundle();
                        String name = inter[itemIndex];
                        String number2 = number.getText().toString();
                        String papa = pa.getText().toString();
                        String beforem = money.getText().toString();
                        String mmonth = stamonth.getText().toString();
                        String mmonth2 = endmonth.getText().toString();
                        String afterm = dollar2.getText().toString();
                        String type = TYPE;
                        Intent it = new Intent(GSG.this, money2_edit.class)
                                .putExtra("beforem", beforem)
                                .putExtra("papa", papa)
                                .putExtra("afterm", afterm)
                                .putExtra("mmonth", mmonth)
                                .putExtra("mmonth2", mmonth2)
                                .putExtra("type", type)
                                .putExtra("number2", number2)
                                .putExtra("name", name);
                        startActivity(it);
                    }
                }
            }
        });
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
        AlertDialog.Builder ad = new AlertDialog.Builder(GSG.this);
        ad.setTitle("返回");
        ad.setMessage("確定要離開此試算嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
                // TODO Auto-generated method stub
                GSG.this.finish();//關閉activity
            }
        });
        ad.setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                //不退出不用執行任何操作
            }
        });
        ad.show();//顯示對話框
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(GSG.this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GSG.this.stamonth.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    protected void showDatePickDlg2() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(GSG.this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                GSG.this.endmonth.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (itemIndex == 20) {
            if (radio3.getId() == checkedId) {
                text7.setText("每月存多少");
            } else {
                text7.setText("共存多少");
            }
        } else if (itemIndex != 20 && itemIndex != 0) {
            if (radio3.getId() == checkedId) {
                text7.setText("每月存多少");
            } else if (radio4.getId() == checkedId) {
                new AlertDialog.Builder(GSG.this)
                        .setTitle("外幣沒有此款定存")
                        .setMessage("請選擇其他方式")
                        .setPositiveButton("了解了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                Toast.makeText(GSG.this, "外幣沒有此款定存,請選擇其他方式", Toast.LENGTH_SHORT).show();
                            }
                        }).show(); //要有這個才會顯示喔~~~~~~~~~
            } else {
                text7.setText("共存多少");
            }
        }
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
            intent.setClass(GSG.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_irr) {
            Intent intent = new Intent();
            intent.setClass(GSG.this, Irr.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
}

