package us.glos.mi.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import us.glos.mi.db.SqlHelper;

/**
 * Servlet implementation class AppCheckDeveloperServlet
 */
public class AppCheckDeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppCheckDeveloperServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("appid")!=null)
		{
			try
			{
				int id=Integer.parseInt(request.getParameter("appid"));
				ArrayList<String> list=SqlHelper.getApplicationDevelopers(id);
				if(list!=null&&!list.isEmpty())
				{
					int len=list.size();
					for(int i=0;i<len;++i)
					{
						response.getWriter().print(list.get(i));
						if(i<len-1)
						    response.getWriter().print(", ");
					}
					response.getWriter().flush();
				}
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
