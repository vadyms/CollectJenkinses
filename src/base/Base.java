package base;

import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public abstract class Base extends HttpServlet {
	
	static Logger logger = Logger.getLogger(Base.class);
	
	public Base() {
		super();


		String log4jConfigFile = "../wtpwebapps/CollectJenkins/WEB-INF/log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        logger.info("Start logger!");
	}

	public String relativeWebPath = "/Data/";
	
	public String absoluteFilePath() {
		return getServletContext().getRealPath(relativeWebPath);
	}
}
