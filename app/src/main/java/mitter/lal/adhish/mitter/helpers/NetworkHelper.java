package mitter.lal.adhish.mitter.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Adhish on 11/03/18.
 */

public class NetworkHelper {

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else if (networkInfo == null) {
                return false;
            }
        }
        return false;
    }
}
