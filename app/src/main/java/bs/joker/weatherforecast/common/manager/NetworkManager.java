package bs.joker.weatherforecast.common.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import bs.joker.MyApplication;
import io.reactivex.Observable;
import io.realm.internal.Util;

/**
 * Created by bakays on 27.10.2017.
 */

public class NetworkManager {

    @Inject
    Context mContext;

    public static final String TAG = "NetworkManager";

    public NetworkManager(){
        MyApplication.getApplicationComponent().inject(this);
    }

    // Проверяем сетевое подключеие

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return ((networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) || Util.isEmulator());
    }

    public Callable<Boolean> isReachableCallable(){
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try{
                    if (!isConnected()){
                        Log.d("NetworManager", "Disconnect");
                        return false;
                    }

                    URL url = new URL("http://api.weatherbit.io");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(2000);
                    urlc.connect();

                    Log.d("NetworManager", "Connect");
                    return true;
                }catch (Exception e) {
                    return false;
                }
            }
        };
    }

    public Observable<Boolean> getNetworkObservable(){
        return Observable.fromCallable(isReachableCallable());
    }
}
