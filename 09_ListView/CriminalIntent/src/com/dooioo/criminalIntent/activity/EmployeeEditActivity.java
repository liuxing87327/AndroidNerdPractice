package com.dooioo.criminalIntent.activity;

import android.app.Fragment;

public class EmployeeEditActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EmployeeEditFragment();
    }

}
