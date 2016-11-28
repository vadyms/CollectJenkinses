package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.Base;

/**
 * Servlet implementation class SaveConfiguration
 */
@WebServlet("/SaveConfiguration")
public class SaveConfiguration extends Base {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveConfiguration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("info"));
		System.out.println("Get saving;");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("info"));
		
		String absoluteFilePath = absoluteFilePath()+JENKINSES_INPUT;
		System.out.println(absoluteFilePath);
		
		try (PrintStream out = new PrintStream(new FileOutputStream(absoluteFilePath))) {
		    out.print(request.getParameter("info"));
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
