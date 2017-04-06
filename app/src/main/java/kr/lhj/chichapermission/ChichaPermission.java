package kr.lhj.chichapermission;

import android.content.Context;
import android.os.Build;

/**
 * Created by user on 2017-04-06.
 */

public class ChichaPermission {

    private static ChichaInstance instance;

    public ChichaPermission (Context context){
        instance = new ChichaInstance(context);
    }

    public ChichaPermission setPermissionListener(PermissionListener listener){
        instance.listener = listener;
        return this;
    }

    public ChichaPermission setConfirmMessage(String permissionMessage){
        instance.permissionMessage = permissionMessage;
        return this;
    }

    public ChichaPermission setDeniedMessage(String permissionDeniedMessage){
        instance.permissionDeniedMessage = permissionDeniedMessage;
        return this;
    }

    public ChichaPermission setPermissions(String... permissions){
        instance.permissions = permissions;
        return this;
    }

    public void goCheck(){
        if(instance.listener == null){
            throw new NullPointerException("You must setPermissionListener() on ChichaPermission");
        }else if (ObjectUtils.isEmpty(instance.permissions)) {
            throw new NullPointerException("You must setPermissions() on TedPermission");
        }

        if (ObjectUtils.isEmpty(instance.permissions)) {
            throw new NullPointerException("You must set permission");
        }


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            DebugLog.d("preMarshmallow");
            instance.listener.onPermissionGranted();

        } else {
            DebugLog.d("Marshmallow");
            instance.checkPermissions();
        }
    }
}
