package kr.lhj.chichapermission;

import java.util.ArrayList;

/**
 * Created by user on 2017-04-06.
 */

public interface PermissionListener {

    void onPermissionGranted();

    void onPermissionDenied(ArrayList<String> deniedPermissions);
}
