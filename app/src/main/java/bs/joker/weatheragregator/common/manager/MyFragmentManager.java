package bs.joker.weatheragregator.common.manager;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;

import bs.joker.weatheragregator.ui.activity.ScrollingActivity;
import bs.joker.weatheragregator.ui.frgment.BaseFragment;

/**
 * Created by bakays on 06.12.2017.
 */

public class MyFragmentManager {

    private BaseFragment mCurrentFragment;

    //установка фрагмента

    public void setFragment(ScrollingActivity activity, BaseFragment fragment, @IdRes int containerId) {
        if (activity != null && !activity.isFinishing()){
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(containerId,fragment);
//            commitAddTransaction(activity, fragment, transaction, false);
            transaction.commit();
            activity.fragmentOnScreen(mCurrentFragment);
        }
    }

//    private void commitAddTransaction(ScrollingActivity activity, BaseFragment fragment, FragmentTransaction transaction, boolean b) {
//    }
}
