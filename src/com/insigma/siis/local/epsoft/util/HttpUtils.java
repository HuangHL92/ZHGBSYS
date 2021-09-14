package com.insigma.siis.local.epsoft.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private static final String CHARSET = "UTF-8";
	
	
	public static String excutePost(HttpClient client, String url, Map<String, String> headers, String jsonParams,
			String reqCharset) {
		try {
			RequestBuilder requestBuilder = RequestBuilder.post().setUri(url);
			if (!org.apache.axis.utils.StringUtils.isEmpty(jsonParams)) {
				StringEntity entity = new StringEntity(jsonParams,"utf-8");
				entity.setContentEncoding("utf-8");
				entity.setContentType("application/json");
				requestBuilder.setEntity(entity);
			}
			HttpUriRequest request = requestBuilder.build();
			if (MapUtils.isNotEmpty(headers)) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					request.addHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse response = client.execute(request);
			try {
				String html = EntityUtils.toString(response.getEntity(), "UTF-8");
				return html;
			} catch (IOException e) {
				logger.error("", e);
			}
		} catch (ParseException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}
	
	
	public static HttpResponse excutePost(HttpClient client, String url, Map<String, String> headers,
			Map<String, String> params, String reqCharset) {
		try {
			RequestBuilder requestBuilder = RequestBuilder.post().setUri(url);
			if (MapUtils.isNotEmpty(params)) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, Charset.forName(reqCharset));
				requestBuilder.setEntity(formEntity);
			}
			HttpUriRequest request = requestBuilder.build();
			if (MapUtils.isNotEmpty(headers)) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					request.addHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse response = client.execute(request);
			return response;
		} catch (ParseException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}
	
	public static String excutePostWrappedString(HttpClient client, String url, Map<String, String> headers,
			Map<String, String> params, String reqCharset, String resCharset) throws ParseException, IOException {
		HttpResponse response = excutePost(client, url, headers, params, reqCharset);
		return EntityUtils.toString(response.getEntity(), resCharset);
	}
}
