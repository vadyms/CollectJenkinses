package com;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import base.Base;
import base.Global;

/**
 * Servlet implementation class SaveConfiguration
 */
@WebServlet("/SaveConfiguration")
public class SaveConfiguration extends Base {
	private static final long			serialVersionUID		= 1L;
	private static final String			CLIENT_PARAMETER		= "info";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveConfiguration() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter(CLIENT_PARAMETER));
		System.out.println("Get saving;");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter(CLIENT_PARAMETER));
		
		String absoluteFilePath = absoluteFilePath()+Global.JENKINSES_INPUT;
		System.out.println(absoluteFilePath);
		
		try (PrintStream out = new PrintStream(new FileOutputStream(absoluteFilePath))) {
		    out.print(request.getParameter(CLIENT_PARAMETER));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//
//			File file = new File(absoluteFilePath);
//
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(request.getParameter("info"));
//			bw.close();
//
//			System.out.println("Done");
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		System.out.println("Post saving;");
	}

}
