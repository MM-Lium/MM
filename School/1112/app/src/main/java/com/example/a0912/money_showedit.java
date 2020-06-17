package com.example.a0912;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class money_showedit extends AppCompatActivity {

    private Button btnSave, button2;
    private Button btnCancel;
    private TextView showTime, type, textView3;
    private TextView showYear;
    private EditText showContent;
    private EditText showTitle;
    private EditText showMoney;
    private EditText showDay, showDay2;
    private EditText showPa;
    private EditText showlLastmoney;
    private Values value;
    DBService myDb;
    String showDayy;
    long month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_showedit);

        init();

        if (showTime.getText().length() == 0)
            showTime.setText(getTime());
        if (showYear.getText().length() == 0)
            showYear.setText(getYear());

        showDay2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        showDay2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });
    }

    public void init() {

        myDb = new DBService(this);
        btnCancel = findViewById(R.id.show_cancel);
        btnSave = findViewById(R.id.show_save);
        showTime = findViewById(R.id.show_time);
        showTitle = findViewById(R.id.show_title);
        showYear = findViewById(R.id.show_year);
        showMoney = findViewById(R.id.show_money);
        showDay = findViewById(R.id.show_day);
        showDay2 = findViewById(R.id.show_day2);
        showPa = findViewById(R.id.show_pa);
        showlLastmoney = findViewById(R.id.show_lastmoney);
        showContent = findViewById(R.id.show_content);
        button2 = findViewById(R.id.button2);
        type = findViewById(R.id.type);
        textView3 = findViewById(R.id.textView3);

        Intent intent = this.getIntent();
        if (intent != null) {
            value = new Values();
            value.setMoney(intent.getStringExtra(DBService.MONEY));
            value.setMoney2(intent.getStringExtra(DBService.LASTMONEY));
            value.setDay(intent.getStringExtra(DBService.DAY));
            value.setDay2(intent.getStringExtra(DBService.DAY2));
            value.setPa(intent.getStringExtra(DBService.PAPA));
            value.settype(intent.getStringExtra(DBService.TYPE));
            value.setTitle(intent.getStringExtra(DBService.TITLE));
            value.setContent(intent.getStringExtra(DBService.CONTENT));
            value.setId(Integer.valueOf(intent.getStringExtra(DBService.ID)));

            showTitle.setText(value.getTitle());
            showMoney.setText(value.getMoney());
            showDay.setText(value.getDay());
            showDay2.setText(value.getDay2());
            type.setText(value.gettype());
            showPa.setText(value.getPa());
            showlLastmoney.setText(value.getMoney2());
            showContent.setText(value.getContent());
            showDayy = value.getDay2();

        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();

                String content = showContent.getText().toString();
                String title = showTitle.getText().toString();
                String day = showDay.getText().toString();
                String day2 = showDay2.getText().toString();
                String money = showMoney.getText().toString();
                String money2 = showlLastmoney.getText().toString();
                String papa = showPa.getText().toString();
                String type1 = type.getText().toString();
                values.put(DBService.TIME, getTime());
                values.put(DBService.YEAR, getYear());
                values.put(DBService.TITLE, title);
                values.put(DBService.CONTENT, content);
                values.put(DBService.DAY, day);
                values.put(DBService.DAY2, day2);
                values.put(DBService.MONEY, money);
                values.put(DBService.LASTMONEY, money2);
                values.put(DBService.PAPA, papa);
                values.put(DBService.TYPE, type1);

                db.update(DBService.TABLE_1, values, DBService.ID + "=?", new String[]{value.getId().toString()});
                Toast.makeText(money_showedit.this, "修改成功", Toast.LENGTH_LONG).show();
                db.close();

                Intent intent = new Intent(money_showedit.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = showMoney.getText().toString();
                final String pa = showPa.getText().toString();
                final String start = showDay.getText().toString();
                final String end = showDay2.getText().toString();

                if (showDay2.getText().toString().equals(showDayy)) {
                    new AlertDialog.Builder(money_showedit.this)
                            .setTitle("請修改到期日")
                            .setMessage("修改到期日，重新計算金額" +
                                    "如要續約，因金額計算方式不同，請新建一份資料")
                            .setPositiveButton("了解了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(money_showedit.this, "修改到期日，重新計算金額", Toast.LENGTH_SHORT).show();
                                }
                            }).show(); //要有這個才會顯示喔~~~~~~~~~
                } else if (showDay2.getText().toString() != showDayy) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        Date d1 = sdf.parse(start);//到期
                        Date d3 = sdf.parse(end);//到期
                        long diff2 = d3.getTime() - d1.getTime();
                        long month2 = diff2 / (1000 * 60 * 60 * 24) / 30;  //共存幾個月
                        long z = Integer.parseInt(number); //金額

                        double p = Double.valueOf(pa).doubleValue() / 1200; //%數
                        int m = Integer.parseInt(String.valueOf(month2));//月份

                        double last; //最後金額
                        double rate_t = 1;

                        if (type.getText().toString().equals("零存整付")) {
                            z = z / month;
                            if (month2 < 1) {
                                last = z;
                                String last2 = String.valueOf(Math.round(last));
                                money_showedit.this.showlLastmoney.setText(last2);
                                money_showedit.this.showMoney.setText(last2);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            } else if (month2 >= 1) {
                                double i;
                                for (i = 0; i < m; i++) {
                                    rate_t *= (1 + p);
                                }
                                last = ((z * (rate_t - 1) / p * (1 + p)) - z * m) * 0.8;
                                String last2 = String.valueOf(Math.round(last + z * m));
                                String last3 = String.valueOf(Math.round(z * m));
                                money_showedit.this.showlLastmoney.setText(last2);
                                money_showedit.this.showMoney.setText(last3);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            }
                        } else if (type.getText().toString().equals("整存整付")) {
                            if (month2 < 1) {
                                last = z;
                                String last2 = String.valueOf(Math.round(last));
                                money_showedit.this.showlLastmoney.setText(last2);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            } else {
                                double interest = Math.pow(1 + p, m);
                                double whole = (Math.round(z * interest) - z) * 0.8;
                                String last2 = String.valueOf(whole + z);
                                money_showedit.this.showlLastmoney.setText(last2);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");

                            }
                        } else if (type.getText().toString().equals("存本取息")) {
                            if (month2 < 1) {
                                last = z;
                                String last2 = String.valueOf(Math.round(last));
                                money_showedit.this.showlLastmoney.setText(last2);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            } else {
                                double interest = z * p;
                                double endd = Math.round(interest) * m;
                                String last2 = String.valueOf(endd + z);
                                money_showedit.this.showlLastmoney.setText(last2);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(money_showedit.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(money_showedit.this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                money_showedit.this.showDay2.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
        final String start = showDay.getText().toString();
        final String end = showDay2.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d1 = null;//起始
        try {
            d1 = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date d2 = null;//到期
        try {
            d2 = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = d2.getTime() - d1.getTime();
        month = diff / (1000 * 60 * 60 * 24) / 30;  //共存幾個月
    }


    String getTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    String getYear() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy年");
        Date date2 = new Date(System.currentTimeMillis());
        return simpleDateFormat1.format(date2);
    }

}


