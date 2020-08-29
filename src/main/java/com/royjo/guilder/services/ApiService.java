package com.royjo.guilder.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
	
//		replaced with API call for current gold value
	public int getGoldValue(){
		int goldValue = 1940;
		return goldValue;
	}
	
	public JSONObject getGold() throws Exception {
		try {
			// DEFINE MY CONNECTION
			String gold_api = "https://metals-api.com/api/latest?access_key=05b181nmvth0vzyge9ekmu68k1ved42u9suox9m554ckfj5j34s45jzha6j3&base=USD&symbols=XAU";
			URL url = new URL(gold_api);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("User-Agent", "Spring");
			// TEST MY CONNECTION
			if(conn.getResponseCode() != 200 ) {
				throw new RuntimeException("Failed request. HTTP ERROR CODE: " + conn.getResponseCode());
			}
			// GETTING RESPONSE FROM API
			InputStreamReader input = new InputStreamReader(conn.getInputStream());
			// READING THE RESPONSE
			BufferedReader br = new BufferedReader(input);
			String output;
			JSONObject json = new JSONObject();
			while((output = br.readLine()) != null) {
				System.out.println(output);
				json = new JSONObject(output);
				return json;
			}
			conn.disconnect();
			return null;
		}
		catch (Exception e) {
			throw e;
		}
	}
}

