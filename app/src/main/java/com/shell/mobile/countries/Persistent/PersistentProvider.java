package com.shell.mobile.countries.Persistent;

import android.content.Context;
import com.shell.mobile.countries.SearchProvider.Provider;
import com.shell.mobile.countries.SearchProvider.SearchListener;
import com.shell.mobile.countries.model.Country;

import java.util.ArrayList;


public class PersistentProvider extends Provider {

    Context context;
    ArrayList<Country> mCountryList;
    CountryDatabaseHelper countryDatabaseHelper;

    public PersistentProvider(Context context) {
        this.context = context;
        countryDatabaseHelper = new CountryDatabaseHelper(context);

    }

    @Override
    public void getCountryList(String query, SearchListener searchListener) {
        try {
            mCountryList=countryDatabaseHelper.searchCountry(query);
            searchListener.onResponse(mCountryList);
        } catch (Exception e) {
            searchListener.onError(e);
        }
    }

    public ArrayList<Country> fetchAllCountry() {
        return countryDatabaseHelper.getAllCountry();
    }

    public long saveCountry(Country country) {
        return countryDatabaseHelper.addCountry(country);

    }
}
