package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.ILiteralProvider;
import org.glc.domain.ErrorMessage;
import org.glc.domain.User;
import org.glc.utils.ServerUtilities;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.domain.Contact;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IDraftDAO;
import us.glos.mi.dao.IModDAO;
import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.util.DocNotification;

/**
 * Servlet implementation class ModAuditServlet
 */
public class ModAuditServlet extends HttpServlet {
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
    static final String DAO_NAME="DAO";
    static final String DRAFT_DAO_NAME="DRAFT_DAO";
    static final String MODEL_AUDIT_SUCCESS="MODEL_AUDIT_SUCCESS";
	static final String MODEL_AUDIT_FAIL="MODEL_AUDIT_FAIL";
	static final String MODEL_APPROVED_EMAIL_TITLE="MODEL_APPROVED_EMAIL_TITLE";
    private IModDAO dao=null;
    private IDraftDAO draft_dao=null; 
    /**
	 * 
	 */
	private static final long serialVersionUID = -6235439800492127589L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ModAuditServlet() {
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
			Class<IModDAO> iclass=(Class<IModDAO>)Class.forName(cln);
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
				ModAdminParam ma=null;
				if(request.getAttribute(ModAdminParam.getClassName()) instanceof ModAdminParam)
				    ma=(ModAdminParam)request.getAttribute(ModAdminParam.getClassName());
				if(ma!=null&&ma.getModel()!=null)
				{
					if(ma.isAudit()&&ma.getModel().getSerialId()>0)
					{
						request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
						ModelInfo model=null;
						model=(ModelInfo)this.draft_dao.getDraftById(ma.getModel().getSerialId());
						if(model!=null)
						{
							//request.setAttribute(ModelInfo.getClassName(), model);
							action="audit";
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action,ModelInfo.getClassName(), model);
						}
						else
							action="error";
						ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						return;
					}
					else if(ma.isUpdate()&&ma.getModel().getId()>0)
					{
						request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
						ModelInfo model=null;
						model=this.dao.getModelInfoById(ma.getModel().getId());
						if(model!=null)
						{
							//request.setAttribute(ModelInfo.getClassName(), model);
							action="update";
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action,ModelInfo.getClassName(), model);
						}
						else
						{
							action="error";
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, "MODEL_ERROR", "The model you request doesn't exist. It may be removed by auditor already!");
						}
						ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						return;
					}
					else if(ma.isRemove())
					{
						if(ma.getModel().getSerialId()>0)
						{
							if(this.draft_dao.removeDraftById(ma.getModel().getSerialId()))
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
						else if(ma.getModel().getId()>0)
						{
							if(this.dao.removeModelInfoById(ma.getModel().getId()))
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
					else if(ma.isChangeOwner())
					{
						if(ma.getModel().getId()>0&&ma.getModel().getOwnerEmail()!=null)
						{
							org.glc.domain.User uid=this.dao.isPossibleModelOwner(ma.getModel().getOwnerEmail());
							if(uid!=null&&uid.getId()>0&&UserPrivilegeMask.IsModeler(uid.getPrivilegeLevel()))
							{
								if(this.dao.updateModelOwner(ma.getModel().getId(), uid.getId()))
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
					else if(ma.isChangeStatus())
					{
						if(ma.getModel().getId()>0)
						{
							if(this.dao.updateModelStatus(ma.getModel().getId(), ma.getModel().isActive()))
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
	private ArrayList<String> validateAgency(ModelInfo mod)
	{
		ArrayList<String> orgnames=null;
		if(mod!=null)
		{
			int id=0;
			for(Contact c:mod.getDevelopers())
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
			for(Contact c:mod.getDistributors())
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
			for(Contact c:mod.getContacts())
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
		int modid=0;
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())
					&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel())
					&&null!=request.getAttribute(ModAdminParam.getClassName()))
			{
				ModAdminParam ma=null;
				if(request.getAttribute(ModAdminParam.getClassName()) instanceof ModAdminParam)
				    ma=(ModAdminParam)request.getAttribute(ModAdminParam.getClassName());
				if(ma!=null&&ma.getModel()!=null)
				{
					String msg=null;
					
					//ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, ILiteralProvider.class.getName(), obj)
					request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
					
					
					if(ma.isApprove())
					{
						ArrayList<String> invalid_orgs=this.validateAgency(ma.getModel());
						if(invalid_orgs!=null&&!invalid_orgs.isEmpty())
						{
							action="invalid_data";
							err=new ErrorMessage();
							for(String s:invalid_orgs)
							{
								err.appendGlobalMessage(String.format("Orgnization <b>%s</b> doesn't exist in the database", s));
							}
							//put appinfo back to the requst so the user could check error
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, ModelInfo.getClassName(), ma.getModel());
							returnVal=-1;
						}
						else
						{
							action="result";
						
							if(ModAdminParam.isApprovedAsNew(ma.getModel().getApprovedFlag()))
							{
								int uid=-1;
								if(ma.getModel().getSerialId()>0)
									uid=this.draft_dao.getOwnerIdByDraftId(ma.getModel().getSerialId());
								else if(ma.getModel().getId()>0)
									uid=this.dao.getOwnerIdByModId(ma.getModel().getId());
								
								if(uid>0&&(modid=this.dao.addModelInfo(ma.getModel(),uid))>0)
								{
									msg=ConfigManager.getAppSetting(MODEL_AUDIT_SUCCESS);
									returnVal=0;
									//session.setAttribute("MODEL_AUDIT_RESULT_MESSAGE", msg);
									//session.setAttribute("MODEL_AUDIT_RESULT_VALUE","0");
								}
								else
								{
									msg=ConfigManager.getAppSetting(MODEL_AUDIT_FAIL);
									returnVal=-1;
									//session.setAttribute("MODEL_AUDIT_RESULT_MESSAGE", msg);
									//session.setAttribute("MODEL_AUDIT_RESULT_VALUE","1");
								}
							}
							else if(ModAdminParam.isApprovedAsUpdate(ma.getModel().getApprovedFlag())&&ma.getModel().getId()>0)
							{
								if(this.dao.updateModelInfo(ma.getModel()))
								{
									modid=ma.getModel().getId();
									msg=ConfigManager.getAppSetting(MODEL_AUDIT_SUCCESS);
									returnVal=0;
								}
								else
								{
									msg=ConfigManager.getAppSetting(MODEL_AUDIT_FAIL);
									returnVal=-1;
								}
							}
						}
						if(returnVal==0&&modid>0)
						{
							String email=this.dao.getOwnerEmailByModelId(modid);
							if(email!=null&&!email.equals(""))
							{
								String title=ConfigManager.getAppSetting(MODEL_APPROVED_EMAIL_TITLE);
								if(title==null)
									title="Approval Notice";
								String content=String.format("***PLEASE DO NOT REPLY THIS EMAIL***\r\nDear %s %s:\r\n\r\nThe model you submitted at Great Lakes Model Inventory with the name of \"%s\" has been approved.\r\n\r\nYou are able to find this model in your approved model list.\r\n\r\nThanks,\r\n\r\nGLOS Staff", 
										usr.getFirstName(),
										usr.getLastName(),
										ma.getModel().getName()
								);


								DocNotification notify=new DocNotification(title,null,new String[]{email},content);
								notify.start();
							}
						}
						if(err!=null)
							ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, ErrorMessage.getClassName(), err);
						ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, "MODEL_AUDIT_RESULT_MESSAGE", msg);
						ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, "MODEL_AUDIT_RESULT_VALUE", returnVal);
						ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						return;
					}
				}
			}
		}
	}

}
