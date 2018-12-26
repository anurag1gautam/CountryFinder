package com.shell.mobile.countries.SearchProvider;

abstract public class Provider   {

    abstract public void getCountryList( String hint, SearchListener searchListener);
}
