package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Encryption;
import us.glos.mi.dao.IUserDAO;
import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.domain.UserAdminParam;
/**
 * Servlet implementation class for Servlet: UserAdminServlet
 *
 */
public class UserAdminServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   
   /**
	 * 
	 */
	private static final long serialVersionUID = -4933526247860130117L;
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
    static final String DAO_NAME="DAO";
   
    private IUserDAO dao=null;
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UserAdminServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			
			
			if(usr!=null&&null!=request.getAttribute(UserAdminParam.getClassName()))
			{
				UserAdminParam ua=null;
				if(request.getAttribute(UserAdminParam.getClassName()) instanceof UserAdminParam)
				    ua=(UserAdminParam)request.getAttribute(UserAdminParam.getClassName());
				if(ua!=null)
				{
					if(ua.isNew()&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
					{
						User u=copyUser(ua);
						u.setEmail(ua.getUsr().getEmail());
						u.setUsername(ua.getUsr().getEmail());
						u.setPrivilegeLevel(UserPrivilegeMask.CreatPrivilegeMask(ua.isAdm(), 
																				 ua.isMod(),
								                                                 ua.isApp()));
						u.setPassword(Encryption.MD5(ua.getNewPassword()));
						if(this.dao.addUser(u))
						{
							response.getWriter().print('0');
							return;
						}
						else
						{
							response.getWriter().print('1');
							return;
						}
					}
					else if(ua.isChangePassword()&&false==UserPrivilegeMask.IsNonPrivilege(usr.getPrivilegeLevel()))
					{
						User u=null;
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{

							if(ua.getEmail()==null||ua.getEmail().equals(usr.getEmail()))//change admin's own password
							{
								u=usr;
							}
							else//change another user's password
							{
								u=new User();
								u.setEmail(ua.getEmail());
								u.setUsername(ua.getEmail());
							}

						}
						else//you can only change your own's password
						{
							/*if(!ua.getEmail().equals(usr.getEmail()))
							{
								ErrorMessage err=new ErrorMessage();
								err.addError("err", "You don't have the privilege to conduct this action.");
								response.getWriter().println(err.getErrorJSON());
								return;
							}*/
							if(this.dao.isValidUser(usr.getEmail(), Encryption.MD5(ua.getOldPassword())))
								u=usr;

						}
						if(u!=null&&ua.getOldPassword()!=null)
							u.setPassword(Encryption.MD5(ua.getOldPassword()));
						if(u!=null&&this.dao.resetPassword(u, Encryption.MD5(ua.getNewPassword())))
						{
							response.getWriter().print('0');
							return;
						}
						else
						{
							response.getWriter().print('1');
							return;
						}
					}
					else if(ua.isUpdate()&&false==UserPrivilegeMask.IsNonPrivilege(usr.getPrivilegeLevel()))
					{
						User u=null;
						boolean isUpdateSession=false;
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{

							if(ua.getUsr().getEmail()==null||ua.getUsr().getEmail().equals(usr.getEmail()))//change admin's info
							{
								u=copyUser(ua);
								u.setEmail(usr.getEmail());
								u.setUsername(usr.getEmail());
								isUpdateSession=true;
							}
							else//change another user's info
							{
								if(ua.getUsr().getEmail()==null)
								{
									ErrorMessage err=new ErrorMessage();
									err.addError("err", "Account name(Email) is required.");
									response.getWriter().println(err.getErrorJSON());
									return;
								}
								u=copyUser(ua);
								u.setEmail(ua.getUsr().getEmail());
								u.setUsername(ua.getUsr().getEmail());
							}

						}
						else//you can only change your own's info
						{
							u=copyUser(ua);
							u.setEmail(usr.getEmail());
							u.setUsername(usr.getEmail());
							isUpdateSession=true;
						}
						int oid=this.dao.getOrgnizationId(u.getOrgnization().getAbbrev(),u.getOrgnization().getName());
						if(oid>0&&this.dao.updateUserInfo(u,oid))
						{
							if(isUpdateSession)
							{
								//u.setPrivilegeLevel(usr.getPrivilegeLevel());
								//session.setAttribute(User.getClassName(),u);
								copyUser(usr,ua);
							}
							response.getWriter().print('0');
							return;
						}
						else
						{
							if(oid<=0)
								response.getWriter().print('2');
							else
								response.getWriter().print('1');
							return;
						}
					}
					else if(ua.isRemove()&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
					{

					}
					else
					{

					}
				}
			}
			
		}
	}   	  	  
	private void copyUser(User usr,UserAdminParam up)
	{
		if(usr!=null&&up.getUsr()!=null)
		{
			usr.setFirstName(up.getUsr().getFirstName());
			usr.setLastName(up.getUsr().getLastName());
			usr.getAddress().setAddress1(up.getUsr().getAddress().getAddress1());
			usr.getAddress().setAddress2(up.getUsr().getAddress().getAddress2());
			usr.getAddress().setCity(up.getUsr().getAddress().getCity());
			usr.getAddress().setState(up.getUsr().getAddress().getState());
			usr.getAddress().setCountry(up.getUsr().getAddress().getCountry());
			usr.getAddress().setZipcode(up.getUsr().getAddress().getZipcode());
			usr.getAddress().setPhone(up.getUsr().getAddress().getPhone());
			usr.getOrgnization().setAbbrev(up.getOrgId());
			usr.getOrgnization().setName(up.getOrgId());
		}
	}
	private User copyUser(UserAdminParam up)
	{
		User usr=null;
		if(up!=null&&up.getUsr()!=null)
		{
			usr=new User();
			usr.setFirstName(up.getUsr().getFirstName());
			usr.setLastName(up.getUsr().getLastName());
			usr.getAddress().setAddress1(up.getUsr().getAddress().getAddress1());
			usr.getAddress().setAddress2(up.getUsr().getAddress().getAddress2());
			usr.getAddress().setCity(up.getUsr().getAddress().getCity());
			usr.getAddress().setState(up.getUsr().getAddress().getState());
			usr.getAddress().setCountry(up.getUsr().getAddress().getCountry());
			usr.getAddress().setZipcode(up.getUsr().getAddress().getZipcode());
			usr.getAddress().setPhone(up.getUsr().getAddress().getPhone());
			usr.getOrgnization().setAbbrev(up.getOrgId());
			usr.getOrgnization().setName(up.getOrgId());
				
		}
		return usr;
	}
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		String tz=this.getInitParameter(TIMEZONE_NAME);
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
			this.dao.setTimeZone(tZone);
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