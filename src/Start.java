import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Start {

	static String JSON_FILE_PATH = "";
//	final static String JENKINS_API_URL = "http://1.9.98.72:8080/";
//	static String JENKINS_API_URL = "http://1.9.96.183:8080/";
	
	final static String JENKINS_API_SUFFICS = "api/json?pretty=true";
	final static String JENKINSES_INPUT = "InputJenkinses.json";
	
	private static List<BuildDetail> buildDetails;
	private static BuildDetail currentBuildDetail;
	private static ObjectMapper mapper;
	private static Job[] jenkinsJobs;
	private static Jenkins[] jenkinses;
	
	/**
	 * This function gets url address and return server GET responce
	 * @param urlAddress requested url
	 * @return server response
	 */
	static String getJson(String urlAddress, String userName, String userPassword) {
		
		String responce = "";
		
		try {

	        URL url = new URL(urlAddress);
	        
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        
	        String encoded;
	        if (userName.length()>0) {
	        	encoded = DatatypeConverter.printBase64Binary((userName+":"+userPassword).getBytes("UTF-8"));
	        	conn.setRequestProperty("Authorization", "Basic "+encoded);
	        }
	        
	        conn.setDoOutput(true);
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-Type", "application/json");
            
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);	        
	        String input = "";

	        OutputStream os = conn.getOutputStream();
	        os.write(input.getBytes());
	        os.flush();

	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));

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
	static String getValueFromJson(String json, String field) {
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

	private static void processJenkins(Job[] jenkinsJobs, Jenkins jenkins) {

		for (Job j:jenkinsJobs) {
        	currentBuildDetail = new BuildDetail();
        	if ((j!=null)&&(j.getName()!="")&&j.getName()!=null) {
	        	String jobUrl = j.getUrl() + JENKINS_API_SUFFICS;
	        	String job = getJson(jobUrl,jenkins.getUserName(),jenkins.getUserPassword());
	        	try {
	            	Build[] builds = mapper.readValue(mapper.readTree(job).get("builds"), Build[].class);
	            	
	            	String buildDetailUrl = builds[0].getUrl()+JENKINS_API_SUFFICS;
	            	System.out.println(buildDetailUrl);
	            	
	            	String buildDetailsJson = getJson(buildDetailUrl,jenkins.getUserName(),jenkins.getUserPassword()); 
	            	System.out.println("count: "+getValueFromJson(buildDetailsJson,"totalCount"));
	            	System.out.println("fails: "+getValueFromJson(buildDetailsJson,"failCount"));
	            	System.out.println("result: "+getValueFromJson(buildDetailsJson,"result"));
	            	
	        		currentBuildDetail.setJobName(j.getName());
	        		//currentBuildDetail.setJobUrl(j.getUrl());
	        		currentBuildDetail.setJobUrl("<a href='" + j.getUrl() + "' target='_blank'/>" + j.getName() + "</a>");
	        		currentBuildDetail.setResult(getValueFromJson(buildDetailsJson,"result"));
	        		currentBuildDetail.setTotalCount(TryParseInt(getValueFromJson(buildDetailsJson,"totalCount")));
	        		currentBuildDetail.setFailCount(TryParseInt(getValueFromJson(buildDetailsJson,"failCount")));
	        		currentBuildDetail.setPassCount(currentBuildDetail.getTotalCount()-currentBuildDetail.getFailCount());
	        		buildDetails.add(currentBuildDetail);
	        	} catch (Exception exception) {
	        		System.err.println(exception);
	        		System.out.println(jobUrl);
	        		System.out.println("Not test job!");
	        	}
        	} else {
        		System.err.println("ERROR: job is not applicable!");
        	}
        }
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, Exception {

		System.out.println("Start analyse Jenkinses...");
		

        mapper = new ObjectMapper();
        if (args.length==0) {
        	jenkinses = new ObjectMapper().readValue(mapper.readTree(new File(JENKINSES_INPUT)), Jenkins[].class);
        } else {
        	String path = args[0] + JENKINSES_INPUT;
        	JSON_FILE_PATH = args[0];
        	jenkinses = new ObjectMapper().readValue(mapper.readTree(new File(path)), Jenkins[].class);
        }
        
        for (Jenkins jenkins:jenkinses) {
        	
        	buildDetails = new ArrayList<BuildDetail>();
        	
            jenkinsJobs = new ObjectMapper().readValue(new ObjectMapper().readTree(getJson(jenkins.getJenkinsUrl()+JENKINS_API_SUFFICS,jenkins.getUserName(),jenkins.getUserPassword())).get("jobs"), Job[].class);
            
            processJenkins(jenkinsJobs, jenkins);
            
            final OutputStream out = new ByteArrayOutputStream();
            final ObjectMapper mapper1 = new ObjectMapper();

            mapper1.writeValue(out, buildDetails);

            final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
            System.out.println(new String(data));
            
            try (FileWriter file = new FileWriter(JSON_FILE_PATH+"OUTPUT_"+jenkins.getJenkinsName())) {
    			file.write(new String(data));
    		}
        }
	}
}
