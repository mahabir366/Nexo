package com.girmiti.nexo.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpConfig {

	static Logger logger = Logger.getLogger(HttpConfig.class);

	private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = Integer
			.parseInt(AcquirerProperties.getProperty("thread.pool.size"));

	private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = Integer
			.parseInt(AcquirerProperties.getProperty("thread.max.per.route"));

	private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = (120 * 1000);

	private static HttpConfig httpConfig = new HttpConfig();

	private RestTemplate restTemplate = null;

	private HttpEntity<String> headerEntity = null;

	private HttpConfig() {

	}

	public static HttpConfig getInstance() {
		return httpConfig;
	}

	public RestTemplate getRestTemplate() {
		if (restTemplate == null) {
			restTemplate = restTemplate();
		}
		return restTemplate;
	}

	public HttpEntity<String> getHeadersEntity() {
		if (null == headerEntity) {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headerEntity = new HttpEntity<>("parameters", headers);
		}
		return headerEntity;
	}

	private ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(httpClient());
	}

	private RestTemplate restTemplate() {
		RestTemplate resTemplate = new RestTemplate(httpRequestFactory());
		List<HttpMessageConverter<?>> converters = resTemplate.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				jsonConverter.setObjectMapper(new ObjectMapper());
			}
		}
		return resTemplate;
	}
	@javax.annotation.CheckForNull
	private CloseableHttpClient httpClient() {
		CloseableHttpClient defaultHttpClient = null;
		PoolingHttpClientConnectionManager connectionManager = null;

		try {
			connectionManager = new PoolingHttpClientConnectionManager();
			connectionManager.setMaxTotal(DEFAULT_MAX_TOTAL_CONNECTIONS);
			connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
			URI uri = null;
			HttpHost host = null;
			if (!isNullAndEmpty(AcquirerProperties.getProperty("chatak-merchant.service.url"))) {
				uri = new URI(AcquirerProperties.getProperty("chatak-merchant.service.url"));
				host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort()) : new HttpHost(uri.getHost());
				connectionManager.setMaxPerRoute(new HttpRoute(host), DEFAULT_MAX_CONNECTIONS_PER_ROUTE);
			}
			RequestConfig config = RequestConfig.custom().setConnectTimeout(DEFAULT_READ_TIMEOUT_MILLISECONDS).build();
			defaultHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
					.setDefaultRequestConfig(config).build();
		} catch (NumberFormatException e) {
			logger.error("ERROR:: HttpConfig :: NumberFormatException Exception", e);
		} catch (URISyntaxException e) {
			logger.error("ERROR:: HttpConfig :: URISyntaxException Exception", e);
		}

		return defaultHttpClient;
	}

	public static boolean isNullAndEmpty(String data) {
		return (data == null || "".equals(data.trim()));
	}

}
