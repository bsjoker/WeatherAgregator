package bs.joker.weatherforecast.common.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by 1 on 15.02.2018.
 */

public class SharedPrefStorage implements LocalStorage {
    private Context context;

    public SharedPrefStorage(Context context) {
        this.context = context;
    }


    @Override
    public void writeMessage(String message) {
        context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                .edit().putString("myMessage", message).apply();
        Log.d("SharedPref", message);
    }

    @Override
    public String readMessage() {
        return context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE).getString("myMessage", "");
            }

//    @Override
//    public Observable<String> readMessage() {
//        return Observable.fromCallable(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return context.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE).getString("myMessage", "");
//            }
//        });
//    }
}
