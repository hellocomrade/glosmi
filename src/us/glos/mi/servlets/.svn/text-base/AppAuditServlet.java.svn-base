package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimeZone;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.ILiteralProvider;
import org.glc.domain.User;
import org.glc.domain.ErrorMessage;
import org.glc.utils.ServerUtilities;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IDraftDAO;
import us.glos.mi.dao.IAppDAO;
import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.Contact;
import us.glos.mi.util.DocNotification;

/**
 * Servlet implementation class AppAuditServlet
 */
public class AppAuditServlet extends HttpServlet {
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
    static final String DAO_NAME="DAO";
    static final String DRAFT_DAO_NAME="DRAFT_DAO";
    static final String APP_AUDIT_SUCCESS="APP_AUDIT_SUCCESS";
	static final String APP_AUDIT_FAIL="APP_AUDIT_FAIL";
	static final String APP_APPROVED_EMAIL_TITLE="APP_APPROVED_EMAIL_TITLE";
    private IAppDAO dao=null;
    private IDraftDAO draft_dao=null;  
    /**
	 * 
	 */
	private static final long serialVersionUID = -7676760400063509215L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AppAuditServlet() {
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
		HttpSession session=request.getSession(false);
		String action=null;
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
			{
				AppAdminParam apparam=null;
				if(request.getAttribute(AppAdminParam.getClassName()) instanceof AppAdminParam)
					apparam=(AppAdminParam)request.getAttribute(AppAdminParam.getClassName());
				if(apparam!=null&&apparam.getApplication()!=null)
				{
					if(apparam.isAudit()&&apparam.getApplication().getSerialId()>0)
					{
						request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
						AppInfo app=null;
						app=(AppInfo)this.draft_dao.getDraftById(apparam.getApplication().getSerialId());
						if(app!=null)
						{
							//request.setAttribute(ModelInfo.getClassName(), model);
							action="audit";
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action,AppInfo.getClassName(), app);
						}
						else
							action="error";
						ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						return;
					}
					else if(apparam.isUpdate()&&apparam.getApplication().getId()>0)
					{
						request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
						AppInfo app=null;
						app=this.dao.getAppInfoById(apparam.getApplication().getId());
						if(app!=null)
						{
							//request.setAttribute(ModelInfo.getClassName(), model);
							action="update";
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action,AppInfo.getClassName(), app);
						}
						else
						{
							action="error";
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, "APP_ERROR", "The application you request doesn't exist. It may be removed by auditor already!");
						}
						ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						return;
					}
					else if(apparam.isRemove())
					{
						if(apparam.getApplication().getSerialId()>0)
						{
							if(this.draft_dao.removeDraftById(apparam.getApplication().getSerialId()))
							{
								response.getWriter().print("0");
								return;
							}
							else
							{
								response.getWriter().print("-1");
								return;
							}
						}
						else if(apparam.getApplication().getId()>0)
						{
							if(this.dao.removeAppInfoById(apparam.getApplication().getId()))
							{
								response.getWriter().print("0");
								return;
							}
							else
							{
								response.getWriter().print("-1");
								return;
							}
						}
					}
					else if(apparam.isChangeOwner())
					{
						if(apparam.getApplication().getId()>0&&apparam.getApplication().getOwnerEmail()!=null)
						{
							org.glc.domain.User uid=this.dao.isPossibleAppOwner(apparam.getApplication().getOwnerEmail());
							if(uid!=null&&uid.getId()>0&&UserPrivilegeMask.IsApplication(uid.getPrivilegeLevel()))
							{
								if(this.dao.updateAppOwner(apparam.getApplication().getId(), uid.getId()))
								{
									response.getWriter().print("0");
									return;
								}
								else
								{
									response.getWriter().print("-2");
									return;
								}
							}
							else
							{
								response.getWriter().print("-1");
								return;
							}
						}
						
					}
					else if(apparam.isChangeStatus())
					{
						if(apparam.getApplication().getId()>0)
						{
							if(this.dao.updateAppStatus(apparam.getApplication().getId(), apparam.getApplication().isActive()))
							{
								response.getWriter().print("0");
								return;
							}
							else
							{
								response.getWriter().print("-1");
								return;
							}
						}
					}
				}
			}
		}
	}
	private ArrayList<String> validateAgency(AppInfo app)
	{
		ArrayList<String> orgnames=null;
		if(app!=null)
		{
			int id=0;
			for(Contact c:app.getContacts())
			{
				if(c.getOrgnization()!=null)
				{
					if(c.getOrgnization().getAbbrev()!=null&&c.getOrgnization().getName()!=null)
					{
						id=this.dao.getOrgnizationId(c.getOrgnization().getAbbrev(), c.getOrgnization().getName());
						if(id>0)
						{
							c.getOrgnization().setId(id);
						}
						else//this org name doesn't exist
						{
							if(orgnames==null)
								orgnames=new ArrayList<String>();
							orgnames.add(c.getOrgnization().getName());
						}
					}
				}
			}
		}
		return orgnames;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		String action=null;
		ErrorMessage err=null;
		int returnVal=0;
		int aid=-1;
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())
					&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel())
					&&null!=request.getAttribute(AppAdminParam.getClassName()))
			{
				AppAdminParam ap=null;
				if(request.getAttribute(AppAdminParam.getClassName()) instanceof AppAdminParam)
				    ap=(AppAdminParam)request.getAttribute(AppAdminParam.getClassName());
				if(ap!=null&&ap.getApplication()!=null)
				{
					String msg=null;
					
					//ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, ILiteralProvider.class.getName(), obj)
					request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
					if(ap.isApprove())
					{
						ArrayList<String> invalid_orgs=this.validateAgency(ap.getApplication());
						if(invalid_orgs!=null&&!invalid_orgs.isEmpty())
						{
							action="invalid_data";
							err=new ErrorMessage();
							for(String s:invalid_orgs)
							{
								err.appendGlobalMessage(String.format("Orgnization <b>%s</b> doesn't exist in the database", s));
							}
							//put appinfo back to the requst so the user could check error
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, AppInfo.getClassName(), ap.getApplication());
							returnVal=-1;
						}
						else
						{
							action="result";
							if(AppAdminParam.isApprovedAsNew(ap.getApplication().getApprovedFlag()))
							{
								int uid=-1;
								if(ap.getApplication().getSerialId()>0)
									uid=this.draft_dao.getOwnerIdByDraftId(ap.getApplication().getSerialId());
								else if(ap.getApplication().getId()>0)
									uid=this.dao.getOwnerIdByAppId(ap.getApplication().getId());
								if(uid>0&&(aid=this.dao.addAppInfo(ap.getApplication(),uid))>0)
								{
									msg=ConfigManager.getAppSetting(APP_AUDIT_SUCCESS);
									returnVal=0;
									//session.setAttribute("MODEL_AUDIT_RESULT_MESSAGE", msg);
									//session.setAttribute("MODEL_AUDIT_RESULT_VALUE","0");
								}
								else
								{
									msg=ConfigManager.getAppSetting(APP_AUDIT_FAIL);
									returnVal=-1;
									//session.setAttribute("MODEL_AUDIT_RESULT_MESSAGE", msg);
									//session.setAttribute("MODEL_AUDIT_RESULT_VALUE","1");
								}
							}
							else if(AppAdminParam.isApprovedAsUpdate(ap.getApplication().getApprovedFlag())&&ap.getApplication().getId()>0)
							{
								if(this.dao.updateAppInfo(ap.getApplication()))
								{
									aid=ap.getApplication().getId();
									msg=ConfigManager.getAppSetting(APP_AUDIT_SUCCESS);
									returnVal=0;
								}
								else
								{
									msg=ConfigManager.getAppSetting(APP_AUDIT_FAIL);
									returnVal=-1;
								}
							}
						}
						if(returnVal==0&&aid>0)
						{
							String email=this.dao.getOwnerEmailByAppId(aid);
							if(email!=null&&!email.equals(""))
							{
								String title=ConfigManager.getAppSetting(APP_APPROVED_EMAIL_TITLE);
								if(title==null)
									title="Approval Notice";
								String content=String.format("***PLEASE DO NOT REPLY THIS EMAIL***\r\nDear %s %s:\r\n\r\nThe application you submitted at Great Lakes Model Inventory with the name of \"%s\" has been approved.\r\n\r\nYou are able to find this application in your approved app list.\r\n\r\nThanks,\r\n\r\nGLOS Staff", 
										usr.getFirstName(),
										usr.getLastName(),
										ap.getApplication().getName()
								);


								DocNotification notify=new DocNotification(title,null,new String[]{email},content);
								notify.start();
							}
						}
						if(err!=null)
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, ErrorMessage.getClassName(), err);
						ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, "APP_AUDIT_RESULT_MESSAGE", msg);
						ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, "APP_AUDIT_RESULT_VALUE", returnVal);
						ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						return;
					}
				}
			}
		}
	}

}
