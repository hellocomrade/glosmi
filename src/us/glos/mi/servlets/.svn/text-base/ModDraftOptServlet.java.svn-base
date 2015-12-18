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

import org.glc.xmlconfig.ConfigManager;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;
import org.glc.domain.User;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.IDraftDAO;
import us.glos.mi.domain.ModAdminParam;

/**
 * Servlet implementation class ModelOptServlet
 */
public class ModDraftOptServlet extends HttpServlet {
	
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -4154266337205743627L;

	static final String DAO_NAME="DAO";
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
	static int MAX_DRAFT_ALLOWED=5;
	static
	{
		String temp=ConfigManager.getAppSetting("MAX_MODEL_DRAFT_ALLOWED");
		if(temp!=null&&!temp.equals(""))
		{
			try
			{
				MAX_DRAFT_ALLOWED=Integer.parseInt(temp);
			}
			catch(NumberFormatException ne)
			{
				Logger.writeLog(ModDraftOptServlet.class.getName()+":"+ne.getMessage(),LogLevel.INFO);
				MAX_DRAFT_ALLOWED=5;
			}
		}
	}
	private IDraftDAO dbDao=null;
	
	 
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ModDraftOptServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
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
			this.dbDao=iclass.getConstructor().newInstance(new Object[0]);
			this.dbDao.setTimeZone(tZone);
			
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
			if(usr!=null&&null!=request.getAttribute(ModAdminParam.getClassName()))
			{
				ModAdminParam ma=null;
				if(request.getAttribute(ModAdminParam.getClassName()) instanceof ModAdminParam)
				    ma=(ModAdminParam)request.getAttribute(ModAdminParam.getClassName());
				if(ma!=null)
				{
					if(ma.isRemove()&&ma.getModel()!=null&&ma.isAjaxCall())
					{
						boolean isRemoved=false;
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{
							isRemoved=this.dbDao.removeDraftById(ma.getModel().getSerialId());
							
						}
						else if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
						{
							isRemoved=this.dbDao.removeByDraftIdAndOwner(usr.getId(), ma.getModel().getSerialId());
						}
						if(isRemoved)
						{
							//action="removed";
							//request.setAttribute("RESULT_MESSAGE", "Your draft has been successfully removed.");
							//ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
							response.getWriter().print("0");
							return;
						}
						else
						{
							
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
					if(ma.isAjaxCall())
					{
						if(ma.isSave()&&ma.getModel()!=null)
						{
							if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
							{
								if(this.dbDao.getDraftCountByOwner(usr.getId())<MAX_DRAFT_ALLOWED)
								{
									ma.getModel().setOwnerEmail(usr.getEmail());
									ma.getModel().setOwnerName(usr.getLastName()+" "+usr.getFirstName());
									int id=this.dbDao.insertDraftByUId(usr.getId(), ma.getModel());
									if(id>0)
									{
										response.getWriter().print(id);
										return;
										//action="inserted";
										//request.setAttribute("RESULT_MESSAGE", "Your draft has been successfully saved.");
										//ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
									}
								}
								else
								{
									response.getWriter().print(-2);
								}
							}
						}
						else if(ma.isUpdate()&&ma.getModel()!=null)
						{
							if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
							{
								if(this.dbDao.updateDraft(usr.getId(),ma.getModel().getSerialId(), ma.getModel()))
									response.getWriter().print("0");
								else
									response.getWriter().print("-1");	
								return;
							}
						}
					}
					
				}
			}
		}
	}

}
