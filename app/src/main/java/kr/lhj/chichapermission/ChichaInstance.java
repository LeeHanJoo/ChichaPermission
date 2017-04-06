package kr.lhj.chichapermission;

import android.content.Context;
import android.content.Intent;

import com.squareup.otto.Subscribe;

import kr.lhj.chichapermission.bus.ChichaBusEvent;
import kr.lhj.chichapermission.bus.ChichaBusProvider;

/**
 * Created by user on 2017-04-06.
 */

public class ChichaInstance {

    public PermissionListener listener;

    public String[] permissions;

    public String permissionMessage;

    public String permissionDeniedMessage;

    public Context context;

    public ChichaInstance(Context context){
        this.context = context;

        ChichaBusProvider.getInstance().register(this);


    }

    public void checkPermissions(){
        Intent intent = new Intent(context,ChichaPermissioinActivity.class);
        intent.putExtra(Global.EXTRA_PERMISSIONS,permissions);
        intent.putExtra(Global.EXTRA_CONFIRM_MESSAGE,permissionMessage);
        intent.putExtra(Global.EXTRA_DENIED_MESSAGE,permissionDeniedMessage);
        intent.putExtra(Global.EXTRA_PACKAGE_NAME, context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

    @Subscribe
    public void onPermissionResult(ChichaBusEvent event) {
        if (event.hasPermission()) {
            listener.onPermissionGranted();
        } else {
            listener.onPermissionDenied(event.getDeniedPermissions());
        }
        ChichaBusProvider.getInstance().unregister(this);
    }
}
