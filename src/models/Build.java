package models;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Build {
	private int number;
	private String url;
	private String[] subBuilds;
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String[] getSubBuilds() {
		return subBuilds;
	}

	public void setSubBuilds(String[] subBuilds) {
		this.subBuilds = subBuilds;
	}
}
