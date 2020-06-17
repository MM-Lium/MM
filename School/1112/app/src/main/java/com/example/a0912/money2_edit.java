package com.example.a0912;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.a0912.DBService.TABLE_4;

public class money2_edit extends AppCompatActivity {

    DBService myDb;
    SQLiteDatabase db2;
    private Button btnCancel;
    private Button btnSave;
    private EditText titleEditText, et_day, et_mm, et_day2;
    private EditText contentEditText, et_money, et_pa, et_lastmoney, et_totally;
    private TextView timeTextView;
    private TextView yearTextView, et_name, textView20, textView21, type;
    String beforem;
    String papa;
    String afterm;
    String mmonth;
    String mmonth2;
    String number2;
    String name;
    String type1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money2_edit);
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
        et_totally = findViewById(R.id.et_totally);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        textView20 = findViewById(R.id.textView20);
        textView21 = findViewById(R.id.textView21);
        type = findViewById(R.id.type);
        et_mm = findViewById(R.id.et_mm);
        et_day = findViewById(R.id.et_day);
        et_lastmoney = findViewById(R.id.et_lastmoney);
        et_money = findViewById(R.id.et_money);
        et_pa = findViewById(R.id.et_pa);
        et_name = findViewById(R.id.et_name);
        et_day2 = findViewById(R.id.et_day2);
        beforem = getIntent().getStringExtra("beforem");
        papa = getIntent().getStringExtra("papa");
        afterm = getIntent().getStringExtra("afterm");
        mmonth = getIntent().getStringExtra("mmonth");
        mmonth2 = getIntent().getStringExtra("mmonth2");
        name = getIntent().getStringExtra("name");
        number2 = getIntent().getStringExtra("number2");
        type1 = getIntent().getStringExtra("type");
        et_name.setText(name);
        et_mm.setText(number2);
        et_totally.setText(afterm);
        et_pa.setText(papa);
        et_money.setText(beforem);
        et_day.setText(mmonth);
        et_day2.setText(mmonth2);
        type.setText(type1);
        textView20.setText(name);
        textView21.setText(name);
        double m = Double.valueOf(number2).doubleValue(); //匯率
        double i = Double.valueOf(afterm).doubleValue(); //外幣總額
        double x = m * i;
        DecimalFormat df = new DecimalFormat("0.00");
        String x_1 = String.valueOf(df.format(x));
        et_lastmoney.setText(x_1);

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(money2_edit.this, MainActivity.class);
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
                String year = yearTextView.getText().toString();
                String day = et_day.getText().toString();
                String day2 = et_day2.getText().toString();
                String money = et_money.getText().toString();
                String money2 = et_lastmoney.getText().toString();
                String papa = et_pa.getText().toString();
                String mm = et_mm.getText().toString();
                String name = et_name.getText().toString();
                String total = et_totally.getText().toString();
                String type1 = type.getText().toString();

                if ("".equals(titleEditText.getText().toString())) {
                    Toast.makeText(money2_edit.this, "標題不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(et_day.getText().toString())) {
                    Toast.makeText(money2_edit.this, "日期不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(et_money.getText().toString())) {
                    Toast.makeText(money2_edit.this, "金額不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                values.put(DBService.TIME, time);
                values.put(DBService.YEAR, year);
                values.put(DBService.TITLE, title);
                values.put(DBService.NAME, name);
                values.put(DBService.NUM, mm);
                values.put(DBService.TYPE, type1);
                values.put(DBService.MONEY, money);
                values.put(DBService.DAY, day);
                values.put(DBService.DAY2, day2);
                values.put(DBService.PAPA, papa);
                values.put(DBService.LASTMONEY, money2);
                values.put(DBService.CONTENT, content);
                values.put(DBService.TOTAL, total);

                db.insert(TABLE_4, null, values);
                Toast.makeText(money2_edit.this, "保存成功", Toast.LENGTH_LONG).show();
                db.close();

                Intent intent = new Intent(money2_edit.this, MainActivity.class);
                intent.putExtra("id", 2);
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

