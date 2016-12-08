package services;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import base.BaseActions;
import base.Global;
import models.BuildDetail;
import models.Jenkins;
import models.Job;

public class ProcessJenkins extends Thread implements Runnable {

	private List<BuildDetail> buildDetails;
	private Job[] jenkinsJobs;
	private Jenkins jenkins;
	
	public Jenkins getJenkins() {
		return jenkins;
	}

	public void setJenkins(Jenkins jenkins) {
		this.jenkins = jenkins;
	}
	
	public void addResult(BuildDetail e) {
		buildDetails.add(e);
	}
	
	private void processJenkins(Job[] jenkinsJobs, Jenkins jenkins) throws Exception {
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (Job j:jenkinsJobs) {
			ProcessJob processJob = new ProcessJob();
			processJob.setJob(j);
			processJob.setJenkins(jenkins);
			processJob.setProcessJenkins(this);
			Thread thread = new Thread(processJob);
			threads.add(thread);
        	thread.start();
        	//thread.join();
        	//buildDetails.add(processJob.getCurrentBuildDetail());
        }
        for (Thread t: threads) {
        	t.join();
        }
	}
	
	@Override
	public void run() {
    	buildDetails = new ArrayList<BuildDetail>();
    	
        try {
			jenkinsJobs = new ObjectMapper().readValue(new ObjectMapper().readTree(BaseActions.getJson(jenkins.getJenkinsUrl()+Global.JENKINS_API_SUFFICS,jenkins.getUserName(),jenkins.getUserPassword())).get("jobs"), Job[].class);
       
			processJenkins(jenkinsJobs, jenkins);
        
			final OutputStream out = new ByteArrayOutputStream();
			final ObjectMapper mapper1 = new ObjectMapper();

			mapper1.writeValue(out, buildDetails);
        
			final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
			System.out.println(new String(data));
        
			FileWriter file = new FileWriter(Global.JSON_FILE_PATH+"OUTPUT_"+jenkins.getJenkinsName());
			file.write(new String(data));
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
