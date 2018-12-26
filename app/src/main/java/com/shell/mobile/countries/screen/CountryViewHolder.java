package com.shell.mobile.countries.screen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shell.mobile.countries.R;

public class CountryViewHolder extends RecyclerView.ViewHolder {
     ImageView imageView;
     TextView textView;


    public CountryViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image_view_country);
        textView = (TextView) itemView.findViewById(R.id.text_view_country);
    }
}
