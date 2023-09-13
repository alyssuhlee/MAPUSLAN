package com.example.mapuslan.userUI.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mapuslan.R;

public class PointsAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the list of recyclable materials
    private final String[] materials;

    //to store the list of points conversion
    private final String[] points;

    public PointsAdapter(Activity context, String[] materialsParam, String[] pointsParam){

        super(context, R.layout.points_row , materialsParam);
        this.context=context;
        this.materials = materialsParam;
        this.points = pointsParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.points_row, null,true);

        //this code gets references to objects in the points_row.xml file
        TextView materialTextField = (TextView) rowView.findViewById(R.id.row_material);
        TextView pointTextField = (TextView) rowView.findViewById(R.id.row_points);

        //this code sets the values of the objects to values from the arrays
        materialTextField.setText(materials[position]);
        pointTextField.setText(points[position]);

        return rowView;

    };

}

