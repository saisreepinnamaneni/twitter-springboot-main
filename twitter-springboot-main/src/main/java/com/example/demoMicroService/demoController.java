package com.example.demoMicroService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class demoController {
	 @Value("${bearerToken}")
	    String bearerToken;
	 
	 @Value("${TweetFilePath}")
	 String filePath;
	 @Value("${twitterApi}")
	 String twitterApi;
	
	
	@GetMapping("/Hi")  
	public String Hello()  
	{  
		
		String ss="Welcome to  Application ";
	return ss;  
	} 
	//calling hello method of student ms from demo ms
	
	//calling /student method of student microservice
	@GetMapping("/RealTimeTweets")
	public void  StudentinfoClient() throws IOException, URISyntaxException {
		HttpClient httpClient = HttpClients.custom()
		        .setDefaultRequestConfig(RequestConfig.custom()
		            .setCookieSpec(CookieSpecs.STANDARD).build())
		        .build();

		    
			URIBuilder uriBuilder = new URIBuilder(twitterApi);

		    HttpGet httpGet = new HttpGet(uriBuilder.build());
		   

		    httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));

		    HttpResponse response = httpClient.execute(httpGet);
		    HttpEntity entity = response.getEntity();
		    if (null != entity) {
		      BufferedReader reader = new BufferedReader(new InputStreamReader((entity.getContent())));
		      String line = reader.readLine();
		      FileWriter fw=new FileWriter(filePath);
		      while (line != null) {
		        System.out.println(line);
		        line = reader.readLine();
			
		           
		           fw.append(line);
		             
		      }}
		
	}
	
	

}
