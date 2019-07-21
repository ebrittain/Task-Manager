package com.example.RecklessLabs.TaskManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;

public class RequestHelp extends AppCompatActivity {

    private LineChart lineChart;
    private DatabaseHelper myDB;
    private ArrayList<Task> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stat_popup);

        lineChart = findViewById(R.id.lineChart);
        myDB = new DatabaseHelper(this);
        tasks = myDB.getAllData();

        int scalingFactor = 1000;   //seconds
        ArrayList<Long> points = myDB.getPoints(tasks.get(0).getName());
        if(Collections.max(points)/1000>120) //greater than 2 minutes
            scalingFactor*=60;    //convert to minutes
        if(Collections.max(points)/1000>3600)    //greather than 1 hour
            scalingFactor*=3600;    //convert to hours
        ArrayList<Entry> yVals = new ArrayList<>();

        lineChart = findViewById(R.id.lineChart);
        lineChart.invalidate();
        lineChart.getDescription().setText("Activity");
        lineChart.getLegend().setEnabled(false);

        int count = 0;
        for (Long time : points) {
            yVals.add(new Entry(count++, (float) time / scalingFactor));
        }

        LineDataSet set1 = new LineDataSet(yVals, "Dataset 1");
        set1.setFillAlpha(110);

        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        lineChart.setData(data);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setSpaceMin(10f);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setAxisMinimum(0f);
        yAxisRight.setSpaceMin(10f);
    }


}
