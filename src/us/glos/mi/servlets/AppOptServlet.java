package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.ILiteralProvider;
import org.glc.domain.User;
import org.glc.utils.ServerUtilities;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IDraftDAO;
import us.glos.mi.dao.IAppDAO;
import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.util.DocNotification;

/**
 * Servlet implementation class AppOptServlet
 */
public class AppOptServlet extends HttpServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7505360543836823452L;

	static final String DAO_NAME="DAO";
	static final String DRAFT_DAO_NAME="DRAFT_DAO";
	static final String TIMEZONE_NAME="TIMEZONE";
	static final String APP_SUBMIT_SUCCESS="APP_SUBMIT_SUCCESS";
	static final String APP_SUBMIT_NOTIFY="APP_SUBMITTAL_NOTIFICATION_RECEIVER";
	static final String APP_SUBMIT_EMAIL="APP_SUBMITED_EMAIL_TITLE";
	static final String APP_SUBMIT_FAIL="APP_SUBMIT_FAIL";
	static TimeZone tZone;
	static String[] mailReceivers=null;
	
	private IAppDAO dao=null;
	private IDraftDAO draft_dao=null;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AppOptServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String tz=this.getInitParameter(TIMEZONE_NAME);
		if(tz==null||tz.equals(""))
			throw new ServletException("TIMEZONE Parameter doesn't exist!");
		else
			tZone=TimeZone.getTimeZone(tz);
		String cln=this.getInitParameter(DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		String cln1=this.getInitParameter(DRAFT_DAO_NAME);
		if(cln1==null||cln1.equals(""))
			throw new ServletException("DRAFT_DAO Parameter doesn't exist!");
		try
		{
			Class<IAppDAO> iclass=(Class<IAppDAO>)Class.forName(cln);
			this.dao=iclass.getConstructor().newInstance(new Object[0]);
			this.dao.setTimeZone(tZone);
			
			Class<IDraftDAO> iclass1=(Class<IDraftDAO>)Class.forName(cln1);
			this.draft_dao=iclass1.getConstructor().newInstance(new Object[0]);
			this.draft_dao.setTimeZone(tZone);
			
			cln=ConfigManager.getAppSetting(APP_SUBMIT_NOTIFY);
			if(cln!=null)
				mailReceivers=cln.split(",");
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		String action=null;
		String msg=null;
		int returnVal=0;
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&null!=request.getAttribute(AppAdminParam.getClassName()))
			{
				AppAdminParam ma=null;
				if(request.getAttribute(AppAdminParam.getClassName()) instanceof AppAdminParam)
				    ma=(AppAdminParam)request.getAttribute(AppAdminParam.getClassName());
				if(ma!=null)
				{
					if(ma.isSubmit()&&ma.getApplication()!=null)
					{
						if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
						{
							/*if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))//put in end table
							{
								
							}
							else*/ //put in draft mode
							{
								action="result";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								if(this.draft_dao.insertDraftByUId(usr.getId(), ma.getApplication())>0)
								{
									msg=ConfigManager.getAppSetting(APP_SUBMIT_SUCCESS);
									if(msg==null)
										msg="Your model has been successfully submitted.";
									//session.setAttribute("MODEL_SUBMITTAL_RESULT_MESSAGE", msg);
									//session.setAttribute("MODEL_SUBMITTAL_RESULT_VALUE","0");
									if(mailReceivers!=null&&mailReceivers.length>0)
									{
										String title=ConfigManager.getAppSetting(APP_SUBMIT_EMAIL);
										if(title==null)
											title="Notification";
										Calendar cal=Calendar.getInstance();
										SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										//StringBuilder sb=new StringBuilder();
										String content=String.format("%s %s (%s) submitted a new application named \"%s\" at %s Eastern Time.", 
												usr.getFirstName(),
												usr.getLastName(),
												usr.getEmail(),
												ma.getApplication().getName(),
												sdf.format(cal.getTime()));


										DocNotification notify=new DocNotification(title,null,mailReceivers,content);
										notify.start();
									}	
									returnVal=0;
								}
								else
								{
									msg=ConfigManager.getAppSetting(APP_SUBMIT_FAIL);
									if(msg==null)
										msg="We are experiencing technical difficulty and can not process your request now.";
									//session.setAttribute("MODEL_SUBMITTAL_RESULT_MESSAGE", msg);
									//session.setAttribute("MODEL_SUBMITTAL_RESULT_VALUE","1");
									returnVal=-1;
								}
								ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action,"APP_SUBMITTAL_RESULT_MESSAGE", msg);
								ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action,"APP_SUBMITTAL_RESULT_VALUE", returnVal);
								//session.setAttribute("MODEL_NAME",ma.getModel().getName());
							}
						}
						else //not authorized
						{
							
						}
						ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						return;
					}
				}
			}
		}
	}

}
