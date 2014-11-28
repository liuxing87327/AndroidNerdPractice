package com.dooioo.criminalIntent.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.dooioo.criminalIntent.R;
import com.dooioo.criminalIntent.model.Crime;
import org.joda.time.DateTime;

/**
 * 功能说明：CrimeFragment
 * 作者：liuxing(2014-11-27 23:55)
 */
public class CrimeFragment extends Fragment {

    private static final String TAG = "CrimeFragment";

    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        crime = new Crime();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        initEvents(v);

        return v;
    }

    /**
     * 设置元素和事件
     * @param v
     */
    private void initEvents(View v) {
        titleField = (EditText) v.findViewById(R.id.crime_title);
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged：" + s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setTitle(s.toString());
                Log.d(TAG, "onTextChanged：" + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged：" + s.toString());
            }

        });

        dateButton = (Button) v.findViewById(R.id.crime_date);
        dateButton.setText(DateTime.now().toString("yyy-MM-dd HH:mm:ss"));
        dateButton.setEnabled(false);

        solvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });
    }

}
