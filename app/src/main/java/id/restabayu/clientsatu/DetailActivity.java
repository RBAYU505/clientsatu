package id.restabayu.clientsatu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;
import java.lang.String;

import id.restabayu.clientsatu.Service.AlarmReciever;
import id.restabayu.clientsatu.Service.Converter;
import id.restabayu.clientsatu.Service.AlarmService;



public class DetailActivity extends AppCompatActivity {


    TextView namaTxt,tanggalTxt,waktuTxt,tempatTxt,deskripsiTxt,interval, tvInterval;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String waktuAcara, tglAcara, vInterval, intervalTgl, intervalWaktu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        namaTxt = (TextView) findViewById(R.id.namaDetailTextView);
        tanggalTxt = (TextView) findViewById(R.id.tanggalDetailTextView);
        waktuTxt = (TextView) findViewById(R.id.waktuDetailTextView);
        tempatTxt = (TextView) findViewById(R.id.tempatDetailTextView);
        deskripsiTxt = (TextView) findViewById(R.id.deskripsiDetailTextView);
        interval = (TextView) findViewById(R.id.interval);
        tvInterval = (TextView) findViewById(R.id.vInterval);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
     //   ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);

        //GET INTENT
        Intent i = this.getIntent();


        //RECEIVE DATA ngangggo iki nol (yen i.getExtra.getString focedclose)
        String nama = i.getStringExtra("NAMA_KEY");
        String tanggal = i.getStringExtra("TANGGAL_KEY");
        String waktu = i.getStringExtra("WAKTU_KEY");
        String tempat = i.getStringExtra("TEMPAT_KEY");
        String deskripsi = i.getStringExtra("DESKRIPSI_KEY");
        String interValKey = i.getStringExtra("INTERVAL_KEY");


        //BIND DATA
        namaTxt.setText(nama);
        tanggalTxt.setText(tanggal);
        waktuTxt.setText(waktu);
        tempatTxt.setText(tempat);
        deskripsiTxt.setText(deskripsi);

        tglAcara = tanggal;
        waktuAcara = waktu;
        vInterval = interValKey;

        tvInterval.setText(vInterval);


    }

    public void ingatkanSaya(View view) {
        String date = Converter.ConvertDate(tanggalTxt.getText().toString(), "dd/MM/yyyy", "yyyy MM dd");
        String time = waktuTxt.getText().toString();
        String dateTime = date + " " + time;
        Date txtAlarm = Converter.toDate(dateTime, "yyyy MM dd HH:mm");

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long setInterval = 0L;
        if(vInterval.equals("1")){
            setInterval = minutesInMilli * 30;
        }else if(vInterval.equals("2")){
            setInterval = hoursInMilli;
        }else if(vInterval.equals("3")){
            setInterval = hoursInMilli * 3;
        }else if(vInterval.equals("4")){
            setInterval = hoursInMilli * 6;
        }else if(vInterval.equals("5")){
            setInterval = hoursInMilli * 12;
        }else if(vInterval.equals("6")){
            setInterval = daysInMilli;
        }else {
            setInterval = minutesInMilli * 30;
        }


        long result = txtAlarm.getTime() - setInterval;
        interval.setText(Converter.getDate(result, "dd/MM/yyyy HH:mm"));
        String dF = Converter.ConvertDate(interval.getText().toString(), "dd/MM/yyyy HH:mm", "yyyy MM dd HH:mm");
        Date resAlarm = Converter.toDate(dF, "yyyy MM dd HH:mm");


        Log.d("MyActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(resAlarm);
            Intent myIntent = new Intent(this, AlarmReciever.class);
            pendingIntent = PendingIntent.getBroadcast(DetailActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            Toast.makeText(getApplicationContext(), "Pengingat telah dibuat !", Toast.LENGTH_SHORT).show();

    }

     /*   public void onToggleClicked (View view) {
            String date = Converter.ConvertDate(tanggalTxt.getText().toString(), "dd/MM/yyyy", "yyyy MM dd");
            String time = notifTxt.getText().toString();
            String dateTime = date + " " + time;
            Date txtAlarm = Converter.toDate(dateTime, "yyyy MM dd HH:mm");
            if (((ToggleButton) view).isChecked()) {
                Log.d("MyActivity", "Alarm On");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(txtAlarm);
                Intent myIntent = new Intent(this, AlarmReciever.class);
                pendingIntent = PendingIntent.getBroadcast(DetailActivity.this, 0, myIntent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

            else {
                alarmManager.cancel(pendingIntent);
                Log.d("MyActivity", "Alarm Off");
            }
        } */
}
