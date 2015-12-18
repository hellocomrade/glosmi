package us.glos.mi.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import us.glos.mi.db.SqlHelper;

/**
 * Servlet implementation class ModCheckStatusServlet
 */
public class ModCheckStatusServlet extends HttpServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6035696733487441224L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ModCheckStatusServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("modid")!=null)
		{
			try
			{
				int id=Integer.parseInt(request.getParameter("modid"));
				if(SqlHelper.isModelEnabled(id))
					response.getWriter().print("1");
				else
					response.getWriter().print("0");
				
			}
			catch(NumberFormatException ne)
			{
				response.getWriter().print("-1");
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
