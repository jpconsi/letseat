package com.considine.util;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	private static final String SERVICE_NAME = "JSON Parser";

	public JSONParser() {
	}

	public JSONObject getJSONObjectFromUrl(String url) throws ParseException,
			ClientProtocolException, JSONException, IOException {

		return new JSONObject(EntityUtils.toString(new DefaultHttpClient()
				.execute(new HttpGet(url)).getEntity()));

	}

}
