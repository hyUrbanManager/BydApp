package com.hy.bydapp.cardata;

import android.content.Context;
import android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice;

/**
 * 真正获取真车数据
 *
 * @author huangye
 */
public class BydCar implements ICar {

    private Context mContext;

    private BYDAutoBodyworkDevice mBodyWorkDevice;

    public BydCar(Context context) {
        this.mContext = context;
        mBodyWorkDevice = BYDAutoBodyworkDevice.getInstance(mContext);
    }

    @Override
    public double getSteeringWheelValue(int type) {
        return mBodyWorkDevice.getSteeringWheelValue(type);
    }
}
