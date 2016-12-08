import org.codehaus.jackson.map.ObjectMapper;

public class ProcessJob extends Thread {

	private BuildDetail currentBuildDetail;
	private ObjectMapper mapper = new ObjectMapper();
	private Jenkins jenkins;
	private Job job;
	private ProcessJenkins processJenkins;
	
	public Jenkins getJenkins() {
		return jenkins;
	}

	public void setJenkins(Jenkins jenkins) {
		this.jenkins = jenkins;
	}
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	public BuildDetail getCurrentBuildDetail() {
		return currentBuildDetail;
	}
	

	public void setCurrentBuildDetail(BuildDetail currentBuildDetail) {
		this.currentBuildDetail = currentBuildDetail;
	}

	public ProcessJenkins getProcessJenkins() {
		return processJenkins;
	}

	public void setProcessJenkins(ProcessJenkins processJenkins) {
		this.processJenkins = processJenkins;
	}

	@Override
	public void run() {
		analyseJob();
	}
	
	public void analyseJob() {
		currentBuildDetail = new BuildDetail();
		
    	if ((job!=null)&&(job.getName()!="")&&job.getName()!=null) {
        	String jobUrl = job.getUrl() + Global.JENKINS_API_SUFFICS;
        	String jobJson = BaseActions.getJson(jobUrl, jenkins.getUserName(),jenkins.getUserPassword());
        	try {
            	Build[] builds = mapper.readValue(mapper.readTree(jobJson).get("builds"), Build[].class);
            	if (builds.length==0) {
	        		currentBuildDetail.setJobName(job.getName());
	        		//currentBuildDetail.setJobUrl(j.getUrl());
	        		currentBuildDetail.setJobUrl("<a href='" + job.getUrl() + "' target='_blank'/>" + job.getName() + "</a>");
	        		currentBuildDetail.setResult("No runs");
	        		currentBuildDetail.setTotalCount(0);
	        		currentBuildDetail.setFailCount(0);
	        		currentBuildDetail.setPassCount(0);
	        		processJenkins.addResult(currentBuildDetail);
            	} else {
	            	String buildDetailUrl = builds[0].getUrl()+Global.JENKINS_API_SUFFICS;
	            	System.out.println(buildDetailUrl);
	            	
	            	String buildDetailsJson = BaseActions.getJson(buildDetailUrl,jenkins.getUserName(),jenkins.getUserPassword()); 
	            	System.out.println("count: "+BaseActions.getValueFromJson(buildDetailsJson,"totalCount"));
	            	System.out.println("fails: "+BaseActions.getValueFromJson(buildDetailsJson,"failCount"));
	            	System.out.println("result: "+BaseActions.getValueFromJson(buildDetailsJson,"result"));
	            	
	        		currentBuildDetail.setJobName(job.getName());
	        		//currentBuildDetail.setJobUrl(j.getUrl());
	        		currentBuildDetail.setJobUrl("<a href='" + job.getUrl() + "' target='_blank'/>" + job.getName() + "</a>");
	        		currentBuildDetail.setResult(BaseActions.getValueFromJson(buildDetailsJson,"result"));
	        		currentBuildDetail.setTotalCount(BaseActions.TryParseInt(BaseActions.getValueFromJson(buildDetailsJson,"totalCount")));
	        		currentBuildDetail.setFailCount(BaseActions.TryParseInt(BaseActions.getValueFromJson(buildDetailsJson,"failCount")));
	        		currentBuildDetail.setPassCount(currentBuildDetail.getTotalCount()-currentBuildDetail.getFailCount());
	        		processJenkins.addResult(currentBuildDetail);
            	}
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
