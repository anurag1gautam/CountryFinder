package com.shell.mobile.countries.screen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.shell.mobile.countries.R;
import com.shell.mobile.countries.SearchProvider.ProviderFactory;
import com.shell.mobile.countries.model.Country;
import com.shell.mobile.countries.utility.Utility;

import java.util.List;

public class CountryDetail extends Activity implements View.OnClickListener {
    Country mCountry;

    TextView mCountryName;
    ImageView mCountryFlag;
    TextView mCountryCapital;
    TextView mCountryCallingCode;
    TextView mCountryRegion;
    TextView mCountrySubRegion;
    TextView mCountryCurrencies;
    TextView mCountryLanguages;
    TextView mCountryTimezone;
    Button mSaveCountry;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mCountry = (Country) extras.getSerializable("country_detail");
        }
        mCountryFlag = (ImageView) findViewById(R.id.country_detail_image);
        mCountryName = (TextView) findViewById(R.id.country_name);
        mCountryCapital = (TextView) findViewById(R.id.country_capital);
        mCountryCallingCode = (TextView) findViewById(R.id.country_calling_code);
        mCountryRegion = (TextView) findViewById(R.id.country_region);
        mCountrySubRegion = (TextView) findViewById(R.id.country_subregion);
        mCountryCurrencies = (TextView) findViewById(R.id.country_currencies);
        mCountryLanguages = (TextView) findViewById(R.id.country_languages);
        mCountryTimezone = (TextView) findViewById(R.id.country_time_zone);
        mSaveCountry = (Button) findViewById(R.id.country_detail_save);
        mSaveCountry.setOnClickListener(this);

        updateUi(mCountry);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.country_detail_save) {
            long res = ProviderFactory.getInstance(this).getPersistentProvider().saveCountry(mCountry);
            if (res > 0) {
                Toast.makeText(CountryDetail.this, "Country saved",
                        Toast.LENGTH_SHORT).show();
            } else if (res == -2) {
                Toast.makeText(CountryDetail.this, mCountry.getName() +" country already saved",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CountryDetail.this, "Country not saved",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void updateUi(Country country) {


        mCountryName.setText(country.getName());
        mCountryCapital.setText(country.getCapital());
        mCountryCallingCode.setText(country.getCallingCodes().toString());
        mCountryRegion.setText(country.getRegion());
        mCountrySubRegion.setText(country.getSubregion());
        mCountryTimezone.setText(country.getTimezones().toString());
        mCountryCurrencies.setText(country.getCurrencies().toString());
        mCountryLanguages.setText(country.getLanguages().toString());


        SvgLoader.pluck() // https://github.com/ar-android/AndroidSvgLoader
                .with(CountryDetail.this)
                .setPlaceHolder(android.R.drawable.progress_indeterminate_horizontal, android.R.drawable.stat_notify_error)
                .load(mCountry.getFlag(), mCountryFlag);

        if (Utility.isNetworkConnected(CountryDetail.this)) {
            mSaveCountry.setEnabled(true);
        } else {
            mSaveCountry.setEnabled(false);
        }
    }

    private String getString(List<String> list) {

        String[] stringArray = new String[list.size()];
        stringArray = list.toArray(stringArray);
        return stringArray.toString();
    }
}
