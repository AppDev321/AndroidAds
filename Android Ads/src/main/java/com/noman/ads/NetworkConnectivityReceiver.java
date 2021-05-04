package com.noman.ads;

public class NetworkConnectivityReceiver {

    public static boolean isConnected() {
        /*final ConnectivityManager connMgr = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isConnected() || mobile.isConnected()) {
            return true;
        } else {
            return false;
        }
     */
        return true;
    }


}
