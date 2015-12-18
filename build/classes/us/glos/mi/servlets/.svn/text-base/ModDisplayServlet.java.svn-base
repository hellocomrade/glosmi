package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.ILiteralProvider;
import org.glc.Logger;
import org.glc.domain.User;
import org.glc.utils.ServerUtilities;
import org.glc.xmlconfig.ConfigManager;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.Attachment;
import us.glos.mi.dao.IModDAO;
import us.glos.mi.dao.IAppDAO;
import us.glos.mi.dao.IFileDAO;
/**
 * Servlet implementation class ModDisplayServlet
 */
public class ModDisplayServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8155222549138925823L;
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
    static final String MOD_DAO_NAME="MOD_DAO";
    private IModDAO mod_dao=null;
    static final String APP_DAO_NAME="APP_DAO";
    private IAppDAO app_dao=null;
    static final String ATT_DAO_NAME="ATT_DAO";
    private IFileDAO att_dao=null;
	private static String BasePath=null;
	private static int Section1Limit;
	private static int Section2Limit;
	private static int Section3Limit;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModDisplayServlet() {
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
		String tmp=ConfigManager.getAppSetting("fileupload.section1.limit");
		if(tmp!=null)
		{
			try
			{
				int i=Integer.parseInt(tmp);
				Section1Limit=i;
			}
			catch(NumberFormatException e)
			{
				Section1Limit=4;
				Logger.writeLog(this.getClass().getName()+" fileupload.section1.limit is not valid", LogLevel.SEVERE);
			}
		}
		tmp=ConfigManager.getAppSetting("fileupload.section2.limit");
		if(tmp!=null)
		{
			try
			{
				int i=Integer.parseInt(tmp);
				Section2Limit=i;
			}
			catch(NumberFormatException e)
			{
				Section2Limit=6;
				Logger.writeLog(this.getClass().getName()+" fileupload.section1.limit is not valid", LogLevel.SEVERE);
			}
		}
		tmp=ConfigManager.getAppSetting("fileupload.section3.limit");
		if(tmp!=null)
		{
			try
			{
				int i=Integer.parseInt(tmp);
				Section3Limit=i;
			}
			catch(NumberFormatException e)
			{
				Section3Limit=4;
				Logger.writeLog(this.getClass().getName()+" fileupload.section1.limit is not valid", LogLevel.SEVERE);
			}
		}
		String cln=this.getInitParameter(MOD_DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("MOD_DAO Parameter doesn't exist!");
		String cln1=this.getInitParameter(APP_DAO_NAME);
		if(cln1==null||cln1.equals(""))
			throw new ServletException("APP_DAO Parameter doesn't exist!");
		String cln2=this.getInitParameter(ATT_DAO_NAME);
		if(cln2==null||cln2.equals(""))
			throw new ServletException("ATT_DAO Parameter doesn't exist!");
		try
		{
			Class<IModDAO> iclass=(Class<IModDAO>)Class.forName(cln);
			this.mod_dao=iclass.getConstructor().newInstance(new Object[0]);
			this.mod_dao.setTimeZone(tZone);
			
			Class<IAppDAO> iclass1=(Class<IAppDAO>)Class.forName(cln1);
			this.app_dao=iclass1.getConstructor().newInstance(new Object[0]);
			this.app_dao.setTimeZone(tZone);
			
			Class<IFileDAO> iclass2=(Class<IFileDAO>)Class.forName(cln2);
			this.att_dao=iclass2.getConstructor().newInstance(new Object[0]);
			this.att_dao.setTimeZone(tZone);
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
		boolean isOwnerView=false;
		if(BasePath==null)
			BasePath=request.getContextPath()+request.getServletPath();
		String action="error";
		request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
		String url=request.getRequestURI();
		if(BasePath.length()<url.length()&&url.charAt(BasePath.length())=='/')
		{
			//response.getWriter().print(url.substring(BasePath.length()+1));
			try
			{
				int id=Integer.parseInt(url.substring(BasePath.length()+1));
				ModelInfo mod=null;
				if(id>0)
					mod=this.mod_dao.getModelInfoById(id);
				if(mod!=null)
				{
					ArrayList<AppInfo> apps=this.app_dao.getAppInfoByModId(mod.getId());
					HttpSession session=request.getSession(false);
					if(session!=null)
					{
						User usr=null;
						if(session.getAttribute(User.getClassName()) instanceof User)
						{
							usr=(User)session.getAttribute(User.getClassName());
							if(UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
								isOwnerView=true;
							else
								isOwnerView=this.mod_dao.isOwnerOfModel(usr.getId(), mod.getId());
						}
					}
					
					ArrayList<Attachment> modfiles1=this.att_dao.getFilePathBySection(1, id, 1);
					ArrayList<Attachment> modfiles2=this.att_dao.getFilePathBySection(2, id, 1);
					HashMap<String,ArrayList<Attachment>> modfiles3=null;
					if(apps!=null&&!apps.isEmpty())
					{
						ArrayList<Attachment> tmp=null;
						for(AppInfo app:apps)
						{
							if(modfiles3==null)
								modfiles3=new HashMap<String,ArrayList<Attachment>>();
							tmp=this.att_dao.getFilePathBySection(3, app.getId(), 2);
							if(tmp!=null)
								modfiles3.put(String.format("%d", app.getId()), tmp);
						}
					}
					
					//ArrayList<Attachment> modfiles3=this.att_dao.getFilePathBySection(3, id, 2);
					action="result";
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, ModelInfo.getClassName(), mod);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "MOD_APPS", apps);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "OWNER_VIEW", isOwnerView?"1":"0");
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "MOD_FILES1", modfiles1);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "MOD_FILES2", modfiles2);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "MOD_FILES3", modfiles3);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "MOD_SEC1_LIMIT", Section1Limit);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "MOD_SEC2_LIMIT", Section2Limit);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "MOD_SEC3_LIMIT", Section3Limit);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "URI", url);
				}
			}
			catch(NumberFormatException nfe)
			{
				
			}
		}
		ServerUtilities.GoTo(this.getServletContext(), request, response, this.getClass().getName(), action);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
