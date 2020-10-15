package com.hy.bydapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * 申请权限。
 *
 * @author huangye
 */
public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "@PermissionAct";

    private static final String[] PERMISSIONS = {
            Manifest.permission.BYDAUTO_BODYWORK_COMMON,
            Manifest.permission.BYDAUTO_BODYWORK_GET,
            Manifest.permission.BYDAUTO_SPEED_COMMON,
            Manifest.permission.BYDAUTO_SPEED_GET,
    };
    private static final int REQUREST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 申请车身状态类的动态权限，判断是否已经赋予权限
        Log.d(TAG, "check permission: " + Arrays.toString(PERMISSIONS));
        boolean permissionAllHave = true;
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PERMISSION_GRANTED) {
                Log.d(TAG, "permission no have: " + permission);
                permissionAllHave = false;
            }
        }
        if (permissionAllHave) {
            startActivity(new Intent(this, SpeedActivity.class));
            finish();
        } else {
            Log.d(TAG, "requestPermissions: " + Arrays.toString(PERMISSIONS));
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUREST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUREST_CODE) {
            Log.d(TAG, "onRequestPermissionsResult: " + Arrays.toString(permissions));
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "权限申请失败，请重新打开应用允许权限", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
            startActivity(new Intent(this, SpeedActivity.class));
            finish();
        }
    }
}