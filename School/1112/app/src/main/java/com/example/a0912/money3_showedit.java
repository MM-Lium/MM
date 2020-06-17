package com.example.a0912;

import android.annotation.SuppressLint;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class money3_showedit extends AppCompatActivity {

    DBService myDb;
    SQLiteDatabase db2;
    private Values value;
    private EditText titleEditText, et_day, Currency, et_TWD;
    private EditText contentEditText, et_money, Rate, IRR, et_totally;
    private TextView timeTextView, type, year;
    private Button button2;
    private Button btnCancel;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money3_showedit);

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
        button2 = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btnCancel);

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
            value.setId4(Integer.valueOf(intent.getStringExtra(DBService.ID3)));

            timeTextView.setText(getTime());
            titleEditText.setText(value.getItitle());
            Currency.setText(value.getIname());
            et_money.setText(value.getImoney());
            et_day.setText(value.getIday());
            type.setText(value.gettype());
            IRR.setText(value.getIpapa());
            et_TWD.setText(value.getImoney2());
            contentEditText.setText(value.getIcontent());
            Rate.setText(value.getInum());
            et_totally.setText(value.gettotal());
        }

        if (et_day.getText().length() == 0)
            et_day.setText(getTime());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                String day = et_day.getText().toString();
                String money = et_money.getText().toString();
                String twd = et_TWD.getText().toString();
                String irr = IRR.getText().toString();
                String currency = Currency.getText().toString();
                String total = et_totally.getText().toString();
                String type1 = type.getText().toString();
                String rr = Rate.getText().toString();
                values.put(DBService.TITLE, title);
                values.put(DBService.TYPE, type1);
                values.put(DBService.Currency, currency);
                values.put(DBService.CONTENT, content);
                values.put(DBService.Rate, rr);
                values.put(DBService.TIME, getTime());
                values.put(DBService.MONEY, money);
                values.put(DBService.DAY, day);
                values.put(DBService.PAPA, irr);
                values.put(DBService.TOTAL, total);
                values.put(DBService.LASTMONEY, twd);

                db.update(DBService.TABLE_3, values, DBService.ID3 + "=?", new String[]{value.getId4().toString()});
                Toast.makeText(money3_showedit.this, "修改成功", Toast.LENGTH_LONG).show();
                db.close();

                Intent intent = new Intent(money3_showedit.this, MainActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(money3_showedit.this, MainActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
            }
        });
    }

    String getTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
