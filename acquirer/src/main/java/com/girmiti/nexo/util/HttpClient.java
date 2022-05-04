package com.girmiti.nexo.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.girmiti.nexo.acquirer.exception.HttpClientException;
import com.girmiti.nexo.acquirer.pojo.OAuthTokenResponse;

public class HttpClient {

	static Logger logger = Logger.getLogger(HttpClient.class);

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String AUTHORIZATION = "Authorization";

	private static final String AUTHORIZATION_PREFIX = "Bearer ";

	private static final String AUTHORIZATION_BASIC = "Basic ";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static final ObjectWriter objectWriter = new ObjectMapper().writer();

	private static final String HTTP_ERROR_CODE = "Failed with HTTP error code : ";

	private static final String CALLING_POST_API = "Calling POST API - ";

	private static final String UTF_8 = "UTF-8";

	private static final String API_RESPONSE = "API Response : ";

	private static final String RESULTANT_OBJECT_AFTER_CONVERTION = "Resultant Object After convertion: ";

	private static final String ERROR = "ERROR in invokePost method";

	private PoolingHttpClientConnectionManager cm = null;

	private final String finalURL;

	private RestTemplate restTemplate = null;

	public static final String BASE_SERVICE_URL = AcquirerProperties.getProperty("chatak-merchant.service.url");

	public static final String BASE_OAUTH_REFRESH_SERVICE_URL = AcquirerProperties
			.getProperty("chatak-merchant.oauth.refresh.service.url");

	private static String oauthRefreshToken = null;

	private static String oauthToken = null;

	private static Long tokenValidity = null;
	
	private static final String ERROR_SATUS_CODE = "Error Status Code : 401";

	public static final String BASE_ADMIN_OAUTH_SERVICE_URL = AcquirerProperties
			.getProperty("chatak-merchant.oauth.service.url");

	public <T extends Object> T invokeGet(Class<T> response, Header[] headers) throws IOException {
		return invokeGetCommon(response, headers);
	}

	public <T extends Object> T invokeGet(Class<T> response, String accessToken) throws IOException {
		return invokeGetCommon(response, accessToken);
	}

	private <T extends Object> T invokeGetCommon(Class<T> response, String accessToken) throws IOException {
		logger.info("Entering into Class Name : HttpClient :: Method Name : invokeGetCommon");
		cm = getHttpPoolManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		CloseableHttpResponse httpResponse = null;
		try {
			logger.info("Calling GET API - " + (finalURL));
			HttpGet getRequest = new HttpGet(finalURL);
			getRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			if (accessToken != null) {
				getRequest.addHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
			}
			httpResponse = httpClient.execute(getRequest);
			return fetchResultantObject(response, httpResponse);
		} catch (Exception e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokeGetCommon :::: Exception", e);
		} finally {
			closeResources(httpClient, httpResponse, cm);
		}
		logger.info("Exiting from Class Name : HttpClient :: Method Name : invokeGetCommon");
		return null;
	}

	public <T> T invokePost(Object request, Class<T> response) {

		return invokePostCommon(request, response, null);
	}

	public <T> T invokePost(Object request, Class<T> response, boolean basicAuth, String serviceToken) {
		if (basicAuth) {
			return invokeBasicAuth(request, response, serviceToken);
		} else {
			return invokePostCommon(request, response, serviceToken);
		}

	}

	public <T> T invokePostRequest(Object request, Class<T> response, String accessToken) {

		return invokePostCommon(request, response, accessToken);
	}

