package com.epsilon.tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONObject;

public class BackEndRequestThread extends Thread {

	private CloseableHttpClient client;
	private HttpGet get;
	private LogManager log;
	private JSONObject jsonContent;
	private MinMaxTimeoutIndentifier identifier;
	
	
	public BackEndRequestThread(CloseableHttpClient client, HttpGet get, LogManager log, MinMaxTimeoutIndentifier identifier) {
		
		this.client = client;
		this.get = get;
		this.log = log;
		this.identifier = identifier;
		
		start();
	}
	
	public void run() {
		
		try {
			
//			System.out.println(Thread.currentThread().getName() + ": " + System.currentTimeMillis() + ": " + "Connecting");
			log.writeLog(Thread.currentThread().getName() + ": " + "Connecting");
			
			CloseableHttpResponse response = client.execute(get);

//			System.out.println("Back end is: " + response.getHeaders("Set-Cookie")[1].getValue().substring(5,12));
			
			BufferedReader dis = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
//			System.out.println(Thread.currentThread().getName() + ": " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase() + " (backend " + response.getHeaders("Set-Cookie")[1].getValue().substring(8,12) + ")");
			
			jsonContent = new JSONObject(dis.readLine());
			
			String resultCode = jsonContent.getString("resultCode");

//			System.out.println(Thread.currentThread().getName() + ": " + System.currentTimeMillis() + ": " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase() + ". AVS code: " + resultCode + " (backend " + response.getHeaders("Set-Cookie")[1].getValue().substring(8,12) + ")");
			log.writeLog(Thread.currentThread().getName() + ": " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase() + ". AVS code: " + resultCode + " (backend " + response.getHeaders("Set-Cookie")[1].getValue().substring(8,12) + ")");
			response.close();
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
//			System.out.println(Thread.currentThread().getName() + ": " + System.currentTimeMillis() + ": " + "Connection timeout");
			identifier.updateMinMax(Integer.parseInt(Thread.currentThread().getName().split("-")[1]));
			log.writeLog(Thread.currentThread().getName() + ": " + "Connection timeout");
//			e.printStackTrace();
		}
		
	}
	
}
