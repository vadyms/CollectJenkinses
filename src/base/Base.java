package base;
import javax.servlet.http.HttpServlet;

public abstract class Base extends HttpServlet {
	
	public final static String JENKINSES_INPUT = "InputJenkinses.json";
	public String relativeWebPath = "/Data/";
	public String absoluteFilePath() {
		return getServletContext().getRealPath(relativeWebPath);
	}
}
