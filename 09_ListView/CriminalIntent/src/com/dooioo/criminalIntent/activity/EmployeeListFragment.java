package com.dooioo.criminalIntent.activity;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooioo.common.utils.HttpUtils;
import com.dooioo.criminalIntent.R;
import com.dooioo.criminalIntent.model.Crime;
import com.dooioo.criminalIntent.model.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明：CrimeListFragment
 * 作者：liuxing(2014-11-28 04:24)
 */
public class EmployeeListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.employee_title);
        new EmployeeTask().execute();
    }

    private class EmployeeTask extends AsyncTask<Void, Void, String> {

        private List<Employee> employees;

        @Override
        protected String doInBackground(Void... params) {
            employees = new ArrayList<>();

            String body = HttpUtils.doGet("http://spring87327.duapp.com/api/v1/employee");
            System.out.println(body);
            if (body != null && !"".equals(body)) {
                JSONObject paginate = JSON.parseObject(body);
                JSONArray pageList = paginate.getJSONObject("paginate").getJSONArray("pageList");
                employees = JSON.parseArray(pageList.toJSONString(), Employee.class);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayAdapter<Employee> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, employees);
            setListAdapter(adapter);
        }

    }

}
