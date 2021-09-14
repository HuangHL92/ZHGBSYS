package com.insigma.siis.local.epsoft.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import net.sf.json.JSON;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.spi.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientFactory {

	private static Logger logger = LoggerFactory.getLogger(HttpClientFactory.class);

	private final int CONNECT_TIMEOUT = 30000;

	private final int SOCKET_TIMEOUT = 30000;

	private final int CONNECTION_REQUEST_TIMEOUT = 30000;

	private final int MAXTOTAL = 100;

	private final int MAXROUTE = 5;

	private static class Holder {
		private static final HttpClientFactory INSTANCE = new HttpClientFactory();
	}

	private HttpClientFactory() {
	}

	public static final HttpClientFactory getInstance() {
		return Holder.INSTANCE;
	}

	public CloseableHttpClient create(boolean proxy) {
		SSLContext context = null;
		try {
			context = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
		} catch (Exception e) {
			logger.error("", e);
		}
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(context, new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});
		Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		reg.hashCode();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
		cm.setMaxTotal(MAXTOTAL);
		cm.setDefaultMaxPerRoute(MAXROUTE);
		RequestConfig.Builder configBuilder = RequestConfig.custom();
//		HttpHost proxyServier1 = new HttpHost("127.0.0.1", 8888);
//		configBuilder.setProxy(proxyServier1);
		/*if (proxy) {
			Map<String, String> map = fetchProxy();
			String ip = map.get("ip");
			String port = map.get("port");
			HttpHost proxyServier = new HttpHost(ip, Integer.valueOf(port));
			configBuilder.setProxy(proxyServier);
		}*/
		configBuilder.setConnectTimeout(CONNECT_TIMEOUT);
		configBuilder.setSocketTimeout(SOCKET_TIMEOUT);
		configBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
		configBuilder.setCookieSpec(CookieSpecs.DEFAULT);
		RequestConfig requestConfig = configBuilder.build();
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		httpClientBuilder.setDefaultRequestConfig(requestConfig);
		httpClientBuilder.setConnectionManager(cm);
		httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler());
		httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
				request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
			}
		});
		httpClientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
		SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
		httpClientBuilder.setDefaultSocketConfig(socketConfig);
		CloseableHttpClient httpclient = httpClientBuilder.build();
		return httpclient;
	}

	public CloseableHttpClient create() {
		return create(false);
	}

	
}
