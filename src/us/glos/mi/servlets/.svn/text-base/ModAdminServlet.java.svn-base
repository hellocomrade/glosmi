package us.glos.mi.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.ILiteralProvider;
import org.glc.domain.User;
import org.glc.utils.UTCDateFormatter;
import org.glc.utils.ServerUtilities;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.domain.DraftDataTableWrapper;
import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.ModelInfoDataTableWrapper;
import us.glos.mi.dao.IModDAO;
import us.glos.mi.dao.IDraftDAO;
import us.glos.mi.util.DataTableParamParser.DataTableParams;
/**
 * Servlet implementation class ModAdminServlet
 */
public class ModAdminServlet extends HttpServlet {
	
	private static final long serialVersionUID = 5418910192259624799L;
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
    static final String DAO_NAME="DAO";
    static final String DRAFT_DAO_NAME="DRAFT_DAO";
    private IModDAO dao=null;
    private IDraftDAO draft_dao=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModAdminServlet() {
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
	 * Get only handles action new and list
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
			if(usr!=null&&null!=request.getAttribute(ModAdminParam.getClassName()))
			{
				ModAdminParam ma=null;
				if(request.getAttribute(ModAdminParam.getClassName()) instanceof ModAdminParam)
				    ma=(ModAdminParam)request.getAttribute(ModAdminParam.getClassName());
				if(ma!=null)
				{
					if(ma.isNew())
					{
						if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))//people who have the modeler privilege
						{
							action="new";
							request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
						    ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
						    return;
						}
					}
					else if(ma.isList())
					{
						
						
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ma.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<ModelInfo> list=this.dao.getModelInfoByPage(dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.dao.getModelCount();
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									ModelInfoDataTableWrapper mdw=new ModelInfoDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
								}
							}
							else//first hit
							{
								
								if(this.dao.getModelCount()>0)
								    action="list";
								else
									action="empty";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
							    ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}
						}
						else if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))//regular modeler
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ma.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<ModelInfo> list=this.dao.getModelInfoByOwnerAndPage(usr.getId(),dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.dao.getModelCountByOwner(usr.getId());
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									ModelInfoDataTableWrapper mdw=new ModelInfoDataTableWrapper(list,dtp,format);
									response.getWriter().print(mdw.ToJSON());
								}
							}
							else//first hit
							{
								if(this.dao.getModelCountByOwner(usr.getId())>0)
								    action="list";
								else
									action="empty";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
								return;
							}

						}
						
					}
					else if(ma.isUpdate())
					{
						if(ma.getModel()!=null)
						{
							if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
							{
								ModelInfo model=null;
								action="update";
								request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
								if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
								{
									model=this.dao.getModelInfoById(ma.getModel().getId());
									//request.setAttribute(ModelInfo.getClassName(),model);
									ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, ModelInfo.getClassName(), model);
									
								}
								else
								{
									model=this.dao.getModelInfoByIdAndUId(ma.getModel().getId(), usr.getId());
									//request.setAttribute(ModelInfo.getClassName(), model);
									ServerUtilities.SetKeyValueOnAction(request, this.getClass().getName(), action, ModelInfo.getClassName(), model);
								}
								
								if(model!=null)
									ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
							}
						}
						
					}
					else if(ma.isSubmit())
					{
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ma.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<ModelInfo> list=this.draft_dao.getDraftByPage(dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.draft_dao.getDraftCount();
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									DraftDataTableWrapper mdw=new DraftDataTableWrapper(list,dtp,format);
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
						else if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))//regular modeler
						{
							if(null!=request.getAttribute(DataTableParams.getClassName())&&ma.isAjaxCall())
							{
								DataTableParams dtp=(DataTableParams)request.getAttribute(DataTableParams.getClassName());
								ArrayList<ModelInfo> list=this.draft_dao.getDraftByOwnerAndPage(usr.getId(),dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
								if(list!=null&&!list.isEmpty())
								{
									int count=this.draft_dao.getDraftCountByOwner(usr.getId());
									dtp.setTotalFiltered(count);
									dtp.setTotalRecords(count);
									UTCDateFormatter format=new UTCDateFormatter(tZone,null);
									DraftDataTableWrapper mdw=new DraftDataTableWrapper(list,dtp,format);
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
		// TODO Auto-generated method stub
		
	}

}
