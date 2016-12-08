package base;

import javax.servlet.http.HttpServlet;

public abstract class Base extends HttpServlet {
	public String relativeWebPath = "/Data/";
	public String absoluteFilePath() {
		return getServletContext().getRealPath(relativeWebPath);
	}
}
