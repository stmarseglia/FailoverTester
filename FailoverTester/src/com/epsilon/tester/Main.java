package com.epsilon.tester;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class Main {

	public static void main(String[] args) {
		
		boolean parametersOk = false;
		
		if (args.length != 3) {
			
			printUsage();
			return;
		}
		
		String logPath = args[0];
		String testType = args[1];
		String period = args[2];
		
		LogManager log = LogManager.getInstance(logPath);
		final MinMaxTimeoutIndentifier identifier = MinMaxTimeoutIndentifier.getInstance();
		
//		/* SHUTDOWN HOOK */
//		Runtime.getRuntime().addShutdownHook(new Thread()
//        {
//            @Override
//            public void run()
//            {
//            		System.out.println("First thread to timeout: " + identifier.getMin());
//            		System.out.println("Last thread to timeout: " + identifier.getMax());
//            		System.out.println("Done testing.");
//					LogManager.getInstance().writeLog("Done testing.");
//            }
//        });
		
		/* Cookie policy */
		CloseableHttpClient client = HttpClients.createDefault();
		
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
		
//		client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();
		
		switch (testType) {
		
		case "WC":
			
			System.out.println("Web Cache test");
			
			parametersOk = true;
			
			try {
				for (int i=0;i<130;i++) {
//				while (true) {
					
					client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();

					/* DEV+ */
//					HttpGet get = new HttpGet("http://www-devplus-ctl.avs-accenture.com/img/spinner.gif");
					/* PROD */
					HttpGet get = new HttpGet("http://wp.prod.ott.centurylink.net/img/spinner.gif");
					
					new WebCacheRequestThread(client, get, log, identifier);
					Thread.sleep(Integer.parseInt(period));
				}
				
        		System.out.println("First thread to timeout: " + identifier.getMin());
        		System.out.println("Last thread to timeout: " + identifier.getMax());
        		System.out.println("Done testing.");	
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			break;
			
		case "FE":
			
			System.out.println("Front End test");
			
			parametersOk = true;
				
				try {
					for (int i=0;i<130;i++) {
//					while (true) {
						
						client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();
						
						/* PROD */
						HttpGet get = new HttpGet("http://10.136.147.103:7785/img/spinner.gif");
						
						/* DEV+ */
//						HttpGet get = new HttpGet("http://10.135.235.103:7785/img/spinner.gif");
						
						new FrontEndRequestThread(client, get, log, identifier);
						Thread.sleep(Integer.parseInt(period));
					}

					System.out.println("First thread to timeout: " + identifier.getMin());
					System.out.println("Last thread to timeout: " + identifier.getMax());
					System.out.println("Done testing.");

				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			break;
			
		case "BE":
			
			System.out.println("Back End test");
			
			parametersOk = true;

			try {
				for (int i=0;i<130;i++) {
//				while (true) {
					
					client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();
					
//					HttpGet get = new HttpGet("http://10.135.235.13:8080/AVS/besc?action=SearchLive&channel=PCTV&query=ralph");
					
					/* DEV+ */
//					HttpGet get = new HttpGet("http://10.135.235.104:8080/AVS/besc?action=SearchLive&channel=PCTV&query=ralph");
					
					/* PROD */
					HttpGet get = new HttpGet("http://10.136.147.104:8080/AVS/besc?action=SearchLive&channel=PCTV&query=ralph");
					
//					HttpGet get = new HttpGet("http://www-devplus-ctl.avs-accenture.com/AVS/besc?action=SearchLive&channel=PCTV&query=ralph");
//					HttpGet get = new HttpGet("https://api.ipify.org");
//					HttpGet get = new HttpGet("https://api.wetjborjblkjrhgkjhfgkbwòerjlkrjgldkfjblejtblktjbljehtb.org");
					
					new BackEndRequestThread(client, get, log, identifier);
					Thread.sleep(Integer.parseInt(period));
				}
				
        		System.out.println("First thread to timeout: " + identifier.getMin());
        		System.out.println("Last thread to timeout: " + identifier.getMax());
        		System.out.println("Done testing.");
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			break;
		
		case "DRM":
			
			System.out.println("DRM test");
			
			parametersOk = true;
			
			try {
				for (int i=0;i<130;i++) {
//				while (true) {
					
					client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();

					/* PROD */
					HttpGet get = new HttpGet("http://10.136.148.107/PlayReady/rightsmanager.asmx");
					
					new DRMRequestThread(client, get, log, identifier);
					Thread.sleep(Integer.parseInt(period));
				}
				
        		System.out.println("First thread to timeout: " + identifier.getMin());
        		System.out.println("Last thread to timeout: " + identifier.getMax());
        		System.out.println("Done testing.");	
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			break;
			
		case "PIN":
			
			System.out.println("Pinboard test");
			
			parametersOk = true;
				
				try {
					for (int i=0;i<130;i++) {
//					while (true) {
						
						client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();
						
						/* PROD */
						HttpGet get = new HttpGet("http://10.136.147.105:8080/prikbord/user");
						
						new PinboardRequestThread(client, get, log, identifier);
						Thread.sleep(Integer.parseInt(period));
					}

					System.out.println("First thread to timeout: " + identifier.getMin());
					System.out.println("Last thread to timeout: " + identifier.getMax());
					System.out.println("Done testing.");

				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
				break;

		case "SDP":

			System.out.println("SDP test");

			parametersOk = true;

			try {
				for (int i=0;i<130;i++) {
					//					while (true) {

					client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();

					/* PROD */
					HttpGet get = new HttpGet("http://10.136.147.107:8080/SDP_CSM_FE/RefCurrencyTypeService?wsdl");

					new SDPRequestThread(client, get, log, identifier);
					Thread.sleep(Integer.parseInt(period));
				}

				System.out.println("First thread to timeout: " + identifier.getMin());
				System.out.println("Last thread to timeout: " + identifier.getMax());
				System.out.println("Done testing.");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			break;

		case "DB":

			System.out.println("Database Cluster test");

			parametersOk = true;

			try {
				for (int i=0;i<130;i++) {
					//					while (true) {

					client = HttpClients.custom().setDefaultRequestConfig(globalConfig).disableAutomaticRetries().build();

					/* PROD */
					HttpGet get = new HttpGet("http://10.136.147.16:8080/AVS/besc?action=SearchLive&channel=PCTV&query=ralph");

					new BackEndDBRequestThread(client, get, log, identifier);
					Thread.sleep(Integer.parseInt(period));
				}

				System.out.println("First thread to timeout: " + identifier.getMin());
				System.out.println("Last thread to timeout: " + identifier.getMax());
				System.out.println("Done testing.");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			break;

		default:
			printUsage();
			break;
		}
		
		if (!parametersOk) return;
		
	}
	
	public static void printUsage() {
		
		System.out.println("Failover Tester");
		System.out.println("Usage: java -jar FailoverTester.jar <logfile path> <test type (WC|FE|BE|DRM|PIN|SDP|DB)> <period (msec)>");
		
	}
}
