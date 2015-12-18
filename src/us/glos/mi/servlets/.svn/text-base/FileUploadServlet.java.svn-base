package us.glos.mi.servlets;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;
import java.io.IOException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.awt.image.BufferedImage;
//import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
//import org.apache.commons.fileupload.FileItemIterator;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.util.Streams;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;
import org.glc.domain.User;
import org.glc.Logger;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.domain.ModAdminParam;
import us.glos.mi.domain.AppAdminParam;
import us.glos.mi.domain.UploadedItem;
import us.glos.mi.dao.IAppDAO;
import us.glos.mi.dao.IModDAO;
import us.glos.mi.dao.IFileDAO;
/**
 * Servlet implementation class FileUploadServlet
 */
public class FileUploadServlet extends HttpServlet {
	private static int SizeThreshold;
	private static long FileSizeMax;
	private static String Repository;
	private static String Save;
	private static String URI;
	private static int Section1Limit;
	private static int Section2Limit;
	private static int Section3Limit;
	private static String ModelID;
	private static String AppID;
	
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
	static final String MOD_DAO_NAME="MOD_DAO";
    private IModDAO mod_dao=null;
    static final String APP_DAO_NAME="APP_DAO";
    private IAppDAO app_dao=null;
	static final String DAO_NAME="DAO";
    private IFileDAO dao=null;
    private static String BasePath=null;
    
