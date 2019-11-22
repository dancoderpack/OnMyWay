package com.conlage.onmyway.system;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.conlage.onmyway.MainActivity;
import com.conlage.onmyway.R;

import java.util.Objects;

public class OnMyWayService extends Service {



    private static final String TAG = OnMyWayService.class.getSimpleName();
    private final static String FOREGROUND_CHANNEL_ID = "foreground_channel_id";
    private NotificationManager mNotificationManager;
    private static int stateService = Constants.STATE_SERVICE.NOT_CONNECTED;

    public OnMyWayService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    int periodValue = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        stateService = Constants.STATE_SERVICE.NOT_CONNECTED;
        update();
    }

    private void update(){
        SharedPreferences sharedPreferences = getSharedPreferences("PeriodData", MODE_PRIVATE);
        periodValue = sharedPreferences.getInt("PeriodValue", 30); //По default - 30 секунд
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Обновление");

                if (OnMyWay.getInstance().group1 != null && OnMyWay.getInstance().group1.isRun()){
                    //Если первая группа запущена

                    if (!OnMyWay.getInstance().group1.getMessage1().equals("")){
                        //Если первое сообщение существует

                        //Существует ли уже пост?
                        if (OnMyWay.getInstance().group1.getPostID1() != -1){
                            //Если да - удаляем его
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group1.getPostID1());
                        }

                        //Отправляем сообщение
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group1.getMessage1());
                    }

                    //Аналогично для второго сообщения
                    if (!OnMyWay.getInstance().group1.getMessage2().equals("")){
                        if (OnMyWay.getInstance().group1.getPostID2() != -1){
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group1.getPostID2());
                        }
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group1.getMessage2());
                    }
                }

                if (OnMyWay.getInstance().group2 != null && OnMyWay.getInstance().group2.isRun()){
                    //Если первая группа запущена

                    if (!OnMyWay.getInstance().group2.getMessage1().equals("")){
                        //Если первое сообщение существует

                        //Существует ли уже пост?
                        if (OnMyWay.getInstance().group2.getPostID1() != -1){
                            //Если да - удаляем его
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group2.getPostID1());
                        }

                        //Отправляем сообщение
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group2.getMessage1());
                    }

                    //Аналогично для второго сообщения
                    if (!OnMyWay.getInstance().group2.getMessage2().equals("")){
                        if (OnMyWay.getInstance().group2.getPostID2() != -1){
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group2.getPostID2());
                        }
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group2.getMessage2());
                    }
                }

                if (OnMyWay.getInstance().group3 != null && OnMyWay.getInstance().group3.isRun()){
                    //Если первая группа запущена

                    if (!OnMyWay.getInstance().group3.getMessage1().equals("")){
                        //Если первое сообщение существует

                        //Существует ли уже пост?
                        if (OnMyWay.getInstance().group3.getPostID1() != -1){
                            //Если да - удаляем его
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group3.getPostID1());
                        }

                        //Отправляем сообщение
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group3.getMessage1());
                    }

                    //Аналогично для второго сообщения
                    if (!OnMyWay.getInstance().group3.getMessage2().equals("")){
                        if (OnMyWay.getInstance().group3.getPostID2() != -1){
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group3.getPostID2());
                        }
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group3.getMessage2());
                    }
                }

                if (OnMyWay.getInstance().group4 != null && OnMyWay.getInstance().group4.isRun()){
                    //Если первая группа запущена

                    if (!OnMyWay.getInstance().group4.getMessage1().equals("")){
                        //Если первое сообщение существует

                        //Существует ли уже пост?
                        if (OnMyWay.getInstance().group4.getPostID1() != -1){
                            //Если да - удаляем его
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group4.getPostID1());
                        }

                        //Отправляем сообщение
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group4.getMessage1());
                    }

                    //Аналогично для второго сообщения
                    if (!OnMyWay.getInstance().group4.getMessage2().equals("")){
                        if (OnMyWay.getInstance().group4.getPostID2() != -1){
                            System.out.println("Удаляем пост с ID: " + OnMyWay.getInstance().group4.getPostID2());
                        }
                        System.out.println("Отправляем сообщение: " + OnMyWay.getInstance().group4.getMessage2());
                    }
                }


                update();
            }
        }, periodValue * 1000);
    }

    @Override
    public void onDestroy() {
        stateService = Constants.STATE_SERVICE.NOT_CONNECTED;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        }

        // if user starts the service
        switch (Objects.requireNonNull(intent.getAction())) {
            case Constants.ACTION.START_ACTION:
                Log.d(TAG, "Received user starts foreground intent");
                startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
                connect();
                break;
            case Constants.ACTION.STOP_ACTION:
                stopForeground(true);
                stopSelf();
                break;
            default:
                stopForeground(true);
                stopSelf();
        }

        return START_NOT_STICKY;
    }

    // its connected, so change the notification text
    private void connect() {
        // after 10 seconds its connected
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.d(TAG, "Bluetooth Low Energy device is connected!!");
                        Toast.makeText(getApplicationContext(),"Connected!",Toast.LENGTH_SHORT).show();
                        stateService = Constants.STATE_SERVICE.CONNECTED;
                        startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
                    }
                }, 10000);

    }


    private Notification prepareNotification() {
        // handle build version above android oreo
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O &&
                mNotificationManager.getNotificationChannel(FOREGROUND_CHANNEL_ID) == null) {
            CharSequence name = getString(R.string.text_name_notification);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(FOREGROUND_CHANNEL_ID, name, importance);
            channel.enableVibration(false);
            mNotificationManager.createNotificationChannel(channel);
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // if min sdk goes below honeycomb
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }*/

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // make a stop intent
        Intent stopIntent = new Intent(this, OnMyWayService.class);
        stopIntent.setAction(Constants.ACTION.STOP_ACTION);
        PendingIntent pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setOnClickPendingIntent(R.id.btn_stop, pendingStopIntent);

        // if it is connected
        switch(stateService) {
            case Constants.STATE_SERVICE.NOT_CONNECTED:
                remoteViews.setTextViewText(R.id.tv_state, "DISCONNECTED");
                break;
            case Constants.STATE_SERVICE.CONNECTED:
                remoteViews.setTextViewText(R.id.tv_state, "CONNECTED");
                break;
        }

        // notification builder
        NotificationCompat.Builder notificationBuilder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder = new NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }
        notificationBuilder
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }

        return notificationBuilder.build();
    }

}
