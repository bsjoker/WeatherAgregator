package bs.joker.weatherforecast.common.manager;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import bs.joker.weatherforecast.ui.activity.BaseActivity;
import bs.joker.weatherforecast.ui.frgment.BaseFragment;

/**
 * Created by bakays on 06.12.2017.
 */

public class MyFragmentManager {

    private BaseFragment mCurrentFragment;

    //установка фрагмента

    public void setFragment(BaseActivity activity, BaseFragment fragment, @IdRes int containerId) {
        if (activity != null && !activity.isFinishing()){
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId,fragment);
            transaction.commit();
            activity.fragmentOnScreen(mCurrentFragment);
        }
    }

    //Добавление фрагмента к Активности
    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