	static
	{
		SizeThreshold=10240;
		Repository=System.getProperty("java.io.tmpdir");
		FileSizeMax=0;
		Save=null;
		ModAdminParam p=new ModAdminParam();
		ModelID=p.getModIdParamName();
		AppAdminParam ap=new AppAdminParam();
		AppID=ap.getAppIdParamName();
	}
    /**
	 * 
	 */
	private static final long serialVersionUID = -3369591200839800156L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		String tmp=null;
		tmp=ConfigManager.getAppSetting("fileupload.SizeThreshold");
		if(tmp!=null)
		{	
			try
			{
				int i=Integer.parseInt(tmp);
				SizeThreshold=i;
			}
			catch(NumberFormatException e)
			{
				Logger.writeLog(this.getClass().getName()+" fileupload.SizeThreshold is not valid", LogLevel.SEVERE);
			}
		}
		tmp=ConfigManager.getAppSetting("fileupload.FileSizeMax");
		if(tmp!=null)
		{	
			try
			{
				long l=Long.parseLong(tmp);
				FileSizeMax=l;
			}
			catch(NumberFormatException e)
			{
				Logger.writeLog(this.getClass().getName()+" fileupload.FileSizeMax is not valid", LogLevel.SEVERE);
			}
		}
		tmp=ConfigManager.getAppSetting("fileupload.Repository");
		if(tmp!=null)
			Repository=tmp;
		tmp=ConfigManager.getAppSetting("fileupload.Save");
		if(tmp!=null)
		{
			Save=tmp;
			if(!Save.endsWith(File.separator))
			{
				Save+=File.separator;
			}
		}
		tmp=ConfigManager.getAppSetting("fileupload.URL");
		if(tmp!=null)
		{
			URI=tmp;
			
			if(!URI.endsWith("/"))
			{
				URI+="/";
			}
		}
		tmp=ConfigManager.getAppSetting("fileupload.section1.limit");
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
		String tz=this.getInitParameter(TIMEZONE_NAME);
		if(tz==null||tz.equals(""))
			throw new ServletException("TIMEZONE Parameter doesn't exist!");
		else
			tZone=TimeZone.getTimeZone(tz);
		String cln=this.getInitParameter(MOD_DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("MOD_DAO Parameter doesn't exist!");
		String cln1=this.getInitParameter(APP_DAO_NAME);
		if(cln1==null||cln1.equals(""))
			throw new ServletException("APP_DAO Parameter doesn't exist!");
		String cln2=this.getInitParameter(DAO_NAME);
		if(cln2==null||cln2.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			Class<IModDAO> iclass=(Class<IModDAO>)Class.forName(cln);
			this.mod_dao=iclass.getConstructor().newInstance(new Object[0]);
			this.mod_dao.setTimeZone(tZone);
			
			Class<IAppDAO> iclass1=(Class<IAppDAO>)Class.forName(cln1);
			this.app_dao=iclass1.getConstructor().newInstance(new Object[0]);
			this.app_dao.setTimeZone(tZone);
			
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
	}
	/*
	 * seems like there is no need to handle multiple file uploading 
	 * in one POST request since metadata can not be identified for
	 * different uploaded files
	 */
	private ArrayList<UploadedItem> handleFileUpload(HttpServletRequest request) 
	{
		if(ServletFileUpload.isMultipartContent(request))
		{
			DiskFileItemFactory factory=new DiskFileItemFactory();
			factory.setSizeThreshold(SizeThreshold);
			factory.setRepository(new File(Repository));
			ServletFileUpload upload = new ServletFileUpload(factory);
			if(FileSizeMax>0)
				upload.setFileSizeMax(FileSizeMax);
			try
			{
				List items=upload.parseRequest(request);
				if(items!=null)
				{
					Iterator iter=items.iterator();
					FileItem item=null;
					ArrayList<UploadedItem> files=new ArrayList<UploadedItem>();
					while(iter.hasNext())
					{
						boolean isFileDone=false;
						item=(FileItem)iter.next();
						UploadedItem file=new UploadedItem();
						if(item.isFormField())
						{
							file.setFieldName(item.getFieldName());
							file.setFieldValue(item.getString());
						}
						else
						{
							if(isFileDone)//multiple file uploading!
								return null;
							if(Save!=null)
							{
								file.setFile(true);
								if(item.getSize()<=FileSizeMax)
								{
									String ext=FilenameUtils.getExtension(item.getName());
									if(ext!=null&&!ext.equals(""))
										ext=ext.toLowerCase();
									long ts=java.util.Calendar.getInstance().getTimeInMillis();
									String filename=String.format("%s%s.%s",Save,ts,ext);
									String fileurl=String.format("%s%s.%s", URI,ts,ext);
									File f=new File(filename);
									item.write(f);
									isFileDone=true;
									//if file is an acceptable image?
									if(ext.equals("jpg")||ext.equals("png")||ext.equals("bmp")||ext.equals("gif")||ext.equals("tif"))
									{
										try
										{
											BufferedImage bf=ImageIO.read(f);
											file.setImageWidth(bf.getWidth());
											file.setImageHeight(bf.getHeight());
											file.setImage(true);
										}
										catch(IOException ioe)
										{
											//if image is not valid, remove it
											f.delete();
											isFileDone=false;
											throw ioe;
										}
									}
									file.setFilePath(filename);
									file.setFileURL(fileurl);
								}
							}
						}
						files.add(file);
					}
					if(!files.isEmpty())
					{
						return files;
					}
				}
			}
			catch(IOException ie)
			{
				Logger.writeLog(this.getClass().getName()+" "+ie.getMessage(), LogLevel.SEVERE);
			}
			catch(FileUploadException fe)
			{
				Logger.writeLog(this.getClass().getName()+" "+fe.getMessage(), LogLevel.SEVERE);
			}
			catch(Exception e)
			{
				Logger.writeLog(this.getClass().getName()+" "+e.getMessage(), LogLevel.SEVERE);
			}
		}
		return null;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(false);
		int id=0;
		int sid=0;
		int fid=0;
		int ownerid=0;
		boolean isModel=false;
		boolean isValid=false;
		boolean isError=false;
		ArrayList<UploadedItem> fileNames=null;
		String fileName=null;
		String fileURL=null;
		String fileDesc=null;
		if(BasePath==null)
			BasePath=request.getContextPath()+request.getServletPath();
		String url=request.getRequestURI();
		if(BasePath.length()<url.length()&&url.charAt(BasePath.length())=='/')
		{
			if(session!=null&&null!=session.getAttribute(User.getClassName()))
			{
				User usr=null;
				if(session.getAttribute(User.getClassName()) instanceof User)
					usr=(User)session.getAttribute(User.getClassName());
				if(usr!=null)
				{
					String path=request.getServletPath();

					if(path!=null&&path.length()>=5)
					{
						String subpath=path.substring(1, 5);

						if(subpath.equals("mod/")&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
						{
							try
							{
								id=Integer.parseInt(url.substring(BasePath.length()+1));
							}
							catch(NumberFormatException nfe)
							{
								id=0;
							}
							//if(Validation.basicValidation(request, ModelID)&&Validation.basicValidation(request, "section_id"))
							
							//if the user is not the owner of the model
							//he should NOT be able to continue uploading procedure
							if(id>0)
							   
							{
								ownerid=this.mod_dao.getOwnerIdByModId(id);
								if(ownerid>0&&(usr.getId()==ownerid||UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())))
								{
										
									isModel=true;
									isValid=true;
								}
							}
						}
						else if(subpath.equals("app/")&&UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel()))
						{
							try
							{
								id=Integer.parseInt(url.substring(BasePath.length()+1));
							}
							catch(NumberFormatException nfe)
							{
								id=0;
							}
							//if(Validation.basicValidation(request, AppID)&&Validation.basicValidation(request, "section_id"))
							if(id>0)
							{
								ownerid=this.app_dao.getOwnerIdByAppId(id);
								if(ownerid>0&&(usr.getId()==ownerid||UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())))
								{
									isModel=false;
									isValid=true;
								}
							}
						}
						if(isValid)
						{
							fileNames=handleFileUpload(request);
							boolean isImage=false;
							if(fileNames!=null&&!fileNames.isEmpty())//seeing fileName is filled means the file has been uploaded successfully
							{

								for(UploadedItem item:fileNames)
								{
									if(item.isFile())
									{
										fileName=item.getFilePath();
										fileURL=item.getFileURL();
										isImage=item.isImage();
									}
									else
									{
										if(item.getFieldName().equals("section_id")&&
												item.getFieldValue()!=null&&
												!item.getFieldValue().trim().equals(""))
										{
											try
											{
												sid=Integer.parseInt(item.getFieldValue().trim());
											}
											catch(NumberFormatException nfe)
											{
												sid=-1;
											}
										}
										else if(item.getFieldName().equals("img_desc")&&
												item.getFieldValue()!=null&&
												!item.getFieldValue().trim().equals(""))
										{
											fileDesc=item.getFieldValue().trim();
										}
										
									}
								}
								
								if(fileName!=null&&sid>0)
								{
									isError=((sid==1||sid==3)&&!isImage);
									
									int lmt=0;
									if(sid==1)
										lmt=Section1Limit;
									else if(sid==2)
										lmt=Section2Limit;
									else if(sid==3)
										lmt=Section3Limit;
									else
										isError=true;
									if(!isError)
										isError=(this.dao.countByIds(id, sid)>=lmt);
									if(!isError)
									{
										fid=this.dao.saveFileRecord(id, sid, ownerid, isModel?1:2, fileName,fileURL,fileDesc);
										if(fid>0)
										{
											//response.getWriter().print(String.format("<html><head><script type='text/javascript'>top.hFileDone.onFileUploaded(%d,'%s','%s',%d,1);</script></head><body></body></html>",fid,fileURL,fileDesc,sid));
											response.getWriter().print(String.format("{\"id\":%d,\"url\":\"%s\",\"desc\":\"%s\",\"sid\":%d,\"ismod\":%d}",fid,fileURL,fileDesc,sid,1));
											return;
										}
										
									}
								}
								//if we reach this point and fileName is not null then something
								//goes wrong, remove the file
								if(fileName!=null)
								{
									File f=new File(fileName);
									f.delete();
								}
								//response.getWriter().print(String.format("<html><head><script type='text/javascript'>top.hFileDone.onFileUploaded(%d,'%s','%s',%d,1);</script></head><body></body></html>",-1,"","",sid));
								response.getWriter().print(String.format("{\"id\":-1,\"url\":\"\",\"desc\":\"\",\"sid\":%d,\"ismod\":%d}",sid));
								return;
							}
						}
					}
				}
			}
		}
	}

}
