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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class money2_showedit extends AppCompatActivity {

    DBService myDb;
    private Values value;

    private Button btnCancel, btnSave, button2;
    private EditText showTitle, show2Pa, show2Day, show2money, show2mm, show2_totally;
    private EditText showContent, show2_name, showl2Lastmoney, show2Day2;
    private TextView showTime, type, textView3;
    private TextView showYear, textView18;
    long month;
    String showDayy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money2_showedit);

        init();

        show2Day2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        show2Day2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });

        if (showTime.getText().length() == 0)
            showTime.setText(getTime());
        if (showYear.getText().length() == 0)
            showYear.setText(getYear());
    }

    public void init() {

        myDb = new DBService(this);
        showTitle = findViewById(R.id.show2_title);
        showContent = findViewById(R.id.show2_content);
        showTime = findViewById(R.id.show2_time);
        showYear = findViewById(R.id.show2_year);
        btnCancel = findViewById(R.id.btn_cancel2);
        btnSave = findViewById(R.id.btn_save);
        button2 = findViewById(R.id.button2);
        show2mm = findViewById(R.id.show2_mm);
        show2_name = findViewById(R.id.show2_name);
        show2Day = findViewById(R.id.show2_day);
        show2Day2 = findViewById(R.id.show2_day2);
        showl2Lastmoney = findViewById(R.id.show2_lastmoney);
        show2money = findViewById(R.id.show2_money);
        show2Pa = findViewById(R.id.show2_pa);
        show2_totally = findViewById(R.id.show2_totally);
        type = findViewById(R.id.type);
        textView3 = findViewById(R.id.textView3);

        Intent intent = this.getIntent();
        if (intent != null) {
            value = new Values();

            value.setItime(intent.getStringExtra(DBService.TIME));
            value.setIyear(intent.getStringExtra(DBService.YEAR));
            value.setImoney(intent.getStringExtra(DBService.MONEY));
            value.setImoney2(intent.getStringExtra(DBService.LASTMONEY));
            value.setIday(intent.getStringExtra(DBService.DAY));
            value.setIday2(intent.getStringExtra(DBService.DAY2));
            value.setIpapa(intent.getStringExtra(DBService.PAPA));
            value.setItitle(intent.getStringExtra(DBService.TITLE));
            value.setIcontent(intent.getStringExtra(DBService.CONTENT));
            value.settotal(intent.getStringExtra(DBService.TOTAL));
            value.setInum(intent.getStringExtra(DBService.NUM));
            value.settype(intent.getStringExtra(DBService.TYPE));
            value.setIname(intent.getStringExtra(DBService.NAME));
            value.setId4(Integer.valueOf(intent.getStringExtra(DBService.ID4)));
            showTime.setText(value.getItime());
            showYear.setText(value.getIyear());
            showTitle.setText(value.getItitle());
            show2_name.setText(value.getIname());
            show2money.setText(value.getImoney());
            show2Day.setText(value.getIday());
            show2Day2.setText(value.getIday2());
            type.setText(value.gettype());
            show2Pa.setText(value.getIpapa());
            showl2Lastmoney.setText(value.getImoney2());
            showContent.setText(value.getIcontent());
            show2mm.setText(value.getInum());
            show2_totally.setText(value.gettotal());
            showDayy = value.getIday2();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                String title = showTitle.getText().toString();
                String content = showContent.getText().toString();
                String day = show2Day.getText().toString();
                String day2 = show2Day2.getText().toString();
                String money = show2money.getText().toString();
                String money2 = showl2Lastmoney.getText().toString();
                String papa = show2Pa.getText().toString();
                String mm = show2mm.getText().toString();
                String name = show2_name.getText().toString();
                String totally = show2_totally.getText().toString();
                String type1 = type.getText().toString();

                if ("".equals(showTitle.getText().toString())) {
                    Toast.makeText(money2_showedit.this, "標題不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(show2Day.getText().toString())) {
                    Toast.makeText(money2_showedit.this, "日期不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(show2money.getText().toString())) {
                    Toast.makeText(money2_showedit.this, "金額不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                values.put(DBService.TIME, getTime());
                values.put(DBService.YEAR, getYear());
                values.put(DBService.TITLE, title);
                values.put(DBService.NAME, name);
                values.put(DBService.NUM, mm);
                values.put(DBService.MONEY, money);
                values.put(DBService.DAY, day);
                values.put(DBService.DAY2, day2);
                values.put(DBService.PAPA, papa);
                values.put(DBService.LASTMONEY, money2);
                values.put(DBService.CONTENT, content);
                values.put(DBService.TOTAL, totally);
                values.put(DBService.TYPE, type1);

                db.update(DBService.TABLE_4, values, DBService.ID4 + "=?", new String[]{value.getId4().toString()});
                Toast.makeText(money2_showedit.this, "修改成功", Toast.LENGTH_LONG).show();
                db.close();

                Intent intent = new Intent(money2_showedit.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = show2money.getText().toString();
                final String pa = show2Pa.getText().toString();
                final String start = show2Day.getText().toString();
                final String end = show2Day2.getText().toString();
                final String nn = show2mm.getText().toString();
                DecimalFormat df = new DecimalFormat("#.##");
                if (show2Day2.getText().toString().equals(showDayy)) {
                    new AlertDialog.Builder(money2_showedit.this)
                            .setTitle("請修改到期日")
                            .setMessage("修改到期日，重新計算金額" +
                                    "如要續約，因金額計算方式不同，請新建一份資料")
                            .setPositiveButton("了解了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(money2_showedit.this, "修改到期日，重新計算金額", Toast.LENGTH_SHORT).show();
                                }
                            }).show(); //要有這個才會顯示喔~~~~~~~~~
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        Date d1 = sdf.parse(start);//開始
                        Date d3 = sdf.parse(end);//到期
                        long diff2 = d3.getTime() - d1.getTime();
                        long month2 = diff2 / (1000 * 60 * 60 * 24) / 30;  //共存幾個月
                        long z = Integer.parseInt(number); //金額
                        double p = Double.valueOf(pa).doubleValue() / 1200; //%數
                        int m = Integer.parseInt(String.valueOf(month2));//月份
                        double mm = Double.valueOf(nn);//匯率
                        double last; //最後外幣
                        double lastm; //最後台幣
                        double rate_t = 1;
                        if (type.getText().toString().equals("零存整付")) {
                            z = z / month;
                            if (month2 < 1) {
                                last = z;
                                lastm = z * mm;
                                String last2 = String.valueOf(Math.round(last));
                                String lastmm = String.valueOf(Math.round(lastm));
                                money2_showedit.this.show2money.setText(last2);
                                money2_showedit.this.show2_totally.setText(last2);
                                money2_showedit.this.showl2Lastmoney.setText(lastmm);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            } else if (month2 >= 1) {
                                double i;
                                for (i = 0; i < m; i++) {
                                    rate_t *= (1 + p);
                                }
                                last = ((z * (rate_t - 1) / p * (1 + p)) - z * m) * 0.8;
                                lastm = (last + z * m) * mm;
                                String last2 = String.valueOf(df.format(last + z * m));
                                String last3 = String.valueOf(Math.round(z * m));
                                String lastmm = String.valueOf(Math.round(lastm));
                                money2_showedit.this.show2_totally.setText(last2);
                                money2_showedit.this.show2money.setText(last3);
                                money2_showedit.this.showl2Lastmoney.setText(lastmm);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            }
                        } else if (type.getText().toString().equals("存本取息")) {
                            if (month2 < 1) {
                                last = z;
                                lastm = (last + z * m) * mm;
                                String last2 = String.valueOf(Math.round(last));
                                String lastmm = String.valueOf(Math.round(lastm));
                                money2_showedit.this.show2_totally.setText(last2);
                                money2_showedit.this.showl2Lastmoney.setText(lastmm);
                                showContent.setText("已解約!!!!!!!!!!!!!!");
                                textView3.setText("解約日");
                            } else {
                                double interest = z * p;
                                double endd = Math.round(interest) * m;
                                lastm = (endd + z) * mm;

                                String last2 = String.valueOf(endd + z);
                                String lastmm = String.valueOf(Math.round(lastm));
                                money2_showedit.this.show2_totally.setText(last2);
                                money2_showedit.this.showl2Lastmoney.setText(lastmm);
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
                Intent intent = new Intent(money2_showedit.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });

        String number = show2Day.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date d1 = sdf.parse(number);
            Date d2 = new Date(System.currentTimeMillis());
            long diff = d1.getTime() - d2.getTime();
            long month = diff / (1000 * 60 * 60 * 24) / 30;
            String month2 = String.valueOf(month);
            textView18.setText(month2);
        } catch (Exception e) {
        }
    }

    protected void showDatePickDlg() {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(money2_showedit.this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                money2_showedit.this.show2Day2.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
        final String start = show2Day.getText().toString();
        final String end = show2Day2.getText().toString();
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
        month = diff / (1000 * 60 * 60 * 24) / 30; //共存幾個月
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
