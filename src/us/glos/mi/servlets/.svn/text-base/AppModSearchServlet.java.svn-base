package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.Logger;
import org.glc.domain.User;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;
import org.glc.xmlconfig.LogLevel;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.ISearchDAO;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.domain.ModelInfoWrapper;
import us.glos.mi.domain.SearchResultParam;

/**
 * Servlet implementation class AppModSearchServlet
 */
public class AppModSearchServlet extends HttpServlet {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6070051372982988773L;

	private static final String MAX_SEARCH_STRING_LENGTH="MAX_SEARCH_STRING_LENGTH";
    private static int MAX_SEARCH_KEY_LEN=255;
    static final String DAO_NAME="DAO";
    private ISearchDAO dao=null;
    
    static
    {
    	String temp=ConfigManager.getAppSetting(MAX_SEARCH_STRING_LENGTH);
		if(temp!=null&&!temp.equals(""))
		{
			try
			{
				MAX_SEARCH_KEY_LEN=Integer.parseInt(temp);
			}
			catch(NumberFormatException e)
			{
				MAX_SEARCH_KEY_LEN=255;
				Logger.writeLog(SearchServlet.class.getName()+" MAX_SEARCH_KEY_LEN is not valid integer", LogLevel.INFO);
			}
		}
    }
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AppModSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String cln=this.getInitParameter(DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			Class<ISearchDAO> iclass=(Class<ISearchDAO>)Class.forName(cln);
			this.dao=iclass.getConstructor().newInstance(new Object[0]);
						
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
	private ArrayList<String> processSearchKeys(String rawstr)
	{
		int lastindex=0;
		ArrayList<String> keys=new ArrayList<String>();
		char ch=' ';
		int i=0;
		int len=rawstr.length();
		for(;i<len;++i)
		{
			ch=rawstr.charAt(i);
			switch(ch){
			case ' ':
			case '\t':
			case ',':
			case ';':
			case '.':
			case '\r':
			case '\n':
				if(lastindex<i)
					keys.add(rawstr.substring(lastindex, i));
				lastindex=i+1;
				break;
			default:
				continue;
			}
		}
		if(lastindex<i)
			keys.add(rawstr.substring(lastindex, i));
		return keys;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<ModelInfo> mods=null;
		HttpSession session=request.getSession(false);
		String action=null;
		boolean bPublic=false;
		boolean orOnly=false;
		
		if(Validation.basicValidation(request, "p"))
			bPublic=true;
		if(Validation.basicValidation(request, "o"))
			orOnly=true;
		
		if(!bPublic&&session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&(UserPrivilegeMask.IsApplication(usr.getPrivilegeLevel())||UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())))
			{
				if(Validation.basicValidation(request,SearchResultParam.getSearchWordsParam()))
				{
					String rawstr=request.getParameter(SearchResultParam.getSearchWordsParam()).trim();
					int len=rawstr.length();
					if(len<=MAX_SEARCH_KEY_LEN)
					{
						ArrayList<String> keys=processSearchKeys(rawstr);
						if(!keys.isEmpty())
						{
							mods=this.dao.searchModelsOR(keys);
							if(mods!=null&&!mods.isEmpty())
							{
								ModelInfoWrapper modwrap=null;
								StringBuilder sb=new StringBuilder();
								sb.append("[");
								for(ModelInfo mod:mods)
								{
									modwrap=new ModelInfoWrapper(mod);
									sb.append(modwrap.ToJSON());
									sb.append(",");
								}
								sb.deleteCharAt(sb.length()-1);
								sb.append("]");
								response.getWriter().print(sb.toString());
								return;
							}
						}
					}
				}
				response.getWriter().print("0");
				return;
			}
		}
		else if(bPublic)//public access to get related models on each individual model page
		{
			if(Validation.basicValidation(request,SearchResultParam.getSearchWordsParam()))
			{
				String rawstr=request.getParameter(SearchResultParam.getSearchWordsParam()).trim();
				int len=rawstr.length();
				if(len<=MAX_SEARCH_KEY_LEN)
				{
					
					ArrayList<String> keys=processSearchKeys(rawstr);
					if(!keys.isEmpty())
					{
						if(orOnly)
							mods=this.dao.searchModelsOR(keys);
						else
							mods=this.dao.searchModelsFuzzy(keys, 5, 0, null);
						
					}
				}
			}
			else if(Validation.basicValidation(request,"cid"))
			{
				try
				{
					int id=Integer.parseInt(request.getParameter("cid"));
					if(id>0)
						mods=this.dao.searchModelsByContact(id);
				}
				catch(NumberFormatException ne)
				{
					mods=null;
				}
			}
			if(mods!=null&&!mods.isEmpty())
			{
				ModelInfoWrapper modwrap=null;
				StringBuilder sb=new StringBuilder();
				sb.append("[");
				for(ModelInfo mod:mods)
				{
					modwrap=new ModelInfoWrapper(mod);
					sb.append(modwrap.ToJSON());
					sb.append(",");
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append("]");
				response.getWriter().print(sb.toString());
				return;
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
