package com.shell.mobile.countries.screen;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.shell.mobile.countries.R;
import com.shell.mobile.countries.SearchProvider.Provider;
import com.shell.mobile.countries.SearchProvider.ProviderFactory;
import com.shell.mobile.countries.SearchProvider.SearchListener;
import com.shell.mobile.countries.model.Country;
import com.shell.mobile.countries.utility.Utility;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static Provider mProvider;
    ArrayList<Country> countryList;
    private RecyclerView recyclerViewCountry;
    private CountryAdapter countryAdapter;
    private TextView onlineStateTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onlineStateTextView =(TextView) findViewById(R.id.onlineState);
        recyclerViewCountry = (RecyclerView) findViewById(R.id.recycler_view_country);
        countryAdapter = new CountryAdapter(this);
        recyclerViewCountry.setAdapter(countryAdapter);
        recyclerViewCountry.setLayoutManager(new LinearLayoutManager(this));

        countryList = new ArrayList<Country>();
        if (!Utility.isNetworkConnected(this)) { // offline
            countryList = ProviderFactory.getInstance(this).getPersistentProvider().fetchAllCountry();
            countryAdapter.setData(countryList);
            onlineStateTextView.setText(R.string.country_offline_search);
            onlineStateTextView.setTextColor(Color.GRAY);
        }else{
            onlineStateTextView.setText(R.string.country_online_search);
            onlineStateTextView.setTextColor(Color.GREEN);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setFocusable(true);
        searchView.setIconified(false);

        MenuItem searchMenuItem = menu.findItem( R.id.search ); // get my MenuItem with placeholder submenu
        searchMenuItem.expandActionView(); // Expand the search menu item in order to show by default the query


        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (Utility.isNetworkConnected(this)) { // online
            if (null != s && s.isEmpty()) {
                countryAdapter.setData(null);
            }else if (null != s && !s.isEmpty()) {
                ProviderFactory.getInstance(this).getNetworkProvider().getCountryList(s, new SearchListener() {
                    @Override
                    public void onResponse(ArrayList<Country> countryList) {
                        Log.v("RESP", countryList.toString());
                        countryAdapter.setData(countryList);
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        Log.v("ERR", throwable.toString());
                        countryAdapter.setData(null);
                    }
                });
            }


        }else {// offline
            if (null != s && s.isEmpty()) {
                countryList = ProviderFactory.getInstance(this).getPersistentProvider().fetchAllCountry();
                countryAdapter.setData(countryList);
            } else if (null != s && !s.isEmpty()) {
                ProviderFactory.getInstance(this).getPersistentProvider().getCountryList(s, new SearchListener() {
                    @Override
                    public void onResponse(ArrayList<Country> countryList) {

                        countryAdapter.setData(countryList);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.v("ERR", throwable.toString());
                        countryAdapter.setData(null);
                    }
                });
            }
        }


        return true;
    }
}
