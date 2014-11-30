package com.dooioo.criminalIntent.activity;

import android.app.Fragment;

public class EmployeeQueryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EmployeeQueryFragment();
    }

}
