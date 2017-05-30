package com.example.aleix.myapplication.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleix.myapplication.R;

/**
 * Created by aleix on 30/05/2017.
 */
/*
public class ElmeuArrayAdapter extends ArrayAdapter<Eetakemon>{
    private final Context context;
    private final Eetakemon[] values;

    public ElmeuArrayAdapter(Context context, Eetakemon[] values){
        super(context, R.layout.list,values);
        this.context = context;
        this.values = values;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listamcost, parent,false);
        TextView textviem = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textviem.setText(values[position]);

        imageView.setImageResource(R.drawable.bernorlax);

        return rowView;
    }
}
*/