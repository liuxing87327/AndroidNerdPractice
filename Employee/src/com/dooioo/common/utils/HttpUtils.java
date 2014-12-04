package com.dooioo.common.utils;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

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

    private static final String TAG = "HttpUtils";

    private static String ENCODE = "UTF-8";
    private static String HTTP_AGENT = " Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; QQPinyin 689; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)";

    private static HttpClient httpClient = new DefaultHttpClient();

    /**
     * GET请求
     * @param requestUrl
     * @param params
     * @return
     */
    public static String doGet(String requestUrl, Map<String, String> params) {
        String requestParam = buildParam(params);
        if (requestParam != null && !requestParam.equals("")) {
            requestUrl = requestUrl + "?" + requestParam;
        }

        HttpGet get = new HttpGet(requestUrl);
        get.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
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
                return EntityUtils.toString(entity, ENCODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage(), e);
        }

        return "";
    }

    /**
     * POST请求
     * @param requestUrl
     * @param params
     * @return
     */
    public static String doPost(String requestUrl, Map<String, String> params) {
        HttpPost post = new HttpPost(requestUrl);
        post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        post.setHeader("Accept-Language", "zh-CN");
        post.setHeader("Accept-Encoding", "deflate");
        post.setHeader("User-Agent", HTTP_AGENT);
        post.setHeader("Connection", "close");

        HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 30000);

        List<NameValuePair> requestParams = buildNameValuePairs(params);

        try {
            if (requestParams != null && requestParams.size() > 0) {
                HttpEntity httpEntity = new UrlEncodedFormEntity(requestParams, ENCODE);
                post.setEntity(httpEntity);
            }

            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, ENCODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage(), e);
        }

        return "";
    }

    /**
     * POST请求
     * @param requestUrl
     * @param params
     * @return
     */
    public static String doPut(String requestUrl, Map<String, String> params) {
        HttpPut put = new HttpPut(requestUrl);
        put.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        put.setHeader("Accept-Language", "zh-CN");
        put.setHeader("Accept-Encoding", "deflate");
        put.setHeader("User-Agent", HTTP_AGENT);
        put.setHeader("Connection", "close");

        HttpClientParams.setCookiePolicy(httpClient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 30000);

        List<NameValuePair> requestParams = buildNameValuePairs(params);

        try {
            if (requestParams != null && requestParams.size() > 0) {
                HttpEntity httpEntity = new UrlEncodedFormEntity(requestParams, ENCODE);
                put.setEntity(httpEntity);
            }

            HttpResponse response = httpClient.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, ENCODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage(), e);
        }

        return "";
    }


    private static String buildParam(Map<String, String> params){
        if (params == null) {
            return "";
        }
        List<NameValuePair> requestParam = new ArrayList<>();
        for (String key : params.keySet()) {
            requestParam.add(new BasicNameValuePair(key, params.get(key)));
        }
        return URLEncodedUtils.format(requestParam, "UTF-8");
    }

    private static List<NameValuePair> buildNameValuePairs(Map<String, String> params){
        if (params == null) {
            return null;
        }

        List<NameValuePair> requestParam = new ArrayList<>();
        for (String key : params.keySet()) {
            requestParam.add(new BasicNameValuePair(key, params.get(key)));
        }

        return requestParam;
    }

}
