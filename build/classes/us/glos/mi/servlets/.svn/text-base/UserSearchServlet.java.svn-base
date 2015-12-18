package us.glos.mi.servlets;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glc.ILiteralProvider;
import org.glc.utils.ServerUtilities;
import org.glc.utils.Validation;
import org.glc.domain.User;
import us.glos.mi.dao.IUserDAO;

/**
 * Servlet implementation class for Servlet: UserSearchServlet
 *
 */
 public class UserSearchServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -616694196688314544L;
	static final String DAO_NAME="DAO";
    static final String USER_SEARCH_RESULT_KEY="USER_SEARCH_RESULT_KEY";
    private IUserDAO dao=null;
    private String className=null;
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UserSearchServlet() {
		super();
		this.className=this.getClass().getName();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
		if(request.getParameter("key")!=null&&!request.getParameter("key").equals(""))
		{
			String temp=request.getParameter("key");
			String[] keys=temp.split(" ");
			String fname=null;
			String lname=null;
			String email=null;
			int count=0;
			/*
			 * we only support up to 3 key words including fname,lname and email
			 * email support includes *@glc.org using * as wildcard
			 */
			for(String s:keys)
			{
				if(count==3)break;
				if(s.equalsIgnoreCase("all"))
				{
					fname=lname=email="%";
					break;
				}
				if(Validation.isValidEmail(s)&&email==null)
					email=s;
				else if(Validation.isValidEmailDomain(s)&&email==null)
				{
					email=s.substring(s.indexOf('@'));
				}
				else if(fname==null)//first name has the priority
					fname=s;
				else
					lname=s;
				++count;
			}
			/*
			 * if only one name is assigned, we use it on both fname and lname
			 */
			if(fname!=null&&lname==null)
				lname=fname;
			ArrayList<User> users=this.dao.searchUsers(fname, lname, email);
			if(users!=null&&users.isEmpty()==false)
			{
				ServerUtilities.SetValueOnScope(request, request.getSession(false), className, USER_SEARCH_RESULT_KEY, users);
				ServerUtilities.GoTo(this.getServletContext(), request, response, this.className, "done");
				return;
			}
			/*else
			{

			}*/
		}
		//else
		//{
			ServerUtilities.GoTo(this.getServletContext(), request, response, this.className, "empty");
			return;
		//}
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