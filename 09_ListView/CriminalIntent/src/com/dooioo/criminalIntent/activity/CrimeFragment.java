package com.dooioo.criminalIntent.activity;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.dooioo.criminalIntent.model.Crime;
import com.dooioo.criminalIntent.model.Employee;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能说明：CrimeFragment
 * 作者：liuxing(2014-11-27 23:55)
 */
public class CrimeFragment extends Fragment {

    private static final String TAG = "CrimeFragment";
    private static final String HTTP_AGENT = " Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.10 Safari/537.36";

    private Crime crime;
    private EditText titleField;
    private Button dateButton;
    private CheckBox solvedCheckBox;
    private Button queryButton;

    private ListView employeeList;

    private HttpClient httpClient = new DefaultHttpClient();

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

        employeeList = (ListView) v.findViewById(R.id.listView);

        solvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        solvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setSolved(isChecked);
            }
        });

        queryButton = (Button) v.findViewById(R.id.queryApi);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                new EmployeeTask(employeeList).execute();
            }
        });
    }

    private String queryApi() {
        HttpGet get = new HttpGet("http://spring87327.duapp.com/api/v1/employee");
        get.setHeader("Content-Type", "application/json;charset=UTF-8");
        get.setHeader("Accept-Language", "zh-CN");
        get.setHeader("Accept-Encoding", "deflate");
        get.setHeader("User-Agent", HTTP_AGENT);
        get.setHeader("Connection", "close");

        HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 30000);

        try {
            HttpResponse response = httpClient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private class EmployeeTask extends AsyncTask<Void, Void, String> {

        private ListView employeeList;
        private List<Employee> employees;

        public EmployeeTask(ListView employeeList) {
            this.employeeList = employeeList;
        }

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
            employeeList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, employees));
        }

    }

}
