package com.shell.mobile.countries.SearchProvider;

import com.shell.mobile.countries.model.Country;

import java.util.ArrayList;
import java.util.List;

public interface SearchListener  {

    void onResponse(ArrayList<Country> countryList);
    void onError(Throwable throwable);

}
