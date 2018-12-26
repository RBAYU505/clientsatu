package id.restabayu.clientsatu.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.app.AlarmManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import static android.support.v4.content.ContextCompat.startForegroundService;
import static id.restabayu.clientsatu.App.CHANNEL_ID;

import java.util.Random;

import id.restabayu.clientsatu.Classified;
import id.restabayu.clientsatu.DetailActivity;
import id.restabayu.clientsatu.MainActivity;
import id.restabayu.clientsatu.R;

public class AlarmService extends IntentService {

    private NotificationManager alarmNotificationManager;

    public AlarmService() {
        super("AlarmService");
        setIntentRedelivery(true);

    }


    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example IntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_notification)
                    .build();

            startForeground(1, notification);
        }

    }

    @Override
    public void onHandleIntent(Intent intent) {
     showNotification("PENGINGAT !, CEK KEMBALI AGENDA YANG ADA", "Sebentar Lagi Ada Acara Yang Akan Segera Dimulai ! Silahkan mempersiapkan diri.");
       /* String[] datas = {cl.getNama(), cl.getTanggal(), cl.getWaktu(), cl.getTempat(), cl.getDeskripsi(), cl.getNotif()}; // replace and send your data from here
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
            getIntentt(datas), 0); */

    }

    private Intent getIntentt(String[] data) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("NAMA_KEY",data[0]);
        i.putExtra("TANGGAL_KEY",data[1]);
        i.putExtra("WAKTU_KEY",data[2]);
        i.putExtra("TEMPAT_KEY",data[3]);
        i.putExtra("DESKRIPSI_KEY",data[4]);
        i.putExtra("NOTIF_KEY" , data[5]);
        return i;
    }

  /*  private void sendNotification(String msg) {
        Log.d("AlarmService", "Preparing to send notification...: " + msg);
        Toast.makeText(this, "Alarm Notif Show", Toast.LENGTH_LONG).show();
        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, DetailActivity.class), 0);

        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Alarm").setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);


        alamNotificationBuilder.setContentIntent(contentIntent);
        alarmNotificationManager.notify(1, alamNotificationBuilder.build());
        Log.d("AlarmService", "Notification sent.");
    } */

    private void showNotification(String title, String body) {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
               new Intent(this, MainActivity.class), 0);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "CHANNEL_X";


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("IK Channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("info")
                .setContentIntent(contentIntent);

        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

}