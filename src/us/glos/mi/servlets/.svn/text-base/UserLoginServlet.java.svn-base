package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import org.glc.ILiteralProvider;
import org.glc.domain.ErrorMessage;
import org.glc.utils.Encryption;
import org.glc.utils.HTMLUtilities;
import org.glc.utils.ServerUtilities;
import org.glc.xmlconfig.ConfigManager;
import org.glc.domain.User;
import us.glos.mi.dao.IUserDAO;
import us.glos.mi.domain.UserParam;
import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.db.SystemStatistics;

/**
 * Servlet implementation class for Servlet: UserLoginServlet
 *
 */
public class UserLoginServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -7380341473062705843L;
	static final String DAO_NAME="DAO";
    static final String COOKIE_MAX_AGE_NAME="COOKIE_MAX_AGE";
    static final String TIMEZONE_NAME="TIMEZONE";
    static TimeZone tZone;
    //public static final String USER_PARAM="USER_PARAM";
    private IUserDAO dao=null;
    private int cookie_age=0;
    private String className=null;
    private String appRoot=null;
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public UserLoginServlet() {
		super();
		this.className=this.getClass().getName();
		
		
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
		return;
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User usr=null;
		UserParam usrp=null;
		SystemStatistics stat=null;
		boolean isLogin=false;
		String path=ConfigManager.getActionForward(this.className, "error");
		HttpSession session=request.getSession(false);
		if(request.getAttribute(UserParam.getClassName()) instanceof UserParam)
		    usrp=(UserParam)request.getAttribute(UserParam.getClassName());
		request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
		if(usrp!=null)
		{
			if(session==null
					||!(session.getAttribute(User.getClassName()) instanceof User))
			{
				if(usrp.getPassword()==null||usrp.isLogout())//first hit,don't create session
				{
					path=ConfigManager.getActionForward(this.className, "login");
					if(ConfigManager.isForwardRedirect(this.className, "login"))
						response.sendRedirect(String.format("%s%s", this.appRoot,path));
					else
					{
						//request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
						ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), "login", "LAST_URL", usrp.getLastUri());
						this.forward(path, request, response);
					}
					return;
				}
				else//try login
				{
					isLogin=true;
				}
			}
			else//session exists
			{
				if(session.getAttribute(User.getClassName()) instanceof User)
					usr=(User)session.getAttribute(User.getClassName());

				if(usr!=null)
				{
					if(usrp!=null&&usrp.isLogout())//logout return user with valid session
					{
						session.removeAttribute(User.getClassName());
						session.invalidate();
						//System.out.println("Remove:"+session);
						ErrorMessage err=(ErrorMessage)request.getAttribute(ErrorMessage.getClassName());
						path=ConfigManager.getActionForward(this.className, "logout");
						if(ConfigManager.isForwardRedirect(this.className, "logout"))
							response.sendRedirect(String.format("%s%s", this.appRoot,path));
						else
						{
							//request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
							this.forward(path, request, response);
						}

						return;
					}
					else//request page
					{
						//String temp=String.format("%s/%s/", this.getServletContext().getContextPath(),
						//		this.getSubPath(usr.getPrivilegeLevel()));
						//request.setAttribute("ROOT_PRI_PATH", temp);
						path=this.getDefaultPath(usr.getPrivilegeLevel());
						stat=new SystemStatistics(usr.getPrivilegeLevel(),this.getServletContext());
						request.setAttribute(SystemStatistics.class.getName(),stat);
						dispatch(usr.getPrivilegeLevel(),path,request,response);
						return;
					}

				}
				else
				{
					session.invalidate();

				}
				//System.out.println("Invalidated:"+session);

			}
		}
		if(isLogin)
		{
			usr=new User();
			usr.setEmail(usrp.getUserName());
			usr.setPassword(Encryption.MD5(usrp.getPassword()));
			if(this.dao.isValidUser(usr)==true)
			{
				if(usrp.isRemembered())
				{
					HTMLUtilities.SetCookie(request, response, 
							this.cookie_age, 
							null, 
							usrp.getUserParamName(),
							usrp.getUserName()+" ");
					HTMLUtilities.SetCookie(request, response, 
							this.cookie_age, 
							null,
							usrp.getPasswordParamName(),
							usrp.getPassword());
				}
				else
				{
					HTMLUtilities.RemoveCookie(request, response, 
							null, 
							usrp.getUserParamName(),
							usrp.getUserName());
					HTMLUtilities.RemoveCookie(request, response, 
							null, 
							usrp.getPasswordParamName(),
							usrp.getPassword());
				}
								
				path=this.getDefaultPath(usr.getPrivilegeLevel());
				
				String temp=String.format("%s/%s/", this.getServletContext().getContextPath(),
						this.getSubPath(usr.getPrivilegeLevel()));
				request.setAttribute("ROOT_PRI_PATH", temp);
				stat=new SystemStatistics(usr.getPrivilegeLevel(),this.getServletContext());
				request.setAttribute(SystemStatistics.class.getName(),stat);
				
				session=request.getSession();
				session.setAttribute(User.getClassName(), usr);
				if(usrp.getLastUri()!=null)
				{
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), "redirect", "HOME", request.getRequestURI());
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), "redirect", "LAST_URI", usrp.getLastUri());
					ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), "redirect");
				}
				else
					dispatch(usr.getPrivilegeLevel(),path,request,response);
				return;
			}
			else//login fail
			{
				ErrorMessage errMsg=new ErrorMessage();
				errMsg.addError("err", "User name or password is not valid");
				request.setAttribute(ErrorMessage.getClassName(), errMsg);
				path=ConfigManager.getActionForward(this.className, "login");
				if(ConfigManager.isForwardRedirect(this.className, "login"))
					response.sendRedirect(String.format("%s%s", this.appRoot,path));
				else
					this.forward(path, request, response);
				return;
			}
			
		}
		//the request reaching here are all problematic
		
		ErrorMessage errMsg=new ErrorMessage();
		errMsg.addError("err", "User name or password is not valid.");
		request.setAttribute(ErrorMessage.getClassName(), errMsg);
		path=ConfigManager.getActionForward(this.className, "error");
		if(ConfigManager.isForwardRedirect(this.className, "error"))
			response.sendRedirect(path);
		else
			this.forward(path, request, response);
		
		
	}
	private void dispatch(int priv,String path,HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
	{
		if(isRedirect(priv))
		{
			response.sendRedirect(String.format("%s%s", this.appRoot,path));
		}
		else
			this.forward(path, request, response);
	}
	private String getSubPath(int priv)
	{
		if((priv&UserPrivilegeMask.ADMIN_MASK)>0)
			return UserPrivilegeMask.getAdminSubPath();
		else if((priv&UserPrivilegeMask.MODELER_MASK)>0)
			return UserPrivilegeMask.getModelerSubPath();
		else if((priv&UserPrivilegeMask.APP_MASK)>0)
			return UserPrivilegeMask.getApplicationSubPath();
		else
			return null;
	}
	private String getDefaultPath(int priv)
	{
		if((priv&UserPrivilegeMask.ADMIN_MASK)>0)
		{
			return ConfigManager.getActionForward(this.className, "adm");
		}
		else if((priv&UserPrivilegeMask.MODELER_MASK)>0)
		{
			return ConfigManager.getActionForward(this.className, "mod");
		}
		else if((priv&UserPrivilegeMask.APP_MASK)>0)
		{
			return ConfigManager.getActionForward(this.className, "app");
		}
		else
			return null;
	}
	private boolean isRedirect(int priv)
	{
		boolean isRedirect=false;
		if((priv&UserPrivilegeMask.ADMIN_MASK)>0)
		{
			isRedirect=ConfigManager.isForwardRedirect(this.className, "adm");
		}
		else if((priv&UserPrivilegeMask.MODELER_MASK)>0)
		{
			isRedirect=ConfigManager.isForwardRedirect(this.className, "mod");
		}
		else if((priv&UserPrivilegeMask.APP_MASK)>0)
		{
			isRedirect=ConfigManager.isForwardRedirect(this.className, "app");
		}
		else
		{

		}
		return isRedirect;
	}
	private void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		this.appRoot=this.getServletContext().getContextPath();
		this.cookie_age=Integer.parseInt(this.getInitParameter(COOKIE_MAX_AGE_NAME));
		if(this.cookie_age<0)
			this.cookie_age=0;
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