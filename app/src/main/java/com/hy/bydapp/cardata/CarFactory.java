package com.hy.bydapp.cardata;

import android.content.Context;

public class CarFactory {

    private static CarFactory sInstance;

    public static CarFactory getInstance() {
        return sInstance;
    }

    public static void init(Context context) {
        sInstance = new CarFactory(context);
    }

    private Context mContext;

    public CarFactory(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public ICar getCar() {
        return new BydCar(mContext);
    }
}
