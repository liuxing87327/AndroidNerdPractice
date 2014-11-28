package com.dooioo.criminalIntent.activity;

import android.content.Context;
import com.dooioo.criminalIntent.model.Crime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 功能说明：CrimeLab
 * 作者：liuxing(2014-11-28 04:17)
 */
public class CrimeLab {

    private List<Crime> crimes;
    private static CrimeLab rCrimeLab;
    private Context appContext;

    private CrimeLab(Context appContext){
        this.appContext = appContext;
        crimes = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime#" + i);
            crimes.add(c);
        }
    }

    public static CrimeLab get(Context c){
        if (rCrimeLab == null) {
            rCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return rCrimeLab;
    }

    public Crime getCrime(UUID uuid) {
        for (Crime c : crimes) {
            if (c.getId().equals(uuid)) {
                return c;
            }
        }

        return null;
    }

    public List<Crime> getCrimes() {
        return crimes;
    }

    public void setCrimes(List<Crime> crimes) {
        this.crimes = crimes;
    }
}
