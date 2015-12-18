package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.TimeZone;

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
import us.glos.mi.domain.AppDraftDataTableWrapper;
import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.util.DataTableParamParser.DataTableParams;

/**
 * Servlet implementation class AppDraftAdminServlet
 */
public class AppDraftAdminServlet extends HttpServlet {
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
    static final String DAO_NAME="DAO";
	
    private IDraftDAO dao=null; 
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -5613714074249614148L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AppDraftAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
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
			Class<IDraftDAO> iclass=(Class<IDraftDAO>)Class.forName(cln);
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
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		String action=null;
		AppInfo app=null;
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
					if(ma.isList())
					{
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ma.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<AppInfo> list=this.dao.getDraftByPage(dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.dao.getDraftCount();
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									AppDraftDataTableWrapper mdw=new AppDraftDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
									return;
								}
							}
							else//first hit
							{
								
								if(this.dao.getDraftCount()>0)
								    action="list";
								else
									action="empty";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
							    ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}
						}
						else if(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))//regular application developer
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ma.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<AppInfo> list=(ArrayList<AppInfo>)this.dao.getDraftByOwnerAndPage(usr.getId(),dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.dao.getDraftCountByOwner(usr.getId());
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									AppDraftDataTableWrapper mdw=new AppDraftDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
									return;
								}
							}
							else//first hit
							{
								if(this.dao.getDraftCountByOwner(usr.getId())>0)
								    action="list";
								else
									action="empty";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}
						}
					}
					else if(ma.isUpdate()&&ma.getApplication()!=null)
					{
						if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))//regular modeler
						{
							app=(AppInfo)this.dao.getDraftByIdAndOwner(ma.getApplication().getSerialId(), usr.getId());
							if(app!=null)
							{
								action="edit";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								//request.setAttribute(ModelInfo.getClassName(),model);
								ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, AppInfo.getClassName(), app);
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
