package com.example.a0912;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.a0912.DBService.HMONEY2;
import static com.example.a0912.DBService.HYEAR2;
import static com.example.a0912.DBService.NEED;
import static com.example.a0912.DBService.TABLE_2;

public class one extends AppCompatActivity {

    DBService myDb;
    private EditText howmuch, year, need;
    private Button mainsave, btnCancel;
    private Values value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        myDb = new DBService(this);
        year = (EditText) findViewById(R.id.year);
        howmuch = (EditText) findViewById(R.id.howmuch);
        need = (EditText) findViewById(R.id.need);
        mainsave = (Button) findViewById(R.id.mainsave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        year.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        year.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(one.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mainsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                String howyear = year.getText().toString();
                String much = howmuch.getText().toString();
                String money = need.getText().toString();

                if ("".equals(year.getText().toString())) {
                    Toast.makeText(one.this, "内容不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(howmuch.getText().toString())) {
                    Toast.makeText(one.this, "内容不能為空", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(need.getText().toString())) {
                    Toast.makeText(one.this, "内容不能為空", Toast.LENGTH_LONG).show();
                    return;
                }

                values.put(HYEAR2, howyear);
                values.put(HMONEY2, much);
                values.put(NEED, money);

                db.insert(TABLE_2, null, values);
                Toast.makeText(one.this, "保存成功", Toast.LENGTH_LONG).show();
                one.this.mainsave.setText("更新目標");
                db.close();

                List<Values> valuesList = new ArrayList<>();
                SQLiteDatabase db2 = myDb.getReadableDatabase();
                Cursor cursor = db2.query(DBService.TABLE_2, null, null,
                        null, null, null, null);

                if (cursor.moveToFirst()) {
                    Values values2;
                    while (!cursor.isAfterLast()) {
                        values2 = new Values();
                        values2.setId2(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBService.ID2))));
                        values2.setHowyear(cursor.getString(cursor.getColumnIndex(DBService.HYEAR2)));
                        values2.setMuch(cursor.getString(cursor.getColumnIndex(DBService.HMONEY2)));
                        values2.setNeed(cursor.getString(cursor.getColumnIndex(DBService.NEED)));
                        valuesList.add(values2);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                db.close();

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("message", need.getText().toString());
                intent.putExtras(bundle);
                intent.setClass(one.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //計算金額
    public void gogo(View v) {
        String text = year.getText().toString();
        String number = howmuch.getText().toString();
        if ("".equals(year.getText().toString())) {
            Toast.makeText(one.this, "請填入日期", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(howmuch.getText().toString())) {
            Toast.makeText(one.this, "請填入金額", Toast.LENGTH_LONG).show();
            return;
        } else {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                Date d1 = sdf.parse(text);
                Date d2 = new Date(System.currentTimeMillis());
                long diff = d1.getTime() - d2.getTime();
                long month = diff / (1000 * 60 * 60 * 24) / 30;
                int month2 = Integer.parseInt(String.valueOf(month));
                int z = Integer.parseInt(number);
                int q;
                q = z / month2;
                String money = String.valueOf(q);
                one.this.need.setText(money);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            one.this.finish();//關閉activity
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showDatePickDlg() {

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(one.this, DatePickerDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                one.this.year.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int id2 = getIntent().getIntExtra("id", 0);

        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(one.this, MainActivity.class);
            startActivity(intent);
        }
        if (id2 == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
