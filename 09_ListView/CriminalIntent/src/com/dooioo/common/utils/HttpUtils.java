package com.dooioo.common.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 类功能说明：httpclient工具类,基于httpclient 4.x
 * Title: HttpClientUtils.java
 * @author 刘兴
 * @date 2014-3-7 下午7:48:58
 * @version V1.0
 */
public class HttpUtils {

	private static long CONTENT_LENGTH = 2147483647L;
	private static String GZIP = "gzip";
    private static String ENCODE = "UTF-8";
    private static String HTTP_AGENT = " Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; QQPinyin 689; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)";

    public static String doPost(String requestUrl, List<NameValuePair> formParams) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postMethod = new HttpPost(requestUrl);
            postMethod.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8")); //将参数填入POST Entity中

            HttpResponse response = httpClient.execute(postMethod); //执行POST方法
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String doGet(String requestUrl) {
//        //先将参数放入List，再对参数进行URL编码
//        List<BasicNameValuePair> params = new LinkedList<>();
//        params.add(new BasicNameValuePair("param1", "中国"));
//        params.add(new BasicNameValuePair("param2", "value2"));
//
//        //对参数编码
//        String param = URLEncodedUtils.format(params, "UTF-8");
//
//        //baseUrl
//        String requestUrl = "http://ubs.free4lab.com/php/method.php";
//        requestUrl = requestUrl + "?" + param;


        HttpGet getMethod = new HttpGet(requestUrl);
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpResponse response = httpClient.execute(getMethod);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
	 *
	 * 功能说明：组装Post数据
	 * @author 刘兴 
	 * @Date 2014年7月6日 下午4:35:31
	 * @param parms
	 * @return
	 */
    public static List<NameValuePair> buildNameValues(Map<String, Object> parms) {
        List<NameValuePair> formParams = new ArrayList<>();
        for (String key : parms.keySet()) {
            formParams.add(new BasicNameValuePair(key, String.valueOf(parms.get(key))));
        }
        return formParams;
    }

    public static void main(String[] args) {
        String employees = HttpUtils.doGet("http://spring87327.duapp.com/api/v1/employee");
        System.out.println(employees);
    }

}
