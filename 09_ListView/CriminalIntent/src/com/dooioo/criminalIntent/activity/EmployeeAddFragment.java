package com.dooioo.criminalIntent.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.dooioo.common.utils.HttpUtils;
import com.dooioo.criminalIntent.R;
import com.dooioo.criminalIntent.model.Employee;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能说明：EmployeeAddFragment
 * 作者：liuxing(2014-11-27 23:55)
 */
public class EmployeeAddFragment extends Fragment {

    private static final String TAG = "EmployeeEditFragment";
    public static final String EXTRA_USERCODE = "com.dooioo.criminalIntent.activity.userCode";

    private Employee employee;
    private EditText userName;
    private Spinner orgName;
    private Spinner position;
    private Spinner status;
    private EditText createdAt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("新增员工");
        employee = new Employee();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_employee_edit, container, false);

        initEvents(v);

        return v;
    }

    /**
     * 设置元素和事件
     * @param v
     */
    private void initEvents(View v) {
        LinearLayout userCodeLiner = (LinearLayout) v.findViewById(R.id.userCode_liner);
        userCodeLiner.setVisibility(View.GONE);

        userName = (EditText) v.findViewById(R.id.userName);
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employee.setUserName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        orgName = (Spinner) v.findViewById(R.id.orgName);
        ArrayAdapter<CharSequence> orgAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.orgName, android.R.layout.simple_spinner_item);
        orgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orgName.setAdapter(orgAdapter);
        orgName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employee.setOrgName(orgName.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Spinner1: unselected");
            }
        });

        position = (Spinner) v.findViewById(R.id.position);
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.position, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(positionAdapter);
        position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                employee.setPosition(position.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Spinner1: unselected");
            }
        });

        status = (Spinner) v.findViewById(R.id.status);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(statusAdapter);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employee.setStatus(status.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Spinner1: unselected");
            }
        });

        createdAt = (EditText) v.findViewById(R.id.createdAt);
        createdAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        Button save = (Button) v.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EmployeePostTask().execute();
            }
        });

    }

    private class EmployeePostTask extends AsyncTask<Void, Void, String> {

        private String message;

        @Override
        protected String doInBackground(Void... params) {
            Map<String, String> requestParams = new HashMap<>();
            requestParams.put("userName", employee.getUserName());
            requestParams.put("orgName", employee.getOrgName());
            requestParams.put("position", employee.getPosition());
            requestParams.put("status", employee.getStatus());
            requestParams.put("createdAt", new DateTime(employee.getCreatedAt()).toString("yyyy-MM-dd"));

            String requestUrl = "http://spring87327.duapp.com/api/v1/employee";
            String body = HttpUtils.doPost(requestUrl, requestParams);
            if (body != null && !"".equals(body)) {
                if ("ok".equals(JSON.parseObject(body).getString("status"))) {
                    message = "保存成功";
                }
                return null;
            }

            message = "保存失败";

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            showToast(message);

            Intent it = new Intent(getActivity(), EmployeeQueryActivity.class);
            startActivity(it);
            getActivity().finish();
        }
    }

    private void showDateDialog() {
        DateTime dateTime = DateTime.now();
        if (!"".equals(createdAt.getText().toString())){
            dateTime = DateTime.parse(createdAt.getText().toString());
        }

        Dialog dialog = new DatePickerDialog(getActivity(), android.R.layout.select_dialog_item, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                createdAt.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                employee.setCreatedAt(DateTime.parse(createdAt.getText().toString()).getMillis());
            }
        }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());

        dialog.show();
    }

    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
