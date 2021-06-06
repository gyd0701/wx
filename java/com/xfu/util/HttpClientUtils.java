package com.xfu.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.Cookie;

public class HttpClientUtils {

	private static String charSet = "UTF-8";
	private static CloseableHttpClient httpClient = null;
	private static CloseableHttpResponse response = null;


	/**
	 * http的Get请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doHttpGet(String url, Map<String, String> param) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;

		try {
			httpclient = HttpClients.createDefault();
			if (param != null && !param.isEmpty()) {
				// 参数集合
				List<NameValuePair> getParams = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : param.entrySet()) {
					getParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(getParams), "UTF-8");
			}
			// 发送gey请求
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}




	public static CloseableHttpResponse doHttpGetResponse(String url, Map<String, String> param) {
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;

		try {
			httpclient = HttpClients.createDefault();
			if (param != null && !param.isEmpty()) {
				// 参数集合
				List<NameValuePair> getParams = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : param.entrySet()) {
					getParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(getParams), "UTF-8");
			}
			// 发送gey请求
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}