package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;

import us.glos.mi.dao.IUserDAO;
import us.glos.mi.domain.UserParam;

/**
 * Servlet implementation class for Servlet: UserAvailServlet
 *
 */
public class UserAvailServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8212811397111068428L;
	static final String DAO_NAME="DAO";
    private IUserDAO dao=null;
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UserAvailServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserParam usrp=null;
		HttpSession session=request.getSession(false);
		if(request.getAttribute(UserParam.getClassName()) instanceof UserParam)
		    usrp=(UserParam)request.getAttribute(UserParam.getClassName());
		if(session!=null&&session.getAttribute(User.getClassName()) instanceof User)
		{
			if(usrp.getUserName()!=null&&usrp.getUserName().equals("")==false)
			{
				if(this.dao.isUserExist(usrp.getUserName()))
				{
					response.getWriter().print("0");
					return;
				}
				
			}
		}
		response.getWriter().print("-1");
		return;
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}   	  	  
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		String cln=this.getInitParameter(DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			Class<IUserDAO> iclass=(Class<IUserDAO>)Class.forName(cln);
			this.dao=iclass.getConstructor().newInstance(new Object[0]);
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
}