package com.example.aleix.myapplication;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aleix.myapplication.Entity.Eetakemon;

import java.util.ArrayList;
import java.util.List;


public class EetakemonAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Eetakemon> mDataSource;

    final String tag = "MAPACT";

    public EetakemonAdapter(Context context, List<Eetakemon> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        Log.d(tag, "Entra");

        // check if the view already exists if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.list_item_eetakemon, parent, false);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.title);
            holder.subtitleTextView = (TextView) convertView.findViewById(R.id.subtitle);
            holder.detailTextView = (TextView) convertView.findViewById(R.id.detail);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);

            Log.d(tag, "Hace el holder");
        }
        else {

            // skip all the expensive inflation/findViewById and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
            Log.d(tag, "Salta al else");
        }

        // Get relevant subviews of row view
        TextView titleTextView = holder.titleTextView;
        TextView subtitleTextView = holder.subtitleTextView;
        TextView detailTextView = holder.detailTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;

        //Get corresponding recipe for row
        Eetakemon eetak = (Eetakemon) getItem(position);

        // Update row view's textviews to display recipe information
        titleTextView.setText(eetak.getId() + ": " + eetak.getNombre());
        subtitleTextView.setText(eetak.getTipo());

        Glide.with(mContext)
                .load(eetak.getFoto())
                .override(150, 150)
                .into(thumbnailImageView);

       /* if(eetak.getNombre().equals("Bernorlax")) {
            thumbnailImageView.setImageResource(R.drawable.bernorlax);
        }
        else if(eetak.getNombre().equals("Davyphno")) {
            thumbnailImageView.setImageResource(R.drawable.davyphno);
        }
        else if(eetak.getNombre().equals("Francerpie")) {
            thumbnailImageView.setImageResource(R.drawable.francerpie);
        }
        else if(eetak.getNombre().equals("Jesuskou")) {
            thumbnailImageView.setImageResource(R.drawable.jesuskou);
        }
        else if(eetak.getNombre().equals("Jordinine")) {
            thumbnailImageView.setImageResource(R.drawable.jordinine);
        }
        else if(eetak.getNombre().equals("Lluiskarp")) {
            thumbnailImageView.setImageResource(R.drawable.lluiskarp);
        }
        else if(eetak.getNombre().equals("Mewdecerio")) {
            thumbnailImageView.setImageResource(R.drawable.mewdecerio);
        }*/

        return convertView;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView detailTextView;
        public ImageView thumbnailImageView;
    }

}