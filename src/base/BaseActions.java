package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.bind.DatatypeConverter;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class BaseActions {

	/**
	 * This function gets url address and return server GET responce
	 * @param urlAddress requested url
	 * @return server response
	 */
	public static String getJson(String urlAddress, String userName, String userPassword) {
		
		String responce = "";
		
		try {

	        URL url = new URL(urlAddress);
	        
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        String encoded;
	        if (userName.length()>0) {
	        	encoded = DatatypeConverter.printBase64Binary((userName+":"+userPassword).getBytes("UTF-8"));
	        	conn.setRequestProperty("Authorization", "Basic "+encoded);
	        	conn.setRequestMethod("POST");
	        } else {
	        	conn.setRequestMethod("GET");	        	
	        }
	        conn.setRequestProperty("Content-Type", " application/json;charset=UTF-8");
	        //conn.setDoOutput(true);	 
	        
//			int responseCode = conn.getResponseCode();
//			System.out.println("\nSending 'GET' request to URL : " + url);
//			System.out.println("Response Code : " + responseCode);
			
	        String input = "";

	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));
	        
//	        OutputStream os = conn.getOutputStream();
//	        os.write(input.getBytes());
//	        os.flush();

	        String output;
//	        System.out.println("Output from Server .... \n");
	        while ((output = br.readLine()) != null) {
	        	responce = responce + output;
	        	//System.out.println(output);
	        }
	        conn.disconnect();
	      } catch (MalformedURLException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	     }
		return responce;
	}
	
	/**
	 * function gets string and return value of the field if it is detected
	 * @param json json as string
	 * @param field field to find
	 * @return value of the field
	 */
	public static String getValueFromJson(String json, String field) {
		String resultString = "";
    	try {
    		JsonNode root =  new ObjectMapper().readTree(json);
    		JsonNode parent = root.findValue(field);
    		resultString = parent.asText();
        	
    	} catch(Exception e) {
    		
    	}		
		return resultString;
	}
	
	public static Integer TryParseInt(String someText) {
		   try {
		      return Integer.parseInt(someText);
		   } catch (NumberFormatException ex) {
		      return 0;
		   }
	}
}
