package us.glos.mi.servlets;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimeZone;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.ILiteralProvider;
import org.glc.Logger;
import org.glc.utils.ServerUtilities;
import org.glc.utils.UTCDateFormatter;
import org.glc.utils.Validation;
import org.glc.domain.User;
import org.glc.xmlconfig.ConfigManager;
import org.glc.xmlconfig.LogLevel;


import us.glos.mi.UserPrivilegeMask;

import us.glos.mi.dao.IFileDAO;
import us.glos.mi.domain.Attachment;
import us.glos.mi.domain.AttachmentTableWrapper;
import us.glos.mi.util.DataTableParamParser;
import us.glos.mi.util.DataTableParamParser.DataTableParams;



/**
 * Servlet implementation class FileAdminServlet
 */
public class FileAdminServlet extends HttpServlet {
	static final String DAO_NAME="DAO";
    private IFileDAO dao=null;
    static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 8314061592224919166L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public FileAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		String tz=this.getInitParameter(TIMEZONE_NAME);
		if(tz==null||tz.equals(""))
			throw new ServletException("TIMEZONE Parameter doesn't exist!");
		else
			tZone=TimeZone.getTimeZone(tz);
		
		String cln2=this.getInitParameter(DAO_NAME);
		if(cln2==null||cln2.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			Class<IFileDAO> iclass2=(Class<IFileDAO>)Class.forName(cln2);
			this.dao=iclass2.getConstructor().newInstance(new Object[0]);
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
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null)
			{
				if(Validation.basicValidation(request, "islist"))
				{
					if(UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel())||
							UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
					{
						int uid=0;
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
							uid=-1;
						else
							uid=usr.getId();
						DataTableParams dtp=DataTableParamParser.ParseDataTableParameters(request);
						if(dtp==null)
						{
							if(this.dao.getCountByUID(uid)>0)
								action="list";
							else
								action="empty";
							request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
							ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
							
						}
						else
						{
							ArrayList<Attachment> atts=this.dao.getAttachmentByOwnerAndPage(uid, dtp.getiStartAt(), dtp.getiDisplayLenth(), dtp.getSortedCols()[0], dtp.getSortedOrder()[0].equalsIgnoreCase("asc"));
							if(atts!=null&&!atts.isEmpty())
							{
								int count=this.dao.getCountByUID(uid);
								dtp.setTotalFiltered(count);
								dtp.setTotalRecords(count);
								UTCDateFormatter format=new UTCDateFormatter(tZone,null);
								AttachmentTableWrapper mdw=new AttachmentTableWrapper(atts,dtp,format);
								response.getWriter().print(mdw.ToJSON());
							}
							
						}
					}
					return;
				}
				else if(Validation.basicValidation(request, "file_desc")
						&&Validation.basicValidation(request, "fid"))
				{
					String desc=request.getParameter("file_desc").trim();
					int id=0;
					try
					{
						id=Integer.parseInt(request.getParameter("fid"));
					}
					catch(NumberFormatException e)
					{
						id=0;
					}
					if(id>0)
					{
						if(Validation.basicValidation(request, "ismod"))
						{
							if((UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel())&&this.dao.isOwnerOfFile(id, usr.getId()))
									||UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
							{
								if(this.dao.updateFileDescription(id, desc))
								{
									response.getWriter().print("0");
									return;
								}
							}
						}
						else//app
						{
							if((UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel())&&this.dao.isOwnerOfFile(id, usr.getId()))
									||UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
							{
								if(this.dao.updateFileDescription(id, desc))
								{
									response.getWriter().print("0");
									return;
								}
							}
						}
					}
				}
				else if(Validation.basicValidation(request, "checked")
						&&Validation.basicValidation(request, "fid"))//update status
				{
					int id=0;
					int checked=0;
					try
					{
						id=Integer.parseInt(request.getParameter("fid"));
					}
					catch(NumberFormatException e)
					{
						id=0;
					}
					try
					{
						checked=Integer.parseInt(request.getParameter("checked"));
					}
					catch(NumberFormatException e)
					{
						checked=0;
					}
					if(id>0)
					{
						if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
						{
							int tid=this.dao.getTypeId(id);
							if(tid==1&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel())
							   ||tid==2&&UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
							{
								if(this.dao.updateFileStatus(id,checked!=0))
								{	
									response.getWriter().print("0");
									return;
								}
								
							}
						}
					}
					
				}
				else if(Validation.basicValidation(request, "fid"))//fid only, it's for deletion
				{
					int id=0;
					try
					{
						id=Integer.parseInt(request.getParameter("fid"));
					}
					catch(NumberFormatException e)
					{
						id=0;
					}
					if(id>0)
					{
						String path=null;
						boolean isValid=false;
						if(Validation.basicValidation(request, "ismod"))
						{
							isValid=((UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel())&&this.dao.isOwnerOfFile(id, usr.getId()))
									||UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()));
							
						}
						else//app
						{
							isValid=((UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel())&&this.dao.isOwnerOfFile(id, usr.getId()))
									||UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()));
							
						}
						if(isValid)
						{
							path=this.dao.getFilePathById(id);
							if(path!=null&&!path.equals(""))
							{
								try
								{
									File f=new File(path);
									if(f.delete())
									{
										if(this.dao.removeFileRecordById(id))
										{
											response.getWriter().print("0");
											return;
										}
									}
									
								}
								catch(Exception e)
								{
									Logger.writeLog("Fail to remove file at "+path, LogLevel.SEVERE);
								}
							}
						}
					}
				}
				
			}
		}
		response.getWriter().print("-1");
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
