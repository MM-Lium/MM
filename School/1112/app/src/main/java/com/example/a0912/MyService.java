package com.example.a0912;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyService extends Service {

    boolean mAllowRebind;
    DBService myDb;
    String[] date;
    String str;
    ArrayList<String> list = new ArrayList<>();

    @Override
    public void onCreate() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        myDb = new DBService(this);

        final Cursor cursor = myDb.date();

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (cursor.getCount() != 0) {
                    cursor.moveToFirst();
                    date = new String[cursor.getCount()];
                    for (int i = 0; i < cursor.getCount(); i++) {
                        String d = cursor.getString(0);
                        date[i] = d;
                        list.add(date[i]);
                        cursor.moveToNext();
                    }
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d");
                Date day = new Date(System.currentTimeMillis());
                str = sdf.format(day);

                for (int i = 0; i < cursor.getCount(); i++) {
                    while (date[i].equals(str)) {
                        timeup();
                        break;
                    }
                }
            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 24 * 60 * 60 * 1000; // 一天

        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        list.clear();
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
    }

    @Override
    public void onDestroy() {
    }

    public void timeup() {
        Intent notifyIntent = new Intent(MyService.this, MainActivity.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent = PendingIntent.getActivity(MyService.this, 0, notifyIntent, 0);

        String id = "2";
        String name = "timeup";
        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        //Notification在android 8.0以上設置时，需要設置渠道信息才能夠正常顯示通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//判断API
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(this)
                    .setChannelId(id)
                    .setContentIntent(appIntent) //點即通知要進入的畫面
                    .setSmallIcon(R.drawable.piggybank) // 設置狀態列裡面的圖示（小圖示）　　
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())// 設置時間發生時間
                    .setLargeIcon(BitmapFactory.decodeResource(MyService.this.getResources(), R.drawable.icon)) // 下拉下拉清單裡面的圖示（大圖示）
                    .setContentTitle("有資料將於今天到期")
                    .setContentText("來看看吧~恭喜你有一筆資料到期~記得解約喔~").build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("有資料將於今天到期")
                    .setContentIntent(appIntent)
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentText("來看看吧~恭喜你有一筆資料到期~記得解約喔~")
                    .setSmallIcon(R.drawable.piggybank)
                    .setLargeIcon(BitmapFactory.decodeResource(MyService.this.getResources(), R.drawable.icon))
                    .setOngoing(true)
                    .setChannelId(id);
            notification = notificationBuilder.build();
        }
        notificationManager.notify(2, notification);
    }
}
