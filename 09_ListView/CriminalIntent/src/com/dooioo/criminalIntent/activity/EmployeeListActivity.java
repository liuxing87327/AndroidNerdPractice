package com.dooioo.criminalIntent.activity;

import android.app.Fragment;

/**
 * 功能说明：EmployeeListActivity
 * 作者：liuxing(2014-11-28 04:38)
 */
public class EmployeeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EmployeeListFragment();
    }

}
