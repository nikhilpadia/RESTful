/**
 * Added By Nikhil On 20-Nov-2018 for
 */
package com.api.RESTful.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtility {
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger log = LoggerFactory.getLogger(HttpUtility.class);
	private static final String BASE_URL = System.getProperty("JIRA_BASE_URL");
	private static final String API_URL = System.getProperty("JIRA_API_URL");

	public static Map<String, Object> get(String query) {
		URI uri = null;
		Map<String, Object> list = null;
		try {
			HttpClient client = HttpClientBuilder.create().build();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("q", query));
			uri = new URIBuilder(BASE_URL + API_URL).addParameters(params).build();

			HttpGet httpGet = new HttpGet(uri);

			HttpResponse httpResponse = client.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				return null;
			}
			String response = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent())).lines()
					.collect(Collectors.joining("\n"));

			list = mapper.readValue(response, Map.class);

			return list;
		} catch (Exception e) {
			log.error("Exception in http client", e);
		} finally {
		}
		return list;
	}

}
