package com.dooioo.criminalIntent.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.dooioo.common.utils.HttpUtils;
import com.dooioo.criminalIntent.R;
import com.dooioo.criminalIntent.model.Employee;
import org.joda.time.DateTime;

/**
 * 功能说明：EmployeeDetailFragment
 * 作者：liuxing(2014-11-27 23:55)
 */
public class EmployeeDetailFragment extends Fragment {

    private static final String TAG = "EmployeeDetailFragment";
    public static final String EXTRA_USERCODE = "com.dooioo.criminalIntent.activity.userCode";

    private Employee employee;
    private TextView userCode;
    private TextView userName;
    private TextView orgName;
    private TextView position;
    private TextView status;
    private TextView createdAt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("员工资料");
        String userCode = getActivity().getIntent().getStringExtra(EXTRA_USERCODE);

        new EmployeeTask(userCode).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_employee_detail, container, false);

        initEvents(v);

        return v;
    }

    /**
     * 设置元素和事件
     * @param v
     */
    private void initEvents(View v) {

        userCode = (TextView) v.findViewById(R.id.userCode);

        userName = (TextView) v.findViewById(R.id.userName);

        orgName = (TextView) v.findViewById(R.id.orgName);

        position = (TextView) v.findViewById(R.id.position);

        status = (TextView) v.findViewById(R.id.status);

        createdAt = (TextView) v.findViewById(R.id.createdAt);

        Button edit = (Button) v.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent it = new Intent(getActivity(), EmployeeEditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(EmployeeEditFragment.EXTRA_USERCODE, employee.getUserCode());
            it.putExtras(bundle);
            startActivity(it);
            getActivity().finish();
            }
        });

    }

    private class EmployeeTask extends AsyncTask<Void, Void, String> {

        private String _userCode;

        public EmployeeTask(String _userCode) {
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
            orgName.setText(employee.getOrgName());
            position.setText(employee.getPosition());
            status.setText(employee.getStatus());
            createdAt.setText(new DateTime(employee.getCreatedAt()).toString("yyyy-MM-dd"));
        }
    }

}
