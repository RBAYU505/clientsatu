package id.restabayu.clientsatu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
// import android.support.v7.widget.Toolbar;
// import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    TextView namaTxt,tanggalTxt,waktuTxt,tempatTxt,deskripsiTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        namaTxt = (TextView) findViewById(R.id.namaDetailTextView);
        tanggalTxt= (TextView) findViewById(R.id.tanggalDetailTextView);
        waktuTxt= (TextView) findViewById(R.id.waktuDetailTextView);
        tempatTxt= (TextView) findViewById(R.id.tempatDetailTextView);
        deskripsiTxt = (TextView) findViewById(R.id.deskripsiDetailTextView);

        //GET INTENT
        Intent i=this.getIntent();


        //RECEIVE DATA
        String nama=i.getExtras().getString("NAMA_KEY");
        String tanggal=i.getExtras().getString("TANGGAL_KEY");
        String waktu=i.getExtras().getString("WAKTU_KEY");
        String tempat=i.getExtras().getString("TEMPAT_KEY");
        String deskripsi=i.getExtras().getString("DESKRIPSI_KEY");

        //BIND DATA
        namaTxt.setText(nama);
        tanggalTxt.setText(tanggal);
        waktuTxt.setText(waktu);
        tempatTxt.setText(tempat);
        deskripsiTxt.setText(deskripsi);

    }

}
