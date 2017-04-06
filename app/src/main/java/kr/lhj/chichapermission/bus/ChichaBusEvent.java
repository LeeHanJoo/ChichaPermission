package kr.lhj.chichapermission.bus;

import java.util.ArrayList;

/**
 * Created by user on 2017-04-06.
 */

public class ChichaBusEvent {

    public boolean permission;
    public ArrayList<String> deniedPermissions;


    public ChichaBusEvent(boolean permission, ArrayList<String> deniedPermissions
    ) {
        this.permission = permission;
        this.deniedPermissions = deniedPermissions;
    }

    public boolean hasPermission() {
        return permission;
    }


    public ArrayList<String> getDeniedPermissions() {
        return deniedPermissions;
    }

}
