package com.dooioo.criminalIntent.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooioo.common.utils.HttpUtils;
import com.dooioo.criminalIntent.R;
import com.dooioo.criminalIntent.model.Employee;
import com.dooioo.criminalIntent.widget.ListViewAdapter;
import org.joda.time.DateTime;

import java.util.*;

/**
 * 功能说明：EmployeeQueryFragment
 * 作者：liuxing(2014-11-27 23:55)
 */
public class EmployeeQueryFragment extends Fragment {

    private static final String TAG = "EmployeeQueryFragment";

    private EditText keyword;
    private Spinner status;
    private EditText dateFrom;
    private EditText dateTo;
    private Button queryButton;

    private ListView employeeList;
    public static List<Employee> rEmployees = new ArrayList<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String userCode = data.getStringExtra(EmployeeDetailFragment.EXTRA_USERCODE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_employee_query, container, false);

        initEvents(v);

        return v;
    }

    /**
     * 设置元素和事件
     * @param v
     */
    private void initEvents(View v) {
        keyword = (EditText) v.findViewById(R.id.keyword);

        status = (Spinner) v.findViewById(R.id.status);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.status, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        status.setAdapter(adapter);

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Spinner1: position=" + position + " id=" + id);
                query();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Spinner1: unselected");
            }
        });

        dateFrom = (EditText) v.findViewById(R.id.dateFrom);
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateFromDialog();
            }
        });

        dateTo = (EditText) v.findViewById(R.id.dateTo);
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateToDialog();
            }
        });

        employeeList = (ListView) v.findViewById(R.id.listView);
        employeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            expressItemClick(position);
            }
        });

        queryButton = (Button) v.findViewById(R.id.queryApi);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                query();
            }
        });

        ImageButton add = (ImageButton) v.findViewById(R.id.addEmp);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmployeeAddActivity.class);
                startActivity(intent);
            }
        });

    }

    private void expressItemClick(int postion){
        Employee employee = rEmployees.get(postion);
        Intent it = new Intent(getActivity(), EmployeeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(EmployeeDetailFragment.EXTRA_USERCODE, employee.getUserCode());
        it.putExtras(bundle);
        startActivity(it);
    }

    /**
     * 查询api数据
     */
    private void query(){
        String statusValue = status.getSelectedItem().toString();
        String keywordValue = keyword.getText().toString();
        String dateFromValue = dateFrom.getText().toString();
        String dateToValue = dateTo.getText().toString();
        new EmployeeTask(statusValue, keywordValue, dateFromValue, dateToValue).execute();
    }

    private class EmployeeTask extends AsyncTask<Void, Void, String> {

        private String statusValue;
        private String keywordValue;
        private String dateFromValue;
        private String dateToValue;

        public EmployeeTask(String statusValue, String keywordValue, String dateFromValue, String dateToValue) {
            this.statusValue = statusValue;
            this.keywordValue = keywordValue;
            this.dateFromValue = dateFromValue;
            this.dateToValue = dateToValue;
        }

        @Override
        protected String doInBackground(Void... params) {
            Map<String, String> requestParams = new HashMap<>();
            String requestUrl = "http://spring87327.duapp.com/api/v1/employee";
            if (statusValue != null && !statusValue.equals("")){
                requestParams.put("status", statusValue);
            }

            if (keywordValue != null && !keywordValue.equals("")){
                requestParams.put("keyword", keywordValue);
            }

            if (dateFromValue != null && !dateFromValue.equals("")){
                requestParams.put("dateFrom", dateFromValue);
            }

            if (dateToValue != null && !dateToValue.equals("")){
                requestParams.put("dateTo", dateToValue);
            }

            String body = HttpUtils.doGet(requestUrl, requestParams);
            System.out.println(body);
            if (body != null && !"".equals(body)) {
                JSONObject paginate = JSON.parseObject(body);
                JSONArray pageList = paginate.getJSONObject("paginate").getJSONArray("pageList");
                rEmployees = JSON.parseArray(pageList.toJSONString(), Employee.class);
            } else {
                rEmployees.clear();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
//            employeeList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, rEmployees));
            //注意SimpleAdapter适配器用法
//            ArrayAdapter adapter = new ArrayAdapter(getActivity(), rEmployees,
//                    R.layout.fragment_employee_list,
//                    new String[]{"userCode", "userName", "picture"},
//                    new int[]{R.id.userCode, R.id.userName, R.id.picture});
            ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), rEmployees); //创建适配器
            employeeList.setAdapter(listViewAdapter);
        }
    }

    /**
     * 创建日期及时间选择对话框
     */
    private void onDateFromDialog() {
        DateTime dateTime = DateTime.now();
        if (!"".equals(dateFrom.getText().toString())){
            dateTime = DateTime.parse(dateFrom.getText().toString());
        }

        Dialog dialog = new DatePickerDialog(getActivity(), android.R.layout.select_dialog_item, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
            dateFrom.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());

        dialog.show();
    }

    /**
     * 创建日期及时间选择对话框
     */
    private void onDateToDialog() {
        DateTime dateTime = DateTime.now();
        if (!"".equals(dateTo.getText().toString())){
            dateTime = DateTime.parse(dateTo.getText().toString());
        }

        Dialog dialog = new DatePickerDialog(getActivity(), android.R.layout.select_dialog_item, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker dp, int year, int month, int dayOfMonth) {
                dateTo.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());

        dialog.show();
    }

}
