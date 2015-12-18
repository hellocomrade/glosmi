package us.glos.mi.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.glc.ILiteralProvider;
import org.glc.utils.ServerUtilities;
import org.glc.domain.Orgnization;

import us.glos.mi.domain.Contact;
import us.glos.mi.domain.AppInfo;
import us.glos.mi.domain.ModelInfo;
import us.glos.mi.dao.IAgencyDAO;
import us.glos.mi.db.SqlHelper;

/**
 * Servlet implementation class OrgDisplayServlet
 */
public class OrgDisplayServlet extends HttpServlet {
	private static String BasePath=null;
	static final String DAO_NAME="DAO";
	private IAgencyDAO dao=null;
    /**
	 * 
	 */
	private static final long serialVersionUID = 7439609591478037123L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public OrgDisplayServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String cln1=this.getInitParameter(DAO_NAME);
		if(cln1==null||cln1.equals(""))
			throw new ServletException("DAO Parameter doesn't exist!");
		try
		{
			
			
			Class<IAgencyDAO> iclass1=(Class<IAgencyDAO>)Class.forName(cln1);
			this.dao=iclass1.getConstructor().newInstance(new Object[0]);
			
			
			
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
		if(BasePath==null)
			BasePath=request.getContextPath()+request.getServletPath();
		String action="error";
		request.setAttribute(ILiteralProvider.class.getName(), ServerUtilities.GetLiteralProvider());
		String url=request.getRequestURI();
		if(BasePath.length()<url.length()&&url.charAt(BasePath.length())=='/')
		{
			Orgnization org=null;
			try
			{
				int id=Integer.parseInt(url.substring(BasePath.length()+1));
				if(id>0)
					org=this.dao.getAgencyById(id);
				if(org!=null)
				{
					ArrayList<Contact> cnts=SqlHelper.getContactsByOrgId(id);
					ArrayList<ModelInfo> models=SqlHelper.getModelsByOrgId(id);
					ArrayList<AppInfo> apps=SqlHelper.getAppsByOrdId(id);
					action="result";
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, Orgnization.class.getName(), org);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "ORG_PEOPLE", cnts);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "ORG_MODEL", models);
					ServerUtilities.SetKeyValueOnAction(request,this.getClass().getName(), action, "ORG_APP", apps);
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