	private <T> T invokePostCommon(Object request, Class<T> response, String accessToken) {
		logger.info("Entering into Class Name : HttpClient :: Method Name : invokePostCommon");
		cm = getHttpPoolManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		CloseableHttpResponse httpResponse = null;
		try {
			logger.info(CALLING_POST_API + (finalURL));
			HttpPost postReq = new HttpPost(finalURL);
			postReq.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			if (accessToken != null) {
				postReq.addHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + accessToken);
			}
			postReq.setEntity(new StringEntity(objectWriter.writeValueAsString(request), UTF_8));
			httpResponse = httpClient.execute(postReq);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
			}
			logger.info(API_RESPONSE + httpResponse.getEntity().getContent());
			T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
			logger.info(RESULTANT_OBJECT_AFTER_CONVERTION + resultantObject);
			return resultantObject;
		} catch (Exception ex) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePostCommon :::: Exception", ex);
		} finally {
			closeResources(httpClient, httpResponse, cm);
		}
		logger.info("ERROR in calling POST API and rerurning NULL " + (finalURL));
		return null;
	}

	private <T> T invokeBasicAuth(Object request, Class<T> response, String accessToken) {
		logger.info("Entering into Class Name : HttpClient :: Method Name : invokeBasicAuth");
		cm = getHttpPoolManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		CloseableHttpResponse httpResponse = null;
		try {
			logger.info(CALLING_POST_API + (finalURL));
			HttpPost postRequest = new HttpPost(finalURL);
			postRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			if (accessToken != null) {
				postRequest.addHeader(AUTHORIZATION, AUTHORIZATION_BASIC + accessToken);
			}
			postRequest.setEntity(new StringEntity(objectWriter.writeValueAsString(request), UTF_8));
			httpResponse = httpClient.execute(postRequest);
			return fetchResultantObject(response, httpResponse);
		} catch (Exception e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokeBasicAuth :::: Exception", e);
		} finally {
			closeResources(httpClient, httpResponse, cm);
		}
		logger.info("ERROR in calling POST API and rerurning NULL " + (finalURL));
		return null;
	}

	private <T> T fetchResultantObject(Class<T> response, CloseableHttpResponse httpResponse)
			throws HttpClientException, IOException {
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
		}
		logger.info(API_RESPONSE + httpResponse.getEntity().getContent());
		T resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
		logger.info(RESULTANT_OBJECT_AFTER_CONVERTION + resultantObject);
		return resultantObject;
	}

	/**
	 * @return
	 */
	private PoolingHttpClientConnectionManager getHttpPoolManager() {
		cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(Integer.valueOf(AcquirerProperties.getProperty("http.client.connection.manager.maxtotal")));
		cm.setDefaultMaxPerRoute(
				Integer.valueOf(AcquirerProperties.getProperty("http.client.connection.manager.defaultmaxperroute")));
		URI uri = null;
		try {
			uri = new URI(finalURL);
		} catch (URISyntaxException e) {
			logger.error("ERROR:: getHttpPoolManager ::::  method :::: closeResources :::: URISyntaxException", e);
		}
		if(uri != null) {
			HttpHost host = (uri.getPort() > 0) ? new HttpHost(uri.getHost(), uri.getPort())
			          : new HttpHost(uri.getHost());
			cm.setMaxPerRoute(new HttpRoute(host), Integer.valueOf(AcquirerProperties.getProperty("http.client.connection.manager.maxperroute")));
		}
		SocketConfig socketConfig = SocketConfig.custom()
				.setSoTimeout(
						Integer.valueOf(AcquirerProperties.getProperty("http.client.connection.manager.soc.timeout")))
				.build();
		cm.setDefaultSocketConfig(socketConfig);
		return cm;
	}

	private void closeResources(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse,
			PoolingHttpClientConnectionManager cm) {
		try {
			if (null != httpResponse) {
				httpResponse.close();
			}
			if (null != httpClient) {
				httpClient.close();
			}
			cm.close();
		} catch (Exception e) {
			logger.error("ERROR:: HttpClient ::::  method :::: closeResources :::: Exception", e);
		}
	}

	public <T> T invokePost(Object request, Class<T> response, String accessToken) throws HttpClientException {

		logger.info("Entering into Class Name : HttpClient :: Method Name : invokePost");
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		T resultantObject = null;
		Header[] headers = new Header[] { new BasicHeader("content-type", "application/json"),

				new BasicHeader(AUTHORIZATION, "Bearer" + accessToken) };
		try {
			logger.info(CALLING_POST_API + (finalURL));
			HttpPost postRequest = getPostRequest(headers);
			httpClient = HttpClients.custom().setConnectionManager(cm).build();

			String jsonRequest = objectWriter.writeValueAsString(request);
			postRequest.setEntity(new StringEntity(jsonRequest, UTF_8));
			httpResponse = httpClient.execute(postRequest);
			getStatusCode(httpResponse);
			logger.info(API_RESPONSE + httpResponse.getEntity().getContent());
			if (response.getName() instanceof java.lang.String) {
				resultantObject = (T) objectMapper.readValue(httpResponse.getEntity().getContent(), Object.class);
				resultantObject = (T) objectWriter.writeValueAsString(resultantObject);
			} else {
				resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
			}
			return resultantObject;
		} catch (RuntimeException e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePost :::: RuntimeException", e);
			throw e;
		} catch (HttpClientException e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePost :::: HttpClientException", e);
			throw e;
		} catch (Exception e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePost :::: Exception", e);
		} finally {
			closeResource(httpClient, httpResponse);
		}
		return null;
	}

	private void closeResource(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
		try {
			if (null != httpClient) {
				httpClient.close();
			}
			if (null != httpResponse) {
				httpResponse.close();
			}
		} catch (IOException e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePost :::: IOException", e);
		}
	}

	private HttpPost getPostRequest(Header[] headers) {
		HttpPost postRequest = new HttpPost(finalURL);
		postRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
		postRequest.setHeaders(headers);
		return postRequest;
	}

	public <T> T invokePost(Class<T> response, String accessToken) throws HttpClientException {
		logger.info("Entering into Class Name : HttpClient :: Method Name : invokePost");
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse httpResponse = null;
		T resultantObject = null;
		Header[] headers = new Header[] { new BasicHeader("content-type", "application/json"),
				new BasicHeader(AUTHORIZATION, "Bearer" + accessToken) };
		try {
			logger.info(CALLING_POST_API + (finalURL));
			HttpPost postRequest = getPostRequest(headers);
			httpClient = HttpClients.custom().setConnectionManager(cm).build();

			httpResponse = httpClient.execute(postRequest);
			getStatusCode(httpResponse);
			logger.info(API_RESPONSE + httpResponse.getEntity().getContent());
			if (response.getName() instanceof java.lang.String) {
				resultantObject = (T) objectMapper.readValue(httpResponse.getEntity().getContent(), Object.class);
				resultantObject = (T) objectWriter.writeValueAsString(resultantObject);
			} else {
				resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
			}
			logger.info(RESULTANT_OBJECT_AFTER_CONVERTION + resultantObject);
			return resultantObject;
		} catch (RuntimeException e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePost :::: RuntimeException", e);
			throw e;
		} catch (HttpClientException e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePost :::: HttpClientException", e);
			throw e;
		} catch (Exception e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokePost :::: Exception", e);
		} finally {
			closeResource(httpClient, httpResponse);
		}
		return null;
	}

	private void getStatusCode(CloseableHttpResponse httpResponse) throws HttpClientException {
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
			logger.info(ERROR_SATUS_CODE);
			throw new HttpClientException("Error_code", 401);
		}
		if (statusCode != HttpStatus.SC_OK) {
			logger.info("Error Status Code : " + statusCode);
			throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
		}
	}

	public <T extends Object> T invokeGetCommon(Class<T> response, Header[] headers) throws IOException {
		logger.info("Entering into Class Name : HttpClient :: Method Name : invokeGetCommon");
		cm = getHttpPoolManager();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		CloseableHttpResponse httpResponse = null;
		T resultantObject = null;
		try {
			logger.info("Calling GET API - " + (finalURL));
			HttpGet getRequest = new HttpGet(finalURL);
			getRequest.addHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
			getRequest.setHeaders(headers);
			httpResponse = httpClient.execute(getRequest);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
				logger.info(ERROR_SATUS_CODE);
				throw new HttpClientException("Error_code", 401);
			}
			if (statusCode != HttpStatus.SC_OK) {
				throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
			}
			logger.info(API_RESPONSE + httpResponse.getEntity().getContent());
			if (response.getName() instanceof java.lang.String) {
				resultantObject = (T) objectMapper.readValue(httpResponse.getEntity().getContent(), Object.class);
				resultantObject = (T) objectWriter.writeValueAsString(resultantObject);
			} else {
				resultantObject = objectMapper.readValue(httpResponse.getEntity().getContent(), response);
			}
			logger.info(RESULTANT_OBJECT_AFTER_CONVERTION + resultantObject);
			return resultantObject;
		} catch (Exception e) {
			logger.error("ERROR:: HttpClient ::::  method :::: invokeGetCommon :::: Exception", e);
			logger.info("ERROR in calling GET API " + (finalURL));
		} finally {
			closeResources(httpClient, httpResponse, cm);
		}
		logger.info("Exiting from Class Name : HttpClient :: Method Name : invokeGetCommon");
		return null;
	}

	public static <T extends Object> T postRequest(Object request, Class<T> response, String serviceEndPoint) {
		T resultantObject = null;
		HttpClient client = new HttpClient(BASE_SERVICE_URL, serviceEndPoint);

		try {

			Header[] headers = new Header[] { new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()),
					new BasicHeader("consumerClientId",
							AcquirerProperties.getProperty("chatak-merchant.consumer.client.id")),
					new BasicHeader("consumerSecret",
							AcquirerProperties.getProperty("chatak-merchant.consumer.client.secret")),
					new BasicHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + getValidOAuth2Token()),

			};
			resultantObject = client.invokePost(request, response, headers);
		} catch (Exception e) {
			logger.error("ERROR: HttpClient :: postRequest method", e);
		}
		return resultantObject;
	}

	public <T> T invokePost(Object request, Class<T> response, Header[] headers) throws HttpClientException {
		logger.info("Entering :: HttpClient :: invokePost");
		try {
			logger.info(CALLING_POST_API + (finalURL));

			ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST,
					new HttpEntity<Object>(request, setHeadersEntity(headers)), response);
			validateResponseStatusCode(resultantObject.getStatusCode().value());

			// PERF >> This method is causing object wait timeout
			logger.info("Exiting :: HttpClient :: invokePost");
			return resultantObject.getBody();
		} catch (RuntimeException | HttpClientException e) {
			logger.error(ERROR, e);
			throw e;
		} catch (Exception e) {
			logger.error(ERROR, e);
		}
		return null;
	}

	private void validateResponseStatusCode(int statusCode) throws HttpClientException {

		logger.info("HTTP Status Code: " + statusCode);
		if (statusCode == HttpStatus.SC_UNAUTHORIZED) {
			logger.info(ERROR_SATUS_CODE);
			throw new HttpClientException("401", statusCode);
		}
		if (statusCode != HttpStatus.SC_OK) {
			logger.info("Error Status Code : " + statusCode);
			throw new HttpClientException(HTTP_ERROR_CODE, statusCode);
		}
	}

	private HttpHeaders setHeadersEntity(Header[] headerArray) {
		HttpHeaders headers = new HttpHeaders();
		if (null != headerArray) {
			for (Header header : headerArray) {
				headers.add(header.getName(), header.getValue());
			}
		}
		return headers;
	}

	private static String refreshOAuth2Token() {
		String output = null;
		HttpClient httpClient = new HttpClient(BASE_SERVICE_URL + BASE_OAUTH_REFRESH_SERVICE_URL, oauthRefreshToken);
		try {
			Header[] headers = new Header[] { new BasicHeader(AUTHORIZATION, getBasicAuthValue()),
					new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()) };
			output = httpClient.invokePost(String.class, headers);
			OAuthTokenResponse apiResponse = new ObjectMapper().readValue(output, OAuthTokenResponse.class);
			oauthToken = apiResponse.getAccess_token();
			oauthRefreshToken = apiResponse.getRefresh_token();
			tokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
		} catch (Exception e) {
			logger.info("Error:: HttpClient:: refreshOAuth2Token method " + e);
		}
		return oauthToken;
	}

	public <T> T invokePost(Class<T> response, Header[] headers) throws HttpClientException {
		logger.info("Entering :: HttpClient :: invokePost");
		try {
			logger.info(CALLING_POST_API + (finalURL));
			ResponseEntity<T> resultantObject = restTemplate.exchange(finalURL, HttpMethod.POST,
					new HttpEntity<T>(null, setHeadersEntity(headers)), response);
			validateResponseStatusCode(resultantObject.getStatusCode().value());
			logger.info(RESULTANT_OBJECT_AFTER_CONVERTION + resultantObject.getBody());
			logger.info("Exiting :: HttpClient :: invokePost");
			return resultantObject.getBody();
		} catch (RuntimeException | HttpClientException e) {
			logger.error(ERROR, e);
			throw e;
		} catch (Exception e) {
			logger.error(ERROR, e);
		}
		return null;
	}

	private static String getValidOAuth2Token() {
		String output = null;
		if (isValidToken()) {
			return oauthToken;
		} else {
			if (BASE_SERVICE_URL.startsWith("https")) {
				checkServerAnbClientTrusted();
			}
			HttpClient httpClient = new HttpClient(BASE_SERVICE_URL, BASE_ADMIN_OAUTH_SERVICE_URL);
			try {
				Header[] headers = new Header[] { new BasicHeader(AUTHORIZATION, getBasicAuthValue()),
						new BasicHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()) };
				output = httpClient.invokePost(String.class, headers);
				OAuthTokenResponse apiResponse = new ObjectMapper().readValue(output, OAuthTokenResponse.class);
				oauthToken = apiResponse.getAccess_token();
				oauthRefreshToken = apiResponse.getRefresh_token();
				tokenValidity = System.currentTimeMillis() + (apiResponse.getExpires_in() * 60);
			} catch (Exception e) {
				logger.info("Error:: HttpClient:: getValidOAuth2Token method " + e);
			}
		}
		return oauthToken;
	}

	private static void checkServerAnbClientTrusted() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {

				return new java.security.cert.X509Certificate[0];
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				try {
					arg0[0].checkValidity();
				} catch (CertificateException e) {
					logger.info("Error:: HttpClient:: checkServerTrusted method ");
					throw new CertificateException("Certificate not valid or trusted.");
				}
			}

			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
					throws CertificateException {
				try {
					arg0[0].checkValidity();
				} catch (CertificateException e) {
					logger.info("Error:: HttpClient:: checkClientTrusted method ");
					throw new CertificateException("Certificate not valid or trusted.");
				}
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			logger.info("Error:: HttpClient:: getValidOAuth2Token method " + e);
		}
	}

	private static boolean isValidToken() {
		if (oauthToken == null || tokenValidity == null) {
			return false;
		} else if (System.currentTimeMillis() > tokenValidity) {
			oauthToken = null;
			return (null != refreshOAuth2Token());
		} else {
			return true;
		}
	}

	private static String getBasicAuthValue() {

		String basicAuth = AcquirerProperties.getProperty("chatak-merchant.oauth.basic.auth.username") + ":"
				+ AcquirerProperties.getProperty("chatak-merchant.oauth.basic.auth.password");
		basicAuth = AUTHORIZATION_BASIC + new String(Base64.getEncoder().encode(basicAuth.getBytes()));
		return basicAuth;
	}

	public HttpClient(String baseURIPath, String apiEndPoint) {
		this.finalURL = baseURIPath + apiEndPoint;
		this.restTemplate = HttpConfig.getInstance().getRestTemplate();
	}
}
