package com.dooioo.criminalIntent.activity;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.dooioo.criminalIntent.R;
import com.dooioo.criminalIntent.model.Crime;

import java.util.List;

/**
 * 功能说明：CrimeListFragment
 * 作者：liuxing(2014-11-28 04:24)
 */
public class CrimeListFragment extends ListFragment {

    private List<Crime> crimes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crime_title);
        crimes = CrimeLab.get(getActivity()).getCrimes();

        ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(), android.R.layout.simple_list_item_1, crimes);
        setListAdapter(adapter);
    }
}
