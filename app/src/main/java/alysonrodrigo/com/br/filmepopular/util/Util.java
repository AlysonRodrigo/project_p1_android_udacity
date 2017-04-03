package alysonrodrigo.com.br.filmepopular.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alyson on 15/03/17.
 */

public class Util {

    public static boolean isConnected(Context c) {
        try {
            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            switch (networkInfo.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return true;
                case ConnectivityManager.TYPE_BLUETOOTH:
                    return true;
                case ConnectivityManager.TYPE_ETHERNET:
                    return true;
                case ConnectivityManager.TYPE_VPN:
                    return true;
                case ConnectivityManager.TYPE_MOBILE:
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
