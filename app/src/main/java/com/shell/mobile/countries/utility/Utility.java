package com.shell.mobile.countries.utility;

import android.content.Context;
import android.net.ConnectivityManager;

public class Utility {


    static public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
