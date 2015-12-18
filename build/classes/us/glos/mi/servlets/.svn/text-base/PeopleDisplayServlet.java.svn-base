package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glc.ILiteralProvider;
import org.glc.domain.User;
import org.glc.utils.ServerUtilities;

import us.glos.mi.dao.IUserDAO;
/**
 * Servlet implementation class PeopleDisplayServlet
 */
public class PeopleDisplayServlet extends HttpServlet {
	static final String DAO_NAME="DAO";
	private IUserDAO dao=null;
	private static String BasePath=null;
    /**
	 * 
	 */
	private static final long serialVersionUID = -8437129365454653839L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public PeopleDisplayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		String cln1=this.getInitParameter(DAO_NAME);
		if(cln1==null||cln1.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			
			
			Class<IUserDAO> iclass1=(Class<IUserDAO>)Class.forName(cln1);
			this.dao=iclass1.getConstructor().newInstance(new Object[0]);
			
			
			
		}
		catch(ClassNotFoundException cfe)
		{
			throw new ServletException(cfe.getMessage());
		}
		catch(IllegalAccessException iae)
		{
			throw new ServletException(iae.getMessage());
		}
		catch(InvocationTargetException ite)
		{
			throw new ServletException(ite.getMessage());
		}
		catch(InstantiationException ie)
		{
			throw new ServletException(ie.getMessage());
		}
		catch(NoSuchMethodException nse)
		{
			throw new ServletException(nse.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(BasePath==null)
			BasePath=request.getContextPath()+request.getServletPath();
		String action="error";
		request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
		String url=request.getRequestURI();
		if(BasePath.length()<url.length()&&url.charAt(BasePath.length())=='/')
		{
			//response.getWriter().print(url.substring(BasePath.length()+1));
			try
			{
				int id=Integer.parseInt(url.substring(BasePath.length()+1));
				User contact=null;
				if(id>0)
					contact=this.dao.getUserById(id);
				if(contact!=null)
				{
					action="result";
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, User.getClassName(), contact);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "URI", url);
				}
				
			}
			catch(NumberFormatException nfe)
			{
				
			}
		}
		ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
