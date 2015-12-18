package us.glos.mi;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.glc.ISecurity;
import org.glc.domain.User;
import org.glc.domain.ErrorMessage;
import org.glc.xmlconfig.ConfigManager;

public class MIUserValidator implements ISecurity{

	private static final String ERROR_PAGE_NAME="ERROR_PAGE";
	private static String ErrorPage;
	static
	{
		ErrorPage=ConfigManager.getAppSetting(ERROR_PAGE_NAME);
	}
	//public final static String USER_PARAM="user";
	public MIUserValidator()
	{
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean IsAuthenticated(ServletRequest request,
			ServletResponse response) {
		// TODO Auto-generated method stub
		HttpServletRequest hrequest=(HttpServletRequest)request;
		String path=hrequest.getServletPath();
		
		if(path!=null&&path.length()>=5)
		{
			String subpath=path.substring(1, 5);
			
			if(subpath.equals("pub/")||subpath.equals("att/")||path.equals("/index.jsp"))
				return true;
			HttpSession sessions=hrequest.getSession(false);
			if(sessions!=null)
			{
				User usr=(User)sessions.getAttribute(User.getClassName());
				if(usr!=null)
				{
					if(subpath.equals("adm/"))
					{
						if((usr.getPrivilegeLevel()&UserPrivilegeMask.ADMIN_MASK)>0)
							return true;
					}
					else if(subpath.equals("mod/"))
					{
						if((usr.getPrivilegeLevel()&UserPrivilegeMask.MODELER_MASK)>0)
							return true;
					}
					else if(subpath.equals("app/"))
					{
						if((usr.getPrivilegeLevel()&UserPrivilegeMask.APP_MASK)>0)
							return true;
					}
				}
			}
			
		}
		if(ErrorPage!=null)
		{
			ErrorMessage err=new ErrorMessage();
			err.setAuthorized(false);
			err.setRedirectURL(String.format("%s/%s", hrequest.getContextPath(),ErrorPage));//(ConfigManager.getActionForward(us.glos.mi.servlets.UserLoginServlet.class.getName(), "error"));
		
			request.setAttribute(ErrorMessage.getClassName(), err);
		}
		return false;
	}
	

}
