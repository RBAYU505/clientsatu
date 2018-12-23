package id.restabayu.clientsatu.Service;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import static android.support.v4.content.ContextCompat.startForegroundService;
import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive( Context context, Intent intent) {
        // this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }

        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startForegroundService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

    }
}
