package com.shell.mobile.countries.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.shell.mobile.countries.SearchProvider.Provider;
import com.shell.mobile.countries.SearchProvider.SearchListener;
import com.shell.mobile.countries.model.Country;

import java.util.ArrayList;
import java.util.Arrays;


public class NetworkProvider extends Provider {
    final String baseUrl = "https://restcountries.eu/rest/v2/name/";
    Context context;

    public NetworkProvider(Context context) {
        this.context = context;
    }


    @Override
    public void getCountryList(String hint, final SearchListener searchListener) {
        final RequestQueue queue = ProviderRequestQueue.getInstance(context);
        final StringRequest request = new StringRequest(Request.Method.GET, baseUrl + hint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("RESP", response);
                Country[] countryList = new Gson().fromJson(response, Country[].class); // gson parse
                ArrayList<Country> countryArrayList = new ArrayList<Country>(Arrays.asList(countryList));
                searchListener.onResponse(countryArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String myError = (null == error) ? error.toString() : "unkown error";
                Log.v("RESP_ERR", error.toString());
                searchListener.onError(error);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                queue.add(request);
            }
        }).start();
    }
}
