package com.dooioo.common.utils;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

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

	private static long CONTENT_LENGTH = 2147483647L;
	private static String GZIP = "gzip";
    private static String ENCODE = "UTF-8";
    private static String HTTP_AGENT = " Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; QQPinyin 689; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729)";

    private static HttpClient httpClient = new DefaultHttpClient();

    public static String doGet(String requestUrl) {
        HttpGet get = new HttpGet(requestUrl);
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
            Log.d(TAG, e.getMessage(), e);
        }

        return "";
    }

}
