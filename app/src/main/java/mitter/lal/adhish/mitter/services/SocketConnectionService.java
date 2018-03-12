package mitter.lal.adhish.mitter.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import mitter.lal.adhish.mitter.networks.sockethelper.SocketHelper;


/**
 * Created by Adhish on 11/03/18.
 */

public class SocketConnectionService extends IntentService {

    private SocketHelper mSocketHelper;


    public SocketConnectionService() {
        super("SocketConnectionService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        mSocketHelper = new SocketHelper(getApplicationContext());
        mSocketHelper.connect_socket();

        return START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
