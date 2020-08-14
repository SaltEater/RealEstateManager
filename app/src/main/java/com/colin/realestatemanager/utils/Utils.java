package com.colin.realestatemanager.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroToDollar(int euros) {
        return (int) Math.round(euros / 0.812);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    /*public static String getTodayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(new Date());
    }*/

    // MANIPULATE DATES

    public static String getTodayDate() {
        return dateFormat.format(new Date());
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static String calendarToString(Calendar calendar) {
        return dateFormat.format(calendar.getTime());
    }

    public static String timestampToString(Long timestamp) {
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }

    public static Date stringToDate(String sDate) {
        try {
            return dateFormat.parse(sDate);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static int compareDates(String date1, String date2) {
        if (date1 == null || date1.equals("") || date2 == null || date2.equals("")) {
            throw new IllegalArgumentException();
        } else {
            long res = stringToDate(date1).getTime() - stringToDate(date2).getTime();
            if (res > 0) {
                return 1;
            } else if (res < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */

    public static Boolean isInternetAvailable(Context context) {
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    public static boolean isInternetAvailable2(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }


    // FETCH FILE PATH


    // FETCH TEXT

    public static String getText(TextInputEditText view) {
        if (view.getText() == null) {
            return "";
        } else {
            return view.getText().toString().trim();
        }
    }
    public static int getTextAsInteger(TextInputEditText view) {
        if (view.getText() == null) {
            throw new IllegalArgumentException();
        } else {
            return Integer.parseInt(view.getText().toString().trim());
        }
    }

}
