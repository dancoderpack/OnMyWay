package com.conlage.onmyway.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.conlage.onmyway.MainActivity;
import com.conlage.onmyway.R;
import com.conlage.onmyway.system.OnMyWay;

public class MyForeGroundService extends Service {

    private ServiceHandler mServiceHandler;
    public MyForeGroundService() {

    }

    private final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Notification notification = getNotification("Приложение работает в фоне");
            startForeground(msg.arg1, notification);
            ServiceHelper serviceHelper = new ServiceHelper(getApplicationContext());
            while (true) {
                synchronized (this) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if (OnMyWay.getInstance().isServiceWorked())
                    serviceHelper.startService();
            }
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        Looper mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        OnMyWay.getInstance().setServiceWorked(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        if (intent != null) {
            msg.setData(intent.getExtras());
            mServiceHandler.sendMessage(msg);
            if (intent.getAction() != null && intent.getAction().equals("Stop service")){
                OnMyWay.getInstance().setServiceWorked(false);
                stopForeground(true);
                stopSelf();
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Сервис отключен", Toast.LENGTH_SHORT).show();
        OnMyWay.getInstance().setServiceWorked(false);
    }

    public Notification getNotification(String message) {
        Intent stopSelf = new Intent(this, MyForeGroundService.class);
        stopSelf.setAction("Stop service");
        PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), MainActivity.id1)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setChannelId(MainActivity.id1)
                .setContentTitle("На пути")
                .setContentText(message)
                .addAction(R.mipmap.ic_launcher, "Остановить", pStopSelf)
                .build();
    }
}
