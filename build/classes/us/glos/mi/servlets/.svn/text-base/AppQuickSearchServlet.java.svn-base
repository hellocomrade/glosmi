package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glc.utils.Validation;

import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.AppInfoWrapper;
import us.glos.mi.domain.ModelInfoWrapper;
import us.glos.mi.dao.IAppDAO;
/**
 * Servlet implementation class AppQuickSearchServlet
 */
public class AppQuickSearchServlet extends HttpServlet {
	static final String DAO_NAME="DAO";
	IAppDAO dao=null;
    /**
	 * 
	 */
	private static final long serialVersionUID = -6397114717814634749L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AppQuickSearchServlet() {
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
			Class<IAppDAO> iclass=(Class<IAppDAO>)Class.forName(cln);
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(Validation.basicValidation(request, "id"))
		{
			try
			{
				int id=Integer.parseInt(request.getParameter("id"));
				if(id>0)
				{
					ArrayList<AppInfo> apps=this.dao.getAppInfoByUID(id,false);
					if(apps!=null&&!apps.isEmpty())
					{
						StringBuilder sb=new StringBuilder();
						AppInfoWrapper appwrap=null;
						sb.append("[");
						for(AppInfo app:apps)
						{
							appwrap=new AppInfoWrapper(app);
							sb.append(appwrap.ToJSON());
							sb.append(",");
						}
						sb.deleteCharAt(sb.length()-1);
						sb.append("]");
						response.getWriter().print(sb.toString());
						return;
					}
				}
			}
			catch(NumberFormatException nfe)
			{
				
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
