package bs.joker.weatheragregator.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import bs.joker.weatheragregator.common.utils.LocalStorage;
import bs.joker.weatheragregator.common.utils.SharedPrefStorage;
import dagger.Module;
import dagger.Provides;

/**
 * Created by 1 on 15.02.2018.
 */

@Module
public class StorageModule {

    Application mApplication;

    public StorageModule(Application mApplication){
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    Application providesApplication(){
        return mApplication;
    }

    @Singleton
    @Provides
    public LocalStorage providesLocalStorage(Application context){
        return new SharedPrefStorage(context);
    }
}
