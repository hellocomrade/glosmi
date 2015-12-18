package us.glos.mi.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;
import org.glc.utils.Validation;

import us.glos.mi.UserPrivilegeMask;
import us.glos.mi.db.SqlHelper;
import us.glos.mi.domain.UserAdminParam;
import us.glos.mi.domain.Contact;
import us.glos.mi.domain.ContactWrapper;

/**
 * Servlet implementation class ContactSearchServlet
 */
public class ContactSearchServlet extends HttpServlet {
	
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 4706263660621447167L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ContactSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			if(usr!=null&&UserPrivilegeMask.IsAdmin(usr.getPrivilegeLevel()))
			{
				UserAdminParam cp=new UserAdminParam();
				if(Validation.basicValidation(request, cp.getUserParamName())
						&&Validation.basicValidation(request, cp.getFirstNameParamName())
						&&Validation.basicValidation(request, cp.getLastNameParamName()))
				{
					if(request.getParameter(cp.getUserParamName()).toLowerCase().equals("n/a")
					  ||Validation.isValidEmail(request.getParameter(cp.getUserParamName())))
					{
						ArrayList<Contact> cnts=SqlHelper.getContactSearch(request.getParameter(cp.getFirstNameParamName()), 
													request.getParameter(cp.getLastNameParamName()), 
													request.getParameter(cp.getUserParamName()));
						if(cnts!=null&&!cnts.isEmpty())
						{
							ContactWrapper cw=null;
							StringBuilder sb=new StringBuilder();
							sb.append("[");
							for(Contact cnt:cnts)
							{
								cw=new ContactWrapper(cnt);
								sb.append(cw.ToJSON());
								sb.append(",");
							}
							if(sb.charAt(sb.length()-1)==',')
								sb.deleteCharAt(sb.length()-1);
							sb.append("]");
							response.getWriter().print(sb.toString());
							return;
						}
						else
							response.getWriter().print("0");
					}
					else
						response.getWriter().print("-2");
				}
				else
					response.getWriter().print("-1");
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
