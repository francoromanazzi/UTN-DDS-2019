package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SmsSender {
	private static String API_KEY = "RMe8QssPavI-SGqPFTYhkoDSIlgd2k5kkTD2ZcBffV";

	//DestinyNumber en formato internacional 
	public static String send(String DestinyNumber, String text) {
		try {
			// Construct data
			String apiKey = "apikey=" + API_KEY;
			String message = "&message=" + text;
			String sender = "&sender=" + "QueMePongo";
			String numbers = "&numbers=" + DestinyNumber;
			String test = "&test=" + "true";

			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
			String data = apiKey + numbers + message + sender + test;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();

			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS " + e);
			return "Error " + e;
		}
	}
}
