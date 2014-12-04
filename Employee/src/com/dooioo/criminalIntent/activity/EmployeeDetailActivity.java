package com.dooioo.criminalIntent.activity;

import android.app.Fragment;

public class EmployeeDetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EmployeeDetailFragment();
    }

}
