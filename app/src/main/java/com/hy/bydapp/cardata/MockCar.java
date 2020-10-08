package com.hy.bydapp.cardata;

import android.content.Context;

public class MockCar implements ICar {

    public MockCar(Context context) {

    }

    @Override
    public double getSteeringWheelValue(int type) {
        return -1;
    }
}
