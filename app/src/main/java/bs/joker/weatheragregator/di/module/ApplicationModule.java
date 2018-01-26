package bs.joker.weatheragregator.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bakays on 06.12.2017.
 */

@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application Application) {
        mApplication = Application;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return mApplication;
    }
}
