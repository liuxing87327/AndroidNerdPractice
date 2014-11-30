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
 * 功能说明：EmployeeEditFragment
 * 作者：liuxing(2014-11-27 23:55)
 */
public class EmployeeEditFragment extends Fragment {

    private static final String TAG = "EmployeeEditFragment";
    public static final String EXTRA_USERCODE = "com.dooioo.criminalIntent.activity.userCode";

    private Employee employee;
    private TextView userCode;
    private EditText userName;
    private Spinner orgName;
    private Spinner position;
    private Spinner status;
    private EditText createdAt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("编辑资料");

        employee = new Employee();

        String userCode = getActivity().getIntent().getStringExtra(EXTRA_USERCODE);
        new EmployeeQueryTask(userCode).execute();
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
        userCode = (TextView) v.findViewById(R.id.userCode);

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
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
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
                employee.setPosition(status.getSelectedItem().toString());
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
                Log.d(TAG, "save");
                new EmployeePostTask().execute();
            }
        });

    }

    private class EmployeeQueryTask extends AsyncTask<Void, Void, String> {

        private String _userCode;

        public EmployeeQueryTask(String _userCode) {
            this._userCode = _userCode;
        }

        @Override
        protected String doInBackground(Void... params) {
            String requestUrl = "http://spring87327.duapp.com/api/v1/employee/" + _userCode;

            String body = HttpUtils.doGet(requestUrl, null);
            if (body != null && !"".equals(body)) {
                employee = JSON.parseObject(body, Employee.class);
            } else {
                employee = new Employee();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            userCode.setText(employee.getUserCode());
            userName.setText(employee.getUserName());
//            orgName.setText(employee.getOrgName());
//            position.setText(employee.getPosition());
//            status.setText(employee.getStatus());
            createdAt.setText(new DateTime(employee.getCreatedAt()).toString("yyy-MM-dd"));
        }
    }

    private class EmployeePostTask extends AsyncTask<Void, Void, String> {

        private String message;

        @Override
        protected String doInBackground(Void... params) {
            Map<String, String> requestParams = new HashMap<>();
            requestParams.put("userCode", employee.getUserCode());
            requestParams.put("userName", employee.getUserName());
            requestParams.put("orgName", employee.getOrgName());
            requestParams.put("position", employee.getPosition());
            requestParams.put("status", employee.getStatus());
            requestParams.put("createdAt", new DateTime(employee.getCreatedAt()).toString("yyyy-MM-dd"));

            String requestUrl = "http://spring87327.duapp.com/api/v1/employee/" + employee.getUserCode();
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

            Intent it = new Intent(getActivity(), EmployeeDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(EmployeeDetailFragment.EXTRA_USERCODE, employee.getUserCode());
            it.putExtras(bundle);
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
