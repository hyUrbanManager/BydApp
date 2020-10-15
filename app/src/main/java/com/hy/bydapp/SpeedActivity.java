package com.hy.bydapp;

import android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice;
import android.hardware.bydauto.speed.BYDAutoSpeedDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SpeedActivity extends AppCompatActivity {

    private static final String TAG = "@SpeedAct";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);
        Log.d(TAG, "onCreate");

        BYDAutoBodyworkDevice bodyworkDevice = BYDAutoBodyworkDevice.getInstance(this);
        double s = bodyworkDevice.getSteeringWheelValue(
                BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_SPEED);
        double a = bodyworkDevice.getSteeringWheelValue(
                BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_ANGEL);
        Log.d(TAG, "s: " + s + ", a:" + a);

        BYDAutoSpeedDevice speedDevice = BYDAutoSpeedDevice.getInstance(this);
        int ad = speedDevice.getAccelerateDeepness();
        int bd = speedDevice.getBrakeDeepness();
        Log.d(TAG, "ad: " + ad + ", bd: " + bd);

    }
}
