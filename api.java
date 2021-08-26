package com.example.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;

public class PopulateContact {
	public static void main(String[] args) throws IOException {
		for (int i = 1; i < 82; i++) {
			try {
				System.out.println("\n");
				JSONObject jsonObject = getPersonData(i);
				System.out.println("Got Data");
				String name = (String) jsonObject.get("name");
				System.out.println(name);
				String[] names = name.split(" ");
				String firstName = names[0];
				String lastName = "";
				if (names.length > 1)
					lastName = names[1];
				String email = firstName.toLowerCase() + "@gmail.com";
				String phoneNumber = "";
				for (int j = 1; j <= 10; j++) {
					String num = "" + (int) (Math.random() * (10 - 0 + 1) + 0);
					phoneNumber += num;
				}
				System.out.println(firstName + " " + lastName + " " + email + " " + phoneNumber);
				Contact contact = new Contact();
				contact.setFirstName(firstName);
				contact.setLastName(lastName);
				contact.setEmail(email);
				contact.setPhoneNumber(phoneNumber);
				contact.setId(0L);
				System.out.println("Created Object\n" + contact.toString() + "\n");
				Contact c = contactRepository.save(contact);
				System.out.println("saved Object\n" + c.toString() + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}


	public static JSONObject getPersonData(Integer id) throws MalformedURLException, IOException {
		URL url = new URL("https://swapi.dev/api/people/" + id.toString() + "/?format=json");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/json");
		con.setConnectTimeout(10000);
		con.setReadTimeout(10000);
		con.setInstanceFollowRedirects(false);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		try {
			JSONObject jsonObject = new JSONObject(content.toString());
			return jsonObject;
		} catch (JSONException err) {
			System.out.println("Error");
		}
		in.close();
		con.disconnect();
		return null;
	}

}
