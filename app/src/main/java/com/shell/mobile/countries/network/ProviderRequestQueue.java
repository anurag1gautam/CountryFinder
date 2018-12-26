package com.shell.mobile.countries.network;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ProviderRequestQueue {

    private static RequestQueue requestQueue ;

    private ProviderRequestQueue(Context context) {
        requestQueue=Volley.newRequestQueue(context);
    }

    public static RequestQueue getInstance(Context context){
    if(null==requestQueue){
           new ProviderRequestQueue(context);
    }
    return requestQueue;

    }
}
