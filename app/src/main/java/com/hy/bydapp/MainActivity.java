package com.hy.bydapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hy.bydapp.cardata.CarFactory;
import com.hy.bydapp.cardata.ICar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "@MainActivity";

    private ICar mCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarFactory.init(this);
        mCar = CarFactory.getInstance().getCar();
        Log.d(TAG, "v: " + mCar.getSteeringWheelValue(0));
    }
}