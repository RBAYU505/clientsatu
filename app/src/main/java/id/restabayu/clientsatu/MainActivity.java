package id.restabayu.clientsatu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /**********************************FIREBASE HELPER START************************/
    public class FirebaseHelper {

        DatabaseReference db;
        ArrayList<Classified> classifieds = new ArrayList<>();
        ListView mListView;
        Context c;

        /*
       let's receive a reference to our FirebaseDatabase
       */
        public FirebaseHelper(DatabaseReference db, Context context, ListView mListView) {
            this.db = db;
            this.c = context;
            this.mListView = mListView;
            this.retrieve();
        }

        /*
        Retrieve and Return them clean data in an arraylist so that they just bind it to ListView.
         */
        public ArrayList<Classified> retrieve() {
            db.child("classified").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    classifieds.clear();
                    if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Now get Classified Objects and populate our arraylist.
                            Classified classified = ds.getValue(Classified.class);
                            classifieds.add(classified);
                        }
                        adapter = new CustomAdapter(c, classifieds);
                        mListView.setAdapter(adapter);

                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(classifieds.size());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("mTAG", databaseError.getMessage());
                    Toast.makeText(c, "ERROR " + databaseError.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            return classifieds;
        }

    }

    /**********************************CUSTOM ADAPTER START************************/
    class CustomAdapter extends BaseAdapter {
        Context c;
        ArrayList<Classified> classifieds;

        public CustomAdapter(Context c, ArrayList<Classified> classifieds) {
            this.c = c;
            this.classifieds = classifieds;
        }

        @Override
        public int getCount() {
            return classifieds.size();
        }

        @Override
        public Object getItem(int position) {
            return classifieds.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
            }

            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            TextView quoteTextView = convertView.findViewById(R.id.quoteTextView);
            TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);

            final Classified s = (Classified) this.getItem(position);

            nameTextView.setText(s.getNama());
            quoteTextView.setText(s.getDeskripsi());
            descriptionTextView.setText(s.getTanggal());

            //ONITECLICK
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, s.getNama(), Toast.LENGTH_SHORT).show();
                    String[] classifieds = {
                            s.getNama(),
                            s.getTanggal(),
                            s.getWaktu(),
                            s.getTempat(),
                            s.getDeskripsi(),
                    };
                    openDetailActivity(classifieds);
                }
            });
            return convertView;
        }

        //OPEN DETAIL ACTIVITY
        private void openDetailActivity(String[] data)
        {
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("NAMA_KEY",data[0]);
            i.putExtra("TANGGAL_KEY",data[1]);
            i.putExtra("WAKTU_KEY",data[2]);
            i.putExtra("TEMPAT_KEY",data[3]);
            i.putExtra("DESKRIPSI_KEY",data[4]);
            startActivity(i);
        }
    }

    /**********************************TOOLBAR MENU*************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, SigninActivity.class));
                break;
        }
        return true;
    }

    /**********************************MAIN ACTIVITY CONTINUATION************************/
    //instance fields
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView mListView;
    FirebaseAuth mAuth;

  /*  @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this,SigninActivity.class));
        }
    } */

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //nyobo
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        mListView = (ListView) findViewById(R.id.myListView);
        //initialize firebase database
        db = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(db, this, mListView);

        }
    }
