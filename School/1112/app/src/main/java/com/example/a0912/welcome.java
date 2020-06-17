package com.example.a0912;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class welcome extends AppCompatActivity {

    DBService myDb;
    String[] date;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        myDb = new DBService(this);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    SharedPreferences jame = getSharedPreferences("jame", 0);
                    boolean isFirst = jame.getBoolean("isFirst", true);//初始狀態為true
                    if (isFirst) {
                        startActivity(new Intent(welcome.this, one.class));
                        SharedPreferences.Editor edit = jame.edit();//創建狀態
                        edit.putBoolean("isFirst", false);//改變狀態
                        edit.commit();
                    } else {
                        startActivity(new Intent(welcome.this, MainActivity.class));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
