package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.ILiteralProvider;
import org.glc.domain.User;
import org.glc.utils.ServerUtilities;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IAppDAO;
import us.glos.mi.domain.AppInfo;


/**
 * So far, it's just a relay point to match app onto the corresponding model page
 * But, you can extend it to have individual app page instead.
 */
public class AppDisplayServlet extends HttpServlet {
	static final String DAO_NAME="DAO";
	private static String BasePath=null;
	private IAppDAO dao=null;
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 887872319108417725L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AppDisplayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String cln1=this.getInitParameter(DAO_NAME);
		if(cln1==null||cln1.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			
			
			Class<IAppDAO> iclass1=(Class<IAppDAO>)Class.forName(cln1);
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
		boolean isOwnerView=false;
		request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
		String url=request.getRequestURI();
		if(BasePath.length()<url.length()&&url.charAt(BasePath.length())=='/')
		{
			//response.getWriter().print(url.substring(BasePath.length()+1));
			try
			{
				int id=Integer.parseInt(url.substring(BasePath.length()+1));
				AppInfo app=null;
				if(id>0)
					app=this.dao.getAppInfoById(id);
				if(app!=null)
				{
					HttpSession session=request.getSession(false);
					if(session!=null)
					{
						User usr=null;
						if(session.getAttribute(User.getClassName()) instanceof User)
						{
							usr=(User)session.getAttribute(User.getClassName());
							if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
								isOwnerView=true;
							else
								isOwnerView=this.dao.isOwnerOfApp(usr.getId(), app.getId());
						}
					}
					if(isOwnerView)
						request.setAttribute("OWNER_APP_ID", id);
					request.getRequestDispatcher(String.format("../model/%d", app.getModId())).forward(request, response);
					return;
				}
				
					
			}
			catch(NumberFormatException nfe)
			{
				
			}
		}
		response.sendRedirect("../error.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
