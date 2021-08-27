package com.test.apitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class App {
	public static void main(String[] args) throws IOException {
		URL url = new URL("https://api.npoint.io/b7f6251946c49491aace");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		
		con.setInstanceFollowRedirects(false);
		System.out.println("Here");
		System.out.println(con);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		try {
			JSONObject jsonObject = new JSONObject(content.toString());
			System.out.println(jsonObject);
		} catch (JSONException err) {
			System.out.println("Error");
		}

	}
}


		
