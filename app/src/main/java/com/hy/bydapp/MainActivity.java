package com.hy.bydapp;

import android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hy.bydapp.cardata.CarFactory;
import com.hy.bydapp.cardata.ICar;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "@MainActivity";

    private ICar mCar;

    private BYDAutoBodyworkDevice mBodyworkDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarFactory.init(this);
        mCar = CarFactory.getInstance().getCar();
        Log.d(TAG, "v: " + mCar.getSteeringWheelValue(0));

        requestBodyworkCommonPermission();
    }

    //申请车身状态类的动态权限
    public void requestBodyworkCommonPermission() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.BYDAUTO_BODYWORK_COMMON)
                != PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.BYDAUTO_BODYWORK_COMMON)) {
                Toast.makeText(this, "之前拒绝了该权限申请，需要清除应用数据，重新申请，否则不能使用！", Toast.LENGTH_SHORT).show();
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.BYDAUTO_BODYWORK_COMMON,}, 1000);
            }
        } else {
            // 已获得车身状态类的动态权限，可以获取实例
            if (mBodyworkDevice == null) {
                //获取车身状态类实例
                mBodyworkDevice = BYDAutoBodyworkDevice.getInstance(this);

                //通过车身状态获得车架号接口获得车机的车架号
                String vin = mBodyworkDevice.getAutoVIN();
                Log.d(TAG, "vin: " + vin);

                Log.d(TAG, "mn: " + mBodyworkDevice.getAutoModelName());
                Log.d(TAG, "sva: " + mBodyworkDevice.getSteeringWheelValue(BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_ANGEL));
                Log.d(TAG, "svs: " + mBodyworkDevice.getSteeringWheelValue(BYDAutoBodyworkDevice.BODYWORK_CMD_STEERING_WHEEL_SPEED));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                    if (permissions[i].equals(android.Manifest.permission.BYDAUTO_BODYWORK_COMMON)) {
                        if (mBodyworkDevice == null) {
                            //获取车身状态类实例
                            mBodyworkDevice = BYDAutoBodyworkDevice.getInstance(this);

                            //通过车身状态获得车架号接口获得车机的车架号
                            String vin = mBodyworkDevice.getAutoVIN();
                            Log.d(TAG, "vin: " + vin);
                        }
                    }
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}