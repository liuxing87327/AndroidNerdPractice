package com.dooioo.criminalIntent.activity;

import android.app.Fragment;

public class EmployeeAddActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EmployeeAddFragment();
    }

}
