package com.example.a0912;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

import static com.example.a0912.DBService.TABLE_1;

public class money_edit extends AppCompatActivity {

    DBService myDb;
    SQLiteDatabase db2;

    private Button btnCancel;
    private Button btnSave;
    private EditText titleEditText, et_day;
    private EditText contentEditText, et_money, et_pa, et_lastmoney, et_day2;
    private TextView timeTextView;
    private TextView yearTextView, type;

    String beforem;
    String papa;
    String afterm;
    String mmonth;
    String mmonth2;
    String type1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_edit);

        init();

        myDb = new DBService(this);
        db2 = myDb.getWritableDatabase();

        if (timeTextView.getText().length() == 0)
            timeTextView.setText(getTime());
        if (yearTextView.getText().length() == 0)
            yearTextView.setText(getYear());
    }

    public void init() {

        titleEditText = findViewById(R.id.et_title);
        contentEditText = findViewById(R.id.et_content);
        timeTextView = findViewById(R.id.edit_time);
        yearTextView = findViewById(R.id.edit_year);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        et_day2 = findViewById(R.id.et_day2);
        et_day = findViewById(R.id.et_day);
        et_lastmoney = findViewById(R.id.et_lastmoney);
        et_money = findViewById(R.id.et_money);
        et_pa = findViewById(R.id.et_pa);
        type = findViewById(R.id.type);

        beforem = getIntent().getStringExtra("beforem");
        papa = getIntent().getStringExtra("papa");
        afterm = getIntent().getStringExtra("afterm");
        mmonth = getIntent().getStringExtra("mmonth");
        mmonth2 = getIntent().getStringExtra("mmonth2");
        type1 = getIntent().getStringExtra("type");

        et_lastmoney.setText(afterm);
        et_pa.setText(papa);
        et_money.setText(beforem);
        et_day.setText(mmonth);
        et_day2.setText(mmonth2);
        type.setText(type1);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(money_edit.this, MainActivity.class);
                intent.putExtra("id", 1);
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
                String year = yearTextView.getText().toString();
                String day = et_day.getText().toString();
                String day2 = et_day2.getText().toString();
                String money = et_money.getText().toString();
                String money2 = et_lastmoney.getText().toString();
                String papa = et_pa.getText().toString();
                String name = "新台幣 (TWD)";
                String total = et_lastmoney.getText().toString();
                String type1 = type.getText().toString();

                if ("".equals(titleEditText.getText().toString())) {
                    Toast.makeText(money_edit.this, "標題不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(et_day.getText().toString())) {
                    Toast.makeText(money_edit.this, "日期不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(et_money.getText().toString())) {
                    Toast.makeText(money_edit.this, "金額不能為空", Toast.LENGTH_LONG).show();
                    return;
                }

                values.put(DBService.TITLE, title);
                values.put(DBService.CONTENT, content);
                values.put(DBService.TIME, time);
                values.put(DBService.YEAR, year);
                values.put(DBService.DAY, day);
                values.put(DBService.TYPE, type1);
                values.put(DBService.DAY2, day2);
                values.put(DBService.MONEY, money);
                values.put(DBService.LASTMONEY, money2);
                values.put(DBService.PAPA, papa);
                values.put(DBService.NAME, name);
                values.put(DBService.TOTAL, total);
                db.insert(TABLE_1, null, values);
                Toast.makeText(money_edit.this, "保存成功", Toast.LENGTH_LONG).show();
                db.close();
                Intent intent = new Intent(money_edit.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });
    }

    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }

    private String getYear() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
        Date date2 = new Date(System.currentTimeMillis());
        String str1 = sdf2.format(date2);
        return str1;
    }
}
