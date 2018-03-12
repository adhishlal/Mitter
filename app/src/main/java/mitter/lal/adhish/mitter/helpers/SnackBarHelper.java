package mitter.lal.adhish.mitter.helpers;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Adhish on 11/03/18.
 */

public class SnackBarHelper {
    static String TAG = "SnackHelper";
    public static Snackbar getSnackBar(View view, String message) {

        if(view!=null) try {

            return Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(Color.RED)
                    .setDuration(Snackbar.LENGTH_SHORT);

        } catch (Exception e) {
            Logger.d(TAG, "error :: " + e.getMessage());
            return null;
        }
        else{
            Logger.d(TAG,"error :: ");
            return null;

        }
    }
}
