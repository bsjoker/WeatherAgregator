package bs.joker.weatherforecast.di.module;

import javax.inject.Singleton;

import bs.joker.weatherforecast.common.manager.MyFragmentManager;
import bs.joker.weatherforecast.common.manager.NetworkManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by bakays on 06.12.2017.
 */

@Module
public class ManagerModule {

    @Singleton
    @Provides
    MyFragmentManager provideMyFragmentManager(){
        return new MyFragmentManager();
    }

    @Singleton
    @Provides
    NetworkManager provideNetworkManager(){
        return new NetworkManager();
    }
}
