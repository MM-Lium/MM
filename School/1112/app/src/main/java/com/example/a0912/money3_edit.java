package com.example.a0912;

import android.app.DatePickerDialog;
import android.content.ContentValues;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.a0912.DBService.TABLE_3;
import static com.example.a0912.DBService.TABLE_4;

public class money3_edit extends AppCompatActivity {

    DBService myDb;
    SQLiteDatabase db2;

    private EditText titleEditText, et_day, Currency, et_TWD;
    private EditText contentEditText, et_money, Rate, IRR, et_totally;

    private TextView timeTextView, type, year;

    String curreny2;
    String b;
    String t;
    String c;
    String p;
    String irr;
    String thetype;
    String number2;
    String expired;

    private Button btnCancel;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money3_edit);

        myDb = new DBService(this);
        db2 = myDb.getWritableDatabase();
        titleEditText = findViewById(R.id.et_title);
        contentEditText = findViewById(R.id.et_content);
        timeTextView = findViewById(R.id.edit_time);
        et_money = findViewById(R.id.et_money);
        et_totally = findViewById(R.id.et_totally);
        type = findViewById(R.id.type);
        et_TWD = findViewById(R.id.et_TWD);
        year = findViewById(R.id.year);
        Currency = findViewById(R.id.Currency);
        et_day = findViewById(R.id.et_day);
        IRR = findViewById(R.id.IRR);
        Rate = findViewById(R.id.Rate);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        et_day.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        et_day.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });

        t = getIntent().getStringExtra("t");
        b = getIntent().getStringExtra("b");
        c = getIntent().getStringExtra("c");
        p = getIntent().getStringExtra("p");
        irr = getIntent().getStringExtra("irr");
        thetype = getIntent().getStringExtra("thetype");
        number2 = getIntent().getStringExtra("number2");
        expired = getIntent().getStringExtra("expired");
        curreny2 = getIntent().getStringExtra("curreny2");

        type.setText(thetype);
        Currency.setText(curreny2);
        Rate.setText(number2);
        et_money.setText(t);
        IRR.setText(irr);
        et_totally.setText(expired);
        contentEditText.setText("預計存" + c + "年");

        double tt = Double.parseDouble(t);
        double r = Double.parseDouble(number2);
        double twd = tt * r;
        et_TWD.setText(String.valueOf(twd));

        if (timeTextView.getText().length() == 0)
            timeTextView.setText(getTime());

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(money3_edit.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                String time = timeTextView.getText().toString();
                String day = et_day.getText().toString();
                String currency = Currency.getText().toString();
                String rate = Rate.getText().toString();
                String money = et_money.getText().toString();
                String irr = IRR.getText().toString();
                String total = et_totally.getText().toString();
                String twd = et_TWD.getText().toString();
                String type1 = type.getText().toString();

                if ("".equals(titleEditText.getText().toString())) {
                    Toast.makeText(money3_edit.this, "標題不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(et_day.getText().toString())) {
                    Toast.makeText(money3_edit.this, "日期不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(et_money.getText().toString())) {
                    Toast.makeText(money3_edit.this, "金額不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                values.put(DBService.TITLE, title);
                values.put(DBService.TYPE, type1);
                values.put(DBService.Currency, currency);
                values.put(DBService.CONTENT, content);
                values.put(DBService.Rate, rate);
                values.put(DBService.TIME, time);
                values.put(DBService.MONEY, money);
                values.put(DBService.DAY, day);
                values.put(DBService.PAPA, irr);
                values.put(DBService.TOTAL, total);
                values.put(DBService.LASTMONEY, twd);

                db.insert(TABLE_3, null, values);
                Toast.makeText(money3_edit.this, "保存成功", Toast.LENGTH_LONG).show();
                db.close();

                Intent intent = new Intent(money3_edit.this, MainActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
            }
        });
    }


    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(money3_edit.this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                money3_edit.this.et_day.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
}
