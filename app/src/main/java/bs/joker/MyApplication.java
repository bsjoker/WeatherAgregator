package bs.joker;

import android.app.Application;

import bs.joker.weatherforecast.di.component.ApplicationComponent;
import bs.joker.weatherforecast.di.component.DaggerApplicationComponent;
import bs.joker.weatherforecast.di.module.ApplicationModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bakays on 06.12.2017.
 */

public class MyApplication extends Application {


    private static ApplicationComponent sApplicationComponent;

    private static MyApplication myApplicationInstance;
    public static MyApplication getInstance(){
        return myApplicationInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initComponent();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        myApplicationInstance = this;
    }

    private void initComponent() {
        sApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getApplicationComponent(){
        return sApplicationComponent;
    }
}
