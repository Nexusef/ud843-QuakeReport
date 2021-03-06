package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    private String formatMag(double mag) {
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(mag);
    }

    private int getMagnitudeColor(double mag) {
        int colorResourceID;
        int magnitudeFloor = (int) Math.floor(mag);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                colorResourceID = R.color.magnitude1;
                break;
            case 2:
                colorResourceID = R.color.magnitude2;
                break;
            case 3:
                colorResourceID = R.color.magnitude3;
                break;
            case 4:
                colorResourceID = R.color.magnitude4;
                break;
            case 5:
                colorResourceID = R.color.magnitude5;
                break;
            case 6:
                colorResourceID = R.color.magnitude6;
                break;
            case 7:
                colorResourceID = R.color.magnitude7;
                break;
            case 8:
                colorResourceID = R.color.magnitude8;
                break;
            case 9:
                colorResourceID = R.color.magnitude9;
                break;
            default:
                colorResourceID = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), colorResourceID);
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format((dateObject));
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Creates view if there are no reusable ones
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Creates the earthquake object
        Earthquake currentearthquake = getItem(position);

        // Passes in Magnitude
        TextView Magnitude = listItemView.findViewById(R.id.magnitude);
        String formattedMag = formatMag(currentearthquake.getMagnitude());
        Magnitude.setText(formattedMag);

        // Set proper magnitude_circle background
        GradientDrawable magnitudeCircle = (GradientDrawable) Magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(currentearthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        // Separates String into Location and Near
        String[] splitLocation = new String[2];
        if (currentearthquake.getLocation().contains(" of ")) {
            splitLocation = currentearthquake.getLocation().split("(?<= of )");
        } else {
            splitLocation[0] = "Near the";
            splitLocation[1] = currentearthquake.getLocation();
        }

        // Passes in Near location
        TextView Near = listItemView.findViewById(R.id.near);
        Near.setText(splitLocation[0]);

        // Passes in Location
        TextView Location = listItemView.findViewById(R.id.location);
        Location.setText(splitLocation[1]);

        // Creates dateObject that can be formatted
        Date dateObject = new Date(currentearthquake.getTimeInMilliseconds());

        // Passes in Date
        TextView Date = listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        Date.setText(formattedDate);

        // Passes in Time
        TextView Time = listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        Time.setText(formattedTime);

        return listItemView;
    }
}
