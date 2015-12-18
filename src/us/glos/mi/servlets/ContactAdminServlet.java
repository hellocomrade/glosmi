package us.glos.mi.servlets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.Hashtable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;

import us.glos.mi.dao.IUserDAO;
import us.glos.mi.domain.ContactParam;
import us.glos.mi.domain.Contact;

/**
 * Servlet implementation class ContactAdminServlet
 */
public class ContactAdminServlet extends HttpServlet {
	
	static final String DAO_NAME="DAO";
	static final String TIMEZONE_NAME="TIMEZONE";
	static TimeZone tZone;
	private IUserDAO dao=null;
    /**
	 * 
	 */
	private static final long serialVersionUID = 9001564522016550833L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ContactAdminServlet() {
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
			Class<IUserDAO> iclass=(Class<IUserDAO>)Class.forName(cln);
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Hashtable<String,ArrayList<Contact>> contactBag=null;
		String id=null;
		HttpSession session=request.getSession(false);
		if(session!=null&&null!=session.getAttribute(User.getClassName()))
		{
			User usr=null;
			if(session.getAttribute(User.getClassName()) instanceof User)
			    usr=(User)session.getAttribute(User.getClassName());
			if(usr!=null&&null!=request.getAttribute(ContactParam.getClassName())&&null!=request.getAttribute(Contact.getClassName()))
			{
				ContactParam param=null;
				Contact contact=null;
				if(request.getAttribute(ContactParam.getClassName()) instanceof ContactParam)
					param=(ContactParam)request.getAttribute(ContactParam.getClassName());
				if(request.getAttribute(Contact.getClassName()) instanceof Contact)
					contact=(Contact)request.getAttribute(Contact.getClassName());
				if(param!=null&&contact!=null&&param.getTimestamp()>0)
				{
					if(param.isAjaxCall())
					{
						
						if(session.getAttribute(this.getClass().getName())==null)
						{
							contactBag=new Hashtable<String,ArrayList<Contact>>();
							session.setAttribute(this.getClass().getName(), contactBag);
						}
						if(param.isNew())
						{
							/*if(true==this.dao.addUser(contact))
							{
								response.getWriter().print(contact.getId());
								return;
							}*/
							id=String.format("%d-%d",usr.getId(),param.getTimestamp());
							if(contactBag.containsKey(id))
							{
								contactBag.get(id).add(contact);
								response.getWriter().print(contactBag.get(id).size()-1);
								return;
							}
						}
					}
				}
				
			}
		}
	}

}
