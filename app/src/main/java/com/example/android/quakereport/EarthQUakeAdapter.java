package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthQUakeAdapter extends ArrayAdapter <EarthQuake>{

    public static final String LOCATION_OFFSET = " of ";
    public EarthQUakeAdapter(@NonNull Context context, ArrayList<EarthQuake> earthQuakes) {
        super(context,0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView = convertView;
        if( rootView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rootView = inflater.inflate(R.layout.earthquake_item,parent,false);
        }
        EarthQuake currentQuake = getItem(position);
        TextView magText = (TextView) rootView.findViewById(R.id.magnitude);
        Double mag = currentQuake.getMagnitude();
        magText.setText(String.valueOf(mag));

        TextView date = (TextView) rootView.findViewById(R.id.date);
        TextView time = (TextView) rootView.findViewById(R.id.timeAcc);
        TextView locationOffset = (TextView) rootView.findViewById(R.id.location_offset);
        TextView primarylocal = (TextView) rootView.findViewById(R.id.primary_location);

        String dateStr = formatDate(currentQuake.getDateInMIlliseconds());
        date.setText(dateStr);
        String timeStr = formatTime(currentQuake.getDateInMIlliseconds());
        time.setText(timeStr);
        String location = currentQuake.getPlace();
        if(location.contains(LOCATION_OFFSET)) {
            String[] parts = location.split(LOCATION_OFFSET);
            locationOffset.setText(parts[0] + LOCATION_OFFSET);
            primarylocal.setText(parts[1]);
        }else{
            locationOffset.setText("Near the");
            primarylocal.setText(location);
        }
        GradientDrawable drawable = (GradientDrawable) magText.getBackground();
        int color = getMagColor(mag);
        drawable.setColor(color);
        return rootView;
    }

    /**
     * Format date to year,month,day format
     * @param time
     * @return
     */
    public String formatDate(int time){
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy");
        Date date = new Date(time);
        String str = format.format(date);
        return str;
    }

    /**
     * Format date object to time format
     * @param time
     * @return
     */
    public  String formatTime(int time){
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        Date date = new Date(time);
        String str = format.format(date);
        return str;
    }
    public int getMagColor(double mag){
        int knownMag = (int) Math.floor(mag);
        int colorValue;
        switch (knownMag){
            case 1: colorValue= R.color.magnitude1;
            break;
            case 2: colorValue= R.color.magnitude2;
                break;
            case 3: colorValue= R.color.magnitude3;
                break;
            case 4: colorValue= R.color.magnitude4;
                break;
            case 5: colorValue= R.color.magnitude5;
                break;
            case 6: colorValue= R.color.magnitude6;
                break;
            case 7: colorValue= R.color.magnitude7;
                break;
            case 8: colorValue= R.color.magnitude8;
                break;
            case 9: colorValue= R.color.magnitude9;
                break;
            case 10: colorValue= R.color.magnitude10plus;
                break;
            default: colorValue =R.color.magnitude1;
        }
        int colorReal = ContextCompat.getColor(getContext(),colorValue);
   return colorReal;
    }

}
