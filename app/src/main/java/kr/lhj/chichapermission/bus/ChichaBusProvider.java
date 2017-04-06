package kr.lhj.chichapermission.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by user on 2017-04-06.
 */

public class ChichaBusProvider extends Bus {

    private static ChichaBusProvider instance;

    public static ChichaBusProvider getInstance(){
        if(instance == null){
            instance = new ChichaBusProvider();
        }

        return instance;
    }

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if(Looper.myLooper() == Looper.getMainLooper()){
            super.post(event);
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ChichaBusProvider.getInstance().post(event);
                }
            });
        }


    }
}
