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

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.lang.String;
import java.util.List;

import id.restabayu.clientsatu.Service.AlarmReciever;
import id.restabayu.clientsatu.Service.Converter;
import id.restabayu.clientsatu.Service.AlarmService;


public class DetailActivity extends AppCompatActivity {


    TextView namaTxt, tanggalTxt, waktuTxt, tempatTxt, deskripsiTxt, interval, tvInterval;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String waktuAcara, tglAcara, vInterval, intervalTgl, intervalWaktu;
    private Calendar calendar;


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
        calendar = Calendar.getInstance();
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
        Date current = calendar.getTime();


        long setInterval = 0L;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        List<Long> data = Arrays.asList(
                daysInMilli,
                hoursInMilli * 12,
                hoursInMilli * 6,
                hoursInMilli * 3,
                hoursInMilli,
                minutesInMilli * 30,
                minutesInMilli * 3
        );
        long finalInterval;
        int tipe;

        if (vInterval.equals("6")) {
            setInterval = data.get(0);
            tipe = 0;
        } else if (vInterval.equals("5")) {
            setInterval = data.get(1);
            tipe = 1;
        } else if (vInterval.equals("4")) {
            setInterval = data.get(2);
            tipe = 2;
        } else if (vInterval.equals("3")) {
            setInterval = data.get(3);
            tipe = 3;
        } else if (vInterval.equals("2")) {
            setInterval = data.get(4);
            tipe = 4;
        } else if (vInterval.equals("1")) {
            setInterval = data.get(5);
            tipe = 5;
        } else {
            tipe = 6;
            setInterval = minutesInMilli * 3;
        }
        long result = txtAlarm.getTime() - setInterval;
        if (current.getTime() > result) {
            if(current.getTime() > txtAlarm.getTime() - data.get(5)){
                finalInterval = txtAlarm.getTime() - data.get(6);
                setAlarm(finalInterval);
            }else {
                long d = txtAlarm.getTime() - data.get(tipe+1);
                finalInterval = d;
                setAlarm(finalInterval);
            }
        } else {
            finalInterval = result;
            setAlarm(finalInterval);
        }
        //tgl_hasil_interva

    }

    private void setAlarm(long iVal) {
        interval.setText(Converter.getDate(iVal, "dd/MM/yyyy HH:mm"));
        String dF = Converter.ConvertDate(interval.getText().toString(), "dd/MM/yyyy HH:mm", "yyyy MM dd HH:mm");
        Date resAlarm = Converter.toDate(dF, "yyyy MM dd HH:mm");

        Log.d("MyActivity", "Alarm On");

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
