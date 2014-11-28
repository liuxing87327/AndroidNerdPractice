package com.dooioo.criminalIntent.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import com.dooioo.criminalIntent.R;

public class CrimeActivity extends SingleFragmentActivity {

//    /**
//     * Called when the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_crime);
//
//        FragmentManager fm = getFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
//
//        if (fragment == null) {
//            fragment = new CrimeFragment();
//            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
//        }
//    }

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }

}
