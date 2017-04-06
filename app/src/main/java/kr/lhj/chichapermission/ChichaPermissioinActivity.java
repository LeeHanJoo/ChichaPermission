package kr.lhj.chichapermission;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;

import kr.lhj.chichapermission.bus.ChichaBusEvent;
import kr.lhj.chichapermission.bus.ChichaBusProvider;

/**
 * Created by user on 2017-04-06.
 */

public class ChichaPermissioinActivity extends AppCompatActivity {

    String[] permissions;
    String confirmMessage;
    String deniedMessage;
    String packageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getPermissionInfo();
        showConfirmDialog();

        if(needWindowPermission()){
            requestWindowPermission();
        }else{
            checkPermission();
        }



    }

    private void requestWindowPermission() {

        showConfirmDialog();

    }

    private void getPermissionInfo(){
        permissions = getIntent().getStringArrayExtra(Global.EXTRA_PERMISSIONS);
        confirmMessage = getIntent().getStringExtra(Global.EXTRA_CONFIRM_MESSAGE);
        deniedMessage = getIntent().getStringExtra(Global.EXTRA_DENIED_MESSAGE);
        packageName = getIntent().getStringExtra(Global.EXTRA_PACKAGE_NAME);
    }

    private void showConfirmDialog(){
        new AlertDialog.Builder(this)
            .setMessage(confirmMessage)
            .setCancelable(false)
            .setNegativeButton(Global.CONFIRM_TEXT, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkPermission();
                }
            })
            .show();
    }

    private boolean needWindowPermission() {
        for (String permission : permissions) {
            if (permission.equals(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
                return !hasWindowPermission();
            }
        }
        return false;
    }

    private boolean hasWindowPermission(){
        return Settings.canDrawOverlays(getApplicationContext());
    }

    private void checkPermission(){
       // ArrayList<String> needPermissions = new ArrayList<>();

        if (!Settings.System.canWrite(this)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.VIBRATE,
                            android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_WIFI_STATE,
                            android.Manifest.permission.WAKE_LOCK, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.CHANGE_WIFI_STATE, android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                            android.Manifest.permission.READ_PHONE_STATE,
                    }, Global.REQ_PERMISSION_CHECK);
        }else{
            permissionGranted();
        }
    }

    private void permissionGranted(){
        ChichaBusProvider.getInstance().post(new ChichaBusEvent(true, null));
        finish();
        overridePendingTransition(0, 0);
    }

    private void permissionDenied(ArrayList<String> deniedpermissions){
        ChichaBusProvider.getInstance().post(new ChichaBusEvent(false, deniedpermissions));
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }

        if (deniedPermissions.isEmpty()) {
            permissionGranted();
        } else {
            permissionDenied(deniedPermissions);
        }

    }
}
