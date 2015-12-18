package us.glos.mi.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.glc.domain.User;
import org.glc.utils.Validation;
import org.glc.xmlconfig.ConfigManager;

import us.glos.mi.domain.UserAdminParam;
import us.glos.mi.util.DocNotification;
import us.glos.mi.UserPrivilegeMask;
/**
 * Servlet implementation class OrgRequestServlet
 */
public class OrgRequestServlet extends HttpServlet {
	private static final String MAIL_RECEIVER_NAME="SERVICE_REQUEST_RECEIVER";
	static final String MAIL_TITLE_NAME="ORG_ADD_EMAIL_TITLE";
	static String[] mailReceivers=null;
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 8535313254489771019L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public OrgRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		String cln=ConfigManager.getAppSetting(MAIL_RECEIVER_NAME);
		if(cln!=null)
			mailReceivers=cln.split(",");
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
			if(usr!=null)
			{
				if(UserPrivilegeMask.IsModeler(usr.getId())||UserPrivilegeMask.IsApplication(usr.getId()))
				{
					UserAdminParam param=new UserAdminParam();
					if(Validation.basicValidation(request, param.getOrganizationNameParamName())
							&&Validation.basicValidation(request, param.getOrgnizationIdParamName())
							&&Validation.basicValidation(request, param.getOrganizationDescriptionParamName()))
					{
						String oname=request.getParameter(param.getOrganizationNameParamName()).trim();
						String oid=request.getParameter(param.getOrgnizationIdParamName()).trim();
						String odesc=request.getParameter(param.getOrganizationDescriptionParamName()).trim();
						String title=ConfigManager.getAppSetting(MAIL_TITLE_NAME);
						if(title==null)
							title="Notification";
						if(oname.length()<=200&&oid.length()<=200&&odesc.length()<=1000)
						{
							Calendar cal=Calendar.getInstance();
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String content=String.format("%s %s (%s) submitted a request for adding new organization at %s Eastern Time.\r\nOrganization Name:\r\n%s\r\nOrganization Abbreviation:\r\n%s\r\nOrganization Description:\r\n%s\r\n", 
									usr.getFirstName(),
									usr.getLastName(),
									usr.getEmail(),
									sdf.format(cal.getTime()),
									oname,oid,odesc
									);


							DocNotification notify=new DocNotification(title,null,mailReceivers,content);
							notify.start();
							response.getWriter().print("0");
							return;
						}
					}
				}
			}
		}
		response.getWriter().print("-1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
