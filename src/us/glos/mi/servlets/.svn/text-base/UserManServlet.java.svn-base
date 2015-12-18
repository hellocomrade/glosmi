package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimeZone;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;
import org.glc.utils.Validation;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IUserDAO;
import us.glos.mi.domain.UserAdminParam;
import us.glos.mi.domain.UserWrapper;
/**
 * Servlet implementation class UserManServlet
 */
public class UserManServlet extends HttpServlet {
	  
    /**
	 * 
	 */
	private static final long serialVersionUID = 3865919115842236564L;
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
	static final String DAO_NAME="DAO";
	private IUserDAO dao=null;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UserManServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		String tz=this.getInitParameter(UserManServlet.TIMEZONE_NAME);
		if(tz==null||tz.equals(""))
			throw new ServletException("TIMEZONE Parameter doesn't exist!");
		else
			tZone=TimeZone.getTimeZone(tz);
		String cln=this.getInitParameter(DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			Class<IUserDAO> iclass=(Class<IUserDAO>)Class.forName(cln);
			this.dao=iclass.getConstructor().newInstance(new Object[0]);
			//this.dao.setTimeZone(tZone);
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
		User usr=null;
		UserWrapper uw=null;
		String aj=null;
		int id=0;
		UserAdminParam param=new UserAdminParam();
		if(Validation.basicValidation(request, param.getAjaxParamName()))
		{
			aj=request.getParameter(param.getAjaxParamName());
			if(!aj.equals("1"))
			{
				response.getWriter().print("I only do ajax.");
				return;
			}

			HttpSession session=request.getSession(false);
			if(session.getAttribute(User.getClassName()) instanceof User)
				usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
			{
				if(request.getParameter("uid")!=null)
				{
					try
					{
						id=Integer.parseInt(request.getParameter("uid").trim());
						if(id>0)
						{
							usr=this.dao.getUserById(id);
							if(usr!=null)
							{
								uw=new UserWrapper(usr);
								response.getWriter().print(uw.ToJSON());
								return;
							}
						}
					}
					catch(NumberFormatException nfe)
					{

					}
				}

			}
		}
		response.getWriter().print("1");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserAdminParam usrp=null;
		User usr=null;
		HttpSession session=request.getSession(false);
		if(request.getAttribute(UserAdminParam.getClassName()) instanceof UserAdminParam)
		    usrp=(UserAdminParam)request.getAttribute(UserAdminParam.getClassName());
		if(session.getAttribute(User.getClassName()) instanceof User)
			usr=(User)session.getAttribute(User.getClassName());

		if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
		{
			if(usrp.getAction()!=null)
			{
				if(usrp.isChangeGroup())
				{
					int priv=UserPrivilegeMask.CreatPrivilegeMask(usrp.isAdm(), usrp.isMod(), usrp.isApp());
					if(usrp.getUsr()!=null&&priv>0)
					{
						usrp.getUsr().setPrivilegeLevel(priv);
						if(this.dao.setPrivilege(usrp.getUsr()))
						{
							if(usrp.getUsr().getEmail().equalsIgnoreCase(usr.getEmail()))
							{
								usr.setPrivilegeLevel(priv);
								//session.setAttribute(User.getClassName(), usr);
							}
							response.getWriter().print("0");
							return;
						}
						
					}
					
				}
				else if(usrp.isChangeStatus())
				{
					if(usrp.getUsr()!=null&&!usrp.getUsr().getEmail().equalsIgnoreCase(usr.getEmail()))
					{
						if(this.dao.updateUserStatus(usrp.getUsr(), usrp.isActive()))
						{
							response.getWriter().print("0");
							return;
						}
					}
				}
			}
		}
		response.getWriter().print("1");
	}

}
