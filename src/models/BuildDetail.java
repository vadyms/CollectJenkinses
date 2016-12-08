package models;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildDetail {
	private String jobName;
	private String jobUrl;
	private String result;
	private int totalCount;
	private int failCount;
	private int passCount;
	
	public String getJobUrl() {
		return jobUrl;
	}

	public void setJobUrl(String jobUrl) {
		this.jobUrl = jobUrl;
	}

	public String getJobName() {
		return jobName;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getFailCount() {
		return failCount;
	}
	
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	
	public int getPassCount() {
		return passCount;
	}
	
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	
}
