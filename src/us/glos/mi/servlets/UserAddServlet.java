package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;
import org.glc.utils.Encryption;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IUserDAO;
import us.glos.mi.domain.UserAdminParam;

/**
 * Servlet implementation class for Servlet: UserAddServlet
 *
 */
public class UserAddServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 2265833509064339521L;
    static final String DAO_NAME="DAO";
    private IUserDAO dao=null;
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UserAddServlet() {
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
					if(ua.isNew())
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
				}
			}
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
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
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