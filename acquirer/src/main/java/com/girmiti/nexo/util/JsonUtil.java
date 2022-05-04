package com.girmiti.nexo.util;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Component
public class JsonUtil {

	@Autowired
	Environment properties;

	private static RestTemplate restTemplate = new RestTemplate();

	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	public static String convertObjectToJSON(Object object) throws JsonProcessingException {
		String input = "";
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		input = objectWriter.writeValueAsString(object);
		return input;
	}

	public static Object convertJSONToObject(String jsonData, Class<?> c) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonData, c);
	}

	public static <T extends Object> T postServiceRequest(Object request, Class<T> response, String baseUrl,
			String serviceEndPoint) {
		T resultantObject = null;

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("originChannel", "WEB");

			HttpEntity<Object> entity = new HttpEntity<>(request, headers);
			ResponseEntity<T> responseEntity = restTemplate.exchange(baseUrl + serviceEndPoint, HttpMethod.POST, entity,
					response);

			logger.info("Entering:: JsonUtil class:: postServiceRequest method"+"Json response code : ", responseEntity.getStatusCode());

			if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				logger.error("JsonUtil class :: postRequest method :: Exception with HttpErrorCode: {}",
						responseEntity.getStatusCode());
			}
			if (responseEntity.getBody() != null) {
				resultantObject = responseEntity.getBody();
			}

		} catch (Exception e) {
			String info = "JsonUtil class :: postRequest method :: Exception :: " + e.getMessage();
			logger.error(info, e.getMessage());
		}
		return resultantObject;
	}

	public static <T extends Object> T getServiceRequest(Class<T> response, String baseUrl, String serviceEndPoint) {
		T resultantObject = null;

		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("originChannel", "WEB");

			HttpEntity<Object> entity = new HttpEntity<>(headers);
			ResponseEntity<T> responseEntity = restTemplate.exchange(baseUrl + serviceEndPoint, HttpMethod.GET, entity,
					response);

			if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				logger.error("JsonUtil class :: postRequest method :: Exception with HttpErrorCode: {}",
						responseEntity.getStatusCode());
			}
			if (responseEntity.getBody() != null) {
				resultantObject = responseEntity.getBody();
			}

		} catch (Exception e) {
			String info = "JsonUtil class :: postRequest method :: Exception ";
			logger.error(info, e.getMessage());
		}
		return resultantObject;
	}

}
