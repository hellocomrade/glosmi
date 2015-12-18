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
import org.glc.domain.User;
import org.glc.utils.ServerUtilities;
import org.glc.utils.UTCDateFormatter;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IDraftDAO;
import us.glos.mi.dao.IAppDAO;
import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppDraftDataTableWrapper;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.AppInfoDataTableWrapper;
import us.glos.mi.util.DataTableParamParser.DataTableParams;


/**
 * Servlet implementation class AppAdminServlet
 */
public class AppAdminServlet extends HttpServlet {
	
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
    static final String DAO_NAME="DAO";
    static final String DRAFT_DAO_NAME="DRAFT_DAO";
    private IAppDAO dao=null;
    private IDraftDAO draft_dao=null;
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 4277744331074641382L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AppAdminServlet() {
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
			if(usr!=null&&null!=request.getAttribute(AppAdminParam.getClassName()))
			{
				AppAdminParam ap=null;
				if(request.getAttribute(AppAdminParam.getClassName()) instanceof AppAdminParam)
				    ap=(AppAdminParam)request.getAttribute(AppAdminParam.getClassName());
				if(ap!=null)
				{
					if(ap.isNew())
					{
						if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))//people who have the modeler privilege
						{
							action="new";
							request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
						    ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						    return;
						}
					}
					else if(ap.isList())
					{
						
						
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ap.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<AppInfo> list=this.dao.getAppInfoByPage(dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.dao.getAppCount();
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									AppInfoDataTableWrapper mdw=new AppInfoDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
								}
							}
							else//first hit
							{
								
								if(this.dao.getAppCount()>0)
								    action="list";
								else
									action="empty";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
							    ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}
						}
						else if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))//regular modeler
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ap.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<AppInfo> list=this.dao.getAppInfoByOwnerAndPage(usr.getId(),dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.dao.getAppCountByOwner(usr.getId());
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									AppInfoDataTableWrapper mdw=new AppInfoDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
								}
							}
							else//first hit
							{
								if(this.dao.getAppCountByOwner(usr.getId())>0)
								    action="list";
								else
									action="empty";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}

						}
						
					}
					else if(ap.isUpdate())
					{
						if(ap.getApplication()!=null)
						{
							if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
							{
								AppInfo app=null;
								action="update";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
								{
									app=this.dao.getAppInfoById(ap.getApplication().getId());
									//request.setAttribute(AppInfo.getClassName(),app);
									ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, AppInfo.getClassName(), app);
									
								}
								else
								{
									app=this.dao.getAppInfoByIdAndUId(ap.getApplication().getId(), usr.getId());
									//request.setAttribute(AppInfo.getClassName(), app);
									ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, AppInfo.getClassName(), app);
								}
								
								if(app!=null)
									ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
							}
						}
						
					}
					else if(ap.isSubmit())
					{
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ap.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<AppInfo> list=this.draft_dao.getDraftByPage(dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.draft_dao.getDraftCount();
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									AppDraftDataTableWrapper mdw=new AppDraftDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
								}
							}
							else//first hit
							{
								
								if(this.draft_dao.getDraftCount()>0)
								    action="list_pending";
								else
									action="empty_pending";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
							    ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}
						}
						else if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))//regular modeler
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ap.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<AppInfo> list=this.draft_dao.getDraftByOwnerAndPage(usr.getId(),dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.draft_dao.getDraftCountByOwner(usr.getId());
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									AppDraftDataTableWrapper mdw=new AppDraftDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
								}
							}
							else//first hit
							{
								if(this.draft_dao.getDraftCountByOwner(usr.getId())>0)
								    action="list_pending";
								else
									action="empty_pending";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
