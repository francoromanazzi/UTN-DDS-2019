package utils;

import excepciones.MensajeriaException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class SmsSender {
	private static final String API_KEY = "RMe8QssPavI-SGqPFTYhkoDSIlgd2k5kkTD2ZcBffV";

	public static String send(String destinyNumber, String text) throws MensajeriaException {
		try {
			// Construct data
			String apiKey = "apikey=" + API_KEY;
			String message = "&message=" + text;
			String sender = "&sender=" + "QueMePongo";
			String numbers = "&numbers=" + destinyNumber;
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
			throw new MensajeriaException();
		}
	}
}
