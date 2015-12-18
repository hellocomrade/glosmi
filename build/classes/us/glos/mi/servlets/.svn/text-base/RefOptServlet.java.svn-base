package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.dao.MINameValuePairDAO;
import us.glos.mi.domain.ReferenceParam;
import us.glos.mi.domain.NameValuePair;

/**
 * Servlet implementation class RefOptServlet
 */
public class RefOptServlet extends HttpServlet {
	
	final String CATEGORY_DAO_NAME="CATEGORY_DAO";
	final String THEME_DAO_NAME="THEME_DAO";
	private MINameValuePairDAO catDao=null;
    private MINameValuePairDAO theDao=null;
    /**
	 * 
	 */
	private static final long serialVersionUID = -7704998163182832830L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public RefOptServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String cln=this.getInitParameter(CATEGORY_DAO_NAME);
		if(cln==null||cln.equals(""))
			throw new ServletException("CATEGORY_DAO Parameter doesn't exist!");
		String cln1=this.getInitParameter(THEME_DAO_NAME);
		if(cln1==null||cln1.equals(""))
			throw new ServletException("THEME_DAO Parameter doesn't exist!");
		try
		{
			Class<MINameValuePairDAO> iclass=(Class<MINameValuePairDAO>)Class.forName(cln);
			this.catDao=iclass.getConstructor().newInstance(new Object[0]);
						
			Class<MINameValuePairDAO> iclass1=(Class<MINameValuePairDAO>)Class.forName(cln1);
			this.theDao=iclass1.getConstructor().newInstance(new Object[0]);
			
			
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
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel())&&UserPrivilegeMask.IsModeler(usr.getPrivilegeLevel()))
			{
				ReferenceParam refparam=null;
				if(request.getAttribute(ReferenceParam.getClassName()) instanceof ReferenceParam)
				    refparam=(ReferenceParam)request.getAttribute(ReferenceParam.getClassName());
				if(refparam!=null)
				{
					NameValuePair nv=null;
					if(refparam.getCategoryItem()!=null)
					{
						nv=new NameValuePair();
						nv.name=refparam.getCategoryItem();
						if(this.catDao.addNameValuePair(nv))
						{
							response.getWriter().print(0);
							return;
						}
						else
						{
							response.getWriter().print(1);
							return;
						}
					}
					else if(refparam.getThemeItem()!=null)
					{
						nv=new NameValuePair();
						nv.name=refparam.getThemeItem();
						if(this.theDao.addNameValuePair(nv))
						{
							response.getWriter().print(0);
							return;
						}
						else
						{
							response.getWriter().print(1);
							return;
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
