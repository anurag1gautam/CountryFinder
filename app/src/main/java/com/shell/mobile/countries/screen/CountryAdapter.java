package com.shell.mobile.countries.screen;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmadrosid.svgloader.SvgLoader;
import com.shell.mobile.countries.R;
import com.shell.mobile.countries.model.Country;


import java.util.ArrayList;


public class CountryAdapter extends RecyclerView.Adapter<CountryViewHolder> {
    ArrayList<Country> countryList;
    Context mContext;

    public CountryAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setData(ArrayList<Country> countryList) {
        this.countryList = countryList;
        notifyDataSetChanged();
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_country_row, parent, false));

    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CountryDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("country_detail", countryList.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });

        Country country = countryList.get(position);
        // holder.imageView
        holder.textView.setText(country.getName());

        SvgLoader.pluck() // https://github.com/ar-android/AndroidSvgLoader
                .with((Activity) mContext)
                .setPlaceHolder(android.R.drawable.progress_indeterminate_horizontal, android.R.drawable.stat_notify_error)
                .load(country.getFlag(), holder.imageView);
    }

    @Override
    public int getItemCount() {
        int size = (countryList == null) ? 0 : countryList.size();
        return size;
    }


}
