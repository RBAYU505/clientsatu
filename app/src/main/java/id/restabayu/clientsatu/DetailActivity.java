package id.restabayu.clientsatu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


    TextView namaTxt,tanggalTxt,waktuTxt,tempatTxt,deskripsiTxt,notifTxt;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        namaTxt = (TextView) findViewById(R.id.namaDetailTextView);
        tanggalTxt = (TextView) findViewById(R.id.tanggalDetailTextView);
        waktuTxt = (TextView) findViewById(R.id.waktuDetailTextView);
        tempatTxt = (TextView) findViewById(R.id.tempatDetailTextView);
        deskripsiTxt = (TextView) findViewById(R.id.deskripsiDetailTextView);
        notifTxt = (TextView) findViewById(R.id.notifTxt);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);

        //GET INTENT
        Intent i = this.getIntent();


        //RECEIVE DATA
        String nama = i.getExtras().getString("NAMA_KEY");
        String tanggal = i.getExtras().getString("TANGGAL_KEY");
        String waktu = i.getExtras().getString("WAKTU_KEY");
        String tempat = i.getExtras().getString("TEMPAT_KEY");
        String deskripsi = i.getExtras().getString("DESKRIPSI_KEY");
        String notif = i.getExtras().getString("NOTIF_KEY");


        //BIND DATA
        namaTxt.setText(nama);
        tanggalTxt.setText(tanggal);
        waktuTxt.setText(waktu);
        tempatTxt.setText(tempat);
        deskripsiTxt.setText(deskripsi);
        notifTxt.setText(notif);


    }



        public void onToggleClicked (View view) {
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
        }
}
