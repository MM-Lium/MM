package com.example.a0912;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    long[] vibrate = {0, 100, 200, 300};
    DBService myDb;

    String stringValue, stringValue2;
    String dollar, str;

    String[] date;
    ArrayList<String> list = new ArrayList<>();

    int TWD;
    int d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DBService(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(R.layout.nav_header_main);
        navigationView.setNavigationItemSelectedListener(this);

        TextView showmuch2 = navigationView.getHeaderView(0).findViewById(R.id.showmuch);
        TextView time = navigationView.getHeaderView(0).findViewById(R.id.time);

        FragmentTransaction tx = getFragmentManager().beginTransaction();
        tx.replace(R.id.fragment, new totally()).commit();

        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            money fragmen = new money();
            FragmentManager fmanger = getFragmentManager();
            FragmentTransaction ftran = fmanger.beginTransaction();
            ftran.replace(R.id.fragment, fragmen).addToBackStack(null).commit();
        }
        if (id == 2) {
            money2 fragmen = new money2();
            FragmentManager fmanger = getFragmentManager();
            FragmentTransaction ftran = fmanger.beginTransaction();
            ftran.replace(R.id.fragment, fragmen).addToBackStack(null).commit();
        }
        if (id == 3) {
            money3 fragmen = new money3();
            FragmentManager fmanger = getFragmentManager();
            FragmentTransaction ftran = fmanger.beginTransaction();
            ftran.replace(R.id.fragment, fragmen).addToBackStack(null).commit();
        }
        Cursor cursor = myDb.viewData();
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            String stringValue = Integer.toString(cursor.getInt(3));
            String stringValue2 = cursor.getString(1);
            dollar = cursor.getString(2);
            showmuch2.setText(stringValue);
            time.setText(stringValue2);
        } else {
            System.out.println("無資料");
            dollar = "0";
        }
        Cursor cursor2 = myDb.sum();
        if (cursor2.getCount() != 0) {
            cursor2.moveToFirst();
            stringValue = Integer.toString(cursor2.getInt(0));
        } else {
            System.out.println("無資料");
        }
        Cursor cursor3 = myDb.sum2();
        if (cursor3.getCount() != 0) {
            cursor3.moveToFirst();
            stringValue2 = Integer.toString(cursor3.getInt(0));
        } else {
            System.out.println("無資料");
        }
        TWD = Integer.parseInt(String.valueOf(stringValue)) + Integer.parseInt(String.valueOf(stringValue2));
        d = Integer.valueOf(dollar);
        if (Integer.parseInt(String.valueOf(dollar)) > 0) {
            if (TWD >= Integer.parseInt(String.valueOf(dollar))) {
                Notification();
            }
        } else {
            System.out.println("無資料");
        }
    }

    public void Notification() {
        Intent notifyIntent = new Intent(MainActivity.this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent = PendingIntent.getActivity(MainActivity.this, 0, notifyIntent, 0);

        String id = "1";
        String name = "Notification";
        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(NOTIFICATION_SERVICE);

        Notification notification = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//判断API
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this)
                    .setChannelId(id)
                    .setContentIntent(appIntent)
                    .setSmallIcon(R.drawable.piggybank)
                    .setAutoCancel(true)
                    .setLargeIcon(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.icon))
                    .setContentTitle("恭喜達成目標")
                    .setContentText("嗨~恭喜存到預設目標~來建立新的目標吧").build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("恭喜達成目標")
                    .setContentIntent(appIntent)
                    .setContentText("嗨~恭喜存到預設目標~來建立新的目標吧")
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.piggybank)
                    .setLargeIcon(BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.icon))
                    .setOngoing(true)
                    .setChannelId(id);
            notification = notificationBuilder.build();
        }
        notificationManager.notify(1, notification);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            ConfirmExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void ConfirmExit() {//退出確認
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("返回");
        ad.setMessage("確定要離開此程式嗎?");
        ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按鈕
            public void onClick(DialogInterface dialog, int i) {
//                // TODO Auto-generated method stub
//                MainActivity.this.finish();//關閉activity
                MainActivity.this.finish();
                System.exit(0);
                onDestroy();
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
            intent.setClass(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.gsg) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, GSG.class);
            startActivity(intent);
        }
        if (id == R.id.action_irr) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, Irr.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentMgr = getFragmentManager();

        if (id == R.id.nav_money) {
            money money1 = new money();
            fragmentMgr.beginTransaction().replace(R.id.fragment, money1).addToBackStack(null).commit();
        } else if (id == R.id.nav_money2) {
            money2 money_2 = new money2();
            fragmentMgr.beginTransaction().replace(R.id.fragment, money_2).addToBackStack(null).commit();
        } else if (id == R.id.nav_money3) {
            money3 money_3 = new money3();
            fragmentMgr.beginTransaction().replace(R.id.fragment, money_3).addToBackStack(null).commit();
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, one.class);
            startActivity(intent);
        } else if (id == R.id.nav_rate) {
            rate rate = new rate();
            fragmentMgr.beginTransaction().replace(R.id.fragment, rate).addToBackStack(null).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
