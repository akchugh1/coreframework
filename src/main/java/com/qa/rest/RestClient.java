package com.qa.rest;

import com.qa.util.Reporter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RestClient {

	private static final Log LOG = LogFactory.getLog(RestClient.class);

	public HttpResponse doGet(String url, Map<String, String> reqHeaders,Map<String, String> params) {
		return this.exchange(url, reqHeaders, null, HttpMethod.GET,params);
	}

	public HttpResponse doPost(String url, Map<String, String> reqHeaders, String requestBody,Map<String, String> params) {
		return this.exchange(url, reqHeaders, requestBody, HttpMethod.POST,params);
	}

	public HttpResponse doPut(String url, Map<String, String> reqHeaders, String requestBody,Map<String, String> params) {
		return this.exchange(url, reqHeaders, requestBody, HttpMethod.PUT,params);
	}

	public HttpResponse exchange(String url, Map<String, String> reqHeaders, String requestBody,
			HttpMethod httpMethod,Map<String, String> params) {
		Reporter reporter = new Reporter();
		HttpResponse httpResponse = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			if (!CollectionUtils.isEmpty(reqHeaders)) {
				headers.setAll(reqHeaders);
			}
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			for (Map.Entry<String, String> entry : params.entrySet()) {
				builder.queryParam(entry.getKey(), entry.getValue());
			}
			HttpEntity<String> requestEntity = requestBody == null ? new HttpEntity<String>(headers)
					: new HttpEntity<String>(requestBody, headers);
			ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), httpMethod, requestEntity, String.class);
			httpResponse = new HttpResponse(response.getBody(), response.getHeaders(),
					response.getStatusCode().value());
			LOG.info("Http response : " + httpResponse);
		} catch (RestClientException restClientException) {
			if (restClientException instanceof HttpClientErrorException) {
				HttpClientErrorException clientErrorException = (HttpClientErrorException) restClientException;
				httpResponse = new HttpResponse(clientErrorException.getResponseBodyAsString(),
						clientErrorException.getResponseHeaders(), clientErrorException.getStatusCode().value());
				LOG.info("Http response : " + httpResponse);
			} else {
				LOG.error("Getting RestClient Exception. Exception : ", restClientException);
				throw new RestClientException("Failed in exchange method ", restClientException);
			}
		} catch (Exception e) {
			LOG.error("Getting Exception. Exception : ", e);
			throw new RestClientException("Failed in exchange method  ", e);
		} finally {
			reporter.addAttachment(url, requestBody, Integer.toString(httpResponse.getStatusCode()),
					httpResponse.getBody());
		}
		return httpResponse;

	}
	
	public HttpResponse doMultipartPost(String url, Map<String, String> reqHeaders, HashMap<String, String> parts) {
	    HttpResponse httpResponse = null;
	    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            if (!CollectionUtils.isEmpty(reqHeaders)) {
                headers.setAll(reqHeaders);
            }
            //headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            for (Map.Entry<String, String> part : parts.entrySet()) {
                String filepath = part.getKey();
                String keyName = part.getValue();
                File file = new File(filepath);
                map.add(keyName, file);
            }
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
//            String response1 = restTemplate.postForObject(url, requestEntity, String.class);
//            System.out.println(response1);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            httpResponse = new HttpResponse(response.getBody(), response.getHeaders(),
                    response.getStatusCode().value());

            LOG.info("Http response : " + httpResponse);
        } catch (RestClientException restClientException) {
            if (restClientException instanceof HttpClientErrorException) {
                HttpClientErrorException clientErrorException = (HttpClientErrorException) restClientException;
                httpResponse = new HttpResponse(clientErrorException.getResponseBodyAsString(),
                        clientErrorException.getResponseHeaders(), clientErrorException.getStatusCode().value());
                LOG.info("Http response : " + httpResponse);
            } else {
                LOG.error("Getting RestClient Exception. Exception : ", restClientException);
                throw new RestClientException("Failed in exchange method ", restClientException);
            }
        } catch (Exception e) {
            LOG.error("Getting Exception. Exception : ", e);
            throw new RestClientException("Failed in exchange method  ", e);
        }
        return httpResponse;
    }

}
