package com.xfu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	private static CloseableHttpClient httpClient = null;
	private static CloseableHttpResponse response = null;
	
	
	/**
	 * HttpGet方法,返回一个String
	 * */
	public static String doHttpGet(String url, Map<String, String> param, Map<String, String> headers, Cookie[] cookie) {
		try {
			httpClient = SSLClient.createSSLClientDefault();
			if (param != null && !param.isEmpty()) {
				List<NameValuePair> getParams = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : param.entrySet()) {
					getParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(getParams), "UTF-8");
			}
			HttpGet httpGet = new HttpGet(url);
			String cook = "";
			for(Cookie c : cookie) {
				cook+=c.getName() + "=" + c.getValue() + "; ";
			}
			httpGet.addHeader(new BasicHeader("Cookie", cook));
			response = httpClient.execute(httpGet);
			Header[] myHeader = response.getAllHeaders();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * HttpGet方法,返回一个String
	 * */
	public static String doHttpGet(String url) {
		try {
			httpClient = SSLClient.createSSLClientDefault();
			HttpGet httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			Header[] myHeader = response.getAllHeaders();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * HttpGet方法,返回一个Byte
	 * */
	public static byte[] doHttpGetWithByte(String url, Map<String, String> param, Map<String, String> headers) {
		try {
			httpClient = SSLClient.createSSLClientDefault();
			if (param != null && !param.isEmpty()) {
				List<NameValuePair> getParams = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : param.entrySet()) {
					getParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(getParams), "UTF-8");
			}
			HttpGet httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			Header[] myHeader = response.getAllHeaders();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toByteArray(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 返回字节型内容的Post
	 * 参数是字节的Body,返回值是二进制内容
	 * */
	public static Map<String,Object> doHttpsPost(String url, byte[] b, Map<String, String> headers) {
		try {
			httpClient = SSLClient.createSSLClientDefault();//HttpClients.createDefault();//
			HttpPost httpPost = new HttpPost(url);
			//BYTE 数组发送
			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(b);
			httpPost.setEntity(byteArrayEntity);
			//设置请求Header
			Set<Entry<String, String>> heads = headers.entrySet();
			for(Entry<String, String> h : heads) {
				httpPost.setHeader(h.getKey(), h.getValue());
			}
			//test
			Header[] myHeader = httpPost.getAllHeaders();
			for(Header h : myHeader) {
				System.out.println(h.getName() + "  :   " + h.getValue());
			}
			response = httpClient.execute(httpPost);
			byte[] content = null;
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					content = EntityUtils.toByteArray(resEntity);
				}
			}
			Map<String,Object> result = new HashMap<>();
			Header[] respHeaders = response.getAllHeaders();
			result.put("content", content);
			result.put("headers", respHeaders);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 返回值中包括String内容的post请求
	 * 请求参数是StringParam
	 * */
	public static Map<String,Object> doHttpsPost(String url, Map<String, String> param, Map<String, String> headers) {
		try {
			httpClient = SSLClient.createSSLClientDefault();//HttpClients.createDefault();//
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : param.entrySet()) {
				postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			HttpEntity paramEntity = new UrlEncodedFormEntity(postParams);
			httpPost.setEntity(paramEntity);
			
			if(headers != null) {
				Set<String> keys = headers.keySet();
				for(String k : keys) {
					httpPost.setHeader(k, headers.get(k));
					System.out.println(k + " : " + headers.get(k));
				}
			}
			
			response = httpClient.execute(httpPost);
			String content = null;
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					content = EntityUtils.toString(resEntity);
				}
			}
			Map<String,Object> result = new HashMap<>();
			Header[] respHeaders = response.getAllHeaders();
			result.put("content", content);
			result.put("headers", respHeaders);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
