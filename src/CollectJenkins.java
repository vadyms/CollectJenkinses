import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import base.Base;

/**
 * Servlet implementation class CollectJenkins
 */
@WebServlet("/CollectJenkins")
public class CollectJenkins extends Base {
	private static final long serialVersionUID = 1L;
    private static boolean isCollecting;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CollectJenkins() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter outPrintWriter = response.getWriter();
		if (isCollecting) {
			outPrintWriter.print("Data refresh already in progress!!!");
			System.out.println("Request to collect already in progress!");
		} else {
			isCollecting=true;
			String absoluteFilePath = absoluteFilePath();
			System.out.println(absoluteFilePath);
			
			try {
				Start.main(new String[]{absoluteFilePath});
				outPrintWriter.print("Data has been refreshed");
				isCollecting=false;
			} catch (Exception e) {
				isCollecting=false;
				outPrintWriter.print("Service can NOT collect data!");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
