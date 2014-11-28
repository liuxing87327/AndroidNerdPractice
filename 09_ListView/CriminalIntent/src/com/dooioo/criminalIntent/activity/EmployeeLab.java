package com.dooioo.criminalIntent.activity;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dooioo.common.utils.HttpUtils;
import com.dooioo.criminalIntent.model.Crime;
import com.dooioo.criminalIntent.model.Employee;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 功能说明：CrimeLab
 * 作者：liuxing(2014-11-28 04:17)
 */
public class EmployeeLab {

    private List<Employee> employees;
    private static EmployeeLab rEmployeeLab;
    private Context appContext;

    private EmployeeLab(Context appContext){
        this.appContext = appContext;
        employees = new ArrayList<>();

        String body = HttpUtils.doGet("http://spring87327.duapp.com/api/v1/employee");
        System.out.println(body);
        if (body != null && !"".equals(body)) {
            JSONObject paginate = JSON.parseObject(body);
            JSONArray pageList = paginate.getJSONObject("paginate").getJSONArray("pageList");
            employees = JSON.parseArray(pageList.toJSONString(), Employee.class);
        }
    }

    public static EmployeeLab get(Context c){
        if (rEmployeeLab == null || rEmployeeLab.getEmployees().size() == 0) {
            rEmployeeLab = new EmployeeLab(c.getApplicationContext());
        }
        return rEmployeeLab;
    }

    public Employee getEmployee(UUID uuid) {
        for (Employee employee : employees) {
            if (employee.getId().equals(uuid)) {
                return employee;
            }
        }

        return null;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
