package id.restabayu.clientsatu.Service;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Converter  {

    @SuppressLint("SimpleDateFormat")
    public static String ConvertDate(String date, String formatAsal, String formatTujuan) {
        SimpleDateFormat sdfa = new SimpleDateFormat(formatAsal, Locale.US);
        SimpleDateFormat sdft = new SimpleDateFormat(formatTujuan, Locale.US);

        Date tgl = null;
        try {
            tgl = sdfa.parse(date);
        } catch (ParseException e) {
            Log.e("SimpleDateFormat", e.getMessage());
        }

        return sdft.format(tgl);
    }

    public static Date toDate(String date, String format) {
        SimpleDateFormat sdfa = new SimpleDateFormat(format, Locale.US);

        Date tgl = null;
        try {
            tgl = sdfa.parse(date);
        } catch (ParseException e) {
            Log.e("SimpleDateFormat", e.getMessage());
        }

        return tgl;
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}