package com.dooioo.criminalIntent.activity;

import android.app.Fragment;

/**
 * 功能说明：CrimeListActivity
 * 作者：liuxing(2014-11-28 04:38)
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
