package bs.joker.weatheragregator.di.module;

import javax.inject.Singleton;

import bs.joker.weatheragregator.common.manager.MyFragmentManager;
import bs.joker.weatheragregator.common.manager.NetworkManager;
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
