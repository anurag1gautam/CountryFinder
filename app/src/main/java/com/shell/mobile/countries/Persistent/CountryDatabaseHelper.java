package com.shell.mobile.countries.Persistent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.shell.mobile.countries.model.Country;

import java.util.ArrayList;

public class CountryDatabaseHelper extends SQLiteOpenHelper {
    private static String COUNTRY_DB_NAME = "countryDB";
    private static int COUNTRY_DB_VERSION = 1;

    private static String COUNTRY_TABLE = "countryTable";

    private static String COLUMN_ID = "id";
    private static String COLUMN_COUNTRY_NAME = "countryName";
    private static String COLUMN_COUNTRY_DETAIL = "countryDetail";


    public CountryDatabaseHelper(Context context) {
        super(context, COUNTRY_DB_NAME, null, COUNTRY_DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + COUNTRY_TABLE + "(" +
                COLUMN_ID + " integer primary key autoincrement, " +
                COLUMN_COUNTRY_NAME + " text NOT NULL UNIQUE, " +
                COLUMN_COUNTRY_DETAIL + " text " +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + COUNTRY_TABLE);
        onCreate(sqLiteDatabase);
    }

    ArrayList<Country> getAllCountry() {
        ArrayList<Country> countryList = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + COUNTRY_TABLE, null);
            if (cursor.moveToFirst()) {
                countryList = new ArrayList<Country>();
                do {
                    Country country = new Gson().fromJson(cursor.getString(2), Country.class); // gson parse
                    countryList.add(country);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            countryList = null;
        }
        return countryList;
    }

    ArrayList<Country> searchCountry(String keyword) {
        ArrayList<Country> countryList = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + COUNTRY_TABLE + " where " + COLUMN_COUNTRY_NAME + " like ?", new String[]{"%" + keyword + "%"});
            if (cursor.moveToFirst()) {
                countryList = new ArrayList<Country>();
                do {
                    Country country = new Gson().fromJson(cursor.getString(2), Country.class); // gson parse
                    countryList.add(country);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            countryList = null;
        }
        return countryList;
    }

    long addCountry(Country country) {
        long result = 1;
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            long res = -1;
            String sql = "INSERT INTO " + COUNTRY_TABLE + " VALUES ( null, ?, ?)";
            Gson gson = new Gson();
            sqLiteDatabase.execSQL(sql, new String[]{country.getName(), gson.toJson(country),});
        } catch (Exception e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                result = -2;
            } else {
                result = -1;
            }
            Log.v("SQLERR", e.getMessage());

        }
        return result;
    }
}
