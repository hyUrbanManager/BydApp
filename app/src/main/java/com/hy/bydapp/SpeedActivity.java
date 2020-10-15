package com.hy.bydapp;

import android.hardware.bydauto.bodywork.AbsBYDAutoBodyworkListener;
import android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice;
import android.hardware.bydauto.speed.AbsBYDAutoSpeedListener;
import android.hardware.bydauto.speed.BYDAutoSpeedDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示速度相关
 *
 * @author huangye
 */
public class SpeedActivity extends AppCompatActivity {

    private static final String TAG = "@SpeedAct";
    @BindView(R.id.wheel_speed)
    AppCompatTextView mWheelSpeedView;
    @BindView(R.id.wheel_angle)
    AppCompatTextView mWheelAngleView;
    @BindView(R.id.accelerate)
    AppCompatTextView mAccelerateView;
    @BindView(R.id.brake)
    AppCompatTextView mBrakeView;

    private BYDAutoBodyworkDevice mBodyworkDevice;
    private BYDAutoSpeedDevice mSpeedDevice;

    private AbsBYDAutoBodyworkListener mBydAutoBodyworkListener;
    private AbsBYDAutoSpeedListener mBydAutoSpeedListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_speed);
        ButterKnife.bind(this);

        mBodyworkDevice = BYDAutoBodyworkDevice.getInstance(this);
        double s = mBodyworkDevice.getSteeringWheelValue(
                BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_SPEED);
        double a = mBodyworkDevice.getSteeringWheelValue(
                BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_ANGEL);
        Log.d(TAG, "s: " + s + ", a:" + a);
        set(mWheelSpeedView, "方向盘速度: " + s + "'/s");
        set(mWheelAngleView, "方向盘角度: " + a + "'");

        mSpeedDevice = BYDAutoSpeedDevice.getInstance(this);
        int ad = mSpeedDevice.getAccelerateDeepness();
        int bd = mSpeedDevice.getBrakeDeepness();
        Log.d(TAG, "ad: " + ad + ", bd: " + bd);
        set(mAccelerateView, "油门深度: " + ad + "%");
        set(mBrakeView, "刹车深度: " + bd + "%");

        if (mBydAutoBodyworkListener == null) {
            mBydAutoBodyworkListener = new AbsBYDAutoBodyworkListener() {
                @Override
                public void onSteeringWheelValueChanged(int type, double value) {
                    super.onSteeringWheelValueChanged(type, value);
                    Log.d(TAG, "onSteeringWheelValueChanged: " + type + ", " + value);
                    if (type == BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_SPEED) {
                        set(mWheelSpeedView, "方向盘速度: " + value + "'/s");
                    } else if (type == BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_ANGEL) {
                        set(mWheelSpeedView, "方向盘角度: " + value + "'");
                    }
                }
            };
        }
        if (mBydAutoSpeedListener == null) {
            mBydAutoSpeedListener = new AbsBYDAutoSpeedListener() {
                @Override
                public void onAccelerateDeepnessChanged(int value) {
                    super.onAccelerateDeepnessChanged(value);
                    Log.d(TAG, "onAccelerateDeepnessChanged: " + value);
                    set(mAccelerateView, "油门深度: " + value + "%");
                }

                @Override
                public void onBrakeDeepnessChanged(int value) {
                    super.onBrakeDeepnessChanged(value);
                    Log.d(TAG, "onBrakeDeepnessChanged: " + value);
                    set(mBrakeView, "刹车深度: " + value + "%");
                }
            };
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "registerListener.");
        mBodyworkDevice.registerListener(mBydAutoBodyworkListener);
        mSpeedDevice.registerListener(mBydAutoSpeedListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "unregisterListener.");
        mBodyworkDevice.unregisterListener(mBydAutoBodyworkListener);
        mSpeedDevice.unregisterListener(mBydAutoSpeedListener);
    }

    private void set(final TextView textView, final String text) {
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }

}
