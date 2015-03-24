package pl.michalgorny.wikiatask.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *  Helper class with useful methods to run tests
 */
public class TestHelper {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


}
