package com.shell.mobile.countries.model;

import java.io.Serializable;

public class CountryOld implements Serializable {
    private  String name;
    private  String capital;
    private  String flag;
    private  String[] callingCodes;
    private  String region;
    private  String subregion;
    private  String[] timezones;
    private  String[] currencies;
    private  String[] languages;



    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    public String[] getCallingCodes() {
        return callingCodes;
    }

    public String getRegion() {
        return region;
    }

    public String getSubregion() {
        return subregion;
    }

    public String[] getTimezones() {
        return timezones;
    }

    public String[] getCurrencies() {
        return currencies;
    }

    public String[] getLanguages() {
        return languages;
    }




}
